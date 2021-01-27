package com.dsmp.report.web.controller;

import com.kxingyi.common.web.response.MsgResult;
import com.kxingyi.common.web.response.UnifyApiCode;
import com.dsmp.report.common.bo.ReportTemplteBo;
import com.dsmp.report.common.domain.ReportTemplte;
import com.dsmp.report.web.request.ReportTempltePagination;
import com.dsmp.report.web.service.IReportTemplteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author byliu
 **/
@Api("报表模板api")
@RestController
@RequestMapping("/api/templte")
public class TemplteController extends BaseApiController {
    @Autowired
    IReportTemplteService templteService;

    @ApiOperation("报表模板分页接口")
    @GetMapping()
    public MsgResult pages(ReportTempltePagination pagination) {
        try {
            Page<ReportTemplte> pages = templteService.pages(pagination);
            return MsgResult.success(pages);
        } catch (Exception e) {
            logger.error("模板分页接口调用失败", e);
            return MsgResult.message(UnifyApiCode.INTERNAL_ERROR.getCode(), "报表模板分页查询失败");
        }
    }

    @ApiOperation("报表模板删除接口")
    @DeleteMapping()
    public MsgResult delete(@RequestBody ArrayList<String> ids) {
        try {
            templteService.delete(ids);
            return MsgResult.success();
        } catch (Exception e) {
            logger.error("模板删除接口调用失败", e);
            return MsgResult.message(UnifyApiCode.INTERNAL_ERROR.getCode(), "模板删除失败");
        }
    }

    @ApiOperation("报表模板导入接口")
    @PostMapping("/upload")
    public MsgResult upload(@RequestParam MultipartFile file, @Valid ReportTemplteBo templtetBo /* MultipartHttpServletRequest req*/) throws IOException {
        /*MultipartFile file = Optional.ofNullable(req.getFile("file")).orElseThrow(() -> new BusinessException("请选择要导入的文件"));
        String boString = Optional.ofNullable(req.getParameter("templtetBo")).orElseThrow(() -> new BusinessException("参数校验失败"));
        ReportTemplteBo bo = JsonUtils.toObject(boString, ReportTemplteBo.class);
        templteService.upload(bo, file);*/
        templteService.upload(templtetBo, file);
        return MsgResult.success();
    }

    @ApiOperation("报表模板新增接口")
    @PostMapping("/add")
    public MsgResult add(@RequestBody @Valid ReportTemplteBo templtetBo) {
        templteService.add(templtetBo);
        return MsgResult.success();
    }

    @ApiOperation("报表模板修改接口")
    @PutMapping()
    public MsgResult updateReportTemplte(@RequestBody @Valid ReportTemplteBo templtetBo) {
        templteService.updateReportTemplte(templtetBo);
        return MsgResult.success();
    }

}
