package com.example.market.service.Impl;

import com.example.market.repository.UnitRepository;
import com.example.market.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author:ZhuQing
 * Date:2017/11/28 19:41
 */
@Service
public class UnitServiceImple implements UnitService {

    @Autowired
    private UnitRepository unitRepository;

}
