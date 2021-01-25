package com.dsmp.report.runner;

import com.dsmp.report.common.domain.ReportTask;
import com.dsmp.report.common.enums.CycleEnum;
import com.dsmp.report.common.enums.EntityStatus;
import com.dsmp.report.common.exception.TaskRepeatException;
import com.dsmp.report.task.CronTaskRegistrar;
import com.dsmp.report.task.SchedulingRunnable;
import com.dsmp.report.web.repository.ReportTaskRepository;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author byliu
 **/
@Order(2)
@Component
public class SysJobRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(SysJobRunner.class);

    @Autowired
    private ReportTaskRepository reportTaskRepository;

    @Autowired
    private CronTaskRegistrar cronTaskRegistrar;

    @Override
    public void run(String... args) {
        // 初始加载数据库里状态为正常的定时任务
        List<ReportTask> normalTasksList = reportTaskRepository.findAllByStatus(EntityStatus.NORMAL);
        if (CollectionUtils.isNotEmpty(normalTasksList)) {
            int execTaskCount = 0;
            for (ReportTask task : normalTasksList) {
                SchedulingRunnable job = new SchedulingRunnable(task);
                String cronExpression = task.getCycleType().getCronExpression();
                //自定义任务初始化时 不添加
                if(!CycleEnum.CUSTOM.equals(task.getCycleType())){
                    try {
                        // 新增任务 outislog传入false 代表不打印任务容器信息
                        cronTaskRegistrar.addCronTask(job, cronExpression, false);
                    } catch (TaskRepeatException e) {
                        continue;
                    }
                    execTaskCount++;
                }
            }
            logger.info("任务总数:{}, 注册任务数:{}, 定时任务已加载完毕....", normalTasksList.size(), execTaskCount);
            if(cronTaskRegistrar.isLog != null && cronTaskRegistrar.isLog )
            cronTaskRegistrar.showTaskContainer();
        }
    }
}
