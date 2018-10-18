package com.tonybuilder.aospinsight.net.model;

import androidx.annotation.NonNull;

/**
 * Simplified class for PageInfo
 * total: 1709,
 * pageNum: 14,
 * pageSize: 10,
 * size: 10,
 * startRow: 131,
 * endRow: 140,
 * pages: 171,
 * prePage: 13,
 * nextPage: 15
 */
public class PagingInfo {
    private long total = 0;
    private int pageNum = 0;
    private int pageSize = 0;
    private int startRow = 0;
    private int endRow = 0;
    private int pages = 0;
    private int prePage = 0;
    private int nextPage = 0;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPrePage() {
        return prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[" + total + ",");
        sb.append(" " + pageNum + ",");
        sb.append(" " + pageSize + ",");
        sb.append(" " + startRow + ",");
        sb.append(" " + endRow + ",");
        sb.append(" " + pages + ",");
        sb.append(" " + prePage + ",");
        sb.append(" " + nextPage + "]");
        return sb.toString();
    }
}
