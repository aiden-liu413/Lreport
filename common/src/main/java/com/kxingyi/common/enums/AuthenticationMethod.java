package com.kxingyi.common.enums;

/**
 * 认证方式的类型
 */
public enum AuthenticationMethod {

    PASSWORD("静态密码");
//    OTP("OTP");
    private String detail;

    AuthenticationMethod(String detail) {
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }

    public static AuthenticationMethod matchByDetail(String detail) {
        for (AuthenticationMethod value : AuthenticationMethod.values()) {
            if (value.getDetail().equals(detail)) return value;
        }
        return null;
    }
}



