package com.example.market.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Author:ZhuQing
 * Date:2017/11/14 16:36
 */
//@Entity
//@Table(name = "t_customer_vege_")
public class CustomerVegeEntity extends BaseEntity {

    private String custmerId;//客户id

    private String vegeId;//蔬菜id

    private Date sendDate;//送货时间

    private Double price;//出售价格

    private Double totlePrice;//单品总价

    private Double discountPrice;

    private Double count;//数量


}
