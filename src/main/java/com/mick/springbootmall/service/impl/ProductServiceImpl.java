package com.mick.springbootmall.service.impl;

import com.mick.springbootmall.dao.ProductDao;
import com.mick.springbootmall.dto.ProductRequest;
import com.mick.springbootmall.model.Product;
import com.mick.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    // 與 dao 層溝通，注入 bean
    private ProductDao productDao;

    @Override
    // 直接 call dao層的對應參數
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        return productDao.createProduct(productRequest);
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        productDao.updateProduct(productId, productRequest);
    }

    @Override
    public void deleteProductById(Integer productId) {
        productDao.deleteProductById(productId);
    }
}
