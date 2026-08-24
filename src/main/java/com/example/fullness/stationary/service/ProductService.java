package com.example.fullness.stationary.service;

import java.util.List;

import com.example.fullness.stationary.entity.Category;
import com.example.fullness.stationary.entity.Product;

public interface ProductService {

    List<Category> getAllCategories();

    List<Product> searchProducts(Long categoryId);

    void saveProduct(Product product);

    void saveCategory(Category category);

    // 商品削除が完了するとtrueを返す
    boolean deleteProduct(Long id);

    void editProduct(Product product);

    Product findById(Long id);

}
