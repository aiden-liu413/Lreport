package com.dsmp.report.web.controller;

import com.dsmp.report.aop.LogReportTaskDetail;
import com.dsmp.report.common.vo.ReportTaskExecVo;
import com.dsmp.report.web.request.ReportTaskExecPagination;
import com.dsmp.report.web.service.IReportExecService;
import com.kxingyi.common.web.response.MsgResult;
import com.kxingyi.common.web.response.UnifyApiCode;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author byliu
 */
@Api("报表任务执行情况api")
@RestController
@RequestMapping("/api/task/exec")
public class TaskExecController extends BaseApiController {
    @Autowired
    IReportExecService execService;
    @GetMapping("/pages")
    public MsgResult pages(ReportTaskExecPagination pagination) {
        try {
            Page<ReportTaskExecVo> pages = execService.pages(pagination);
            return MsgResult.success(pages);
        } catch (Exception e) {
            logger.error("任务执行详情接口调用失败", e);
            return MsgResult.message(UnifyApiCode.INTERNAL_ERROR.getCode(), "任务执行详情查询失败");
        }
    }
}
