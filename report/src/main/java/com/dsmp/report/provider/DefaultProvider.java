package com.dsmp.report.provider;

import com.bstek.ureport.exception.ReportException;
import com.bstek.ureport.provider.report.ReportFile;
import com.dsmp.report.common.domain.ReportTemplte;
import com.dsmp.report.web.service.IReportTemplteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author byliu
 **/
@Component
public class DefaultProvider extends VenusProvider {

    /**
     * 存储器名称
     */
    private static final String NAME = "dsmp-provider";

    /**
     * 是否禁用
     */
    private boolean disabled = false;

    @Autowired
    private IReportTemplteService IReportTemplteService;

    public DefaultProvider() {
        super("dsmp:", "venus.report");
    }

    /**
     * 根据报表名加载报表文件
     *
     * @param file 报表名称
     * @return 返回的InputStream
     */
    @Override
    public InputStream loadReport(String file) {
        ReportTemplte reportFile = IReportTemplteService.getReportFileByName(getCorrectName(file));
        InputStream stream = null;
        if (null != reportFile) {
            try {
                stream = new ByteArrayInputStream(reportFile.getContent().getBytes());
            } catch (Exception e) {
                throw new ReportException(e);
            }
        }
        return stream;
    }

    /**
     * 根据报表名，删除指定的报表文件
     *
     * @param file 报表名称
     */
    @Override
    public void deleteReport(String file) {
        IReportTemplteService.removeReportFileByName(getCorrectName(file));
    }

    /**
     * 获取所有的报表文件
     *
     * @return 返回报表文件列表
     */
    @Override
    public List<ReportFile> getReportFiles() {
        List<ReportTemplte> list = IReportTemplteService.listAllReportFile();
        List<ReportFile> reportList = new ArrayList<>();
        for (ReportTemplte file : list) {
            reportList.add(new ReportFile(file.getName(), file.getModifyTime()));
        }
        return reportList;
    }

    /**
     * 保存报表文件
     *
     * @param file 报表名称
     * @param content 报表的XML内容
     */
    @Override
    public void saveReport(String file, String content) {
        file = getCorrectName(file);
        ReportTemplte reportFile = IReportTemplteService.getReportFileByName(file);
        if(null == reportFile){
            reportFile = new ReportTemplte();
            reportFile.setName(file);
            reportFile.setContent(content);
            IReportTemplteService.saveReportFile(reportFile);
        }else{
            reportFile.setContent(content);
            IReportTemplteService.updateReportFile(reportFile);
        }
    }

    /**
     * @return 返回存储器名称
     */
    @Override
    public String getName() {
        return NAME;
    }

    /**
     * @return 返回是否禁用
     */
    @Override
    public boolean disabled() {
        return disabled;
    }

    /**
     * @return 返回报表文件名前缀
     */
    @Override
    public String getPrefix() {
        return prefix;
    }

    /**
     * 获取没有前缀的文件名
     *
     * @param name 报表名称
     */
    public String getCorrectName(String name){
        return super.getCorrectName(name);
    }
}