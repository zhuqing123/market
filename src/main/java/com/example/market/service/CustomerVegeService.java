package com.example.market.service;

import com.example.market.utils.ResponseView;

import java.util.List;

/**
 * Author:ZhuQing
 * Date:2017/11/23 17:17
 */
public interface CustomerVegeService {
    ResponseView getCustomerOrder();

    ResponseView addCustomerOrder();

    ResponseView editCustomerOrder();

    ResponseView deleteCustomerOrder(List<String> list);
}
