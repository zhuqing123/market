package com.example.market.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Author:ZhuQing
 * Date:2017/11/14 15:29
 */
@Entity
@Table(name = "t_customer_info")
public class CustomerEntity extends BaseEntity {

    @Column(name = "customer_name_",length = 100)
    private String customerName;

    @Column(name = "phone_num_",length = 50)
    private String phoneNum;

    @Column(name = "customer_addr_",length = 50)
    private String customerAddr;

    @Column(name = "type_id_")
    private String type;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }


    public String getCustomerAddr() {
        return customerAddr;
    }

    public void setCustomerAddr(String customerAddr) {
        this.customerAddr = customerAddr;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
