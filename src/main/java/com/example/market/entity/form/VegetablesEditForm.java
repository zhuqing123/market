package com.example.market.entity.form;

import javax.validation.constraints.NotNull;

/**
 * Author:ZhuQing
 * Date:2017/11/20 16:15
 */
public class VegetablesEditForm {

    @NotNull(message = "主键不能为空")
    private String id;

    private String vegeName;

    private String vegeCode;

    private Double price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
