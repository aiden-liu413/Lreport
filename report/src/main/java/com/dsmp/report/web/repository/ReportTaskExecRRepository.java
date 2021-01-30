package com.dsmp.report.web.repository;

import com.dsmp.report.common.domain.ReportTask;
import com.dsmp.report.common.domain.ReportTaskExecR;
import com.dsmp.report.common.enums.EntityStatus;
import com.dsmp.report.common.vo.ReportTaskVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @author byliu
 **/
public interface ReportTaskExecRRepository extends JpaRepository<ReportTaskExecR, String> {

}
