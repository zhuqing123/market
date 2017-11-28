package com.example.market.service.Impl;

import com.example.market.entity.CustomerEntity;
import com.example.market.entity.CustomerVegeEntity;
import com.example.market.entity.TypeDiscountEntity;
import com.example.market.entity.form.CustomerAddForm;
import com.example.market.entity.form.CustomerEditForm;
import com.example.market.entity.form.CustomerForm;
import com.example.market.entity.vo.CustomerVo;
import com.example.market.entity.vo.PageVo;
import com.example.market.repository.CustomerRepository;
import com.example.market.repository.CustomerVegeRepository;
import com.example.market.repository.TypeDiscountRepository;
import com.example.market.service.CustomerService;
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
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TypeDiscountRepository typeDiscountRepository;

    @Autowired
    private CustomerVegeRepository customerVegeRepository;

    @Override
    public ResponseView findAllCustomer(CustomerForm form) {
        Pageable page = new PageRequest(form.getPage() - 1, form.getRows(), Sort.Direction.valueOf(form.getDirection()), form.getProperty());
        Page<CustomerEntity> all = this.customerRepository.findAll(new Specification<CustomerEntity>() {
            @Override
            public Predicate toPredicate(Root<CustomerEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = root.isNotNull();
                if (StringUtils.isNoneBlank(form.getCustomerName())) {
                    predicate = cb.and(cb.like(root.get("customerName").as(String.class), "%" + form.getCustomerName() + "%"), predicate);
                }
                if (StringUtils.isNoneBlank(form.getCustomerAddr())) {
                    predicate = cb.and(cb.like(root.get("customerAddr").as(String.class), "%" + form.getCustomerAddr() + "%"), predicate);
                }
                if (StringUtils.isNoneBlank(form.getPhoneNum())) {
                    predicate = cb.and(cb.like(root.get("phoneNum").as(String.class), "%" + form.getPhoneNum() + "%"), predicate);
                }
                if (StringUtils.isNoneBlank(form.getType())) {
                    predicate = cb.and(cb.equal(root.get("type").as(String.class), form.getType()), predicate);
                }
                return predicate;
            }
        }, page);

        PageVo<CustomerVo> pageVo = new PageVo<CustomerVo>();
        pageVo.setCurrentPage(form.getPage());
        pageVo.setPageSize(form.getRows());
        int prevPage = all.hasPrevious() ? form.getPage() - 1 : form.getRows();
        pageVo.setPrevPage(prevPage);
        pageVo.setFirstPage(all.isFirst());
        int nextPage = all.hasNext() ? form.getPage() + 1 : form.getRows();
        pageVo.setNextPage(nextPage);
        pageVo.setLastPage(all.isLast());
        pageVo.setTotalElements(all.getTotalElements());
        pageVo.setTotalPage(all.getTotalPages());
        pageVo.setCurrentPageElements(all.getNumberOfElements());
        List<CustomerEntity> content = all.getContent();
        if (!CollectionUtils.isEmpty(content)) {
            for (CustomerEntity customerEntity : content) {
                CustomerVo vo = new CustomerVo();
                BeanUtils.copyProperties(customerEntity, vo);
                TypeDiscountEntity repositoryOne = this.typeDiscountRepository.findOne(customerEntity.getType());
                if (repositoryOne != null) {
                    vo.setTypeName(repositoryOne.getType());
                }
                pageVo.getContent().add(vo);
            }
        }
        return new ResponseView(0, "success", pageVo);
    }

    @Transactional
    @Override
    public ResponseView addCustomer(CustomerAddForm form) {
        CustomerEntity customerEntity = this.customerRepository.findByCustomerName(form.getCustomerName());
        if (customerEntity != null) {
            return new ResponseView(1, "客户名称不能重复");
        }
        customerEntity = this.customerRepository.findByPhoneNum(form.getPhoneNum());
        if (customerEntity != null) {
            return new ResponseView(1, "客户手机不能重复");
        }
        customerEntity = this.customerRepository.findByCustomerAddr(form.getCustomerAddr());
        if (customerEntity != null) {
            return new ResponseView(1, "客户地址不能重复");
        }
        customerEntity = new CustomerEntity();
        BeanUtils.copyProperties(form, customerEntity);
        this.customerRepository.save(customerEntity);
        return new ResponseView(0, "保存成功");
    }

    @Transactional
    @Override
    public ResponseView editCustomer(CustomerEditForm form) {
        CustomerEntity customerEntity = this.customerRepository.findOne(form.getId());
        if (customerEntity != null) {
            CustomerEntity customerEntity1 = this.customerRepository.findByCustomerAddr(form.getCustomerAddr());
            if (customerEntity1 != null && !form.getId().equals(customerEntity1.getId())) {
                return new ResponseView(1, "客户地址不能重复");
            }
            customerEntity1 = this.customerRepository.findByCustomerName(form.getCustomerName());
            if (customerEntity1 != null && !form.getId().equals(customerEntity1.getId())) {
                return new ResponseView(1, "客户名称不能重复");
            }
            customerEntity1 = this.customerRepository.findByPhoneNum(form.getPhoneNum());
            if (customerEntity1 != null && !form.getId().equals(customerEntity1.getId())) {
                return new ResponseView(1, "客户手机不能重复");
            }
            BeanUtils.copyProperties(form, customerEntity);
            this.customerRepository.save(customerEntity);
            return new ResponseView(0, "更新成功");
        } else {
            return new ResponseView(1, "数据丢失请联系管理员");
        }
    }

    @Transactional
    @Override
    public ResponseView deleteCustomer(List<String> list) throws MarketException {
        for (String id:list){
            List<CustomerVegeEntity> vegeEntityList=this.customerVegeRepository.findByCustmerId(id);
            if (!CollectionUtils.isEmpty(vegeEntityList)){
                throw new MarketException("菜单中有关联客户不能删除");
            }
        }
        List<CustomerEntity> all = this.customerRepository.findAll(list);
        if (!CollectionUtils.isEmpty(all)) {
            this.customerRepository.delete(all);
        }
        return new ResponseView(0, "删除成功");
    }

    @Override
    public ResponseView customerListNotPage(String q) {
        List<CustomerEntity> customerEntities = this.customerRepository.findAll(new Specification<CustomerEntity>() {
            @Override
            public Predicate toPredicate(Root<CustomerEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                Predicate predicate = root.isNotNull();
                if (StringUtils.isNoneBlank(q)) {
                    predicate = cb.and(cb.like(root.get("customerName").as(String.class), "%" + q + "%"), predicate);
                }
                return predicate;
            }
        });
        List<CustomerVo> vos = new ArrayList<CustomerVo>();
        for (CustomerEntity customerEntity : customerEntities) {
            CustomerVo vo = new CustomerVo();
            BeanUtils.copyProperties(customerEntity, vo);
            vos.add(vo);
        }
        return new ResponseView(0,"success",vos);
    }

}
