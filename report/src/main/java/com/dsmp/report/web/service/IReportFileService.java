package com.dsmp.report.web.service;

import com.dsmp.report.common.enums.ReportEnum;
import com.dsmp.report.common.vo.ReportFileVo;
import com.dsmp.report.web.request.ReportFilePagination;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @author byliu
 **/
public interface IReportFileService {

    Page<ReportFileVo> pages(ReportFilePagination pagination);

    void delete(List<String> ids);

    void delete(Date startTime, Date endTime);

    void delete(Date expireTime);

    Object download(String fileId, ReportEnum reportEnum, HttpServletResponse response);
}
