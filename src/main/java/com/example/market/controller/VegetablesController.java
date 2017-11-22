package com.example.market.controller;

import com.example.market.entity.form.VegetablesAddForm;
import com.example.market.entity.form.VegetablesEditForm;
import com.example.market.entity.form.VegetablesForm;
import com.example.market.service.VegetablesService;
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
 * Date:2017/11/14 15:53
 */
@RestController
public class VegetablesController extends BaseController {

    @Autowired
    private VegetablesService vegetablesService;

    @GetMapping("/vege/list")
    public ResponseEntity vegetablesPage(@Valid VegetablesForm form) {
        ResponseView view = this.vegetablesService.vegetablesPage(form);
        return new ResponseEntity(view, HttpStatus.OK);
    }

    @PostMapping(value = "add/vege")
    public ResponseEntity addVegetable(@Valid VegetablesAddForm form) {
        ResponseView view = this.vegetablesService.addVegetable(form);
        return new ResponseEntity(view, HttpStatus.OK);

    }

    @PutMapping(value = "/edit/vege")
    public ResponseEntity editVegetable(@Valid VegetablesEditForm form) {
        ResponseView view = this.vegetablesService.editVegetable(form);
        return new ResponseEntity(view, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/vege")
    public ResponseEntity deleteVegetable(@NotNull String ids) {
        List<String> list = Arrays.asList(ids.split(","));
        ResponseView view = this.vegetablesService.deleteVegetable(list);
        return  new ResponseEntity(view, HttpStatus.OK);

    }


}
