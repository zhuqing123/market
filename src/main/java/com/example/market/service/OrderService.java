package com.example.market.service;

import com.example.market.entity.form.OrderOperatyFrom;
import com.example.market.entity.form.PageForm;
import com.example.market.utils.MarketException;
import com.example.market.utils.ResponseView;

/**
 * @Author ZhuQing
 * @Date: 2018/8/14  09:25
 */
public interface OrderService {

    ResponseView addOrder(OrderOperatyFrom from) throws MarketException;

    ResponseView editOrder(String id, OrderOperatyFrom from) throws MarketException;

    ResponseView findAll(PageForm pageForm);

    ResponseView findAll();
}
