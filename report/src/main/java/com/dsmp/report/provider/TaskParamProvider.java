package com.dsmp.report.provider;

import com.bstek.ureport.console.html.ITaskParamProvider;
import com.dsmp.common.util.JsonUtils;
import com.dsmp.report.common.domain.ReportTask;
import com.dsmp.report.web.repository.ReportTaskRepository;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

/**
 * @author byliu
 **/
@Component
public class TaskParamProvider implements ITaskParamProvider {
    @Autowired
    ReportTaskRepository reportTaskRepository;
    @Override
    public Map<String, Object> getParamsByTaskId(String s) {
        ReportTask task = null;
        Map<String, Object> params = null;
        Optional<ReportTask> byId = reportTaskRepository.findById(s);
        if(byId.isPresent()){
            task = byId.get();
        }
        String taskParamsJson = task.getParams();
        params = StringUtils.isBlank(taskParamsJson) ? Maps.newHashMap() : JsonUtils.toMap(taskParamsJson);
        return params;
    }
}
