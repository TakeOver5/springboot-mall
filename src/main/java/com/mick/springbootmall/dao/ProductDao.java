package com.mick.springbootmall.dao;


import com.mick.springbootmall.dto.ProductRequest;
import com.mick.springbootmall.model.Product;

public interface ProductDao {
    // 返回方法是 product 類型
    Product getProductById(Integer productId);
    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);
}
