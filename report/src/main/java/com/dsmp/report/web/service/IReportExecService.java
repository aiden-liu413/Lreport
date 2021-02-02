package com.dsmp.report.web.service;

import com.dsmp.report.common.exception.TaskExecException;
import com.dsmp.report.common.vo.ReportTaskExecVo;
import com.dsmp.report.web.request.ReportTaskExecPagination;
import org.springframework.data.domain.Page;

/**
 * @author byliu
 */
public interface IReportExecService {
    /**
     * 分页查询方法
     * @param pagination 分页对象
     * @return
     */
    Page<ReportTaskExecVo> pages(ReportTaskExecPagination pagination);

    void logTaskExecFailDetail(TaskExecException execException);

    void logTaskExecSuccessDetail(String taskId, String TemplteId, String spentTime);
}
