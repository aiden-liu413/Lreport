package com.dsmp.report.common.vo;

/**
 * @author byliu
 **/
public class LogHighRiskIpSVo {
    private String ip;
    private String port;
    private String userName;
    private int count;

    public LogHighRiskIpSVo() {
    }

    public LogHighRiskIpSVo(String ip, String port, String userName, int count) {
        this.ip = ip;
        this.port = port;
        this.userName = userName;
        this.count = count;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "LogHighRiskIpSVo{" +
                "ip='" + ip + '\'' +
                ", port='" + port + '\'' +
                ", userName='" + userName + '\'' +
                ", count=" + count +
                '}';
    }
}
