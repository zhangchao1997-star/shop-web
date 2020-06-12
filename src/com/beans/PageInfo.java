package com.beans;

/**
 * author zhangchao
 * 分页实体类
 */
public class PageInfo {
    private int pageSize; //当前页面的大小
    private int rowCount; //一共有多少条数据
    private int pageIndex; //当前是第几页
    private int pageCount; //一共要分多少页
    private int beginRow; //要从第几行开始查
    private boolean hasNext;//是否有下一页
    private boolean hasPre;//是否有上一页

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getBeginRow() {
        return beginRow;
    }

    public void setBeginRow(int beginRow) {
        this.beginRow = beginRow;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public boolean isHasPre() {
        return hasPre;
    }

    public void setHasPre(boolean hasPre) {
        this.hasPre = hasPre;
    }
}
