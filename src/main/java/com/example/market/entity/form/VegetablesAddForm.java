package com.example.market.entity.form;

import javax.validation.constraints.NotNull;

/**
 * Author:ZhuQing
 * Date:2017/11/16 17:08
 */
public class VegetablesAddForm {

    @NotNull(message = "菜名是必填")
    private String vegeName;

    @NotNull(message = "菜编码是必填")
    private String vegeCode;

    @NotNull(message = "价格是必填")
    private Double price;

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
