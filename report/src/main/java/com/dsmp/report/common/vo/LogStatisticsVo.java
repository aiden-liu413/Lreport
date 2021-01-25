package com.dsmp.report.common.vo;

/**
 * @author byliu
 **/
public class LogStatisticsVo {
    private String source;
    private String opType;
    private int count;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getOpType() {
        return opType;
    }

    public void setOpType(String opType) {
        this.opType = opType;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public LogStatisticsVo(String source, String opType, int count) {
        this.source = source;
        this.opType = opType;
        this.count = count;
    }

    public LogStatisticsVo() {
    }

    @Override
    public String toString() {
        return "RiskVo{" +
                "source='" + source + '\'' +
                ", opType='" + opType + '\'' +
                ", count=" + count +
                '}';
    }
}
