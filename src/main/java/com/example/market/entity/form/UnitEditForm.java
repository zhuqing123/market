package com.example.market.entity.form;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * Author:ZhuQing
 * Date:2017/11/28 20:06
 */
public class UnitEditForm {

    @NotNull(message = "主键不能为空")
    private String id;

    @Length(min = 1,max = 15,message = "单位名长度不对")
    private String unitName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
}
