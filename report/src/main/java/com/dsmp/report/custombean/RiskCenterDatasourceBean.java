package com.dsmp.report.custombean;

import com.dsmp.report.common.vo.LogHighRiskIpSVo;
import com.dsmp.report.common.vo.LogStatisticsVo;
import com.dsmp.report.transfer.RiskTransfer;
import com.dsmp.report.web.request.RiskReportParam;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author byliu
 **/
@Component("RiskCenterDatasourceBean")
public class RiskCenterDatasourceBean {

    private static final SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
    @Autowired
    RiskTransfer riskTransfer;

    public List<LogStatisticsVo> loadRiskLogData(String dsName, String datasetName, Map<String, Object> parameters) throws ParseException {
        List<LogStatisticsVo> list = new ArrayList();

        Dealparam dealparam = new Dealparam(parameters).invoke();
        try {
            //list = riskTransfer.getStatisticsOfSourceAndOpType(new RiskReportParam(dealparam.getStartTime(), dealparam.getEndTime(), dealparam.getUserNames(), null));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return list;
        }
    }

    public List<LogHighRiskIpSVo> loadHighRiskIPData(String dsName, String datasetName, Map<String, Object> parameters) throws ParseException {
        List<LogHighRiskIpSVo> list = new ArrayList();
        Dealparam dealparam = new Dealparam(parameters).invoke();
        try {
            list = riskTransfer.getAnalysisOfHighRiskIp(new RiskReportParam(dealparam.getStartTime(), dealparam.getEndTime(), dealparam.getUserNames(), 10));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return list;
        }
    }

    private class Dealparam {
        private final Map<String, Object> parameters;
        private Date startTime;
        private Date endTime;
        private List<String> userNames;

        public Dealparam(Map<String, Object> parameters) {
            this.parameters = parameters;
        }

        public Date getStartTime() {
            return startTime;
        }

        public Date getEndTime() {
            return endTime;
        }

        public List<String> getUserNames() {
            return userNames;
        }

        public Dealparam invoke() throws ParseException {
            Optional<Object> startTimeStr = Optional.ofNullable(parameters.get("startTime"));
            Optional<Object> endTimeStr = Optional.ofNullable(parameters.get("endTime"));
            Optional<Object> userNamesObj = Optional.ofNullable(parameters.get("userNames"));
            startTime = null;
            endTime = null;
            userNames = new ArrayList<String>();
            if (startTimeStr.isPresent())
                startTime = dft.parse(startTimeStr.get() + "");
            if (endTimeStr.isPresent())
                endTime = dft.parse(endTimeStr.get() + "");
            if (userNamesObj.isPresent())
                userNames = (List<String>) userNamesObj.get();
            return this;
        }
    }
}
