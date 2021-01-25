package com.kxingyi.common.web.response;


/**
 * @Author: chengpan
 * @Date: 2020/8/10
 */
public enum UnifyApiCode {
    OK("0", ""),
    UNAUTHORIZED("401", "未认证"),
    FORBIDDEN("403", "无授权"),
    NOT_FOUND("404", "接口不存在"),
    BAD_METHOD("405", "非法调用"),
    INTERNAL_ERROR("500", "内部错误"),
    DATA_NOT_FOUND("2", "Data Not Found"),
    ILLEGALARGUMENT("5", "参数解析错误"),
    PASSWORD_INVALID_MODIFY_PASSWORD("10","密码过期，请修改密码"),
    LOGIN_FAIL("100", "登录失败"),
    ENCRYPT_DENCRYPT_ERR("4","加解密出现错误"),
    NO_DATA_AUTHORITY("600","无权限进行此操作");
    private String code;
    private String msg;

    UnifyApiCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
