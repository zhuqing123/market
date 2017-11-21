package com.example.market.controller;

import com.example.market.service.VegetablesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Author:ZhuQing
 * Date:2017/11/14 15:07
 */
@Controller
@RequestMapping("/flt")
public class FltController {

    @Autowired
    private VegetablesService vegetablesService;

    @RequestMapping("/{url:.+\\.html$}")
    public String menu(@PathVariable String url) {
        url=url.substring(0).replace(".html", "");
        return "flt/"+url;
    }

}
