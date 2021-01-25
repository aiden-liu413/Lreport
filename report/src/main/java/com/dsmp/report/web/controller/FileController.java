package com.dsmp.report.web.controller;

import com.dsmp.common.exception.BusinessException;
import com.dsmp.common.web.response.MsgResult;
import com.dsmp.common.web.response.UnifyApiCode;
import com.dsmp.report.common.vo.ReportFileVo;
import com.dsmp.report.web.request.FileParam;
import com.dsmp.report.web.request.ReportFilePagination;
import com.dsmp.report.web.service.IReportFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author byliu
 **/
@Api("报表文件api")
@RestController
@RequestMapping("/api/file")
public class FileController extends BaseApiController {
    @Autowired
    IReportFileService fileService;

    @ApiOperation("报表文件分页接口")
    @GetMapping()
    public MsgResult pages(ReportFilePagination pagination) {
        try {
            Page<ReportFileVo> pages = fileService.pages(pagination);
            return MsgResult.success(pages);
        } catch (Exception e) {
            logger.error("文件分页接口调用失败", e);
            return MsgResult.message(UnifyApiCode.INTERNAL_ERROR.getCode(), "报表文件分页查询失败");
        }
    }

    @ApiOperation("报表文件删除接口")
    @DeleteMapping()
    public MsgResult delete(@RequestBody FileParam param) {
        try {
            if (null != param.getEndTime() && null != param.getStartTime()) {
                fileService.delete(param.getStartTime(), param.getEndTime());
            } else {
                fileService.delete(param.getIds());
            }
            return MsgResult.success();
        } catch (Exception e) {
            logger.error("文件删除接口调用失败", e);
            return MsgResult.message(UnifyApiCode.INTERNAL_ERROR.getCode(), "文件删除失败");
        }
    }

    @ApiOperation("报表文件下载接口")
    @PostMapping("/download")
    public void download(@RequestBody FileParam param, HttpServletResponse response) throws IOException {
        try {
            if (StringUtils.isBlank(param.getFileId()) || null == param.getFileType()) {
                response.sendError(Integer.parseInt(UnifyApiCode.ILLEGALARGUMENT.getCode()), UnifyApiCode.ILLEGALARGUMENT.getMsg());
            }
            fileService.download(param.getFileId(), param.getFileType(), response);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            response.sendError(Integer.parseInt(UnifyApiCode.INTERNAL_ERROR.getCode()), UnifyApiCode.INTERNAL_ERROR.getMsg());
        } catch (BusinessException e) {
            logger.error(e.getMessage(), e);
            response.sendError(Integer.parseInt(UnifyApiCode.INTERNAL_ERROR.getCode()), e.getMessage());
        }
    }


}
