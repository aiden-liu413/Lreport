package com.dsmp.report.web.request;

import com.dsmp.common.web.request.Pagination;
import com.dsmp.report.common.enums.EntityStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author byliu
 **/
@ApiModel("报表文件分页对象")
public class ReportFilePagination extends Pagination {
    @ApiModelProperty("模糊查询参数")
    private String content;
    @ApiModelProperty("开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    private Date startTime;
    @ApiModelProperty("结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    private Date endTime;
    @ApiModelProperty("报表任务的状态")
    private EntityStatus status;
    @ApiModelProperty("报表任务id")
    private String taskId;
    @ApiModelProperty("报表模板id")
    private String templteId;

    public String getTemplteId() {
        return templteId;
    }

    public void setTemplteId(String templteId) {
        this.templteId = templteId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public EntityStatus getStatus() {
        return status;
    }

    public void setStatus(EntityStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ReportFilePagination{" +
                "content='" + content + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", status=" + status +
                ", taskId='" + taskId + '\'' +
                ", templteId='" + templteId + '\'' +
                '}';
    }
}
