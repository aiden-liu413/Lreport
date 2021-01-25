package com.kxingyi.common.util.lifecycle;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author byliu
 **/
public class SensitiveDataBo {
    @NotBlank(message = "场景不能为空")
    private String sceneType;
    private List distinctFields;
    private String startTime;
    private String endTime;

    public String getSceneType() {
        return sceneType;
    }

    public void setSceneType(String sceneType) {
        this.sceneType = sceneType;
    }

    public List getDistinctFields() {
        return distinctFields;
    }

    public void setDistinctFields(List distinctFields) {
        this.distinctFields = distinctFields;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public SensitiveDataBo(@NotBlank(message = "场景不能为空") String sceneType, List distinctFields, String startTime, String endTime) {
        this.sceneType = sceneType;
        this.distinctFields = distinctFields;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public SensitiveDataBo() {
    }

    @Override
    public String toString() {
        return "SensitiveDataBo{" +
                "sceneType='" + sceneType + '\'' +
                ", distinctFields=" + distinctFields +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
