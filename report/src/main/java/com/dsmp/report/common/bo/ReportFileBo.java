package com.dsmp.report.common.bo;

import com.dsmp.report.common.enums.EntityStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

/**
 * @author byliu
 * 报表文件
 **/
@ApiModel("报表文件BO对象")
public class ReportFileBo {

    /**
     * 任务ID
     */
    private String id;
    /**
     * 文件名
     */
    @ApiModelProperty("文件名")
    private String name;
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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    private Date createTime;

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

    @Override
    public String toString() {
        return "ReportFile{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                '}';
    }
}
