package com.dsmp.report.web.service;

import com.dsmp.report.common.bo.ReportTemplteBo;
import com.dsmp.report.common.domain.ReportTemplte;
import com.dsmp.report.web.request.ReportTempltePagination;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * @author byliu
 **/
public interface IReportTemplteService {

    /**
     * 根据报表模板名称检查报表模板是否存在
     *
     * @param name 报表名称模板
     */
    boolean checkExistByName(String name);

    /**
     *  根据报表模板名称查询报表
     *
     * @param name 报表名称模板
     */
    ReportTemplte getReportFileByName(String name);

    /**
     * 查询全部报表模板
     */
    List<ReportTemplte> listAllReportFile();

    /**
     * 根据报表名称删除报表模板
     *
     * @param name 报表名称模板
     */
    ReportTemplte removeReportFileByName(String name);


    /**
     * 保存报表模板
     */
    ReportTemplte saveReportFile(ReportTemplte entity);

    /**
     * 更新报表模板
     */
    ReportTemplte updateReportFile(ReportTemplte entity);

    ReportTemplte updateReportTemplte(ReportTemplteBo bo);

    void setExtendJson(String extendJson, ReportTemplte reportTemplte);


    Page<ReportTemplte> pages(ReportTempltePagination pagination);

    void delete(List<String> ids);

    void delete(Date startTime, Date endTime);

    void upload(ReportTemplteBo bo, MultipartFile file);

    void add(ReportTemplteBo bo);
}
