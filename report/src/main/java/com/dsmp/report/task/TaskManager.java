package com.dsmp.report.task;

import com.bstek.ureport.export.ExportConfigure;
import com.bstek.ureport.export.ExportConfigureImpl;
import com.bstek.ureport.export.ExportManager;
import com.dsmp.report.aop.LogReportTaskDetail;
import com.dsmp.report.common.bo.EmailBo;
import com.dsmp.report.common.bo.ReportTaskBo;
import com.dsmp.report.common.domain.ReportFile;
import com.dsmp.report.common.domain.ReportTask;
import com.dsmp.report.common.domain.ReportTaskParam;
import com.dsmp.report.common.domain.ReportTemplte;
import com.dsmp.report.common.enums.CycleEnum;
import com.dsmp.report.common.enums.EntityStatus;
import com.dsmp.report.common.enums.ReportEnum;
import com.dsmp.report.common.exception.TaskExecException;
import com.dsmp.report.common.exception.TaskRepeatException;
import com.dsmp.report.common.vo.ReportTaskVo;
import com.dsmp.report.utils.ValidatorUtils;
import com.dsmp.report.utils.WebdriveUtils;
import com.dsmp.report.web.repository.ReportFileRepository;
import com.dsmp.report.web.repository.ReportRepository;
import com.dsmp.report.web.repository.ReportTaskRepository;
import com.dsmp.report.web.request.ReportTaskPagination;
import com.dsmp.report.web.service.IReportExecService;
import com.dsmp.report.web.service.IReportParamService;
import com.google.common.collect.Maps;
import com.kxingyi.common.exception.BusinessException;
import com.kxingyi.common.service.BaseService;
import com.kxingyi.common.util.JsonUtils;
import com.kxingyi.common.util.UUIDTool;
import com.kxingyi.common.util.minio.MinIoComponent;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author byliu
 **/
