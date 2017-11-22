package com.example.market.controller;

import com.example.market.entity.form.CustomerAddForm;
import com.example.market.entity.form.CustomerEditForm;
import com.example.market.entity.form.CustomerForm;
import com.example.market.service.CustomerService;
import com.example.market.utils.ResponseView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

@RestController
public class CostomerController extends BaseController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/get/customer")
    public ResponseEntity findAllCustomer(@Valid CustomerForm form) {
        ResponseView view = this.customerService.findAllCustomer(form);
        return new ResponseEntity(view, HttpStatus.OK);
    }

    @PostMapping("/add/customer")
    public ResponseEntity addCustomer(@Valid CustomerAddForm form) {
        ResponseView view = this.customerService.addCustomer(form);
        return new ResponseEntity(view,HttpStatus.OK);
    }

    @PutMapping("/edit/customer")
    public ResponseEntity editCustomer(@Valid CustomerEditForm form) {
        ResponseView view = this.customerService.editCustomer(form);
        return new ResponseEntity(view,HttpStatus.OK);
    }

    @DeleteMapping("/delete/customer")
    public   ResponseEntity deleteCustomer(@NotNull String ids){
        List<String> list = Arrays.asList(ids.split(","));
        return new ResponseEntity(this.customerService.deleteCustomer(list),HttpStatus.OK);
    }
}
