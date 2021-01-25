package com.dsmp.report.web.service.impl;

import com.dsmp.common.exception.BusinessException;
import com.dsmp.common.util.minio.MinIoComponent;
import com.dsmp.report.common.domain.ReportFile;
import com.dsmp.report.common.enums.ReportEnum;
import com.dsmp.report.common.vo.ReportFileVo;
import com.dsmp.report.web.repository.ReportFileRepository;
import com.dsmp.report.web.request.ReportFilePagination;
import com.dsmp.report.web.service.IReportFileService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author byliu
 **/
@Service
public class ReportFileServiceImpl implements IReportFileService {

    @Autowired
    ReportFileRepository reportFileRepository;

    @Override
    public Page<ReportFileVo> pages(ReportFilePagination pagination) {

        PageRequest pageRequest = PageRequest.of(pagination.getPage(), pagination.getSize(), Sort.Direction.DESC, "createTime");
        Page<ReportFileVo> reportFiles = reportFileRepository.listWithPage(pagination.getContent(), pagination.getTaskId(),
                null, pagination.getTemplteId(), pagination.getStartTime(), pagination.getEndTime(), pagination.getContent(),
                pagination.getContent(), pageRequest);
        return reportFiles;
    }

    @Override
    @Transactional
    public void delete(List<String> ids) {
        List<ReportFile> reportFileList = reportFileRepository.findByIdIn(ids);
        deleteFile(reportFileList);
        reportFileRepository.deleteByIdIn(ids);
    }

    @Override
    @Transactional
    public void delete(Date startTime, Date endTime) {
        List<ReportFile> reportFileList = reportFileRepository.findByCreateTimeBetween(startTime, endTime);
        deleteFile(reportFileList);
        reportFileRepository.deleteByCreateTimeBetween(startTime, endTime);
    }

    @Override
    public void delete(Date expireTime) {
        List<ReportFile> reportFileList = reportFileRepository.findByCreateTimeLessThan(expireTime);
        deleteFile(reportFileList);
        reportFileRepository.deleteByCreateTimeLessThan(expireTime);
    }

    void deleteFile(List<ReportFile> reportFileList) {
        reportFileList.forEach(reportFile -> {
            MinIoComponent.removeObject(reportFile.getCreateTime(), reportFile.getMappingName(), ReportEnum.EXCEL.getSuffix());
            MinIoComponent.removeObject(reportFile.getCreateTime(), reportFile.getMappingName(), ReportEnum.WORD.getSuffix());
            MinIoComponent.removeObject(reportFile.getCreateTime(), reportFile.getMappingName(), ReportEnum.PDF.getSuffix());
        });
    }

    @Override
    public Object download(String fileId, ReportEnum reportEnum, HttpServletResponse response) {

        Optional<ReportFile> optionalReportFile = reportFileRepository.findById(fileId);
        ReportFile file = optionalReportFile.orElseThrow(() -> new BusinessException("无此文件"));
        ServletOutputStream outputStream = null;
        response.setContentType("application/octet-stream");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        try {
            outputStream = response.getOutputStream();
            if (ReportEnum.ALL.equals(reportEnum)) {
                response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(file.getName(),"UTF-8") + "." + ReportEnum.ALL.getSuffix());
                ZipOutputStream zou = new ZipOutputStream(outputStream);
                zou.setMethod(ZipOutputStream.DEFLATED);
                InputStream pdf = MinIoComponent.getObject(file.getCreateTime(), file.getMappingName(), ReportEnum.PDF.getSuffix());
                InputStream word = MinIoComponent.getObject(file.getCreateTime(), file.getMappingName(), ReportEnum.WORD.getSuffix());
                InputStream excel = MinIoComponent.getObject(file.getCreateTime(), file.getMappingName(), ReportEnum.EXCEL.getSuffix());
                write(zou, Arrays.asList(pdf, word, excel), file.getName(),
                        new String[]{ReportEnum.PDF.getSuffix(),
                                ReportEnum.WORD.getSuffix(),
                                ReportEnum.EXCEL.getSuffix()});
                IOUtils.closeQuietly(pdf);
                IOUtils.closeQuietly(word);
                IOUtils.closeQuietly(excel);
                IOUtils.closeQuietly(zou);
            } else {
                response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(file.getName() ,"UTF-8")+ "." + reportEnum.getSuffix());
                InputStream reportFile = MinIoComponent.getObject(file.getCreateTime(), file.getMappingName(), reportEnum.getSuffix());
                outputStream.write(FileCopyUtils.copyToByteArray(reportFile));
            }
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(outputStream);
        }
        return null;
    }


    void write(ZipOutputStream zou, List<InputStream> ins, String name, String[] suffixArr) throws IOException {
        for (int i = 0; i < ins.size(); i++) {
            zou.putNextEntry((new ZipEntry(name + "." + suffixArr[i])));
            byte[] b = new byte[1024];
            int length = 0;
            DataOutputStream os = new DataOutputStream(zou);
            while ((length = ins.get(i).read(b)) != -1) {
                os.write(b, 0, length);
            }
            zou.closeEntry();
        }
    }

}
