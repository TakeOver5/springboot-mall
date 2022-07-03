package com.mick.springbootmall.dao;


import com.mick.springbootmall.dto.ProductQueryParams;
import com.mick.springbootmall.dto.ProductRequest;
import com.mick.springbootmall.model.Product;

import java.util.List;

public interface ProductDao {

    Integer countProduct(ProductQueryParams productQueryParams);

    List<Product> getProducts(ProductQueryParams productQueryParams);

    // 返回方法是 product 類型
    Product getProductById(Integer productId);
    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
