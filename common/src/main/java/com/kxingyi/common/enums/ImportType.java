package com.kxingyi.common.enums;

/**
 * @author: wu_chao
 * @date: 2020/10/15
 * @time: 9:54
 */
public enum ImportType {
    CREATE("新增"),
    UPDATE("修改");

    private String detail;

    ImportType(String detail) {
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }

    public static ImportType matchByDetail(String detail) {
        for (ImportType value : ImportType.values()) {
            if (value.getDetail().equals(detail)) return value;
        }
        return null;
    }
}
