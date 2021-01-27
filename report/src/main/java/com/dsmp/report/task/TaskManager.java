package com.dsmp.report.task;

import com.kxingyi.common.exception.BusinessException;
import com.kxingyi.common.util.JsonUtils;
import com.dsmp.report.common.bo.ReportTaskBo;
import com.dsmp.report.common.domain.ReportTask;
import com.dsmp.report.common.domain.ReportTemplte;
import com.dsmp.report.common.enums.EntityStatus;
import com.dsmp.report.common.exception.TaskRepeatException;
import com.dsmp.report.common.vo.ReportTaskVo;
import com.dsmp.report.web.repository.ReportRepository;
import com.dsmp.report.web.repository.ReportTaskRepository;
import com.dsmp.report.web.request.ReportTaskPagination;
import com.google.common.base.Objects;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author byliu
 **/
@Component
public class TaskManager {
    @Autowired
    ReportTaskRepository reportTaskRepository;

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    CronTaskRegistrar cronTaskRegistrar;

    public Page<ReportTaskVo> pages(ReportTaskPagination pagination) {

        PageRequest pageRequest = PageRequest.of(pagination.getPage(), pagination.getSize(), Sort.Direction.DESC, "createTime");
        Page<ReportTaskVo> reportTaskVos = reportTaskRepository.listWithPage(pagination.getContent(), pagination.getStatus(), pagination.getStartTime(), pagination.getEndTime(), pageRequest);
        reportTaskVos.getContent().forEach(reportTaskVo -> {
            List<ReportTemplte> templteList = reportRepository.findByIdIn(Arrays.asList(reportTaskVo.getReportIds().split(",")));
            reportTaskVo.setReportTempltes(templteList);
        });
        return reportTaskVos;
    }

    /**
     * @Description:新建任务
     * @Author: byliu
     * @Date: 2020/12/24 15:31
     * @param taskBo: 任务BO对象
     * @return: boolean
     **/
    @Transactional
    public boolean addTask(ReportTaskBo taskBo) {
        ReportTask task = toDo(taskBo);
        checkName(taskBo);
        ReportTask save = reportTaskRepository.save(task);
        if (save.getStatus().equals(EntityStatus.NORMAL)) {
            reportTaskRepository.findAll().forEach( t -> {
              if(t.equals(save) && !t.getId().equals(save.getId())){
                  throw new TaskRepeatException("已存在相同任务,新建失败");
              }
            });
            SchedulingRunnable job = new SchedulingRunnable(save);
            cronTaskRegistrar.addCronTask(job, save.getCycleType().getCronExpression(), null);
        }
        return true;
    }

    private void checkName(ReportTaskBo bo) {
        List<ReportTask> byName = reportTaskRepository.findByName(bo.getName());
        if (byName.size() > 0 && !byName.get(0).getId().equals(bo.getId())) {
            throw new BusinessException("已有同名的报表任务,请更改名字后再进行操做");
        }
    }
    /**
     * @Description:删除任务
     * @Author: byliu
     * @Date: 2020/12/24 15:26
     * @param taskId: 任务id
     * @return: boolean
     **/
    public boolean removeTask(String taskId) {
        Optional<ReportTask> byId = reportTaskRepository.findById(taskId);
        ReportTask task = byId.orElse(null);
        if (null != task) {
            SchedulingRunnable job = new SchedulingRunnable(task);
            cronTaskRegistrar.removeCronTask(job);
            reportTaskRepository.deleteById(taskId);
        }
        return true;
    }

    public boolean removeTask(List<String> taskIds) {
        taskIds.forEach( taskId -> {
            removeTask(taskId);
        });
        return true;
    }
    /**
     * @Description:编辑任务
     * @Author: byliu
     * @Date: 2020/12/24 15:25
     * @param bo: 任务BO对象
     * @return: boolean
     **/
    @Transactional
    public boolean updateTask(ReportTaskBo bo) {
        Optional<ReportTask> oldTaskOpt = reportTaskRepository.findById(bo.getId());
        ReportTask task = null;
        if (oldTaskOpt.isPresent()) {
            // 删除之前的任务
            ReportTask oldTask = oldTaskOpt.get();
            task = toDo(bo, oldTask);
            SchedulingRunnable oldJob = new SchedulingRunnable(oldTask);
            cronTaskRegistrar.removeCronTask(oldJob, false);
            // 创建新任务
            SchedulingRunnable newJob = new SchedulingRunnable(task);
            cronTaskRegistrar.addCronTask(newJob, task.getCycleType().getCronExpression(), null);
        }
        reportTaskRepository.save(task);
        return true;
    }
    /**
     * @Description:启停任务
     * @Author: byliu
     * @Date: 2020/12/24 15:25
     * @param taskId: 任务id
     * @return: boolean
     **/
    @Transactional
    public boolean changeStatus(String taskId) {
        Optional<ReportTask> existTaskOpt = reportTaskRepository.findById(taskId);
        if (existTaskOpt.isPresent()) {
            ReportTask existTask = existTaskOpt.get();
            if (existTask.getStatus().equals(EntityStatus.STOP) || existTask.getStatus().equals(EntityStatus.CLOSED)) {
                SchedulingRunnable job = new SchedulingRunnable(existTask);
                cronTaskRegistrar.addCronTask(job, existTask.getCycleType().getCronExpression(), null);
                existTask.setStatus(EntityStatus.NORMAL);
            } else {
                SchedulingRunnable job = new SchedulingRunnable(existTask);
                cronTaskRegistrar.removeCronTask(job);
                existTask.setStatus(EntityStatus.STOP);
            }
            reportTaskRepository.save(existTask);
        }
        return true;
    }

    ReportTask toDo(ReportTaskBo bo){
        ReportTask task = new ReportTask();
        BeanUtils.copyProperties(bo, task);
        task.setParams(JsonUtils.toJson(bo.getParams()));
        return task;
    }

    ReportTask toDo(ReportTaskBo bo, ReportTask oldTask){
        ReportTask doo = null;
        try {
            doo = oldTask.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        BeanUtils.copyProperties(bo, doo);
        doo.setParams(JsonUtils.toJson(bo.getParams()));
        return doo;
    }
}
