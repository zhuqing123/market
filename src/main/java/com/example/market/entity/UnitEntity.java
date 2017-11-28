package com.example.market.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Author:ZhuQing
 * Date:2017/11/28 19:37
 */
@Table(name = "t_unit_")
@Entity
public class UnitEntity extends BaseEntity {

    @Column(name = "unit_name_")
    private String unitName;

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
}
