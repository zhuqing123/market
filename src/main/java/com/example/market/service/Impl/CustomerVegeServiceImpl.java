package com.example.market.service.Impl;

import com.example.market.entity.CustomerEntity;
import com.example.market.entity.CustomerVegeEntity;
import com.example.market.entity.TypeDiscountEntity;
import com.example.market.entity.VegetablesEntity;
import com.example.market.entity.form.CustomerVegeAddForm;
import com.example.market.entity.form.CustomerVegeEditForm;
import com.example.market.entity.form.CustomerVegeForm;
import com.example.market.entity.vo.CustomerVegeVo;
import com.example.market.entity.vo.PageVo;
import com.example.market.repository.CustomerRepository;
import com.example.market.repository.CustomerVegeRepository;
import com.example.market.repository.TypeDiscountRepository;
import com.example.market.repository.VegetablesRepository;
import com.example.market.service.CustomerVegeService;
import com.example.market.utils.MarketException;
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
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Author:ZhuQing
 * Date:2017/11/23 17:18
 */
@Service
public class CustomerVegeServiceImpl implements CustomerVegeService {

    @Autowired
    private CustomerVegeRepository customerVegeRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private VegetablesRepository vegetablesRepository;

    @Autowired
    private TypeDiscountRepository typeDiscountRepository;

    @Override
    public ResponseView getCustomerOrder(CustomerVegeForm form) {

        List<String> customerIds = new ArrayList<String>();
        if (StringUtils.isNoneBlank(form.getCustomerName())) {
            List<CustomerEntity> entities = this.customerRepository.findByCustomerNameLike("%" + form.getCustomerName() + "%");
            entities.forEach(entity -> customerIds.add(entity.getId()));
        }

        List<String> vegeIds = new ArrayList<String>();
        if (StringUtils.isNoneBlank(form.getVegeName())) {
            List<VegetablesEntity> vegetablesEntities = this.vegetablesRepository.findByVegeNameLike("%" + form.getVegeName() + "%");
            vegetablesEntities.forEach(entity -> vegeIds.add(entity.getId()));
        }

        Pageable page = new PageRequest(form.getPage() - 1, form.getRows(), Sort.Direction.valueOf(form.getDirection()), form.getProperty());
        Page<CustomerVegeEntity> entities = this.customerVegeRepository.findAll(new Specification<CustomerVegeEntity>() {
            @Override
            public Predicate toPredicate(Root<CustomerVegeEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                Predicate predicate = root.isNotNull();
                if (!CollectionUtils.isEmpty(customerIds)) {
                    predicate = cb.and(root.get("custmerId").in(customerIds));
                }
                if (!CollectionUtils.isEmpty(vegeIds)) {
                    predicate = cb.and(root.get("vegeId").in(vegeIds));
                }
                if (form.getStartTime() != null) {
                    predicate = cb.and(cb.greaterThanOrEqualTo(root.get("sendDate").as(Date.class), form.getStartTime()), predicate);
                }
                if (form.getEndTime() != null) {
                    predicate = cb.and(cb.lessThan(root.get("sendDate").as(Date.class), form.getEndTime()), predicate);
                }
                return predicate;
            }
        }, page);

        PageVo<CustomerVegeVo> pageVo = new PageVo<CustomerVegeVo>();
        pageVo.setCurrentPage(form.getPage());
        pageVo.setPageSize(form.getRows());
        int prevPage = entities.hasPrevious() ? form.getPage() - 1 : form.getRows();
        pageVo.setPrevPage(prevPage);
        pageVo.setFirstPage(entities.isFirst());
        int nextPage = entities.hasNext() ? form.getPage() + 1 : form.getRows();
        pageVo.setNextPage(nextPage);
        pageVo.setLastPage(entities.isLast());
        pageVo.setTotalElements(entities.getTotalElements());
        pageVo.setTotalPage(entities.getTotalPages());
        pageVo.setCurrentPageElements(entities.getNumberOfElements());
        List<CustomerVegeEntity> content = entities.getContent();
        if (!CollectionUtils.isEmpty(content)) {
            for (CustomerVegeEntity customerVegeEntity : content) {
                CustomerVegeVo vo = new CustomerVegeVo();
                BeanUtils.copyProperties(customerVegeEntity, vo);
                VegetablesEntity vegetablesEntity = this.vegetablesRepository.findOne(customerVegeEntity.getVegeId());
                if (vegetablesEntity != null) {
                    vo.setVegeId(vegetablesEntity.getVegeName());
                }
                CustomerEntity customerEntity = this.customerRepository.findOne(customerVegeEntity.getCustmerId());
                if (customerEntity != null) {
                    vo.setCustmerId(customerEntity.getCustomerName());
                }
                pageVo.getContent().add(vo);
            }
        }
        return new ResponseView(0, "success", pageVo);
    }

