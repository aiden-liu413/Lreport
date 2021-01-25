package com.kxingyi.common.web.response;

public class MsgResult {
    private String code;
    private String msg;
    private Object data;

    public MsgResult(UnifyApiCode code) {
        this.code = code.getCode();
        this.msg = code.getMsg();
    }

    public MsgResult() {
    }

    public MsgResult(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public MsgResult(String code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static MsgResult message(String code, String message) {
        return new MsgResult(code, message);
    }

    public static MsgResult message(String code, String message, Object data) {
        return new MsgResult(message, message, data);
    }

    public static MsgResult success() {
        return new MsgResult(UnifyApiCode.OK);
    }

    public static MsgResult success(Object data) {
        return new MsgResult(UnifyApiCode.OK.getCode(), UnifyApiCode.OK.getMsg(), data);
    }

    public static MsgResult message(String code, Object data) {
        return new MsgResult(code, null, data);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 数据错误对象
     */
    public static class DataErrorBody {
        private String errCode;
        private String errText;

        public DataErrorBody(String errCode, String errText) {
            this.errCode = errCode;
            this.errText = errText;
        }

        public String getErrCode() {
            return errCode;
        }

        public void setErrCode(String errCode) {
            this.errCode = errCode;
        }

        public String getErrText() {
            return errText;
        }

        public void setErrText(String errText) {
            this.errText = errText;
        }
    }
}
