package com.dsmp.report.runner;

import com.dsmp.report.web.service.IReportParamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author byliu
 **/
@Order(1)
@Component
public class InitRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(InitRunner.class);

    @Autowired
    IReportParamService paramService;

    @Override
    public void run(String... args) {
        logger.info("初始化参数...");
        try {
            paramService.init();
        } catch (Exception e) {
            logger.info("初始化失败...");
        }
    }
}
