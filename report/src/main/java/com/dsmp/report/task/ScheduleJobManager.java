package com.dsmp.report.task;

import com.dsmp.report.utils.DateUtils;
import com.dsmp.report.web.service.IReportFileService;
import com.dsmp.report.web.service.IReportParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;

/**
 * @author byliu
 **/
@Component
public class ScheduleJobManager {

    @Autowired
    IReportParamService paramService;

    @Autowired
    IReportFileService fileService;

    @Scheduled(cron = "0 10 12 ? * *")
    void clearExpireReportFile(){
        int fileCacheParam = paramService.getFileCacheParam();
        Date expireTime = DateUtils.localDate2Date(LocalDate.now().plusDays(-fileCacheParam));
        fileService.delete(expireTime);
    }
}
