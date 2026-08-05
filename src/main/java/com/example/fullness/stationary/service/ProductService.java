package com.example.fullness.stationary.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fullness.stationary.entity.Category;
import com.example.fullness.stationary.entity.Product;
import com.example.fullness.stationary.repository.CategoryRepository;
import com.example.fullness.stationary.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // 全カテゴリ取得（プルダウン表示用）
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // 商品検索（カテゴリIDで絞り込み）
    public List<Product> searchProducts(Long categoryId) {
        if (categoryId == null || categoryId == 0) {
            return productRepository.findAll();
        }
        return productRepository.findByCategoryId(categoryId);
    }

    // 商品登録
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    // 商品削除（論理削除）
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // 商品詳細取得
    public Product findById(Long id) {
        return productRepository.findById(id);
    }
}