package com.mick.springbootmall.service.impl;

import com.mick.springbootmall.controller.OrderController;
import com.mick.springbootmall.dao.OrderDao;
import com.mick.springbootmall.dao.ProductDao;
import com.mick.springbootmall.dao.UserDao;
import com.mick.springbootmall.dto.BuyItem;
import com.mick.springbootmall.dto.CreateOrderRequest;
import com.mick.springbootmall.model.Order;
import com.mick.springbootmall.model.OrderItem;
import com.mick.springbootmall.model.Product;
import com.mick.springbootmall.model.User;
import com.mick.springbootmall.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    // log 填入當前 class
    private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Override
    public Order getOrderById(Integer orderId) {

        // 取得訂單
        Order order = orderDao.getOrderById(orderId);

        // 取得 Item
        List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(orderId);

        // 合併數據一張訂單有多個 item，要擴充 Order class
        order.setOrderItemList(orderItemList);

        return order;
    }

    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
        // 檢查 user 是否存在
        User user = userDao.getUserById(userId);

        if(user == null) {
            log.warn("該 userId {} 不存在", userId);
            // 中斷請求
            throw new ResponseStatusException((HttpStatus.BAD_REQUEST));
        }

        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        // 商業邏輯的判斷
        for(BuyItem buyItem : createOrderRequest.getBuyItemList()) {
            Product product = productDao.getProductById(buyItem.getProductId());


            // 檢查 product 是否存在、庫存是否足夠
            if(product == null) {
                log.warn("商品 {} 不存在", buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            } else if(product.getStock() < buyItem.getQuantity()) {
                log.warn("商品 {} 庫存數量不足，無法購買。剩餘庫存 {}，欲購買數量 {}",
                        buyItem.getProductId(), product.getStock(), buyItem.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            // 扣除商品庫存
            productDao.updateStock(product.getProductId(), product.getStock() - buyItem.getQuantity());
            // 計算總價錢
            int amount = buyItem.getQuantity() * product.getPrice();
            totalAmount = totalAmount + amount;

            // 轉換 BuyItem to OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);

            orderItemList.add(orderItem);
        }

        // 創建訂單，因為是由兩張 table 構成的
        // 使用者和總計
        Integer orderId = orderDao.createOrder(userId, totalAmount);

        orderDao.createOrderItems(orderId, orderItemList);

        return orderId;
    }
}
