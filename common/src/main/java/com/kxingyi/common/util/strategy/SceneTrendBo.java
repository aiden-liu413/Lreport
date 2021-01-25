package com.kxingyi.common.util.strategy;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author byliu
 **/
public class SceneTrendBo {
    @NotBlank(message = "场景类型不能为空")
    private String sceneType;
    @NotBlank(message = "开始时间不能为空")
    private String startTime;
    @NotBlank(message = "结束时间不能为空")
    private String endTime;
    @NotNull
    @Min(value = 3600, message = "interval 不能小于 3600")
    private int interval;

    public String getSceneType() {
        return sceneType;
    }

    public void setSceneType(String sceneType) {
        this.sceneType = sceneType;
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

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public SceneTrendBo(@NotBlank(message = "场景类型不能为空") String sceneType,
                        @NotBlank(message = "开始时间不能为空") String startTime,
                        @NotBlank(message = "结束时间不能为空") String endTime,
                        @NotNull @Min(value = 3600, message = "interval 不能小于 3600") int interval) {
        this.sceneType = sceneType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.interval = interval;
    }

    public SceneTrendBo() {
    }

    @Override
    public String toString() {
        return "SceneTrendParams{" +
                "sceneType='" + sceneType + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", interval=" + interval +
                '}';
    }
}
