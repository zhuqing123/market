package com.example.market.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Author:ZhuQing
 * Date:2017/11/14 16:21
 */
public class VegetablesVo {

    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date lastModifyTime;

    private String vegeName;

    private String vegeCode;

    private Double price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public String getVegeName() {
        return vegeName;
    }

    public void setVegeName(String vegeName) {
        this.vegeName = vegeName;
    }

    public String getVegeCode() {
        return vegeCode;
    }

    public void setVegeCode(String vegeCode) {
        this.vegeCode = vegeCode;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
