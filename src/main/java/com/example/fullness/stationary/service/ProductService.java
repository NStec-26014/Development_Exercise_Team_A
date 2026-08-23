package com.example.fullness.stationary.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fullness.stationary.entity.Category;
import com.example.fullness.stationary.entity.Product;
import com.example.fullness.stationary.mapper.CategoryMapper;
import com.example.fullness.stationary.mapper.ProductMapper;

@Service
public class ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    // 全カテゴリ取得（プルダウン表示用）
    public List<Category> getAllCategories() {
        return categoryMapper.findAll();
    }

    // 商品検索（カテゴリIDで絞り込み）
    public List<Product> searchProducts(Long categoryId) {
        if (categoryId == null || categoryId == 0) {
            return productMapper.findAll();
        }
        return productMapper.findByCategoryId(categoryId);
    }

    // 商品登録
    public void saveProduct(Product product) {
        productMapper.insert(product);
    }

    // カテゴリ登録
    public void saveCategory(Category category) {
        categoryMapper.insert(category);
    }

    // 商品削除（論理削除）
    public void deleteProduct(Long id) {
        productMapper.deleteById(id);
    }

    // 商品詳細取得
    public Product findById(Long id) {
        return productMapper.findById(id);
    }
}