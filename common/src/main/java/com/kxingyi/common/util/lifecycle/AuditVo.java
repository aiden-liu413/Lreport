package com.kxingyi.common.util.lifecycle;

import java.util.Optional;

/**
 * @author byliu
 **/
public class AuditVo {
    private String opType;
    private String opTime;
    private String opResult;
    private String source;
    private String opName;
    private String serverIp;
    private String serverPort;
    private String originIp;
    private String originPort;
    private String appName;
    private String targetIp;
    private String targetPort;
    private String cmd;
    private String url;
    private String sql;
    private String forbid;
    private String mask;
    private String water;
    private String httpMethod;
    private String userName;
    private String userAccount;
    private String org;
    private String protocol;
    private String fileName;
    private String filePath;
    private String dbName;
    private String scheme;
    private String tableName;
    private String fieldName;
    private String remark;
    private String behavior;
    private Object originLog;
    private String senLevel;
    private String senType;
    private String targetAssetsName;
    private String targetAssetsType;
    private String assetsName;
    private String assetsType;

    public String getOpType() {
        return Optional.ofNullable(opType).orElse("");
    }

    public void setOpType(String opType) {
        this.opType = opType;
    }

    public String getOpTime() {
        return Optional.ofNullable(opTime).orElse("");
    }

    public void setOpTime(String opTime) {
        this.opTime = opTime;
    }

    public String getOpResult() {
        return Optional.ofNullable(opResult).orElse("");
    }

    public void setOpResult(String opResult) {
        this.opResult = opResult;
    }

    public String getSource() {
        return Optional.ofNullable(source).orElse("");
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getOpName() {
        return Optional.ofNullable(opName).orElse("");
    }

    public void setOpName(String opName) {
        this.opName = opName;
    }

    public String getServerIp() {
        return Optional.ofNullable(serverIp).orElse("");
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getServerPort() {
        return Optional.ofNullable(serverPort).orElse("");
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public String getOriginIp() {
        return Optional.ofNullable(originIp).orElse("");
    }

    public void setOriginIp(String originIp) {
        this.originIp = originIp;
    }

    public String getOriginPort() {
        return Optional.ofNullable(originPort).orElse("");
    }

    public void setOriginPort(String originPort) {
        this.originPort = originPort;
    }

    public String getAppName() {
        return Optional.ofNullable(appName).orElse("");
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getTargetIp() {
        return Optional.ofNullable(targetIp).orElse("");
    }

    public void setTargetIp(String targetIp) {
        this.targetIp = targetIp;
    }

    public String getTargetPort() {
        return Optional.ofNullable(targetPort).orElse("");
    }

    public void setTargetPort(String targetPort) {
        this.targetPort = targetPort;
    }

    public String getCmd() {
        return Optional.ofNullable(cmd).orElse("");
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getUrl() {
        return Optional.ofNullable(url).orElse("");
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSql() {
        return Optional.ofNullable(sql).orElse("");
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getForbid() {
        return Optional.ofNullable(forbid).orElse("");
    }

    public void setForbid(String forbid) {
        this.forbid = forbid;
    }

    public String getMask() {
        return Optional.ofNullable(mask).orElse("");
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public String getWater() {
        return Optional.ofNullable(water).orElse("");
    }

    public void setWater(String water) {
        this.water = water;
    }

    public String getHttpMethod() {
        return Optional.ofNullable(httpMethod).orElse("");
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getUserName() {
        return Optional.ofNullable(userName).orElse("");
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAccount() {
        return Optional.ofNullable(userAccount).orElse("");
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getOrg() {
        return Optional.ofNullable(org).orElse("");
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getProtocol() {
        return Optional.ofNullable(protocol).orElse("");
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getFileName() {
        return Optional.ofNullable(fileName).orElse("");
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return Optional.ofNullable(filePath).orElse("");
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getDbName() {
        return Optional.ofNullable(dbName).orElse("");
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getScheme() {
        return Optional.ofNullable(scheme).orElse("");
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getTableName() {
        return Optional.ofNullable(tableName).orElse("");
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFieldName() {
        return Optional.ofNullable(fieldName).orElse("");
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getRemark() {
        return Optional.ofNullable(remark).orElse("");
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBehavior() {
        return Optional.ofNullable(behavior).orElse("");
    }

    public void setBehavior(String behavior) {
        this.behavior = behavior;
    }

    public Object getOriginLog() {
        return Optional.ofNullable(originLog).orElse("");
    }

    public void setOriginLog(Object originLog) {
        this.originLog = originLog;
    }

    public String getSenLevel() {
        return senLevel;
    }

    public void setSenLevel(String senLevel) {
        this.senLevel = senLevel;
    }

    public String getSenType() {
        return Optional.ofNullable(senType).orElse("");
    }

    public void setSenType(String senType) {
        this.senType = senType;
    }

    public String getTargetAssetsName() {
        return Optional.ofNullable(targetAssetsName).orElse("");
    }

    public void setTargetAssetsName(String targetAssetsName) {
        this.targetAssetsName = targetAssetsName;
    }

    public String getTargetAssetsType() {
        return Optional.ofNullable(targetAssetsType).orElse("");
    }

    public void setTargetAssetsType(String targetAssetsType) {
        this.targetAssetsType = targetAssetsType;
    }

    public String getAssetsName() {
        return Optional.ofNullable(assetsName).orElse("");
    }

    public void setAssetsName(String assetsName) {
        this.assetsName = assetsName;
    }

    public String getAssetsType() {
        return Optional.ofNullable(assetsType).orElse("");
    }

    public void setAssetsType(String assetsType) {
        this.assetsType = assetsType;
    }

    @Override
    public String toString() {
        return "AuditVo{" +
                "opType='" + opType + '\'' +
                ", opTime='" + opTime + '\'' +
                ", opResult='" + opResult + '\'' +
                ", source='" + source + '\'' +
                ", opName='" + opName + '\'' +
                ", serverIp='" + serverIp + '\'' +
                ", serverPort='" + serverPort + '\'' +
                ", originIp='" + originIp + '\'' +
                ", originPort='" + originPort + '\'' +
                ", appName='" + appName + '\'' +
                ", targetIp='" + targetIp + '\'' +
                ", targetPort='" + targetPort + '\'' +
                ", cmd='" + cmd + '\'' +
                ", url='" + url + '\'' +
                ", sql='" + sql + '\'' +
                ", forbid='" + forbid + '\'' +
                ", mask='" + mask + '\'' +
                ", water='" + water + '\'' +
                ", httpMethod='" + httpMethod + '\'' +
                ", userName='" + userName + '\'' +
                ", userAccount='" + userAccount + '\'' +
                ", org='" + org + '\'' +
                ", protocol='" + protocol + '\'' +
                ", fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", dbName='" + dbName + '\'' +
                ", scheme='" + scheme + '\'' +
                ", tableName='" + tableName + '\'' +
                ", fieldName='" + fieldName + '\'' +
                ", remark='" + remark + '\'' +
                ", behavior='" + behavior + '\'' +
                ", originLog='" + originLog + '\'' +
                ", senLevel='" + senLevel + '\'' +
                ", senType='" + senType + '\'' +
                ", targetAssetsName='" + targetAssetsName + '\'' +
                ", targetAssetsType='" + targetAssetsType + '\'' +
                ", assetsName='" + assetsName + '\'' +
                ", assetsType='" + assetsType + '\'' +
                '}';
    }
}
