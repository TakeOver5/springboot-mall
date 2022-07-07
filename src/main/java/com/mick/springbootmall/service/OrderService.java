package com.mick.springbootmall.service;

import com.mick.springbootmall.dto.CreateOrderRequest;
import com.mick.springbootmall.dto.OrderQueryParams;
import com.mick.springbootmall.model.Order;

import java.util.List;

public interface OrderService {

    Integer countOrder(OrderQueryParams orderQueryParams);

    List<Order> getOrders(OrderQueryParams orderQueryParams);

    Order getOrderById(Integer orderId);
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
