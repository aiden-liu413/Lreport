package com.dsmp.report.web.controller;

import com.kxingyi.common.exception.BusinessException;
import com.kxingyi.common.util.minio.MinIoComponent;
import com.kxingyi.common.web.response.MsgResult;
import com.kxingyi.common.web.response.UnifyApiCode;
import com.dsmp.report.common.bo.ReportTaskBo;
import com.dsmp.report.common.enums.CycleEnum;
import com.dsmp.report.common.enums.EntityStatus;
import com.dsmp.report.common.vo.ReportTaskVo;
import com.dsmp.report.task.TaskManager;
import com.dsmp.report.web.request.ReportTaskPagination;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author byliu
 **/
@Api("报表任务api")
@RestController
@RequestMapping("/api/task")
public class TaskController extends BaseApiController {
    @Autowired
    TaskManager taskManager;

    @ApiOperation("报表任务分页接口")
    @GetMapping("/pages")
    public MsgResult pages(@Valid ReportTaskPagination pagination) {
        try {
            Page<ReportTaskVo> pages = taskManager.pages(pagination);
            return MsgResult.success(pages);
        } catch (Exception e) {
            logger.error("任务分页接口调用失败", e);
            return MsgResult.message(UnifyApiCode.INTERNAL_ERROR.getCode(), "报表任务分页查询失败");
        }
    }

    @ApiOperation("报表任务新增接口")
    @PostMapping()
    public MsgResult addTask(@RequestBody @Valid ReportTaskBo taskBo) {
        taskManager.addTask(taskBo);
        return MsgResult.success();
    }

    @ApiOperation("报表任务修改接口")
    @PutMapping()
    public MsgResult updateTask(@RequestBody @Valid ReportTaskBo task) {
        taskManager.updateTask(task);
        return MsgResult.success();
    }

    @ApiOperation("报表任务删除接口（单个）")
    @DeleteMapping("/{id}")
    public MsgResult delTask(@RequestParam String id) {
        taskManager.removeTask(id);
        return MsgResult.success();
    }

    @ApiOperation("报表任务删除接口（批量）")
    @DeleteMapping()
    public MsgResult deltaskBtach(@RequestBody List<String> taskIds){
        taskManager.removeTask(taskIds);
        return MsgResult.success();
    }

    @ApiOperation("报表任务改变状态接口")
    @PutMapping("/{id}")
    public MsgResult changeStatus(@RequestParam String id) {
        taskManager.changeStatus(id);
        return MsgResult.success();
    }
}
