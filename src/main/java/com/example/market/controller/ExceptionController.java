package com.example.market.controller;

import com.example.market.utils.MarketException;
import com.example.market.utils.ResponseView;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Author:ZhuQing
 * Date:2017/11/22 21:03
 */
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public ResponseView err(BindException e) {
        ResponseView view = new ResponseView();
        view.setStatus(1);
        BindingResult b = e.getBindingResult();
        List<FieldError> list = b.getFieldErrors();
        if (!list.isEmpty()) {
            FieldError f = list.get(0);
            view.setMessage(f.getDefaultMessage());
        }
        return view;
    }

    @ExceptionHandler(value = MarketException.class)
    @ResponseBody
    public ResponseView exc(MarketException e) {
        ResponseView view = new ResponseView();
        view.setStatus(1);
        view.setMessage(e.getMessage());
        e.printStackTrace();
        return view;
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseView exc(Exception e) {
        ResponseView view = new ResponseView();
        view.setStatus(1);
        view.setMessage(e.getMessage());
        e.printStackTrace();
        return view;
    }
}
