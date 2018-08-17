package com.example.market.entity.form;

import javax.validation.constraints.NotNull;

/**
 * Author:ZhuQing
 * Date:2017/11/16 17:08
 */
public class VegetablesAddForm {

    @NotNull(message = "名字是必填")
    private String vegeName;

    @NotNull(message = "编码是必填")
    private String vegeCode;

    @NotNull(message = "价格是必填")
    private Double price;

    @NotNull(message = "单位是必填")
    private String unitId;

    @NotNull(message = "销售价是必填")
    private Double salePrice;

    @NotNull(message = "折扣是必填")
    private Double rebate;

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

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public void setRebate(Double rebate) {
        this.rebate = rebate;
    }

    public Double getRebate() {
        return rebate;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public Double getSalePrice() {
        return salePrice;
    }
}
