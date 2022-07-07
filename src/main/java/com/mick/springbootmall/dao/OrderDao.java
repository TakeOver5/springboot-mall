package com.mick.springbootmall.dao;

import com.mick.springbootmall.model.Order;
import com.mick.springbootmall.model.OrderItem;

import java.util.List;

public interface OrderDao {

    Order getOrderById(Integer order);

    // 一筆訂單可能會買很多東西
    List<OrderItem> getOrderItemsByOrderId(Integer orderId);

    Integer createOrder(Integer userId, Integer totalAmount);

    void createOrderItems(Integer orderId, List<OrderItem> orderItemList);

}
