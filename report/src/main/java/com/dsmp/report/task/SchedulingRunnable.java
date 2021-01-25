package com.dsmp.report.task;

import com.bstek.ureport.export.ExportConfigure;
import com.bstek.ureport.export.ExportConfigureImpl;
import com.bstek.ureport.export.ExportManager;
import com.dsmp.common.util.JsonUtils;
import com.dsmp.common.util.UUIDTool;
import com.dsmp.common.util.minio.MinIoComponent;
import com.dsmp.report.common.bo.EmailBo;
import com.dsmp.report.common.domain.ReportFile;
import com.dsmp.report.common.domain.ReportTask;
import com.dsmp.report.common.domain.ReportTaskParam;
import com.dsmp.report.common.domain.ReportTemplte;
import com.dsmp.report.common.enums.CycleEnum;
import com.dsmp.report.common.enums.EntityStatus;
import com.dsmp.report.common.enums.ReportEnum;
import com.dsmp.report.utils.SpringContextUtils;
import com.dsmp.report.utils.ValidatorUtils;
import com.dsmp.report.utils.WebdriveUtils;
import com.dsmp.report.web.repository.ReportFileRepository;
import com.dsmp.report.web.repository.ReportRepository;
import com.dsmp.report.web.repository.ReportTaskRepository;
import com.dsmp.report.web.service.IReportParamService;
import com.google.common.collect.Maps;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author byliu
 **/
public class SchedulingRunnable implements Runnable {


    private static final Logger logger = LoggerFactory.getLogger(SchedulingRunnable.class);

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH_mm_ss");

    private ReportTask task;

    public SchedulingRunnable() {
    }


    public SchedulingRunnable(ReportTask task) {
        this.task = task;
    }

    @Override
    public void run() {
        logger.info("定时任务开始执行 - task：{}", task);
        long startTime = System.currentTimeMillis();
        try {
            ExportManager exportManager = SpringContextUtils.getBean(ExportManager.class);
            ReportRepository reportRepository = SpringContextUtils.getBean(ReportRepository.class);
            ReportFileRepository fileRepository = SpringContextUtils.getBean(ReportFileRepository.class);
            ReportTaskRepository taskRepository = SpringContextUtils.getBean(ReportTaskRepository.class);
            IReportParamService paramService = SpringContextUtils.getBean(IReportParamService.class);
            ThreadPoolTaskExecutor threadPoolTaskExecutor = SpringContextUtils.getBean(ThreadPoolTaskExecutor.class);

            List<String> reportIds = Arrays.asList(task.getReportIds().split(","));
            reportIds.forEach(reportId -> {

                Optional<ReportTemplte> report = reportRepository.findById(reportId);
                ReportTemplte reportTemplte = report.orElseThrow(() -> new RuntimeException("can not find uReportFile"));
                // 任务参数用于生成报表文件
                Map<String, Object> parameters = StringUtils.isBlank(task.getParams()) ? Maps.newHashMap() : JsonUtils.toMap(task.getParams());
                // 任务参数用于发送邮件
                ReportTaskParam reportTaskParam = JsonUtils.toObject(task.getParams(), ReportTaskParam.class);
                // 文件对象
                ReportFile file = new ReportFile();
                file.setMappingName(UUIDTool.getPrimarykeyId());
                file.setTaskId(task.getId());
                file.setName(task.getName() + "_" + reportTemplte.getName() + "_" + LocalDateTime.now().format(FORMATTER));
                file.setTemplteId(reportId);
                file.setStatus(EntityStatus.NORMAL);
                file.setUserId(task.getUserId());

                // 预览操作 参数:任务id，查询时间区间
                WebdriveUtils.preview(reportTemplte.getName(), task, parameters);
                Map<String, InputStream> inputStreamMap = new HashMap<>();
                Arrays.asList(ReportEnum.values()).forEach(reportEnum -> {
                    if (ReportEnum.ALL.equals(reportEnum)) {
                    } else {
                        ExportConfigure configure = new ExportConfigureImpl("dsmp:" + reportTemplte.getName(),
                                parameters,
                                new ByteArrayOutputStream());
                        InputStream in = null;
                        try {
                            switch (reportEnum) {
                                case PDF:
                                    exportManager.exportPdf(configure);
                                    break;
                                case WORD:
                                    exportManager.exportWord(configure);
                                    break;
                                case EXCEL:
                                    exportManager.exportExcel(configure);
                                    break;
                                default:
                                    break;
                            }

                            in = parse(configure.getOutputStream());
                            inputStreamMap.put(file.getName() + "." + reportEnum.getSuffix(), in);
                            // 上传文件
                            MinIoComponent.putObject(in, new Date(), file.getMappingName(), reportEnum.getSuffix());
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            IOUtils.closeQuietly(configure.getOutputStream());
                            IOUtils.closeQuietly(in);
                        }
                    }
                });

                // 保存报表文件数据
                fileRepository.save(file);
                // 如果是自定义周期任务，在任务完成后将任务状态改为完成
                if (CycleEnum.CUSTOM.equals(task.getCycleType())) {
                    task.setStatus(EntityStatus.CLOSED);
                    taskRepository.save(task);
                }
                EmailBo emailParam = paramService.getEmailParam();
                // 如果邮件相关参数正常  执行发送邮件
                if (ValidatorUtils.validateEntity(emailParam).size() == 0) {
                    List<String> recipients = reportTaskParam.getRecipients();
                    // 发送邮件
                    threadPoolTaskExecutor.execute(new EmailRunnable(emailParam, file, recipients.toArray(new String[recipients.size()])));
                }
            });
        } catch (Exception ex) {
            logger.error(String.format("定时任务执行异常 - task：%s", task), ex);
        }
        long times = System.currentTimeMillis() - startTime;
        logger.info("定时任务执行结束 - task：{}，耗时：{} 毫秒", task, times);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SchedulingRunnable that = (SchedulingRunnable) o;
        if (null == task.getParams()) {
            return task.getCycleType().equals(that.task.getCycleType()) &&
                    task.getReportIds().equals(that.task.getReportIds());
        }

        return task.getCycleType().equals(that.task.getCycleType()) &&
                task.getReportIds().equals(that.task.getReportIds()) &&
                task.getParams().equals(that.task.getParams());
    }

    @Override
    public int hashCode() {
        if (null == task.getParams()) {
            return Objects.hash(task.getCycleType(), task.getReportIds());
        }

        return Objects.hash(task.getParams(), task.getCycleType(), task.getReportIds());
    }

    public ByteArrayInputStream parse(final OutputStream out) throws Exception {
        ByteArrayOutputStream baos;
        if (null == out) {
            return null;
        }
        baos = (ByteArrayOutputStream) out;
        final ByteArrayInputStream swapStream = new ByteArrayInputStream(baos.toByteArray());
        return swapStream;
    }

    @Override
    public String toString() {
        return "SchedulingRunnable{" +
                "task=" + task +
                '}';
    }
}