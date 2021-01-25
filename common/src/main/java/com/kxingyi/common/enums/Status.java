package com.kxingyi.common.enums;

public enum Status {
    SUCCESS("成功"),
    FAILED("失败");

    Status(String detail) {
        this.detail = detail;
    }

    private String detail;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
