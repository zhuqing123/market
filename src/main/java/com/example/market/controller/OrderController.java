package com.example.market.controller;

import com.example.market.entity.form.OrderOperatyFrom;
import com.example.market.entity.form.PageForm;
import com.example.market.service.OrderService;
import com.example.market.utils.MarketException;
import com.example.market.utils.ResponseView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author ZhuQing
 * @Date: 2018/8/14  09:24
 */
@RestController
public class OrderController extends BaseController {


    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @PostMapping("/add/order")
    public ResponseEntity addOrder(@Validated OrderOperatyFrom from) throws MarketException {
        ResponseView view = this.orderService.addOrder(from);
        return new ResponseEntity(view, HttpStatus.OK);
    }

    @PutMapping("edit/order/id/{id}")
    public ResponseEntity editOrder(@PathVariable("id")String id, @Validated OrderOperatyFrom from) throws MarketException {
        ResponseView view = this.orderService.editOrder(id,from);
        return new ResponseEntity(view, HttpStatus.OK);
    }

    @GetMapping("/order/list/page")
    public ResponseEntity findAll(PageForm pageForm){
        ResponseView view=this.orderService.findAll(pageForm);
        return new ResponseEntity(view,HttpStatus.OK);
    }

    @GetMapping("/order/list")
    public ResponseEntity findAll(){
        ResponseView view=this.orderService.findAll();
        return new ResponseEntity(view,HttpStatus.OK);
    }
}
