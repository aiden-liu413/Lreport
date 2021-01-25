package com.bstek.ureport.console.html;

import java.util.Map;

/**
 * @author byliu
 **/
public interface ITaskParamProvider {
    Map<String, Object> getParamsByTaskId(String taskId);
}
