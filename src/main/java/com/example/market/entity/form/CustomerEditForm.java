package com.example.market.entity.form;

import javax.validation.constraints.NotNull;

public class CustomerEditForm extends  CustomerAddForm {

    @NotNull(message = "主键不能为空")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
