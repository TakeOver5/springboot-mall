package com.mick.springbootmall.controller;

import com.mick.springbootmall.model.Product;
import com.mick.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
}
