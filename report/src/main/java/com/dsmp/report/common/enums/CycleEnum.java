package com.dsmp.report.common.enums;

public enum CycleEnum {
    MONTH("0 0 0 1 * ?"),// 每个月最后一天00：00：00执行
    WEEK("0 0 1 ? * 2"),// 每周一01：00：00执行
    DAY("0 0 0 ? * *"),// 每天0：00：00执行
    YEAR("0 0 0 31 12 *"),// 每年最后一天00：00：00
    CUSTOM(null);// 自定义 周期为自定义时代表立即执行，且任务

    CycleEnum(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    private String cronExpression;

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public static CycleEnum matchByCronExpression(String detail) {
        for (CycleEnum value : CycleEnum.values()) {
            if (value.getCronExpression().equals(detail)) return value;
        }
        return null;
    }
}
