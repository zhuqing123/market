package com.example.market.service.Impl;

import com.example.market.repository.CustomerVegeRepository;
import com.example.market.service.CustomerVegeService;
import com.example.market.utils.ResponseView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author:ZhuQing
 * Date:2017/11/23 17:18
 */
@Service
public class CustomerVegeServiceImpl implements CustomerVegeService {

    @Autowired
    private CustomerVegeRepository customerVegeRepository;

    @Override
    public ResponseView getCustomerOrder() {
        return null;
    }

    @Override
    public ResponseView addCustomerOrder() {
        return null;
    }

    @Override
    public ResponseView editCustomerOrder() {
        return null;
    }

    @Override
    public ResponseView deleteCustomerOrder(List<String> list) {
        return null;
    }
}
