package com.example.market.service.Impl;

import com.example.market.entity.UnitEntity;
import com.example.market.entity.VegetablesEntity;
import com.example.market.entity.form.UnitEditForm;
import com.example.market.entity.vo.UnitVo;
import com.example.market.repository.UnitRepository;
import com.example.market.repository.VegetablesRepository;
import com.example.market.service.UnitService;
import com.example.market.utils.MarketException;
import com.example.market.utils.ResponseView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:ZhuQing
 * Date:2017/11/28 19:41
 */
@Service
public class UnitServiceImple implements UnitService {

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private VegetablesRepository vegetablesRepository;

    @Override
    public ResponseView getUnit() {
        List<UnitEntity> unitEntities = this.unitRepository.findAll();
        List<UnitVo> vos = new ArrayList<UnitVo>();
        for (UnitEntity unitEntity : unitEntities) {
            UnitVo vo = new UnitVo();
            BeanUtils.copyProperties(unitEntity, vo);
            vos.add(vo);
        }
        return new ResponseView(0, "success", vos);
    }

    @Transactional
    @Override
    public ResponseView addUnit(String unitName) throws MarketException {
        if (StringUtils.isBlank(unitName)) {
            throw new MarketException("单位名不能为空");
        } else {
            if (unitName.length() >15) {
                throw new MarketException("单位名长度不能超过10");
            }
            UnitEntity unitEntity = new UnitEntity();
            unitEntity.setUnitName(unitName);
            this.unitRepository.save(unitEntity);
        }
        return new ResponseView(0, "保存成功");
    }

    @Transactional
    @Override
    public ResponseView editUnit(UnitEditForm from) throws MarketException {
        UnitEntity unitEntity = this.unitRepository.findOne(from.getId());
        if (unitEntity==null){
            throw new MarketException("数据库存数据错误");
        }
        unitEntity.setUnitName(from.getUnitName());
        this.unitRepository.save(unitEntity);
        return new ResponseView(0,"修改成功");
    }

    @Transactional
    @Override
    public ResponseView deleteType(List<String> list) throws MarketException {
        for (String unitId:list){
            List<VegetablesEntity> vegetablesEntities=this.vegetablesRepository.findByUnitId(unitId);
            if (!CollectionUtils.isEmpty(vegetablesEntities)){
                throw new MarketException("单位与菜品已经有关联不能删除");
            }
        }
        List<UnitEntity> unitEntities = this.unitRepository.findAll(list);
        this.unitRepository.delete(unitEntities);
        return new ResponseView(0,"删除成功");
    }
}
