package com.mick.springbootmall.service;

import com.mick.springbootmall.dto.CreateOrderRequest;
import com.mick.springbootmall.model.Order;

public interface OrderService {

    Order getOrderById(Integer orderId);
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
