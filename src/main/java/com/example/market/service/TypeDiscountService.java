package com.example.market.service;

import com.example.market.entity.form.TypeDiscountAddForm;
import com.example.market.entity.form.TypeDiscountEditForm;
import com.example.market.utils.MarketException;
import com.example.market.utils.ResponseView;

import java.util.List;

/**
 * Author:ZhuQing
 * Date:2017/11/22 20:34
 */
public interface TypeDiscountService {
    ResponseView findAllType();

    ResponseView addType(TypeDiscountAddForm form) throws MarketException;

    ResponseView editType(TypeDiscountEditForm form) throws MarketException;

    ResponseView deleteType(List<String> list) throws MarketException;
}
