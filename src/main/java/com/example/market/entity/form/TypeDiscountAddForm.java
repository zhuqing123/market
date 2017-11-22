package com.example.market.entity.form;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;

/**
 * Author:ZhuQing
 * Date:2017/11/22 20:47
 */
public class TypeDiscountAddForm {

    @Pattern(regexp = "^[A-Z]{1}$",message = "只能为大写字母格式")
    private String type;

    @Digits(integer = 1,fraction = 1,message = "格式不对")
    private Double discount;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }
}
