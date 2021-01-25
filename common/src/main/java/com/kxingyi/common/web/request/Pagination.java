package com.kxingyi.common.web.request;

/**
 * @author: wu_chao
 * @date: 2020/10/14
 * @time: 19:34
 */
public class Pagination {
    private int page = 1;
    //默认每页数量【10】
    private int size = 10;
    //是否分页，默认分页
    private boolean paged = true;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isPaged() {
        return paged;
    }

    public void setPaged(boolean paged) {
        this.paged = paged;
    }
}
