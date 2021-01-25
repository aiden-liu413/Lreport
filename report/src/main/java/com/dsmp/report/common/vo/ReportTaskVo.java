package com.dsmp.report.common.vo;

import com.dsmp.report.common.domain.ReportTask;
import com.dsmp.report.common.domain.ReportTemplte;
import com.dsmp.report.common.enums.CycleEnum;
import com.dsmp.report.common.enums.EntityStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * @author byliu
 * 报表任务
 **/
@ApiModel("报表任务对象")
public class ReportTaskVo {
    /**
     * 任务ID
     */
    private String id;

    @ApiModelProperty("任务name")
    private String name;

    @ApiModelProperty("报表（模板）的id集合")
    private String reportIds;

    @ApiModelProperty("报表（模板）的id集合")
    private List<ReportTemplte> ReportTempltes;

    @ApiModelProperty("周期")
    private CycleEnum cycleType;

    @ApiModelProperty("状态")
    private EntityStatus status;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("报表参数")
    private String  params;

    @ApiModelProperty("用户id")
    private String userId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReportIds() {
        return reportIds;
    }

    public void setReportIds(String reportIds) {
        this.reportIds = reportIds;
    }

    public List<ReportTemplte> getReportTempltes() {
        return ReportTempltes;
    }

    public void setReportTempltes(List<ReportTemplte> reportTempltes) {
        ReportTempltes = reportTempltes;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ReportTaskVo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", reportIds='" + reportIds + '\'' +
                ", ReportTempltes=" + ReportTempltes +
                ", cycleType=" + cycleType +
                ", status=" + status +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", params='" + params + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }

    public ReportTaskVo() {
    }

    public ReportTaskVo(ReportTask ddo) {
        this.id = ddo.getId();
        this.name = ddo.getName();
        this.reportIds = ddo.getReportIds();
        this.cycleType = ddo.getCycleType();
        this.status = ddo.getStatus();
        this.remark = ddo.getRemark();
        this.createTime = ddo.getCreateTime();
        this.updateTime = ddo.getUpdateTime();
        this.params = ddo.getParams();
        this.userId = ddo.getUserId();
    }

}
