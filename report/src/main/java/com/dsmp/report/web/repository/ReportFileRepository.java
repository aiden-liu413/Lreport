package com.dsmp.report.web.repository;

import com.dsmp.report.common.domain.ReportFile;
import com.dsmp.report.common.enums.EntityStatus;
import com.dsmp.report.common.vo.ReportFileVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author byliu 报表文件
 **/
public interface ReportFileRepository extends JpaRepository<ReportFile, String> {

    String baseSelectSql = "select new com.dsmp.report.common.vo.ReportFileVo(r,task.name,templte.name) from  ReportFile r " +
            "left join ReportTask task on task.id=r.taskId " +
            "left join ReportTemplte templte on r.templteId=templte.id  WHERE  1=1 ";
    String fuzzyQuery = " and (?1 is null or r.name LIKE %?1%) " +
            "and (?2 is null or r.taskId = ?2) " +
            "and (?3 is null or r.status = ?3) " +
            "and (?4 is null or r.templteId = ?4) " +
            "and ((cast (?5 as date) is null and cast (?6 as date) is null) or ( r.createTime between ?5 and ?6 )) " +
            "and (?7 is null or r.name LIKE %?7%) " +
            "and (?8 is null or r.name LIKE %?8%) ";

    @Query(baseSelectSql + fuzzyQuery)
    Page<ReportFileVo> listWithPage(String name, String taskId, EntityStatus status, String templteId, Date startTime, Date endTime,
                                    String templteName, String taskName,
                                    Pageable pageable);
    @Modifying
    @Transactional
    void deleteByIdIn(List<String> ids);
    @Transactional
    @Modifying
    void deleteByCreateTimeBetween(Date startTime, Date endTime);

    @Transactional
    @Modifying
    void deleteByCreateTimeLessThan(Date expireTime);

    List<ReportFile> findByCreateTimeBetween(Date startTime, Date endTime);

    List<ReportFile> findByCreateTimeLessThan(Date expireTime);

    List<ReportFile> findByIdIn(List<String> ids);
}
