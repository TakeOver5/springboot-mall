package com.mick.springbootmall.rowmapper;

import com.mick.springbootmall.constant.ProductCategory;
import com.mick.springbootmall.model.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet resultSet, int i) throws SQLException {
        Product product = new Product();
        product.setProductId(resultSet.getInt("product_id"));
        product.setProductName(resultSet.getString("product_name"));

        // 接住 resultSet 中，資料庫取出來的值
        String categoryStr = resultSet.getString("category");
        // 字串轉對應的 Enum 類型，根據字串的值，去找尋對應的 ProductCategory 中的固定值
        ProductCategory category = ProductCategory.valueOf(categoryStr);
        product.setCategory(category);

        // 單行寫法
        // product.setCategory(ProductCategory.valueOf(resultSet.getString("category")));

        product.setImageUrl(resultSet.getString("image_url"));
        product.setPrice(resultSet.getInt("price"));
        product.setStock(resultSet.getInt("stock"));
        product.setDescription(resultSet.getString("description"));
        product.setCreateDate(resultSet.getTimestamp("created_date"));
        product.setLastModifiedDate(resultSet.getTimestamp("last_modified_date"));

        return product;
    }
}
