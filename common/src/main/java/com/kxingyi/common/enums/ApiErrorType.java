package com.kxingyi.common.enums;

public enum ApiErrorType {
    CLINET_ARG("参数错误"),
    CLIENT_BUSINESS("失败"),
    SERVER_ERROR("服务器错误");

    private String description;

    ApiErrorType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
