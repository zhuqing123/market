package com.example.market.service;

import com.example.market.entity.form.CustomerAddForm;
import com.example.market.entity.form.CustomerEditForm;
import com.example.market.entity.form.CustomerForm;
import com.example.market.utils.ResponseView;

import java.util.List;

public interface CustomerService {
    ResponseView findAllCustomer(CustomerForm form);

    ResponseView addCustomer(CustomerAddForm form);

    ResponseView editCustomer(CustomerEditForm form);

    ResponseView deleteCustomer(List<String> list);
}
