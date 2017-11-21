package com.example.market.entity.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:ZhuQing
 * Date:2017/11/14 16:20
 */
public class PageVo<T> {

    /**
     * 当前页
     */
    private int currentPage = 1;

    /**
     * 分页大小
     */
    private int pageSize = 5;

    /**
     * 前一页
     */
    private int prevPage;

    /**
     * 是不是第一页
     */
    private boolean isFirstPage;

    /**
     * 后一页
     */
    private int nextPage;

    /**
     * 是不是最后一页
     */
    private boolean isLastPage;

    /**
     * 当前页拥有的元素
     */
    private int currentPageElements;

    /**
     * 总页数
     */
    private int totalPage;

    /**
     * 总的条数
     */
    private long totalElements;


    /**
     * 查询的结果数据
     */
    private List<T> content = new ArrayList<>();

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPrevPage() {
        return prevPage;
    }

    public void setPrevPage(int prevPage) {
        this.prevPage = prevPage;
    }

    public boolean isFirstPage() {
        return isFirstPage;
    }

    public void setFirstPage(boolean firstPage) {
        isFirstPage = firstPage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public int getCurrentPageElements() {
        return currentPageElements;
    }

    public void setCurrentPageElements(int currentPageElements) {
        this.currentPageElements = currentPageElements;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }
}
