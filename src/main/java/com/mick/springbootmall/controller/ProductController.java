package com.mick.springbootmall.controller;

import com.mick.springbootmall.constant.ProductCategory;
import com.mick.springbootmall.dto.ProductQueryParams;
import com.mick.springbootmall.dto.ProductRequest;
import com.mick.springbootmall.model.Product;
import com.mick.springbootmall.service.ProductService;
import com.mick.springbootmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
// 加上 Validated 註解，才能讓 min 和 max 生效
@Validated
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getProducts(
            // 查詢條件 Filtering
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) String search,
            // 排序
            @RequestParam(defaultValue = "created_date") String orderBy, // 由什麼欄位去排序, 預設是要去呈現最新的商品
            @RequestParam(defaultValue = "desc") String sort,            // 升序還是降序，降序，大->小
            // 分頁 Pagination
            // 考慮資料庫效能，不必全部傳遞
            // 最大不可超過 1000 不可以比 0 還小，避免有奇怪的負數
            @RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer limit,    // 取得幾筆
            @RequestParam(defaultValue = "0") @Min(0) Integer offset    // 跳過幾筆
    ) {
        ProductQueryParams productQueryParams = new ProductQueryParams();
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);
        productQueryParams.setOrderBy(orderBy);
        productQueryParams.setSort(sort);
        productQueryParams.setLimit(limit);
        productQueryParams.setOffset(offset);

        // 前端會自動把字串轉成 enum
        List<Product> productList = productService.getProducts(productQueryParams);

        // 根據查詢條件，計算總數筆
        Integer total = productService.countProduct(productQueryParams);

        // 設定 page 分頁
        Page<Product> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total);           // 商品總數資訊
        page.setResults(productList);   // 把商品資訊的值，放入 result 變數裡

        // 不管有沒有查到商品數據都是回傳 200
        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

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

    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
                                                 @RequestBody @Valid ProductRequest productRequest) {

        // 先嘗試查詢商品存不存在
        Product product = productService.getProductById(productId);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        productService.updateProduct(productId, productRequest);

        // 取得更新的商品資訊
        Product updateProduct = productService.getProductById(productId);

        // 200 表示修改成功
        return ResponseEntity.status(HttpStatus.OK).body(updateProduct);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId) {
        productService.deleteProductById(productId);
        // 不用檢查 id 的原因，只要告訢前端商品不存在就好了
        // 確定這商品消失即可
        // 204 刪除成功，build 回傳給前端
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
