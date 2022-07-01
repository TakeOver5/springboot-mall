package com.mick.springbootmall.controller;

import com.mick.springbootmall.dto.ProductRequest;
import com.mick.springbootmall.model.Product;
import com.mick.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products/{productId}")
    // @PathVariable 表示值是源於 url 路徑
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId) {
        // 使用 ProductService 的方法
        Product product = productService.getProductById(productId);

        if(product != null) {
            // 把 product 放到 body 就能傳給前端
            return ResponseEntity.status(HttpStatus.OK).body(product);
        } else {
            // 404 表示找不到商品
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/products")
    // RequestBody 表示前端傳進的 json 參數
    // notnull 要使用還要加上 @Valid 才能生效
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        // 預期這個方法會去資料庫中創建商品
        // 並返回 id
        Integer productId = productService.createProduct(productRequest);

        Product product = productService.getProductById(productId);

        // 201 表示數據被創建
        // 放入 body 傳回給前端
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
}
