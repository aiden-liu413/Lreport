package com.dsmp.report.config;

import com.kxingyi.common.exception.handler.GlobalExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author byliu
 **/
@Configuration
public class ReportBeans {
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
       return new JdbcTemplate(dataSource);
    }

    //@Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }
}
