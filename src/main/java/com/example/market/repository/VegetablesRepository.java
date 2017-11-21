package com.example.market.repository;

import com.example.market.entity.VegetablesEntity;

/**
 * Author:ZhuQing
 * Date:2017/11/14 15:38
 */
public interface VegetablesRepository extends BaseRepository<VegetablesEntity> {
    VegetablesEntity findByVegeName(String vegeName);

    VegetablesEntity findByVegeCode(String vegeCode);
}
