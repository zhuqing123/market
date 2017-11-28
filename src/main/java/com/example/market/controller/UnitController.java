package com.example.market.controller;

import com.example.market.entity.form.UnitEditForm;
import com.example.market.service.UnitService;
import com.example.market.utils.MarketException;
import com.example.market.utils.ResponseView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

/**
 * Author:ZhuQing
 * Date:2017/11/28 19:40
 */
@RestController
public class UnitController extends BaseController {

    @Autowired
    private UnitService unitService;

    @GetMapping("/get/unit")
    public ResponseEntity getUnit() {
        ResponseView view = this.unitService.getUnit();
        return new ResponseEntity(view, HttpStatus.OK);
    }

    @PostMapping("/add/unit")
    public ResponseEntity addUnit(@NotNull String unitName) throws MarketException {
        ResponseView view = this.unitService.addUnit(unitName);
        return new ResponseEntity(view, HttpStatus.OK);

    }

    @PutMapping("/edit/unit")
    public ResponseEntity editUnit(@Valid UnitEditForm from) throws MarketException {
        ResponseView view = this.unitService.editUnit(from);
        return new ResponseEntity(view, HttpStatus.OK);
    }

    @DeleteMapping("/delete/unit")
    public ResponseEntity deleteType(@NotNull String ids) throws MarketException {
        List<String> list = Arrays.asList(ids.split(","));
        ResponseView view=this.unitService.deleteType(list);
        return new ResponseEntity(view,HttpStatus.OK);
    }



}
