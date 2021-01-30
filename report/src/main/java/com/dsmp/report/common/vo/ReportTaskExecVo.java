package com.dsmp.report.common.vo;

import com.dsmp.report.common.domain.ReportTaskExec;
import com.dsmp.report.common.enums.ExecStatus;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

/**
 * @author byliu
 **/
public class ReportTaskExecVo {
    /**
     * ID
     */
    private String id;

    /**
     * 执行状态
     */
    private ExecStatus execStatus;
    /**
     * 执行日志
     */
    private String content;
    /**
     * 执行时间
     */
    @CreatedDate
    private Date execTime;
    /**
     * 任务耗时
     */
    private String spentTime;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 任务id
     */
    private String taskId;
    /**
     * 模板id
     */
    private String templteId;
    /**
     * 模板名称
     */
    private String templteName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ExecStatus getExecStatus() {
        return execStatus;
    }

    public void setExecStatus(ExecStatus execStatus) {
        this.execStatus = execStatus;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getExecTime() {
        return execTime;
    }

    public void setExecTime(Date execTime) {
        this.execTime = execTime;
    }

    public String getSpentTime() {
        return spentTime;
    }

    public void setSpentTime(String spentTime) {
        this.spentTime = spentTime;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
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

    public String getTemplteName() {
        return templteName;
    }

    public void setTemplteName(String templteName) {
        this.templteName = templteName;
    }

    @Override
    public String toString() {
        return "ReportTaskExecVo{" +
                "id='" + id + '\'' +
                ", execStatus=" + execStatus +
                ", content='" + content + '\'' +
                ", execTime=" + execTime +
                ", spentTime='" + spentTime + '\'' +
                ", taskName='" + taskName + '\'' +
                ", taskId='" + taskId + '\'' +
                ", templteId='" + templteId + '\'' +
                ", templteName='" + templteName + '\'' +
                '}';
    }

    public ReportTaskExecVo(ReportTaskExec exec, String taskName, String taskId, String templteId, String templteName) {
        this.id = exec.getId();
        this.execStatus = exec.getExecStatus();
        this.content = exec.getContent();
        this.execTime = exec.getExecTime();
        this.spentTime = exec.getSpentTime();
        this.taskName = taskName;
        this.taskId = taskId;
        this.templteId = templteId;
        this.templteName = templteName;
    }
}
