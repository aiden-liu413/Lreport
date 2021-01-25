package com.dsmp.report.common.bo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class EmailBo {

    @NotBlank(message = "邮件服务器地址不能为空")
    private String emailServer;

    @NotNull(message = "邮件服务器端口不能为空")
    private Integer emailPort;

    @NotBlank(message = "邮件用户不能为空")
    private String emailUser;

    @NotBlank(message = "邮件密码不能为空")
    private String emailPassword;

    public String getEmailServer() {
        return emailServer;
    }

    public void setEmailServer(String emailServer) {
        this.emailServer = emailServer;
    }

    public Integer getEmailPort() {
        return emailPort;
    }

    public void setEmailPort(Integer emailPort) {
        this.emailPort = emailPort;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getEmailPassword() {
        return emailPassword;
    }

    public void setEmailPassword(String emailPassword) {
        this.emailPassword = emailPassword;
    }
}
