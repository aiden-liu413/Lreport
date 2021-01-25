package com.kxingyi.common.util.elasticsearch;


import java.util.Collections;
import java.util.List;
import java.util.Map;

public class EsPage {
    public EsPage() {
    }

    /**
     * 当前页
     */
    private int page;
    /**
     * 每页显示多少条
     */
    private int size;

    /**
     * 总记录数
     */
    private int totalElements;
    /**
     * 本页的数据列表
     */
    private Object content;

    /**
     * 总页数
     */
    private int pageCount;
    /**
     * 页码列表的开始索引（包含）
     */
    private int beginPageIndex;
    /**
     * 页码列表的结束索引（包含）
     */
    private int endPageIndex;


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

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getBeginPageIndex() {
        return beginPageIndex;
    }

    public void setBeginPageIndex(int beginPageIndex) {
        this.beginPageIndex = beginPageIndex;
    }

    public int getEndPageIndex() {
        return endPageIndex;
    }

    public void setEndPageIndex(int endPageIndex) {
        this.endPageIndex = endPageIndex;
    }

    @Override
    public String toString() {
        return "EsPage{" +
                "page=" + page +
                ", size=" + size +
                ", totalElements=" + totalElements +
                ", content=" + content +
                ", pageCount=" + pageCount +
                ", beginPageIndex=" + beginPageIndex +
                ", endPageIndex=" + endPageIndex +
                '}';
    }

    /**
     * 返回一个空分页
     *
     * @return
     */
    public static EsPage EmptyPage() {
        EsPage esPage = new EsPage();
        esPage.setPage(0);
        esPage.setSize(0);
        esPage.setPageCount(0);
        esPage.setTotalElements(0);
        esPage.setContent(Collections.emptyList());
        return esPage;
    }


    /**
     * 只接受前4个必要的属性，会自动的计算出其他3个属性的值
     *
     * @param page
     * @param size
     * @param totalElements
     * @param content
     */


    public EsPage(int page, int size, int totalElements, List<Map<String, Object>> content) {
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.content = content;

        // 计算总页码
        pageCount = (totalElements + size - 1) / size;

        // 计算 beginPageIndex 和 endPageIndex
        // >> 总页数不多于10页，则全部显示
        if (pageCount <= 10) {
            beginPageIndex = 1;
            endPageIndex = pageCount;
        }
        // 总页数多于10页，则显示当前页附近的共10个页码
        else {
            // 当前页附近的共10个页码（前4个 + 当前页 + 后5个）
            beginPageIndex = page - 4;
            endPageIndex = page + 5;
            // 当前面的页码不足4个时，则显示前10个页码
            if (beginPageIndex < 1) {
                beginPageIndex = 1;
                endPageIndex = 10;
            }
            // 当后面的页码不足5个时，则显示后10个页码
            if (endPageIndex > pageCount) {
                endPageIndex = pageCount;
                beginPageIndex = pageCount - 10 + 1;
            }
        }
    }

    /**
     * 只接受前4个必要的属性，会自动的计算出其他3个属性的值
     *
     * @param page
     * @param size
     * @param totalElements
     * @param content
     */


    public EsPage(int page, int size, int totalElements, List<Map<String, Object>> content, EsResultAdapter esResultAdapter) {
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.content = esResultAdapter.getEntity(content);

        // 计算总页码
        pageCount = (totalElements + size - 1) / size;

        // 计算 beginPageIndex 和 endPageIndex
        // >> 总页数不多于10页，则全部显示
        if (pageCount <= 10) {
            beginPageIndex = 1;
            endPageIndex = pageCount;
        }
        // 总页数多于10页，则显示当前页附近的共10个页码
        else {
            // 当前页附近的共10个页码（前4个 + 当前页 + 后5个）
            beginPageIndex = page - 4;
            endPageIndex = page + 5;
            // 当前面的页码不足4个时，则显示前10个页码
            if (beginPageIndex < 1) {
                beginPageIndex = 1;
                endPageIndex = 10;
            }
            // 当后面的页码不足5个时，则显示后10个页码
            if (endPageIndex > pageCount) {
                endPageIndex = pageCount;
                beginPageIndex = pageCount - 10 + 1;
            }
        }
    }


}
