//package com.example.market.service.Impl;
//
//import com.example.market.entity.OrderEntity;
//import com.example.market.entity.VegetablesEntity;
//import com.example.market.entity.form.OrderOperatyFrom;
////import com.example.market.repository.OrderRepository;
//import com.example.market.service.OrderService;
//import com.example.market.service.VegetablesService;
//import com.example.market.utils.MarketException;
//import com.example.market.utils.ResponseView;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.math.BigDecimal;
//
///**
// * @Author ZhuQing
// * @Date: 2018/8/14  09:26
// */
//@Service
//public class OrderServiceImpl implements OrderService {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);
//
//    //@Autowired
//   // private OrderRepository orderRepository;
//
//    @Autowired
//    private VegetablesService vegetablesService;
//
//    @Transactional
//    @Override
//    public ResponseView addOrder(OrderOperatyFrom from) throws MarketException {
//        VegetablesEntity vegetablesEntity = this.vegetablesService.findById(from.getCommodityId());
//        if (null == vegetablesEntity) {
//            throw new MarketException("水果不存在");
//        }
//        OrderEntity orderEntity = new OrderEntity();
//        this.setOrderPriperties(from, vegetablesEntity, orderEntity);
//        return new ResponseView(0, "操作成功");
//    }
//
//
//    @Transactional
//    @Override
//    public ResponseView editOrder(String id, OrderOperatyFrom from) throws MarketException {
//        VegetablesEntity vegetablesEntity = this.vegetablesService.findById(from.getCommodityId());
//        if (null == vegetablesEntity) {
//            throw new MarketException("水果不存在");
//        }
//       // OrderEntity orderEntity = this.orderRepository.findOne(id);
//        if (null == orderEntity) {
//            throw new MarketException("订单不存在");
//        }
//        //this.setOrderPriperties(from, vegetablesEntity, orderEntity);
//        return new ResponseView(0, "操作成功");
//    }
//
//
//    private void setOrderPriperties(OrderOperatyFrom from, VegetablesEntity vegetablesEntity, OrderEntity orderEntity) {
//        orderEntity.setCommodityId(from.getCommodityId());
//        orderEntity.setQuantity(from.getQuantity());
//        if (null != from.getRebate() && 0 < from.getRebate()) {
//            orderEntity.setRebate(from.getRebate());
//        } else if (null != vegetablesEntity.getRebate() && 0 < vegetablesEntity.getRebate()) {
//            orderEntity.setRebate(vegetablesEntity.getRebate());
//        } else {
//            orderEntity.setRebate(Double.valueOf(1));
//        }
//        Double totalPrice = this.multiplication(orderEntity.getRebate(), this.multiplication(orderEntity.getQuantity(), vegetablesEntity.getSalePrice()));
//        orderEntity.setTotalPrice(totalPrice);
//
//        orderEntity.setSalePrice(vegetablesEntity.getSalePrice());
//        orderEntity.setPrice(vegetablesEntity.getPrice());
//        orderEntity.setProfit(this.subtraction(totalPrice, this.multiplication(orderEntity.getQuantity(), vegetablesEntity.getPrice())));
//        this.orderRepository.save(orderEntity);
//    }
//
//    /**
//     * 乘法
//     *
//     * @param multiplicand
//     * @param multiplier
//     * @return
//     */
//    private Double multiplication(Double multiplicand, Double multiplier) {
//        BigDecimal multiplicandDecimal = new BigDecimal(String.valueOf(multiplicand));
//        BigDecimal multiplierDecimal = new BigDecimal(String.valueOf(multiplier));
//        return multiplicandDecimal.multiply(multiplierDecimal).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
//    }
//
//    /**
//     * 减法
//     *
//     * @param subtracted
//     * @param subtraction
//     * @return
//     */
//    private Double subtraction(Double subtracted, Double subtraction) {
//        BigDecimal subtractedDecimal = new BigDecimal(String.valueOf(subtracted));
//        BigDecimal subtractionDecimal = new BigDecimal(String.valueOf(subtraction));
//        return subtractedDecimal.subtract(subtractionDecimal).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
//    }
//}
