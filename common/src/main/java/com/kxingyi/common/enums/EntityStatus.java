package com.kxingyi.common.enums;

public enum EntityStatus {
    // 当任务处于normal时，则认为任务是处于运行中
    NORMAL("正常"),
    LOCK("锁定"),
    DEL("删除"),
    // 定时执行任务和周期执行任务停止调度时的状态
    STOP("停止"),
    // 立即执行任务执行结束时的状态
    CLOSED("完成"),
    // 内置目录的状态
    BUILT_IN("内置"),
    DISABLED("禁用");

    EntityStatus(String detail) {
        this.detail = detail;
    }

    private String detail;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public static EntityStatus matchByDetail(String detail) {
        for (EntityStatus value : EntityStatus.values()) {
            if (value.getDetail().equals(detail)) return value;
        }
        return null;
    }
}
