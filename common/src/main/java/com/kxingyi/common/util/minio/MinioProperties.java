package com.kxingyi.common.util.minio;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author byliu
 **/
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {

    private String url;
    private String accessKey;
    private String secretKey;
    private String chunkBucKet;
    private String suffixWhitList;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getChunkBucKet() {
        return chunkBucKet;
    }

    public void setChunkBucKet(String chunkBucKet) {
        this.chunkBucKet = chunkBucKet;
    }

    public String getSuffixWhitList() {
        return suffixWhitList;
    }

    public void setSuffixWhitList(String suffixWhitList) {
        this.suffixWhitList = suffixWhitList;
    }

    @Override
    public String toString() {
        return "MinioProperties{" +
                "url='" + url + '\'' +
                ", accessKey='" + accessKey + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", chunkBucKet='" + chunkBucKet + '\'' +
                ", suffixWhitList='" + suffixWhitList + '\'' +
                '}';
    }
}
