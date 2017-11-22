package com.example.market.repository;

import com.example.market.entity.TypeDiscountEntity;

/**
 * Author:ZhuQing
 * Date:2017/11/22 20:35
 */
public interface TypeDiscountRepository extends BaseRepository<TypeDiscountEntity> {
    TypeDiscountEntity findByType(String type);
}
