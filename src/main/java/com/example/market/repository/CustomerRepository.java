package com.example.market.repository;

import com.example.market.entity.CustomerEntity;

/**
 * Author:ZhuQing
 * Date:2017/11/14 15:34
 */
public interface CustomerRepository extends BaseRepository<CustomerEntity> {
    CustomerEntity findByCustomerName(String customerName);

    CustomerEntity findByPhoneNum(String phoneNum);

    CustomerEntity findByCustomerAddr(String customerAddr);
}
