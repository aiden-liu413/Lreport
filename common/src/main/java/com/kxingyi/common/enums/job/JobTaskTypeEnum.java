package com.kxingyi.common.enums.job;
/**
 * 任务类型枚举类
 * @author admin
 *
 */
public enum JobTaskTypeEnum {
	dsmp_sensitiveFindHandle("敏感发现任务");

	JobTaskTypeEnum(String detail) {
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
