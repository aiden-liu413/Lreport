package com.dsmp.report.web.repository;

import com.dsmp.report.common.domain.ReportTaskExec;
import com.dsmp.report.common.vo.ReportTaskExecVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

/**
 * @author byliu
 **/
public interface ReportTaskExecRepository extends JpaRepository<ReportTaskExec, String> {
    String baseSelectSql = "select new com.dsmp.report.common.vo.ReportTaskExecVo(t1,t3.name,t3.id,t4.id,t4.name) from  ReportTaskExec t1 " +
            "JOIN ReportTaskExecR t2 ON t1.id=t2.execId " +
            "JOIN ReportTask t3 ON t3.id=t2.taskId " +
            "LEFT JOIN ReportTemplte t4 ON t4.id=t2.templteId  WHERE  1=1 ";
    String fuzzyQuery = " and (?1 is null or t3.name LIKE %?1%) " +
            "and (?2 is null or t4.name LIKE %?2%) " +
            "and ((cast (?3 as date) is null and cast (?4 as date) is null) or ( t1.execTime between ?3 and ?4 )) " +
            "and (?5 is null or t3.id = ?5) " +
            "and (?6 is null or t4.id = ?6) ";

    @Query(baseSelectSql + fuzzyQuery)
    Page<ReportTaskExecVo> listWithPage(String taskName, String templteName, Date startTime, Date endTime, String taskId, String templteId,
                                        Pageable pageable);
}
