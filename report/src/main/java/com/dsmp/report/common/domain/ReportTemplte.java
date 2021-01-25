package com.dsmp.report.common.domain;

import com.dsmp.report.common.enums.EntityStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * @author byliu
 * 报表模板
 **/
@EntityListeners(AuditingEntityListener.class)
@Entity
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@Table(name = "t_report_info")
public class ReportTemplte {
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    private String id;
    private String name;
    @JsonIgnore
    private String content;
    @Enumerated(EnumType.STRING)
    private EntityStatus type;
    @CreatedDate
    private Date createTime;
    @LastModifiedDate
    private Date modifyTime;
    private String extendJson;
    private String remark;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public EntityStatus getType() {
        return type;
    }

    public void setType(EntityStatus type) {
        this.type = type;
    }

    public String getExtendJson() {
        return extendJson;
    }

    public void setExtendJson(String extendJson) {
        this.extendJson = extendJson;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ReportTemplte{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", type=" + type +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", extendJson='" + extendJson + '\'' +
                ", remark='" + remark + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
