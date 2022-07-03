package com.mick.springbootmall.service;

import com.mick.springbootmall.dto.ProductQueryParams;
import com.mick.springbootmall.dto.ProductRequest;
import com.mick.springbootmall.model.Product;

import java.util.List;

/* 同樣也是 getProductById */
/* 表示提供的功能 */
public interface ProductService {

    List<Product> getProducts(ProductQueryParams productQueryParams);

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
