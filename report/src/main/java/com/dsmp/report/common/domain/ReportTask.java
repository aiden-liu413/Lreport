package com.dsmp.report.common.domain;

import com.dsmp.report.common.enums.CycleEnum;
import com.dsmp.report.common.enums.EntityStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author byliu
 * 报表任务
 **/
@EntityListeners(AuditingEntityListener.class)
@ApiModel("报表任务对象")
@Entity
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@Table(name = "t_report_task_info")
public class ReportTask implements Serializable,Cloneable{
    /**
     * 任务ID
     */
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    private String id;
    /**
     * 任务name
     */
    @ApiModelProperty("任务name")
    private String name;
    /**
     * 报表（模板）的id集合
     */
    @ApiModelProperty("报表（模板）的id集合")
    private String reportIds;
    /**
     * cron表达式枚举
     */
    @ApiModelProperty("周期")
    @Enumerated(EnumType.STRING)
    private CycleEnum cycleType;
    /**
     * 状态
     */
    @Enumerated(EnumType.STRING)
    @ApiModelProperty("状态")
    private EntityStatus status;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @CreatedDate
    private Date createTime;
    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    @LastModifiedDate
    private Date updateTime;
    /**
     * 报表参数
     */
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
        return "ReportTask{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", reportIds='" + reportIds + '\'' +
                ", cycleType=" + cycleType +
                ", status=" + status +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", params='" + params + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof ReportTask)) return false;

        ReportTask that = (ReportTask) o;

        return new EqualsBuilder()
                .append(reportIds, that.reportIds)
                .append(cycleType, that.cycleType)
                .append(params, that.params)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(reportIds)
                .append(cycleType)
                .append(params)
                .toHashCode();
    }

    @Override
    public ReportTask clone() throws CloneNotSupportedException {
        return (ReportTask)super.clone();
    }
}
