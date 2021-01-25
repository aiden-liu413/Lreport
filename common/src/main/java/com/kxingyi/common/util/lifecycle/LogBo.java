package com.kxingyi.common.util.lifecycle;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author byliu
 **/
public class LogBo {

    private String blurry;
    private String fileName;
    private String source;
    private String sceneType;
    @NotBlank(message = "开始时间不能为空")
    private String startTime;
    @NotBlank(message = "结束时间不能为空")
    private String endTime;
    @NotNull(message = "page不能为空")
    @Min(value = 0, message = "page不能小于0")
    private Integer page;


    @JsonProperty(value = "LogWhere")
    private String LogWhere;

    public LogBo() {
    }

    public LogBo(String blurry, String fileName, String source, String sceneType,
                 @NotBlank(message = "开始时间不能为空") String startTime,
                 @NotBlank(message = "结束时间不能为空") String endTime,
                 @NotNull(message = "page不能为空") Integer page,
                 @NotNull(message = "size不能为空")
                 @Min(value = 1) Integer size) {
        this.blurry = blurry;
        this.fileName = fileName;
        this.source = source;
        this.sceneType = sceneType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.page = page;
        this.size = size;
    }

    @NotNull(message = "size不能为空")
    @Min(value = 1)
    private Integer size;

    public String getBlurry() {
        return blurry;
    }

    public void setBlurry(String blurry) {
        this.blurry = blurry;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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

    public String getSceneType() {
        return sceneType;
    }

    public void setSceneType(String sceneType) {
        this.sceneType = sceneType;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getLogWhere() {
        return LogWhere;
    }

    public void setLogWhere(String logWhere) {
        this.LogWhere = logWhere;
    }

    @Override
    public String toString() {
        return "LogBo{" +
                "blurry='" + blurry + '\'' +
                ", fileName='" + fileName + '\'' +
                ", source='" + source + '\'' +
                ", sceneType='" + sceneType + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", page=" + page +
                ", logWhere='" + LogWhere + '\'' +
                ", size=" + size +
                '}';
    }
}
