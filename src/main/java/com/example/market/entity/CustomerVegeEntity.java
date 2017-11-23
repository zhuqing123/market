package com.example.market.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Author:ZhuQing
 * Date:2017/11/14 16:36
 */
@Entity
@Table(name = "t_customer_vege_")
public class CustomerVegeEntity extends BaseEntity {

    @Column(name = "customer_id_")
    private String custmerId;//客户id

    @Column(name = "vege_id_")
    private String vegeId;//蔬菜id

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "send_date_")
    private Date sendDate;//送货时间

    @Column(name = "price_",columnDefinition = "double(10,2)")
    private Double price= Double.valueOf(0);//出售价格

    @Column(name = "totle_price_",columnDefinition = "double(10,2)")
    private Double totlePrice= Double.valueOf(0);//单品总价格

    @Column(name = "count_",columnDefinition = "double(10,2)")
    private Double count= Double.valueOf(0);//数量

    @Column(name = "profit_",columnDefinition = "double(10,2)")
    private Double profit= Double.valueOf(0);//利润

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
}
