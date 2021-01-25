package com.dsmp.report.task;

import com.dsmp.common.util.minio.MinIoComponent;
import com.dsmp.report.common.bo.EmailBo;
import com.dsmp.report.common.domain.ReportFile;
import com.dsmp.report.common.enums.ReportEnum;
import com.dsmp.report.utils.EmailUtils;
import com.google.common.collect.Maps;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.util.Map;

/**
 * @author byliu
 **/
public class EmailRunnable implements Runnable {

    private EmailBo email;
    private final Map<String, InputStream> inputStreamMap = Maps.newHashMap();
    private String[] toEmail;
    private ReportFile file;

    public EmailRunnable() {

    }

    public EmailRunnable(EmailBo email, ReportFile file, String[] toEmail) {
        this.email = email;
        this.file = file;
        this.toEmail = toEmail;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used to create a thread, starting the thread
     * causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        InputStream pdf = MinIoComponent.getObject(file.getCreateTime(), file.getMappingName(), ReportEnum.PDF.getSuffix());
        InputStream excel = MinIoComponent.getObject(file.getCreateTime(), file.getMappingName(), ReportEnum.EXCEL.getSuffix());
        InputStream word = MinIoComponent.getObject(file.getCreateTime(), file.getMappingName(), ReportEnum.WORD.getSuffix());
        inputStreamMap.put(file.getName() + "." + ReportEnum.PDF.getSuffix(), pdf);
        inputStreamMap.put(file.getName() + "." + ReportEnum.WORD.getSuffix(), word);
        inputStreamMap.put(file.getName() + "." + ReportEnum.EXCEL.getSuffix(), excel);
        if (null != email && toEmail.length > 0) {
            EmailUtils.sendMessage(email, toEmail,
                    file.getName(), "附件为任务执行所生成的报表文件，请查阅！", inputStreamMap);
        }
        inputStreamMap.forEach((k, v) -> {
            IOUtils.closeQuietly(v);
        });
    }
}
