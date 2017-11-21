package com.example.market.entity;

import java.util.Date;

/**
 * Author:ZhuQing
 * Date:2017/11/14 16:36
 */
//@Entity
//@Table(name = "t_customer_vege_")
public class CustomerVegeEntity extends BaseEntity {

    private String custmerId;

    private String vegeId;

    private Date sendDate;

    private Double price;

    private Double totlePrice;

    private Double count;
}