    @Transactional
    @Override
    public ResponseView addCustomerOrder(CustomerVegeAddForm form) throws MarketException {
        List<String> list = Arrays.asList(form.getVegeId().split(","));
        if (!CollectionUtils.isEmpty(list)) {
            CustomerEntity customerEntity = this.customerRepository.findOne(form.getCustmerId());
            if (customerEntity != null) {
                TypeDiscountEntity typeDiscountEntity = typeDiscountRepository.findOne(customerEntity.getType());
                if (typeDiscountEntity != null) {
                    List<CustomerVegeEntity> customerVegeEntities = new ArrayList<CustomerVegeEntity>();
                    for (String vegeId : list) {
                        CustomerVegeEntity customerVegeEntity = new CustomerVegeEntity();
                        VegetablesEntity vegetablesEntity = this.vegetablesRepository.findOne(vegeId);
                        if (vegetablesEntity == null) {
                            throw new MarketException("菜品不存在");
                        }
                        //计算卖出的价格
                        double v = vegetablesEntity.getPrice() * (1 + (typeDiscountEntity.getDiscount() / 10));
                        customerVegeEntity.setPrice(v);
                        customerVegeEntity.setCustmerId(form.getCustmerId());
                        customerVegeEntity.setVegeId(vegeId);
                        customerVegeEntities.add(customerVegeEntity);
                        customerVegeEntity.setSendDate(form.getSendDate());
                    }
                    this.customerVegeRepository.save(customerVegeEntities);
                }
            }

        }
        return new ResponseView(0, "保存成功");
    }

    @Transactional
    @Override
    public ResponseView editCustomerOrder(CustomerVegeEditForm form) throws MarketException {
        CustomerVegeEntity customerVegeEntity = this.customerVegeRepository.findOne(form.getId());
        if (customerVegeEntity != null) {
            Date date=new Date();
            if (!validataDateOneDay(date,customerVegeEntity.getCreateTime())){
                throw new MarketException("修改失败，当天只能修改，当天新增的数据");
            }
            customerVegeEntity.setCount(form.getCount());
            customerVegeEntity.setSendDate(form.getSendDate());
            customerVegeEntity.setTotlePrice(customerVegeEntity.getPrice() * form.getCount());
            VegetablesEntity repositoryOne = this.vegetablesRepository.findOne(customerVegeEntity.getVegeId());
            if (repositoryOne != null) {
                customerVegeEntity.setProfit((customerVegeEntity.getPrice() * form.getCount()) - (repositoryOne.getPrice() * form.getCount()));
            }
        } else {
            throw new MarketException("数据库数据错误，请联系庆哥");
        }
        return new ResponseView(0, "修改成功");
    }

    @Transactional
    @Override
    public ResponseView deleteCustomerOrder(List<String> list) throws MarketException {
        List<CustomerVegeEntity> customerVegeEntities = this.customerVegeRepository.findAll(list);
        if (!CollectionUtils.isEmpty(customerVegeEntities)){
            Date date=new Date();
            for (CustomerVegeEntity customerVegeEntity:customerVegeEntities){
                if (validataDateOneDay(date,customerVegeEntity.getCreateTime())){
                    this.customerVegeRepository.delete(customerVegeEntity);
                }else {
                    throw new MarketException("删除失败，当天只能删除，当天新增的数据");
                }
            }
        }
        return new ResponseView(0,"删除成功");
    }

    /**
     * 验证两个时间是不是同一天
     * @param date1
     * @param date2
     * @return
     */
    private boolean validataDateOneDay(Date date1,Date date2){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        String format1 = format.format(date1);
        String format2 = format.format(date2);
        if (format1.equals(format2)){
            return true;
        }
        return false;
    }
}
