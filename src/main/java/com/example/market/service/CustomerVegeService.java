package com.example.market.service;

import com.example.market.entity.form.CustomerVegeAddForm;
import com.example.market.entity.form.CustomerVegeEditForm;
import com.example.market.entity.form.CustomerVegeForm;
import com.example.market.utils.MarketException;
import com.example.market.utils.ResponseView;

import java.util.List;

/**
 * Author:ZhuQing
 * Date:2017/11/23 17:17
 */
public interface CustomerVegeService {
    ResponseView getCustomerOrder(CustomerVegeForm form);

    ResponseView addCustomerOrder(CustomerVegeAddForm form) throws MarketException;

    ResponseView editCustomerOrder(CustomerVegeEditForm form) throws MarketException;

    ResponseView deleteCustomerOrder(List<String> list) throws MarketException;
}
