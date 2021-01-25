package com.kxingyi.common.enums.job;
/**
 * 任务执行状态
 * @author admin
 *
 */
public enum JobExecuteStatus {
	EXECUTE_WAIT("等待执行"),
	EXECUTE_NOW("正在执行"),
	EXECUTE_END("完成执行")
	;

	JobExecuteStatus(String detail) {
        this.detail = detail;
    }

    private String detail;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
