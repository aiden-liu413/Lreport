package com.dsmp.report.common.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author byliu
 **/
@ApiModel("报表任务参数对象")
public class ReportTaskParam {
    @ApiModelProperty("用户id集合")
    /*@NotNull(message = "用户id集合不能为空")*/
    private List<String> userIds = new ArrayList<>();
    @ApiModelProperty("资产id集合")
    /*@NotNull(message = "资产id集合不能为空")*/
    private List<String> resourceIds = new ArrayList<>();
    @ApiModelProperty("用户名集合")
    /*@NotNull(message = "用户名集合不能为空")*/
    private List<String> userNames = new ArrayList<>();
    @ApiModelProperty("收件人集合")
    /*@NotNull(message = "收件人集合不能为空")*/
    private List<String> recipients = new ArrayList<>();
    @ApiModelProperty("开始时间")
    @NotNull(message = "开始时间不能为空")
    private Date startTime;
    @ApiModelProperty("结束时间")
    @NotNull(message = "结束时间不能为空")
    private Date endTime;

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public List<String> getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(List<String> resourceIds) {
        this.resourceIds = resourceIds;
    }

    public List<String> getUserNames() {
        return userNames;
    }

    public void setUserNames(List<String> userNames) {
        this.userNames = userNames;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public List<String> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<String> recipients) {
        this.recipients = recipients;
    }

    @Override
    public String toString() {
        return "ReportTaskParam{" +
                "userIds=" + userIds +
                ", resourceIds=" + resourceIds +
                ", userNames=" + userNames +
                ", recipients=" + recipients +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
