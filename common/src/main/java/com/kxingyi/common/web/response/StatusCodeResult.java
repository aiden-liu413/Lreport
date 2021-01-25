package com.kxingyi.common.web.response;


/**
 * @Author: chengpan
 * @Date: 2020/4/21
 */
public class StatusCodeResult {
    private int status_code;
    private String status_text;
    private Object data;

    public static StatusCodeResult success() {
        StatusCodeResult scr = new StatusCodeResult();
        scr.setStatus_code(0);
        scr.setStatus_text("成功");
        return scr;
    }

    public static StatusCodeResult failed() {
        StatusCodeResult scr = new StatusCodeResult();
        scr.setStatus_code(1);
        scr.setStatus_text("失败");
        return scr;
    }

    public static StatusCodeResult success(Object data){
        StatusCodeResult scr = new StatusCodeResult();
        scr.setStatus_code(0);
        scr.setStatus_text("成功");
        scr.setData(data);
        return scr;
    }


    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
