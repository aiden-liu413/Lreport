package com.dsmp.report.common.vo;

import com.dsmp.report.common.domain.ReportFile;
import com.dsmp.report.common.enums.EntityStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author byliu
 * 报表文件
 **/
@ApiModel("报表文件VO对象")
public class ReportFileVo {

    /**
     * 任务ID
     */
    private String id;

    @ApiModelProperty("文件名")
    private String name;

    @ApiModelProperty("文件状态")
    private EntityStatus status;

    @ApiModelProperty("生成时间")
    private Date createTime;

    @ApiModelProperty("任务id")
    private String taskId;

    @ApiModelProperty("模板id")
    private String templteId;

    @ApiModelProperty("任务名称")
    private String taskName;

    @ApiModelProperty("模板名称")
    private String templteName;

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

    public EntityStatus getStatus() {
        return status;
    }

    public void setStatus(EntityStatus status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTemplteId() {
        return templteId;
    }

    public void setTemplteId(String templteId) {
        this.templteId = templteId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTemplteName() {
        return templteName;
    }

    public void setTemplteName(String templteName) {
        this.templteName = templteName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ReportFileVo() {
    }

    public ReportFileVo(ReportFile file, String taskName, String templteName) {
        this.id = file.getId();
        this.name = file.getName();
        this.status = file.getStatus();
        this.createTime = file.getCreateTime();
        this.taskId = file.getTaskId();
        this.templteId = file.getTemplteId();

        this.taskName = taskName;
        this.templteName = templteName;
        this.userId = file.getUserId();
    }

    @Override
    public String toString() {
        return "ReportFileVo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", taskId='" + taskId + '\'' +
                ", templteId='" + templteId + '\'' +
                ", taskName='" + taskName + '\'' +
                ", templteName='" + templteName + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
