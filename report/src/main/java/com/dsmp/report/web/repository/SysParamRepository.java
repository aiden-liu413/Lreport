package com.dsmp.report.web.repository;

import com.dsmp.report.common.domain.SysParam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysParamRepository extends JpaRepository<SysParam, String> {

    SysParam findByKey(String key);
}
