package com.dsmp.report.web.request;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author byliu
 **/
public class RiskReportParam {

    private String startTime;
    private String endTime;
    private List<String> userNames = new ArrayList<>();
    private Integer topN;

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    private String getSecond(Date date){
        return null ==date ? "" : String.valueOf(date.getTime() / 1000);
    }

    public List<String> getUserNames() {
        return userNames;
    }

    public void setUserNames(List<String> userNames) {
        this.userNames = userNames;
    }

    public Integer getTopN() {
        return topN;
    }

    public void setTopN(Integer topN) {
        this.topN = topN;
    }

    public RiskReportParam(Date startTime, Date endTime, List<String> userNames, Integer topN) {
        this.startTime = getSecond(startTime);
        this.endTime = getSecond(endTime);
        this.userNames = userNames;
        this.topN = topN;
    }

    public RiskReportParam() {
    }

    @Override
    public String toString() {
        return "RiskParam{" +
                "startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", userNames=" + userNames +
                ", topN=" + topN +
                '}';
    }
}
