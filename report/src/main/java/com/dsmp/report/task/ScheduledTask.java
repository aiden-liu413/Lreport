package com.dsmp.report.task;

import java.util.concurrent.ScheduledFuture;

/**
 * @author byliu
 **/
public class ScheduledTask {
    volatile ScheduledFuture<?> future;

    /**
     * 取消定时任务
     */
    public void cancel() {
        ScheduledFuture<?> future = this.future;
        if (future != null) {
            future.cancel(true);
        }
    }
}
