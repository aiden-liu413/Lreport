package com.kxingyi.common.web.response;

import java.io.Serializable;
import java.util.HashMap;


public class HandleResult implements Serializable {

    private static final long serialVersionUID = 3960913710864539549L;

    private String message;

    private Boolean flag;

    private String detail;

    private HashMap<String, Object> data;

    public HandleResult() {
    }

    public HandleResult(String message, String detail) {
        this.message = message;
        this.detail = detail;
    }

    public HandleResult(boolean flag,String message, String detail) {
        this.flag = flag;
        this.message = message;
        this.detail = detail;
    }

    public static HandleResult message(String message) {
        return new HandleResult(message, message);
    }

    public static HandleResult success(String message) {
        return new HandleResult(true,message, message);
    }

    public static HandleResult fail(String message) {
        return new HandleResult(false,message, message);
    }

    public static HandleResult message(String message, String detail) {
        return new HandleResult(message, detail);
    }

    public HandleResult(String message, String detail, HashMap<String, Object> data) {
        this.message = message;
        this.detail = detail;
        this.data = data;
    }

    public static HandleResult message(String message, String detail, HashMap<String, Object> data) {
        return new HandleResult(message, detail, data);
    }

    public static HandleResult message(String message, HashMap<String, Object> data) {
        return new HandleResult(message,message, data);
    }

    public String getString(String key) {
        if (data != null) {
            return (String) data.get(key);
        } else {
            return null;
        }
    }

    public Boolean getBoolean(String key) {
        if (data != null) {
            return (boolean) data.get(key);
        } else {
            return null;
        }
    }

    public Integer getInteger(String key) {
        if (data != null) {
            return (Integer) data.get(key);
        } else {
            return null;
        }
    }

    public HandleResult put(String key, Object value) {
        if (data == null) {
            data = new HashMap<>();
        }
        data.put(key, value);
        return this;
    }

    public Object get(String key) {
        if (data != null) {
            return data.get(key);
        } else {
            return null;
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }
}
