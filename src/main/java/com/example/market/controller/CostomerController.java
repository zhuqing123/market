package com.example.market.controller;

import com.example.market.entity.form.CustomerAddForm;
import com.example.market.entity.form.CustomerEditForm;
import com.example.market.entity.form.CustomerForm;
import com.example.market.service.CustomerService;
import com.example.market.utils.ResponseView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class CostomerController extends BaseController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/get/customer")
    private ResponseEntity findAllCustomer(@Valid CustomerForm form) {
        ResponseView view = this.customerService.findAllCustomer(form);
        return new ResponseEntity(view, HttpStatus.OK);
    }

    @PostMapping("/add/customer")
    private ResponseEntity addCustomer(@Valid CustomerAddForm form) {
        ResponseView view = this.customerService.addCustomer(form);
        return new ResponseEntity(view,HttpStatus.OK);
    }

    @PutMapping("/edit/customer")
    private ResponseEntity editCustomer(@Valid CustomerEditForm form) {
        ResponseView view = this.customerService.editCustomer(form);
        return new ResponseEntity(view,HttpStatus.OK);
    }
}
