package com.dsmp.report.task;

import com.dsmp.report.common.domain.ReportTask;
import com.dsmp.report.utils.SpringContextUtils;

import java.util.Objects;

/**
 * @author byliu
 **/
public class SchedulingRunnable implements Runnable {

    private ReportTask task;

    public SchedulingRunnable() {
    }


    public SchedulingRunnable(ReportTask task) {
        this.task = task;
    }

    @Override
    public void run() {

        TaskManager taskManager = SpringContextUtils.getBean(TaskManager.class);
        taskManager.exec(task);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
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

    @Override
    public String toString() {
        return "SchedulingRunnable{" +
                "task=" + task +
                '}';
    }
}