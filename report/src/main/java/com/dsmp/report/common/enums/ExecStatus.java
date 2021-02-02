package com.dsmp.report.common.enums;

/**
 * @author byliu
 */

public enum ExecStatus {
    SUCCESS("执行成功"),
    FAILED("执行失败");

    String execDetail;

    public String getExecDetail() {
        return execDetail;
    }

    public void setExecDetail(String execDetail) {
        this.execDetail = execDetail;
    }

    ExecStatus(String execDetail) {
        this.execDetail = execDetail;
    }
}
