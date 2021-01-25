package com.dsmp.report.web.repository;

import com.dsmp.report.common.domain.ReportTemplte;
import com.dsmp.report.common.enums.EntityStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author byliu 报表模板
 **/
public interface ReportRepository extends JpaRepository<ReportTemplte, String> {
    String baseSelectSql = "select r from  ReportTemplte r  WHERE  1=1 ";
    String fuzzyQuery = " and (?1 is null or r.name LIKE %?1%) " +
            "and (?2 is null or r.type = ?2) " +
            "and ((cast (?3 as date) is null and cast (?4 as date) is null) or ( r.createTime between ?3 and ?4 )) ";

    @Query(baseSelectSql + fuzzyQuery)
    Page<ReportTemplte> listWithPage(String name, EntityStatus type, Date startTime, Date endTime,
                                  Pageable pageable);

    @Transactional
    @Modifying
    void deleteByIdInAndTypeNot(List<String> ids, EntityStatus status);

    @Transactional
    @Modifying
    void deleteByCreateTimeBetween(Date startTime, Date endTime);

    List<ReportTemplte> findByIdIn(List<String> ids);

    List<ReportTemplte> findByName(String name);
}
