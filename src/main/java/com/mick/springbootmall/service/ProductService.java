package com.mick.springbootmall.service;

import com.mick.springbootmall.dto.ProductRequest;
import com.mick.springbootmall.model.Product;

/* 同樣也是 getProductById */
/* 表示提供的功能 */
public interface ProductService {
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);
}
