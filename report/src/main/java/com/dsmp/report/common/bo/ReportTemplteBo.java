package com.dsmp.report.common.bo;

import com.dsmp.report.common.enums.EntityStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author byliu
 * 报表模板
 **/
@ApiModel("报表模板BO对象")
public class ReportTemplteBo {

    private String id;
    @ApiModelProperty("模板名称")
    @NotBlank(message = "模板名称不能为空")
    private String name;
    @ApiModelProperty("未使用")
    private String content;
    @ApiModelProperty("模板状态")
    @NotNull(message = "模板状态不能为空")
    private EntityStatus type;
    @ApiModelProperty("扩展json（暂未使用）")
    private String extendJson;
    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("用户id")
    @NotBlank(message = "用户id不能为空")
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
        return "ReportTemplteBo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", type=" + type +
                ", extendJson='" + extendJson + '\'' +
                ", remark='" + remark + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
