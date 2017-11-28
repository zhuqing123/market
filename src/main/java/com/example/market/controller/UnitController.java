package com.example.market.controller;

import com.example.market.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author:ZhuQing
 * Date:2017/11/28 19:40
 */
@RestController
public class UnitController extends BaseController {

    @Autowired
    private UnitService unitService;


}
