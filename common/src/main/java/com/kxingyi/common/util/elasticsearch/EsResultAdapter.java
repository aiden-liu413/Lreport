package com.kxingyi.common.util.elasticsearch;

import java.util.List;
import java.util.Map;

public interface EsResultAdapter {
    List getEntity(List<Map<String, Object>> esData);
}
