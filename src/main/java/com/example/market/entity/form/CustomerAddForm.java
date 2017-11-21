package com.example.market.entity.form;

import javax.validation.constraints.NotNull;

public class CustomerAddForm {

    @NotNull(message = "客户名称必填")
    private String customerName;
    @NotNull(message = "客户电话必填")
    private String phoneNum;
    @NotNull(message = "客户地址必填")
    private String customerAddr;
    @NotNull(message = "客户类型必填")
    private String type;

    public String getCustomerAddr() {
        return customerAddr;
    }

    public void setCustomerAddr(String customerAddr) {
        this.customerAddr = customerAddr;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
