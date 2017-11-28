package com.example.market.service;

import com.example.market.entity.form.UnitEditForm;
import com.example.market.utils.MarketException;
import com.example.market.utils.ResponseView;

import java.util.List;

/**
 * Author:ZhuQing
 * Date:2017/11/28 19:40
 */
public interface UnitService {
    ResponseView getUnit();

    ResponseView addUnit(String unitName) throws MarketException;

    ResponseView editUnit(UnitEditForm from) throws MarketException;

    ResponseView deleteType(List<String> list) throws MarketException;
}
