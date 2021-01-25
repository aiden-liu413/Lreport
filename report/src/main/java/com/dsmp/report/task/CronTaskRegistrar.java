package com.dsmp.report.task;

import com.dsmp.report.common.exception.TaskRepeatException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.config.CronTask;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author byliu
 **/
@Component
public class CronTaskRegistrar implements DisposableBean {

    private static final Logger logger = LoggerFactory.getLogger(CronTaskRegistrar.class);
    public static Boolean isLog = false;
    private final Map<Runnable, ScheduledTask> scheduledTasks = new ConcurrentHashMap<>(16);
    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor; // 自定义任务Executor
    @Autowired
    private TaskScheduler taskScheduler; // 周期型任务(天，周，月，年)Executor

    @Value("${task.debug}")
    public void setLog(Boolean log) {
        if(null != log)
        isLog = log;
    }

    public TaskScheduler getScheduler() {
        return this.taskScheduler;
    }

    /**
     * @param task:
     * @param cronExpression:
     * @param outIsLog:       外部的是否打印任务容器的参数  null:使用配置文件的islog参数 false：不打印 true 打印
     **/
    public void addCronTask(Runnable task, String cronExpression, Boolean outIsLog) {
        //
        if (StringUtils.isBlank(cronExpression)) {
            threadPoolTaskExecutor.execute(task);
        } else {
            addCronTask(new CronTask(task, cronExpression), outIsLog != null ? outIsLog : isLog);
        }
    }


    /**
     * @param cronTask:
     * @param isLog:
     **/
    public void addCronTask(CronTask cronTask, boolean isLog) {
        if (cronTask != null) {
            Runnable task = cronTask.getRunnable();
            if (this.scheduledTasks.containsKey(task)) {
                //removeCronTask(task, false);
                throw new TaskRepeatException("已存在相同任务,新建失败");
            }

            this.scheduledTasks.put(task, scheduleCronTask(cronTask));
            if (isLog)
                showTaskContainer();
        }
    }

    /**
     * @param task:
     **/
    public void removeCronTask(Runnable task) {
        ScheduledTask scheduledTask = this.scheduledTasks.remove(task);
        if (scheduledTask != null)
            scheduledTask.cancel();

        if (isLog) {
            showTaskContainer();
        }
    }

    /**
     * 打印任务容器信息
     **/
    public void showTaskContainer() {
        logger.info("任务容器:[");
        this.scheduledTasks.keySet().stream().forEach(e -> logger.info(e.toString()));
        logger.info("]");
    }

    /**
     * @param task:
     * @param isLog:
     **/
    public void removeCronTask(Runnable task, boolean isLog) {
        ScheduledTask scheduledTask = this.scheduledTasks.remove(task);
        if (scheduledTask != null)
            scheduledTask.cancel();

        if (isLog)
            showTaskContainer();
    }

    public ScheduledTask scheduleCronTask(CronTask cronTask) {
        ScheduledTask scheduledTask = new ScheduledTask();
        scheduledTask.future = this.taskScheduler.schedule(cronTask.getRunnable(), cronTask.getTrigger());

        return scheduledTask;
    }


    @Override
    public void destroy() {
        for (ScheduledTask task : this.scheduledTasks.values()) {
            task.cancel();
        }

        this.scheduledTasks.clear();
    }
}
