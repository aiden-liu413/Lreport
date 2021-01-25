package com.kxingyi.common.web.request;

import com.kxingyi.common.enums.ImportType;

/**
 * @author: wu_chao
 * @date: 2020/10/15
 * @time: 13:45
 */
public class ImportTypeCellOption implements CellOption {
    @Override
    public String[] getOptions() {
        String[] result = new String[ImportType.values().length];
        for (int i = 0; i < ImportType.values().length; i++) {
            result[i] = ImportType.values()[i].getDetail();
        }
        return result;
    }
}
