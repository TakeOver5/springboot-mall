package com.mick.springbootmall.dao;


import com.mick.springbootmall.model.Product;

public interface ProductDao {
    // 返回方法是 product 類型
    Product getProductById(Integer productId);
}