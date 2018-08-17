package com.example.market.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author ZhuQing
 * @Date: 2018/8/14  09:14
 */
//@Entity
//@Table(name = "t_order")
public class OrderEntity extends BaseEntity {

    /**
     * 商品id
     */
    @Column(name = "commodity_id", columnDefinition = "double(10,2)")
    private String commodityId;

    /**
     * 数量
     */
    @Column(name = "quantity", columnDefinition = "double(10,2)")
    private Double quantity;

    /**
     * 总价
     */
    @Column(name = "total_price", columnDefinition = "double(10,2)")
    private Double totalPrice;

    /**
     * 销售价
     */
    @Column(name = "sale_price", columnDefinition = "double(10,2)")
    private Double salePrice;

    /**
     * 进价
     */
    @Column(name = "price", columnDefinition = "double(10,2)")
    private Double price;

    /**
     * 折扣
     */
    @Column(name = "rebate", columnDefinition = "double(10,2)")
    private Double rebate;

    /**
     * 利润
     */
    @Column(name = "rebate", columnDefinition = "double(10,2)")
    private Double profit;

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getRebate() {
        return rebate;
    }

    public void setRebate(Double rebate) {
        this.rebate = rebate;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }
}
