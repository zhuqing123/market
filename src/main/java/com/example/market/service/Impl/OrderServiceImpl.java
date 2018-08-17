package com.example.market.service.Impl;

import com.example.market.entity.OrderEntity;
import com.example.market.entity.UnitEntity;
import com.example.market.entity.VegetablesEntity;
import com.example.market.entity.form.OrderOperatyFrom;
import com.example.market.entity.form.PageForm;
import com.example.market.entity.vo.OrderVo;
import com.example.market.entity.vo.PageVo;
import com.example.market.repository.OrderRepository;
import com.example.market.service.OrderService;
import com.example.market.service.UnitService;
import com.example.market.service.VegetablesService;
import com.example.market.utils.MarketException;
import com.example.market.utils.ResponseView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author ZhuQing
 * @Date: 2018/8/14  09:26
 */
@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private VegetablesService vegetablesService;

    @Autowired
    private UnitService unitService;

    @Transactional
    @Override
    public ResponseView addOrder(OrderOperatyFrom from) throws MarketException {
        VegetablesEntity vegetablesEntity = this.vegetablesService.findById(from.getCommodityId());
        if (null == vegetablesEntity) {
            throw new MarketException("水果不存在");
        }
        OrderEntity orderEntity = new OrderEntity();
        UnitEntity unitEntity = this.unitService.findById(vegetablesEntity.getUnitId());
        this.setOrderPriperties(from, vegetablesEntity, orderEntity, unitEntity);
        return new ResponseView(0, "操作成功");
    }


    @Transactional
    @Override
    public ResponseView editOrder(String id, OrderOperatyFrom from) throws MarketException {
        VegetablesEntity vegetablesEntity = this.vegetablesService.findById(from.getCommodityId());
        if (null == vegetablesEntity) {
            throw new MarketException("水果不存在");
        }
        OrderEntity orderEntity = this.orderRepository.findOne(id);
        if (null == orderEntity) {
            throw new MarketException("订单不存在");
        }
        UnitEntity unitEntity = this.unitService.findById(vegetablesEntity.getUnitId());
        this.setOrderPriperties(from, vegetablesEntity, orderEntity, unitEntity);
        return new ResponseView(0, "操作成功");
    }

    @Override
    public ResponseView findAll(PageForm form) {
        Pageable pageable = new PageRequest(form.getPage() - 1, form.getRows(), Sort.Direction.valueOf(form.getDirection()), form.getProperty());
        Page<OrderEntity> page = this.orderRepository.findAll(pageable);
        PageVo<OrderVo> pageVo = new PageVo<OrderVo>();
        pageVo.setCurrentPage(form.getPage());
        pageVo.setPageSize(form.getRows());
        int prevPage = page.hasPrevious() ? form.getPage() - 1 : form.getRows();
        pageVo.setPrevPage(prevPage);
        pageVo.setFirstPage(page.isFirst());
        int nextPage = page.hasNext() ? form.getPage() + 1 : form.getRows();
        pageVo.setNextPage(nextPage);
        pageVo.setLastPage(page.isLast());
        pageVo.setTotalElements(page.getTotalElements());
        pageVo.setTotalPage(page.getTotalPages());
        pageVo.setCurrentPageElements(page.getNumberOfElements());
        List<OrderEntity> orderEntityList = page.getContent();
        if (!CollectionUtils.isEmpty(orderEntityList)) {
            orderEntityList
                    .stream()
                    .forEach(orderEntity -> {
                        OrderVo orderVo = new OrderVo();
                        BeanUtils.copyProperties(orderEntity, orderVo);
                        VegetablesEntity vegetablesEntity = this.vegetablesService.findById(orderVo.getCommodityId());
                        if (null != vegetablesEntity) {
                            orderVo.setCombogrName(vegetablesEntity.getVegeName());
                        }
                        pageVo.getContent().add(orderVo);
                    });

        }

        return new ResponseView(0, "success", pageVo);
    }

    @Override
    public ResponseView findAll() {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        List<OrderEntity> orderEntityList = this.orderRepository.findAll(sort);
        if (!CollectionUtils.isEmpty(orderEntityList)) {
            List<OrderVo> orderVoList = new ArrayList<OrderVo>();
            orderEntityList.stream().forEach(orderEntity -> {
                OrderVo vo = new OrderVo();
                orderVoList.add(vo);
            });
            return new ResponseView(0, "success", orderVoList);
        }
        return new ResponseView(0, "success");
    }


    private void setOrderPriperties(OrderOperatyFrom from, VegetablesEntity vegetablesEntity, OrderEntity orderEntity, UnitEntity unitEntity) {
        orderEntity.setCommodityId(from.getCommodityId());
        orderEntity.setQuantity(from.getQuantity());
        orderEntity.setSalePrice(vegetablesEntity.getSalePrice());
        if (null != from.getSalePrice() && 0 != from.getSalePrice()) {
            orderEntity.setSalePrice(from.getSalePrice());
        }
        orderEntity.setPrice(vegetablesEntity.getPrice());
        if (null != from.getRebate() && 0 < from.getRebate()) {
            orderEntity.setRebate(from.getRebate());
        } else if (null != vegetablesEntity.getRebate() && 0 < vegetablesEntity.getRebate()) {
            orderEntity.setRebate(vegetablesEntity.getRebate());
        } else {
            orderEntity.setRebate(Double.valueOf(1));
        }
        Double totalSalePrice = this.multiplication(orderEntity.getRebate(), this.multiplication(orderEntity.getQuantity(), orderEntity.getSalePrice()));
        orderEntity.setTotalSalePrice(totalSalePrice);

        Double totalPrice = this.multiplication(orderEntity.getPrice(), orderEntity.getQuantity());
        orderEntity.setTotalPrice(totalPrice);
        if (null != unitEntity) {
            orderEntity.setUnitName(unitEntity.getUnitName());
        }

        orderEntity.setProfit(this.subtraction(totalSalePrice, this.multiplication(orderEntity.getQuantity(), vegetablesEntity.getPrice())));
        this.orderRepository.save(orderEntity);
    }

    /**
     * 乘法
     *
     * @param multiplicand
     * @param multiplier
     * @return
     */
    private Double multiplication(Double multiplicand, Double multiplier) {
        BigDecimal multiplicandDecimal = new BigDecimal(String.valueOf(multiplicand));
        BigDecimal multiplierDecimal = new BigDecimal(String.valueOf(multiplier));
        return multiplicandDecimal.multiply(multiplierDecimal).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
    }

    /**
     * 减法
     *
     * @param subtracted
     * @param subtraction
     * @return
     */
    private Double subtraction(Double subtracted, Double subtraction) {
        BigDecimal subtractedDecimal = new BigDecimal(String.valueOf(subtracted));
        BigDecimal subtractionDecimal = new BigDecimal(String.valueOf(subtraction));
        return subtractedDecimal.subtract(subtractionDecimal).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
    }
}