@Component
public class TaskManager extends BaseService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH_mm_ss");
    @Autowired
    ReportTaskRepository reportTaskRepository;

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    CronTaskRegistrar cronTaskRegistrar;

    @Autowired
    IReportExecService execService;

    @Autowired
    ExportManager exportManager;

    @Autowired
    ReportFileRepository fileRepository;

    @Autowired
    ReportTaskRepository taskRepository;

    @Autowired
    IReportParamService paramService;

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

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
     * @param taskBo: 任务BO对象
     **/
    @Transactional
    public boolean addTask(ReportTaskBo taskBo) {
        ReportTask task = toDo(taskBo);
        checkName(taskBo);
        ReportTask save = reportTaskRepository.save(task);
        if (save.getStatus().equals(EntityStatus.NORMAL)) {
            reportTaskRepository.findAll().forEach(t -> {
                if (t.equals(save) && !t.getId().equals(save.getId())) {
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
     * @param taskId: 任务id
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
        taskIds.forEach(taskId -> {
            removeTask(taskId);
        });
        return true;
    }

    /**
     * @param bo: 任务BO对象
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
     * @param taskId: 任务id
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

    @LogReportTaskDetail
    public void exec(ReportTask task) {
        logger.info("定时任务开始执行 - task：{}", task);
        long startTime = System.currentTimeMillis();
        try {
            List<String> reportIds = Arrays.asList(task.getReportIds().split(","));
            reportIds.forEach(reportId -> {

                Optional<ReportTemplte> report = reportRepository.findById(reportId);
                ReportTemplte reportTemplte = report.orElseThrow(() -> new RuntimeException("can not find uReportFile"));
                // 任务参数用于生成报表文件
                Map<String, Object> parameters = StringUtils.isBlank(task.getParams()) ? Maps.newHashMap() : JsonUtils.toMap(task.getParams());
                // 任务参数用于发送邮件
                ReportTaskParam reportTaskParam = JsonUtils.toObject(task.getParams(), ReportTaskParam.class);
                // 文件对象
                ReportFile file = new ReportFile();
                file.setMappingName(UUIDTool.getPrimarykeyId());
                file.setTaskId(task.getId());
                file.setName(task.getName() + "_" + reportTemplte.getName() + "_" + LocalDateTime.now().format(FORMATTER));
                file.setTemplteId(reportId);
                file.setStatus(EntityStatus.NORMAL);
                file.setUserId(task.getUserId());

                // 预览操作 参数:任务id，查询时间区间
                WebdriveUtils.preview(reportTemplte.getName(), task, parameters);
                Map<String, InputStream> inputStreamMap = new HashMap<>();
                Arrays.asList(ReportEnum.values()).forEach(reportEnum -> {
                    if (ReportEnum.ALL.equals(reportEnum)) {
                    } else {
                        ExportConfigure configure = new ExportConfigureImpl("dsmp:" + reportTemplte.getName(),
                                parameters,
                                new ByteArrayOutputStream());
                        InputStream in = null;
                        try {
                            switch (reportEnum) {
                                case PDF:
                                    exportManager.exportPdf(configure);
                                    break;
                                case WORD:
                                    exportManager.exportWord(configure);
                                    break;
                                case EXCEL:
                                    exportManager.exportExcel(configure);
                                    break;
                                default:
                                    break;
                            }

                            in = parse(configure.getOutputStream());
                            inputStreamMap.put(file.getName() + "." + reportEnum.getSuffix(), in);
                            // 上传文件
                            MinIoComponent.putObject(in, new Date(), file.getMappingName(), reportEnum.getSuffix());
                        } catch (Exception e) {
                            throw new TaskExecException(task.getId(), reportId, String.format("报表文件:[%s] 生成失败:[%s]", reportEnum.getSuffix(), e.getMessage()));
                        } finally {
                            IOUtils.closeQuietly(configure.getOutputStream());
                            IOUtils.closeQuietly(in);
                        }
                    }
                });

                // 保存报表文件数据
                fileRepository.save(file);
                // 记录执行情况
                execService.logTaskExecSuccessDetail(task.getId(), reportId, (System.currentTimeMillis() - startTime) + "");
                EmailBo emailParam = paramService.getEmailParam();
                // 如果邮件相关参数正常  执行发送邮件
                if (ValidatorUtils.validateEntity(emailParam).size() == 0) {
                    List<String> recipients = reportTaskParam.getRecipients();
                    // 发送邮件
                    threadPoolTaskExecutor.execute(new EmailRunnable(emailParam, file, recipients.toArray(new String[recipients.size()])));
                }
            });
            // 如果是自定义周期任务，在任务完成后将任务状态改为完成
            if (CycleEnum.CUSTOM.equals(task.getCycleType())) {
                task.setStatus(EntityStatus.CLOSED);
                taskRepository.save(task);
            }
        } catch (Exception ex) {
            logger.error(String.format("定时任务执行异常 - task：%s", task), ex);
            if (ex instanceof TaskExecException) {
                throw ex;
            }
            throw new TaskExecException(task.getId(), null, String.format("任务执行失败:[%s]", ex.getMessage()));
        }
        long times = System.currentTimeMillis() - startTime;
        logger.info("定时任务执行结束 - task：{}，耗时：{} 毫秒", task, times);
    }

    public ByteArrayInputStream parse(final OutputStream out) throws Exception {
        ByteArrayOutputStream baos;
        if (null == out) {
            return null;
        }
        baos = (ByteArrayOutputStream) out;
        final ByteArrayInputStream swapStream = new ByteArrayInputStream(baos.toByteArray());
        return swapStream;
    }

    ReportTask toDo(ReportTaskBo bo) {
        ReportTask task = new ReportTask();
        BeanUtils.copyProperties(bo, task);
        task.setParams(JsonUtils.toJson(bo.getParams()));
        return task;
    }

    ReportTask toDo(ReportTaskBo bo, ReportTask oldTask) {
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
