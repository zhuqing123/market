package com.example.market.entity.form;

import org.apache.commons.lang3.StringUtils;

/**
 * Author:ZhuQing
 * Date:2017/11/14 15:56
 */
public class PageForm {

    private int page=1;

    private int rows=5;

    private String property="createTime";

    private String direction="DESC";

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        if (StringUtils.isBlank(property)){
            property="createTime";
        }
        this.property = property;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        if (StringUtils.isBlank(direction)){
            direction="DESC";
        }
        this.direction = "ASC".equals(direction.toUpperCase()) ? "ASC" : "DESC";
    }
}