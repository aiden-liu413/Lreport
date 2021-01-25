package com.kxingyi.common.enums;

/**
 * @author: wu_chao
 * @date: 2020/12/1
 * @time: 11:12
 */
public enum LogLevel {

    LOW("低级"), MIDDLE("中级"), HIGH("高级");

    LogLevel(String desc) {
        this.desc = desc;
    }

    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
