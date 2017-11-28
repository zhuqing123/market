package com.example.market.service;

import com.example.market.entity.form.VegetablesAddForm;
import com.example.market.entity.form.VegetablesEditForm;
import com.example.market.entity.form.VegetablesForm;
import com.example.market.utils.ResponseView;

import java.util.List;

/**
 * Author:ZhuQing
 * Date:2017/11/14 16:06
 */
public interface VegetablesService {
    ResponseView vegetablesPage(VegetablesForm form);

    ResponseView addVegetable(VegetablesAddForm form);

    ResponseView editVegetable(VegetablesEditForm form);

    ResponseView deleteVegetable(List<String> ids);

    ResponseView findVegeList(String p);
}
