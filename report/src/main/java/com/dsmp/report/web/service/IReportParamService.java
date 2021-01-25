package com.dsmp.report.web.service;

import com.dsmp.report.common.bo.EmailBo;

/**
 * @author byliu
 **/
public interface IReportParamService {
    String FILE_CACHE_DAYS = "FileCacheDays";
    String EMAIL_SERVER = "emailServer";
    String EMAIL_PORT = "emailPort";
    String EMAIL_USER = "emailUser";
    String EMAIL_PASSWORD = "emailPassword";

    void updateFileCacheParam(int cacheDays);

    int getFileCacheParam();

    EmailBo getEmailParam();

    void init();
}
