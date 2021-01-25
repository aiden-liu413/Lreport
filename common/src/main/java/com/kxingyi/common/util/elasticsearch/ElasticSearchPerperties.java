package com.kxingyi.common.util.elasticsearch;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author byliu
 **/
@ConfigurationProperties(prefix = "elasticsearch")
public class ElasticSearchPerperties {

    private String clusterName;

    private String address;

    private String user;

    private String pwd;

    private boolean sniff;

    private boolean showDsl;

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public boolean isSniff() {
        return sniff;
    }

    public void setSniff(boolean sniff) {
        this.sniff = sniff;
    }

    public boolean isShowDsl() {
        return showDsl;
    }

    public void setShowDsl(boolean showDsl) {
        this.showDsl = showDsl;
    }

    @Override
    public String toString() {
        return "ElasticSearchPerperties{" +
                "clusterName='" + clusterName + '\'' +
                ", address='" + address + '\'' +
                ", user='" + user + '\'' +
                ", pwd='" + pwd + '\'' +
                ", sniff=" + sniff +
                ", showDsl=" + showDsl +
                '}';
    }
}
