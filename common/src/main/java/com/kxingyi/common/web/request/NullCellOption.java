package com.kxingyi.common.web.request;

/**
 * @author: wu_chao
 * @date: 2020/10/15
 * @time: 10:53
 */
public final class NullCellOption implements CellOption{

    public String[] getOptions() {
        return new String[0];
    }
}
