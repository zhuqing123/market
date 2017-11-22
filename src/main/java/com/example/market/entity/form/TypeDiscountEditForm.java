package com.example.market.entity.form;

import javax.validation.constraints.NotNull;

/**
 * Author:ZhuQing
 * Date:2017/11/22 20:58
 */
public class TypeDiscountEditForm extends TypeDiscountAddForm {

    @NotNull(message = "请选择一条记录")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
