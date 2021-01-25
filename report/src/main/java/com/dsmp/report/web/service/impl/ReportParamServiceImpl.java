package com.dsmp.report.web.service.impl;

import com.dsmp.common.util.security.CryptUtil;
import com.dsmp.report.common.bo.EmailBo;
import com.dsmp.report.common.domain.SysParam;
import com.dsmp.report.web.repository.SysParamRepository;
import com.dsmp.report.web.service.IReportParamService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author byliu
 **/
@Service
public class ReportParamServiceImpl implements IReportParamService {

    @Autowired
    SysParamRepository paramRepository;
    @Override
    public void updateFileCacheParam(int cacheDays) {
        SysParam fileCacheDays = paramRepository.findByKey(FILE_CACHE_DAYS);
        if(null == fileCacheDays){
            fileCacheDays = new SysParam();
            fileCacheDays.setKey(FILE_CACHE_DAYS);
            fileCacheDays.setValue(cacheDays+"");
        }else{
            fileCacheDays.setValue(cacheDays+"");
        }
        paramRepository.save(fileCacheDays);
    }

    @Override
    public int getFileCacheParam() {
        SysParam fileCacheDays = paramRepository.findByKey(FILE_CACHE_DAYS);
        if(null == fileCacheDays){
            return 30;
        }
        return Integer.valueOf(fileCacheDays.getValue());
    }

    @Override
    public EmailBo getEmailParam() {
        EmailBo bo = new EmailBo();
        bo.setEmailServer(paramRepository.findByKey(EMAIL_SERVER).getValue());
        if(StringUtils.isNotBlank(paramRepository.findByKey(EMAIL_PORT).getValue())){
            bo.setEmailPort(Integer.parseInt(paramRepository.findByKey(EMAIL_PORT).getValue()));
        }
        bo.setEmailUser(paramRepository.findByKey(EMAIL_USER).getValue());
        bo.setEmailPassword(CryptUtil.decrypt(paramRepository.findByKey(EMAIL_PASSWORD).getValue()));
        return bo;
    }

    @Override
    public void init() {
        SysParam server = paramRepository.findByKey(EMAIL_SERVER);
        SysParam port = paramRepository.findByKey(EMAIL_PORT);
        SysParam user = paramRepository.findByKey(EMAIL_USER);
        SysParam password = paramRepository.findByKey(EMAIL_PASSWORD);
        SysParam fileCache = paramRepository.findByKey(FILE_CACHE_DAYS);
        if(null == server){
            server = new SysParam();
            server.setKey(EMAIL_SERVER);
            paramRepository.save(server);
        }
        if(null == port){
            port = new SysParam();
            port.setKey(EMAIL_PORT);
            paramRepository.save(port);
        }
        if(null == user){
            user = new SysParam();
            user.setKey(EMAIL_USER);
            paramRepository.save(user);
        }
        if(null == password){
            password = new SysParam();
            password.setKey(EMAIL_PASSWORD);
            paramRepository.save(password);
        }
        if(null == fileCache){
            fileCache = new SysParam();
            fileCache.setKey(FILE_CACHE_DAYS);
            fileCache.setValue("30");
            paramRepository.save(fileCache);
        }
    }
}
