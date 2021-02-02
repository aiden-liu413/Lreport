package com.dsmp.report.common.domain;

import com.dsmp.report.common.enums.ExecStatus;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author byliu
 * 报表任务
 **/
@EntityListeners(AuditingEntityListener.class)
@Entity
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@Table(name = "t_report_task_exec_info")
public class ReportTaskExec implements Serializable, Cloneable {
    /**
     * ID
     */
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    private String id;
    /**
     * 执行状态
     */
    @Enumerated(EnumType.STRING)
    private ExecStatus execStatus = ExecStatus.SUCCESS;
    /**
     * 执行日志
     */
    private String content = ExecStatus.SUCCESS.getExecDetail();
    /**
     * 执行时间
     */
    @CreatedDate
    private Date execTime;
    /**
     * 任务耗时
     */
    private String spentTime;

    public ReportTaskExec(String message, String spentTime) {
        this.content=message;
    }

    public ReportTaskExec() {

    }

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

    @Override
    public String toString() {
        return "ReportTaskExec{" +
                "id='" + id + '\'' +
                ", execStatus=" + execStatus +
                ", content='" + content + '\'' +
                ", execTime=" + execTime +
                ", spentTime='" + spentTime + '\'' +
                '}';
    }
}
