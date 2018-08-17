package com.example.market.entity.form;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author ZhuQing
 * @Date: 2018/8/14  09:36
 */

public class OrderOperatyFrom implements Serializable {

    @NotNull(message = "商品不能为空")
    @Length(max = 33, message = "最多33位")
    private String commodityId;

    @NotNull(message = "商品数量不能为空")
    @Digits(integer = 8, fraction = 2, message = "数量最多保存两位小数")
    private Double quantity;

    @NotNull(message = "折扣不能为空")
    @Digits(integer = 8, fraction = 2, message = "折扣最多保存两位小数")
    private Double rebate = Double.valueOf(1);

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

    public Double getRebate() {
        return rebate;
    }

    public void setRebate(Double rebate) {
        this.rebate = rebate;
    }
}
