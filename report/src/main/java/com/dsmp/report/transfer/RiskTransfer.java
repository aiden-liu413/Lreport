package com.dsmp.report.transfer;

import com.kxingyi.common.util.feign.FeignClient;
import com.dsmp.report.common.vo.LogHighRiskIpSVo;
import com.dsmp.report.common.vo.LogStatisticsVo;
import com.dsmp.report.config.feign.DataSyncFeignConfig;
import com.dsmp.report.web.request.RiskReportParam;
import feign.Headers;
import feign.RequestLine;

import java.util.List;


@FeignClient(configuration = DataSyncFeignConfig.class)
public interface RiskTransfer {

    @RequestLine(value = "POST /risk/report/log/statistics")
    @Headers("Content-Type: application/json")
    List<LogStatisticsVo> getStatisticsOfSourceAndOpType(RiskReportParam params);


    @RequestLine(value = "POST /risk/report/hrip")
    @Headers("Content-Type: application/json")
    List<LogHighRiskIpSVo> getAnalysisOfHighRiskIp(RiskReportParam params);

}
