package com.dsmp.report.web.service.impl;

import com.kxingyi.common.exception.BusinessException;
import com.kxingyi.common.util.UUIDTool;
import com.dsmp.report.aop.AppendUserId;
import com.dsmp.report.common.bo.ReportTemplteBo;
import com.dsmp.report.common.domain.ReportTemplte;
import com.dsmp.report.common.enums.EntityStatus;
import com.dsmp.report.web.repository.ReportRepository;
import com.dsmp.report.web.request.ReportTempltePagination;
import com.dsmp.report.web.service.IReportTemplteService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

/**
 * @author byliu
 **/
@Service
public class ReportTemplteServiceImpl implements IReportTemplteService {

    private static final Logger logger = LoggerFactory.getLogger(ReportTemplteServiceImpl.class);
    private static final String T_REPORT_TEMPLTE_INFO = "t_report_info";
    private static final String VENUS_REPORT = ".venus.report";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ReportRepository reportRepository;

    private ReportTemplte toPo(ResultSet rs) {
        ReportTemplte uf = new ReportTemplte();
        try {
            uf.setContent(rs.getString("content"));
            uf.setCreateTime(rs.getDate("create_time"));
            uf.setModifyTime(rs.getDate("modify_time"));
            uf.setName(rs.getString("name"));
            uf.setId(rs.getString("id"));
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return uf;
    }

    /**
     * 根据报表名称检查报表是否存在
     *
     * @param name 报表名称
     */
    @Override
    public boolean checkExistByName(String name) {
        List<ReportTemplte> query = jdbcTemplate.query("select t.* from " + T_REPORT_TEMPLTE_INFO + " t where t.name = ?", new Object[]{name}, (rs, rowNum) -> toPo(rs));
        return query.size() > 0;
    }

    /**
     * 根据报表名称查询报表
     *
     * @param name 报表名称
     */
    @Override
    public ReportTemplte getReportFileByName(String name) {
        List<ReportTemplte> query = jdbcTemplate.query("select t.* from " + T_REPORT_TEMPLTE_INFO + " t where t.name = ?", new Object[]{name}, (rs, rowNum) -> toPo(rs));
        return query.size() > 0 ? query.get(0) : null;
    }

    /**
     * 查询全部报表
     */
    @Override
    public List<ReportTemplte> listAllReportFile() {
        List<ReportTemplte> query = jdbcTemplate.query("select t.* from " + T_REPORT_TEMPLTE_INFO + " t", (rs, rowNum) -> toPo(rs));
        return query;
    }

    /**
     * 根据报表名称删除报表
     *
     * @param name 报表名称
     */
    @Override
    public ReportTemplte removeReportFileByName(String name) {
        jdbcTemplate.update("delete from " + T_REPORT_TEMPLTE_INFO + " t where t.name = ?", name);
        ReportTemplte reportTemplte = new ReportTemplte();
        reportTemplte.setName(name);
        return reportTemplte;
    }

    /**
     * 保存报表
     *
     * @param entity
     */
    @AppendUserId
    @Override
    public ReportTemplte saveReportFile(ReportTemplte entity) {
        Date date = new Date();
        jdbcTemplate.update(
                "insert into " + T_REPORT_TEMPLTE_INFO + "(id, name, content, create_time, modify_time) values(?,?,?,?,?)",
                UUIDTool.getPrimarykeyId(),
                entity.getName(),
                entity.getContent(),
                date,
                date);
        return entity;
    }

    /**
     * 更新报表
     *
     * @param entity
     */
    @AppendUserId
    @Override
    public ReportTemplte updateReportFile(ReportTemplte entity) {
        jdbcTemplate.update(
                "update  " + T_REPORT_TEMPLTE_INFO + " set " +
                        "content = ?, " +
                        "modify_time = ? where name = ?",
                entity.getContent(),
                entity.getModifyTime(),
                entity.getName());
        return entity;
    }

    @Override
    public ReportTemplte updateReportTemplte(ReportTemplteBo bo) {
        if (StringUtils.isBlank(bo.getId())) {
            throw new BusinessException("模板id不能为空");
        }
        if (!bo.getName().contains(VENUS_REPORT)) {
            bo.setName(bo.getName() + VENUS_REPORT);
        } else {
            bo.setName(bo.getName());
        }
        checkName(bo);
        ReportTemplte doo = reportRepository.findById(bo.getId()).orElseThrow(() -> new BusinessException("无此模板"));
        if(EntityStatus.BUILT_IN.equals(doo.getType())){
            throw new BusinessException("内置模板不能修改");
        }
        doo.setRemark(bo.getRemark());
        doo.setName(bo.getName());
        return reportRepository.save(doo);
    }

    @Override
    public void setExtendJson(String extendJson, ReportTemplte reportTemplte) {
        jdbcTemplate.update(
                "update  " + T_REPORT_TEMPLTE_INFO + " set " +
                        "extend_json = ? " +
                        "where name = ?",
                extendJson,
                reportTemplte.getName());
    }

    @Override
    public Page<ReportTemplte> pages(ReportTempltePagination pagination) {

        PageRequest pageRequest = PageRequest.of(pagination.getPage(), pagination.getSize(), Sort.Direction.DESC, "createTime");
        Page<ReportTemplte> reportFiles = reportRepository.listWithPage(pagination.getContent(), null, pagination.getStartTime(), pagination.getEndTime(), pageRequest);
        return reportFiles;
    }

    @Override
    public void delete(List<String> ids) {
        reportRepository.deleteByIdInAndTypeNot(ids, EntityStatus.BUILT_IN);
    }

    @Override
    public void delete(Date startTime, Date endTime) {
        reportRepository.deleteByCreateTimeBetween(startTime, endTime);
    }

    @Override
    public void upload(ReportTemplteBo bo, MultipartFile file) {
        if (!bo.getName().contains(VENUS_REPORT)) {
            bo.setName(bo.getName() + VENUS_REPORT);
        } else {
            bo.setName(bo.getName());
        }
        checkName(bo);
        try (InputStreamReader inr = new InputStreamReader(file.getInputStream());
             BufferedReader bufferedReader = new BufferedReader(inr)) {
            StringBuilder content = new StringBuilder();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line);
            }
            bo.setContent(content.toString());
            reportRepository.save(toDo(bo));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(ReportTemplteBo bo) {
        if (!bo.getName().contains(VENUS_REPORT)) {
            bo.setName(bo.getName() + VENUS_REPORT);
        } else {
            bo.setName(bo.getName());
        }
        checkName(bo);
        reportRepository.save(toDo(bo));
    }

    private void checkName(ReportTemplteBo bo) {
        List<ReportTemplte> byName = reportRepository.findByName(bo.getName());
        if (byName.size() > 0 && !byName.get(0).getId().equals(bo.getId())) {
            throw new BusinessException("已有同名的报表模板,请更改名字后再进行操做");
        }
    }

    ReportTemplte toDo(ReportTemplteBo bo) {
        ReportTemplte templte = new ReportTemplte();
        BeanUtils.copyProperties(bo, templte);
        return templte;
    }
}
