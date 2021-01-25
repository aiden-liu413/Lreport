package com.dsmp.report.common.enums;

public enum ReportEnum {
    EXCEL("xlsx"),
    WORD("docx"),
    ALL("zip"),
    PDF("pdf");

    ReportEnum(String suffix) {
        this.suffix = suffix;
    }

    private String suffix;

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public static ReportEnum matchBySuffix(String detail) {
        for (ReportEnum value : ReportEnum.values()) {
            if (value.getSuffix().equals(detail)) return value;
        }
        return null;
    }
}
