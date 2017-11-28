package com.example.market.service.Impl;

import com.example.market.entity.VegetablesEntity;
import com.example.market.entity.form.VegetablesAddForm;
import com.example.market.entity.form.VegetablesEditForm;
import com.example.market.entity.form.VegetablesForm;
import com.example.market.entity.vo.PageVo;
import com.example.market.entity.vo.VegetablesVo;
import com.example.market.repository.VegetablesRepository;
import com.example.market.service.VegetablesService;
import com.example.market.utils.ResponseView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Author:ZhuQing
 * Date:2017/11/14 16:06
 */
@Service
public class VegetablesServiceImpl implements VegetablesService {

    @Autowired
    private VegetablesRepository vegetablesRepository;

    @Override
    public ResponseView vegetablesPage(VegetablesForm form) {
        Pageable page = new PageRequest(form.getPage() - 1, form.getRows(), Sort.Direction.valueOf(form.getDirection()), form.getProperty());
        Page<VegetablesEntity> all = this.vegetablesRepository.findAll(new Specification<VegetablesEntity>() {
            @Override
            public Predicate toPredicate(Root<VegetablesEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = root.isNotNull();
                if (StringUtils.isNoneBlank(form.getVegeName())) {
                    predicate = cb.and(cb.like(root.get("vegeName").as(String.class), "%" + form.getVegeName() + "%"), predicate);
                }
                if (StringUtils.isNoneBlank(form.getVegeCode())) {
                    predicate = cb.and(cb.like(root.get("vegeCode").as(String.class), "%" + form.getVegeCode() + "%"), predicate);
                }
                return predicate;
            }
        }, page);

        PageVo<VegetablesVo> pageVo=new PageVo<VegetablesVo>();
        pageVo.setCurrentPage(form.getPage());
        pageVo.setPageSize(form.getRows());
        int prevPage=all.hasPrevious()?form.getPage()-1:form.getRows();
        pageVo.setPrevPage(prevPage);
        pageVo.setFirstPage(all.isFirst());
        int nextPage=all.hasNext()?form.getPage()+1:form.getRows();
        pageVo.setNextPage(nextPage);
        pageVo.setLastPage(all.isLast());
        pageVo.setTotalElements(all.getTotalElements());
        pageVo.setTotalPage(all.getTotalPages());
        pageVo.setCurrentPageElements(all.getNumberOfElements());
        List<VegetablesEntity> content = all.getContent();
        if (!CollectionUtils.isEmpty(content)){
            for (VegetablesEntity vegetablesEntity:content){
                VegetablesVo vo=new VegetablesVo();
                BeanUtils.copyProperties(vegetablesEntity,vo);
                pageVo.getContent().add(vo);
            }
        }

        return new ResponseView(0,"success",pageVo);
    }

    @Transactional
    @Override
    public ResponseView addVegetable(VegetablesAddForm form) {
        VegetablesEntity  vegetablesEntity=this.vegetablesRepository.findByVegeName(form.getVegeName());
        if (vegetablesEntity!=null){
            return new ResponseView(1,"菜名重复");
        }
        vegetablesEntity=this.vegetablesRepository.findByVegeCode(form.getVegeCode());
        if (vegetablesEntity!=null){
            return new ResponseView(1,"菜编码重复");
        }
        vegetablesEntity=new VegetablesEntity();
        BeanUtils.copyProperties(form,vegetablesEntity);
        this.vegetablesRepository.save(vegetablesEntity);
        return new ResponseView(0,"保存成功");
    }

    @Transactional
    @Override
    public ResponseView editVegetable(VegetablesEditForm form) {
        VegetablesEntity vegetablesEntity = this.vegetablesRepository.findOne(form.getId());
        if (vegetablesEntity==null){
            return new ResponseView(1,"数据错误");
        }
        if (StringUtils.isNoneBlank(form.getVegeName())){
            VegetablesEntity byVegeName = this.vegetablesRepository.findByVegeName(form.getVegeName());
            if (byVegeName!=null&&!form.getId().equals(byVegeName.getId())){
                return new ResponseView(1,"菜名重复，请重新输入");
            }else {
                vegetablesEntity.setVegeName(form.getVegeName());
            }
        }

        if (StringUtils.isNoneBlank(form.getVegeCode())){
            VegetablesEntity byVegeCode = this.vegetablesRepository.findByVegeCode(form.getVegeCode());
            if (byVegeCode!=null&&!form.getId().equals(byVegeCode.getId())){
                return new ResponseView(1,"菜编码重复，请重新输入");
            }else {
                vegetablesEntity.setVegeCode(form.getVegeCode());
            }
        }

        if (form.getPrice()!=null){
            vegetablesEntity.setPrice(form.getPrice());
        }

        this.vegetablesRepository.save(vegetablesEntity);

        return new ResponseView(0,"编辑成功");
    }

    @Transactional
    @Override
    public ResponseView deleteVegetable(List<String> ids) {
        List<VegetablesEntity> all = this.vegetablesRepository.findAll(ids);
        if (!CollectionUtils.isEmpty(all)){
            this.vegetablesRepository.delete(all);
        }
        return new ResponseView(0,"删除成功");
    }

    @Override
    public ResponseView findVegeList(String p) {
        List<VegetablesEntity> vegetablesEntities = this.vegetablesRepository.findAll(new Specification<VegetablesEntity>() {
            @Override
            public Predicate toPredicate(Root<VegetablesEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                Predicate predicate = root.isNotNull();
                if (StringUtils.isNoneBlank(p)) {
                    predicate = cb.and(cb.like(root.get("vegeName").as(String.class), "%"+p+"%"), predicate);
                    predicate = cb.or(cb.like(root.get("vegeCode").as(String.class), "%"+p+"%"), predicate);
                }
                return predicate;
            }
        });
        List<VegetablesVo> vos=new ArrayList<VegetablesVo>();
        for (VegetablesEntity vegetablesEntity:vegetablesEntities){
            VegetablesVo vo=new VegetablesVo();
            BeanUtils.copyProperties(vegetablesEntity,vo);
            vos.add(vo);
        }
        return new ResponseView(0,"success",vos);
    }
}