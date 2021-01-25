package com.dsmp.report.web.repository;

import com.dsmp.report.common.domain.ReportTask;
import com.dsmp.report.common.domain.ReportTemplte;
import com.dsmp.report.common.enums.EntityStatus;
import com.dsmp.report.common.vo.ReportTaskVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @author byliu 报表任务
 **/
public interface ReportTaskRepository extends JpaRepository<ReportTask, String> {
    String baseSelectSql = "select new com.dsmp.report.common.vo.ReportTaskVo(r) from  ReportTask r  WHERE  1=1 ";
    String fuzzyQuery = " and (?1 is null or r.name LIKE %?1%) " +
            "and (?2 is null or r.status = ?2) " +
            "and ((cast (?3 as date) is null and cast (?4 as date) is null) or ( r.createTime between ?3 and ?4 )) ";

    @Query(baseSelectSql + fuzzyQuery)
    Page<ReportTaskVo> listWithPage(String name, EntityStatus status, Date startTime, Date endTime,
                                  Pageable pageable);

    List<ReportTask> findAllByStatus(EntityStatus status);

    List<ReportTask> findByName(String name);
}
