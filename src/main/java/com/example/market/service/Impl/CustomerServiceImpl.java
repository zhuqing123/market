package com.example.market.service.Impl;

import com.example.market.entity.CustomerEntity;
import com.example.market.entity.form.CustomerAddForm;
import com.example.market.entity.form.CustomerEditForm;
import com.example.market.entity.form.CustomerForm;
import com.example.market.entity.vo.CustomerVo;
import com.example.market.entity.vo.PageVo;
import com.example.market.repository.CustomerRepository;
import com.example.market.service.CustomerService;
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
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public ResponseView findAllCustomer(CustomerForm form) {
        Pageable page=new PageRequest(form.getPage()-1,form.getRows(), Sort.Direction.valueOf(form.getDirection()), form.getProperty());
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
                    predicate = cb.and(cb.equal(root.get("type").as(String.class),form.getType()), predicate);
                }
                return predicate;
            }
        }, page);

        PageVo<CustomerVo> pageVo=new PageVo<CustomerVo>();
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
        List<CustomerEntity> content = all.getContent();
        if (!CollectionUtils.isEmpty(content)){
            for (CustomerEntity customerEntity:content){
                CustomerVo vo=new CustomerVo();
                BeanUtils.copyProperties(customerEntity,vo);
                pageVo.getContent().add(vo);
            }
        }
        return new ResponseView(0,"success",pageVo);
    }

    @Override
    public ResponseView addCustomer(CustomerAddForm form) {
        CustomerEntity customerEntity=this.customerRepository.findByCustomerName(form.getCustomerName());
        if (customerEntity!=null){
            return new ResponseView(1,"客户名称不能重复");
        }
        customerEntity=this.customerRepository.findByPhoneNum(form.getPhoneNum());
        if (customerEntity!=null){
            return new ResponseView(1,"客户手机不能重复");
        }
        customerEntity=this.customerRepository.findByCustomerAddr(form.getCustomerAddr());
        if (customerEntity!=null){
            return new ResponseView(1,"客户地址不能重复");
        }
        customerEntity=new CustomerEntity();
        BeanUtils.copyProperties(form,customerEntity);
        this.customerRepository.save(customerEntity);
        return new ResponseView(0,"保存成功");
    }

    @Override
    public ResponseView editCustomer(CustomerEditForm form) {
        CustomerEntity customerEntity = this.customerRepository.findOne(form.getId());
        if (customerEntity!=null){

        }else {
            return new ResponseView(1,"数据丢失请联系管理员");
        }
        return null;
    }

}
