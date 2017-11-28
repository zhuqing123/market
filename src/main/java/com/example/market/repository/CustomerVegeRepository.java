package com.example.market.repository;

import com.example.market.entity.CustomerVegeEntity;

import java.util.List;

/**
 * Author:ZhuQing
 * Date:2017/11/23 17:18
 */
public interface CustomerVegeRepository extends BaseRepository<CustomerVegeEntity> {
    List<CustomerVegeEntity> findByVegeId(String id);

    List<CustomerVegeEntity> findByCustmerId(String id);
}
