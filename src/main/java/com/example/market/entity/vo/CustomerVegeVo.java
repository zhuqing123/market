package com.example.market.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Author:ZhuQing
 * Date:2017/11/28 15:16
 */
public class CustomerVegeVo {

    private String id;

    //客户id
    private String custmerId;

    //蔬菜id
    private String vegeId;

    //送货时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date sendDate;

    //出售价格
    private Double price= Double.valueOf(0);

    //单品总价格
    private Double totlePrice= Double.valueOf(0);

    //数量
    private Double count= Double.valueOf(0);

    //利润
    private Double profit= Double.valueOf(0);

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date lastModifyTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustmerId() {
        return custmerId;
    }

    public void setCustmerId(String custmerId) {
        this.custmerId = custmerId;
    }

    public String getVegeId() {
        return vegeId;
    }

    public void setVegeId(String vegeId) {
        this.vegeId = vegeId;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTotlePrice() {
        return totlePrice;
    }

    public void setTotlePrice(Double totlePrice) {
        this.totlePrice = totlePrice;
    }

    public Double getCount() {
        return count;
    }

    public void setCount(Double count) {
        this.count = count;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
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
}
