package com.dsmp.report.web.controller;

import com.kxingyi.common.exception.BusinessException;
import com.kxingyi.common.web.response.MsgResult;
import com.dsmp.report.web.service.IReportParamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author byliu
 **/
@Api("报表参数api")
@RestController
@RequestMapping("/api/param")
public class ParamController extends BaseApiController {
    @Autowired
    IReportParamService paramService;

    @ApiOperation("文件缓存时间修改接口")
    @PutMapping("/fileCacheDays")
    public MsgResult updateFileCacheDays(@RequestParam int fileCacheDays) {
        if (fileCacheDays >= 2000) {
            throw new BusinessException("超过最大缓存时间");
        }
        paramService.updateFileCacheParam(fileCacheDays);
        return MsgResult.success();
    }


    @ApiOperation("文件缓存时间查询接口")
    @GetMapping("/fileCacheDays")
    public MsgResult getFileCacheDays() {
        int fileCacheParam = paramService.getFileCacheParam();
        return MsgResult.success(fileCacheParam);
    }


}
