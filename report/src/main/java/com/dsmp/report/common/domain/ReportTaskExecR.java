package com.dsmp.report.common.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author byliu
 * 报表任务
 */
@Entity
@Table(name = "t_report_task_exec_r_info")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class ReportTaskExecR implements Serializable, Cloneable {
    /**
     * ID
     */
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    private String id;
    /**
     * 任务id
     */
    private String taskId;
    /**
     * 模板id
     */
    private String templteId;
    /**
     * 执行记录id
     */
    private String execId;

    public ReportTaskExecR(String taskId, String templteId, String execId) {
        this.execId=execId;
        this.taskId=taskId;
        this.templteId=templteId;
    }

    public ReportTaskExecR() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getExecId() {
        return execId;
    }

    public void setExecId(String execId) {
        this.execId = execId;
    }

    @Override
    public String toString() {
        return "ReportTaskExecR{" +
                "id='" + id + '\'' +
                ", taskId='" + taskId + '\'' +
                ", templteId='" + templteId + '\'' +
                ", execId='" + execId + '\'' +
                '}';
    }
}
