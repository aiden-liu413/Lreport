package com.dsmp.report;

import com.kxingyi.common.util.feign.EnableFeignClient;
import com.kxingyi.common.util.minio.EnableMinio;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableMinio
@EnableJpaAuditing
@EnableFeignClient
@NacosPropertySource(dataId = "fileCacheDays", groupId="report",autoRefreshed = true)
public class ReportApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReportApplication.class, args);
    }

}
