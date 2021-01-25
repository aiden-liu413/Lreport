package com.dsmp.report.common.domain;

import com.dsmp.report.common.enums.EntityStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * @author byliu
 * 报表文件
 **/
@EntityListeners(AuditingEntityListener.class)
@ApiModel("报表文件对象")
@Entity
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@Table(name = "t_report_file_info")
public class ReportFile {

    /**
     * 任务ID
     */
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    private String id;
    /**
     * 文件名
     */
    @ApiModelProperty("文件名")
    private String name;
    /**
     * 映射的文件路径
     */
    private String mappingName;
    /**
     * 文件的状态
     */
    @ApiModelProperty("文件状态")
    @Enumerated(EnumType.STRING)
    private EntityStatus status;
    /**
     * 生成时间
     */
    @ApiModelProperty("生成时间")
    @CreatedDate
    private Date createTime;

    @ApiModelProperty("任务id")
    private String taskId;

    @ApiModelProperty("模板id")
    private String templteId;

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

    public String getMappingName() {
        return mappingName;
    }

    public void setMappingName(String mappingName) {
        this.mappingName = mappingName;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ReportFile{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", mappingName='" + mappingName + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", taskId='" + taskId + '\'' +
                ", templteId='" + templteId + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
