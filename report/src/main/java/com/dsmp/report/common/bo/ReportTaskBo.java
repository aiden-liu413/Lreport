package com.dsmp.report.common.bo;

import com.dsmp.report.common.domain.ReportTaskParam;
import com.dsmp.report.common.enums.CycleEnum;
import com.dsmp.report.common.enums.EntityStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author byliu
 * 报表任务
 **/
@ApiModel("报表任务BO对象")
public class ReportTaskBo {
    /**
     * 任务ID
     */
    private String id;
    /**
     * 任务name
     */
    @ApiModelProperty("任务name")
    @NotBlank(message = "任务名称不能为空")
    private String name;
    /**
     * 报表（模板）的id集合
     */
    @ApiModelProperty("报表（模板）的id集合")
    @NotBlank(message = "模板集合不能为空")
    private String reportIds;
    /**
     * cron表达式枚举
     */
    @ApiModelProperty("周期")
    @NotNull
    private CycleEnum cycleType;
    /**
     * 状态
     */
    @ApiModelProperty("状态")
    @NotNull(message = "任务状态不能为空")
    private EntityStatus status;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;
    /**
     * 报表参数
     */
    @ApiModelProperty("报表参数")
    @NotNull(message = "报表参数不能为空")
    private ReportTaskParam  params;

    @ApiModelProperty("用户id")
    @NotBlank(message = "用户id不能为空")
    private String userId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReportIds() {
        return reportIds;
    }

    public void setReportIds(String reportIds) {
        this.reportIds = reportIds;
    }

    public CycleEnum getCycleType() {
        return cycleType;
    }

    public void setCycleType(CycleEnum cycleType) {
        this.cycleType = cycleType;
    }

    public EntityStatus getStatus() {
        return status;
    }

    public void setStatus(EntityStatus status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public ReportTaskParam getParams() {
        return params;
    }

    public void setParams(ReportTaskParam params) {
        this.params = params;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ReportTaskBo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", reportIds='" + reportIds + '\'' +
                ", cycleType=" + cycleType +
                ", status=" + status +
                ", remark='" + remark + '\'' +
                ", params=" + params +
                ", userId='" + userId + '\'' +
                '}';
    }
}
