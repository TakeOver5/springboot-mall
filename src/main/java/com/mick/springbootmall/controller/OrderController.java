package com.mick.springbootmall.controller;


import com.mick.springbootmall.dto.CreateOrderRequest;
import com.mick.springbootmall.model.Order;
import com.mick.springbootmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 路徑的使用，要有帳號，才有訂單
    // 訂單是帳號的附屬功能
    // 使用 Restful 表示帳號與訂單的關係
    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<?> createOrder(@PathVariable Integer userId,
                                         @RequestBody @Valid CreateOrderRequest createOrderRequest) {
        Integer orderId = orderService.createOrder(userId, createOrderRequest);

        // 取得訂單資料
        Order order = orderService.getOrderById(orderId);

        // 返回整筆訂單資訊 order
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

}
