package com.dsmp.report.web.service.impl;

import com.dsmp.report.common.domain.ReportTaskExec;
import com.dsmp.report.common.domain.ReportTaskExecR;
import com.dsmp.report.common.exception.TaskExecException;
import com.dsmp.report.common.vo.ReportTaskExecVo;
import com.dsmp.report.web.repository.ReportTaskExecRRepository;
import com.dsmp.report.web.repository.ReportTaskExecRepository;
import com.dsmp.report.web.request.ReportTaskExecPagination;
import com.dsmp.report.web.service.IReportExecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class ReportTakExecService implements IReportExecService {

    @Autowired
    ReportTaskExecRepository reportTaskExecRepository;
    @Autowired
    ReportTaskExecRRepository reportTaskExecRRepository;
    /**
     * 分页查询方法
     *
     * @param pagination 分页对象
     * @return
     */
    @Override
    public Page<ReportTaskExecVo> pages(ReportTaskExecPagination pagination) {
        PageRequest pageRequest = PageRequest.of(pagination.getPage(), pagination.getSize(), Sort.Direction.DESC, "execTime");
        return reportTaskExecRepository.listWithPage(pagination.getContent(), pagination.getContent(),
                pagination.getStartTime(), pagination.getEndTime(), pagination.getTaskId(), pagination.getTemplteId(),
                pageRequest);
    }

    @Override
    public void logTaskExecFailDetail(TaskExecException execException) {
        ReportTaskExec exec = new ReportTaskExec(execException.getMessage(), null);
        String execId = reportTaskExecRepository.save(exec).getId();
        reportTaskExecRRepository.save(new ReportTaskExecR(execException.getTaskId(), execException.getTemplteId(), execId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void logTaskExecSuccessDetail(String taskId, String TemplteId, String spentTime) {
        ReportTaskExec exec = new ReportTaskExec();
        String execId = reportTaskExecRepository.save(exec).getId();
        reportTaskExecRRepository.save(new ReportTaskExecR(taskId, TemplteId, execId));

    }
}
