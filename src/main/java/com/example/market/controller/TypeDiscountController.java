package com.example.market.controller;

import com.example.market.entity.form.TypeDiscountAddForm;
import com.example.market.entity.form.TypeDiscountEditForm;
import com.example.market.service.TypeDiscountService;
import com.example.market.utils.ResponseView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Author:ZhuQing
 * Date:2017/11/22 20:34
 */
@RestController
public class TypeDiscountController extends BaseController {

    @Autowired
    private TypeDiscountService typeDiscountService;

    @GetMapping("/get/type")
    public ResponseEntity findAllType(){
        ResponseView view=this.typeDiscountService.findAllType();
        return new ResponseEntity(view, HttpStatus.OK);
    }

    @PostMapping("/add/type")
    public ResponseEntity addType(@Valid TypeDiscountAddForm form){
        ResponseView view=this.typeDiscountService.addType(form);
        return new ResponseEntity(view,HttpStatus.OK);
    }

    @PutMapping("/edit/type")
    public ResponseEntity editType(@Valid TypeDiscountEditForm form){
        ResponseView view=this.typeDiscountService.editType(form);
        return new ResponseEntity(view,HttpStatus.OK);
    }


}
