package com.example.market.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Author:ZhuQing
 * Date:2017/11/22 20:28
 */
@Table(name = "t_type_discount")
@Entity
public class TypeDiscountEntity extends BaseEntity{

    @Column(name = "t_type_")
    private String type;

    @Column(name = "t_discount_",columnDefinition = "double(2,1)")
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
