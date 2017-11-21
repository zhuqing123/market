package com.example.market.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Author:ZhuQing
 * Date:2017/11/14 10:49
 */
@Controller
public class PageController {

    @RequestMapping(value = {"/","/login.html"},method = RequestMethod.GET)
    public String loginPage(){
        return "login";
    }

    @RequestMapping(value = {"index.html"},method = RequestMethod.GET)
    public String indexPage(){
        return "index";
    }

}
