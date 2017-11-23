package com.example.market.service.Impl;

import com.example.market.entity.CustomerEntity;
import com.example.market.entity.TypeDiscountEntity;
import com.example.market.entity.form.TypeDiscountAddForm;
import com.example.market.entity.form.TypeDiscountEditForm;
import com.example.market.entity.vo.TypeDiscountVo;
import com.example.market.repository.CustomerRepository;
import com.example.market.repository.TypeDiscountRepository;
import com.example.market.service.TypeDiscountService;
import com.example.market.utils.MarketException;
import com.example.market.utils.ResponseView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:ZhuQing
 * Date:2017/11/22 20:34
 */
@Service
public class TypeDiscountServiceImpl implements TypeDiscountService {

    @Autowired
    private TypeDiscountRepository typeDiscountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public ResponseView findAllType() {
        List<TypeDiscountEntity> typeDiscountEntities = typeDiscountRepository.findAll();
        List<TypeDiscountVo> vos = new ArrayList<TypeDiscountVo>();
        for (TypeDiscountEntity typeDiscountEntity : typeDiscountEntities) {
            TypeDiscountVo vo = new TypeDiscountVo();
            BeanUtils.copyProperties(typeDiscountEntity, vo);
            vos.add(vo);
        }

        return new ResponseView(0, "success", vos);
    }

    @Transactional
    @Override
    public ResponseView addType(TypeDiscountAddForm form) throws MarketException {
        TypeDiscountEntity typeDiscountEntity = this.typeDiscountRepository.findByType(form.getType());
        if (typeDiscountEntity != null) {
            throw new MarketException("类型重复");
        }
        typeDiscountEntity = new TypeDiscountEntity();
        BeanUtils.copyProperties(form, typeDiscountEntity);
        this.typeDiscountRepository.save(typeDiscountEntity);
        return new ResponseView(0, "保存成功");
    }

    @Transactional
    @Override
    public ResponseView editType(TypeDiscountEditForm form) throws MarketException {
        TypeDiscountEntity byType = this.typeDiscountRepository.findByType(form.getType());
        if (null != byType && !form.getId().equals(byType.getId())) {
            throw new MarketException("类型重复");
        }
        TypeDiscountEntity typeDiscountEntity = byType = this.typeDiscountRepository.findOne(form.getId());
        BeanUtils.copyProperties(form, typeDiscountEntity);
        this.typeDiscountRepository.save(typeDiscountEntity);
        return new ResponseView(0, "更新成功");
    }

    @Transactional
    @Override
    public ResponseView deleteType(List<String> list) throws MarketException {
        List<CustomerEntity> customerEntityList=this.customerRepository.findByTypeIn(list);
        if (!CollectionUtils.isEmpty(customerEntityList)){
            throw new MarketException("不能删除，客户中有关联类型");
        }
        List<TypeDiscountEntity> all = this.typeDiscountRepository.findAll(list);
        if (!CollectionUtils.isEmpty(all)){
            this.typeDiscountRepository.delete(all);
        }
        return new ResponseView(0,"删除成功");
    }
}
