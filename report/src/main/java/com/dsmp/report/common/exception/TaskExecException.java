package com.dsmp.report.common.exception;

import com.kxingyi.common.exception.BusinessException;

/**
 * @author byliu
 **/
public class TaskExecException extends BusinessException {
    String taskId;
    String templteId;
    public TaskExecException() {
    }

    public TaskExecException(String message) {
        super(message);
    }

    public TaskExecException(String taskId, String templteId, String message) {
        super(message);
        this.taskId=taskId;
        this.templteId=templteId;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getTemplteId() {
        return templteId;
    }
}
