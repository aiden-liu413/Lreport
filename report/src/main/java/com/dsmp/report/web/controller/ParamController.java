package com.dsmp.report.web.controller;


import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.annotation.NacosConfigListener;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.api.exception.NacosException;
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

    @NacosInjected
    private ConfigService configService;

    private Integer useLocalCache;
    @NacosValue(value = "${fileCacheDays:123}", autoRefreshed = true)
    @NacosConfigListener(dataId = "fileCacheDays",groupId = "report", timeout = 50)
    public void setUseLocalCache(String useLocalCache) {
        this.useLocalCache = Integer.parseInt(useLocalCache);
    }

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

    @ApiOperation("文件缓存时间查询接口-nacos")
    @GetMapping("/fileCacheDays/nacos")
    public MsgResult getFileCacheDaysOfNacos() {
        return MsgResult.success(useLocalCache);
    }

    @ApiOperation("文件缓存时间修改接口-nacos")
    @PutMapping("/fileCacheDays/nacos")
    public MsgResult updateFileCacheDaysOfNacos(@RequestParam int fileCacheDays) {
        if (fileCacheDays >= 2000) {
            throw new BusinessException("超过最大缓存时间");
        }
        boolean isPublishOk = true;
        try {
           isPublishOk = configService.publishConfig("fileCacheDays", "report", ""+fileCacheDays);
        } catch (NacosException e) {
            isPublishOk =false;
        }
        paramService.updateFileCacheParam(fileCacheDays);
        return MsgResult.success(isPublishOk);
    }
}
