package com.kxingyi.common.enums.job;

/**
 * @author lai_changhao on 2020/7/24.
 */
public enum ExecuteType {

    IMMEDIATE("立即执行"),
    DELAY("定时执行"),
    SCHEDULE("周期执行");

    private String detail;

    ExecuteType(String detail) {
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
