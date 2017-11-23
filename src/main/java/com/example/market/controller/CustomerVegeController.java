package com.example.market.controller;

import com.example.market.service.CustomerVegeService;
import com.example.market.utils.ResponseView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

/**
 * Author:ZhuQing
 * Date:2017/11/23 17:16
 */
@RestController
public class CustomerVegeController extends BaseController {

    @Autowired
    private CustomerVegeService customerVegeService;

    @GetMapping("/get/customerOrder")
    public ResponseEntity getCustomerOrder(){
        ResponseView view=this.customerVegeService.getCustomerOrder();
        return new ResponseEntity(view, HttpStatus.OK);
    }

    @PostMapping("/add/customerOrder")
    public ResponseEntity addCustomerOrder(){
        ResponseView view=this.customerVegeService.addCustomerOrder();
        return new ResponseEntity(view, HttpStatus.OK);
    }

    @PutMapping("/edit/customerOrder")
    public ResponseEntity editCustomerOrder(){
        ResponseView view=this.customerVegeService.editCustomerOrder();
        return new ResponseEntity(view, HttpStatus.OK);
    }

    @DeleteMapping("/delete/customerOrder")
    public ResponseEntity deleteCustomerOrder(@NotNull String ids){
        List<String> list = Arrays.asList(ids.split(","));
        ResponseView view=this.customerVegeService.deleteCustomerOrder(list);
        return new ResponseEntity(view, HttpStatus.OK);
    }
}
