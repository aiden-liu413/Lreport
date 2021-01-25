package com.kxingyi.common.enums;

public enum OperateType {
    CREATE(LogLevel.LOW, "新增"),

    QUERY(LogLevel.LOW, "查询"),

    UPDATE(LogLevel.MIDDLE, "更新"),

    DELETE(LogLevel.MIDDLE, "删除"),
    
    START(LogLevel.MIDDLE, "启动"),
    
    STOP(LogLevel.MIDDLE, "停止"),

    LOGIN(LogLevel.MIDDLE, "登录"),

    LOGOUT(LogLevel.MIDDLE, "登出"),

    EXPORT(LogLevel.HIGH, "导出"),

    IMPORT(LogLevel.HIGH, "导入"),

    DOWNLOAD(LogLevel.HIGH, "下载"),

    UPLOAD(LogLevel.HIGH, "上传"),

    // 执行任务、命令、策略等操作
    EXECUTE(LogLevel.MIDDLE, "执行"),

    // 执行任务、命令、策略等操作
    CHANGE_PASSWORD(LogLevel.HIGH, "改密")
    ;


    OperateType(LogLevel logLevel, String desc) {
        this.logLevel = logLevel;
        this.desc = desc;
    }

    /** 操作级别  */
    private LogLevel logLevel;

    /** 操作描述  */
    private String desc;


    public LogLevel getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }



}
