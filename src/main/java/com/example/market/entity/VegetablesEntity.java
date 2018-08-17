package com.example.market.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Author:ZhuQing
 * Date:2017/11/14 15:18
 */
@Entity
@Table(name = "t_vegetables_")
public class VegetablesEntity extends BaseEntity {

    @Column(name = "vege_name_",length = 100)
    private String vegeName;

    @Column(name = "vege_code_",length = 100)
    private String vegeCode;

    @Column(name = "price",columnDefinition = "double(10,2)")
    private Double price;

    @Column(name = "unit_id_")
    private String unitId;

    @Column(name = "sale_price",columnDefinition = "double(10,2)")
    private Double salePrice;

    @Column(name = "rebate",columnDefinition = "double(10,2)")
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

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public Double getRebate() {
        return rebate;
    }

    public void setRebate(Double rebate) {
        this.rebate = rebate;
    }
}
