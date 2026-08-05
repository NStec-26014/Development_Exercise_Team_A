package com.example.fullness.stationary.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.fullness.stationary.entity.Product;
import com.example.fullness.stationary.mapper.ProductMapper;

@Repository
public class ProductRepository {

    @Autowired
    private ProductMapper productMapper;

    // 全商品取得（削除済み除く）
    public List<Product> findAll() {
        return productMapper.findAll();
    }

    // カテゴリID指定で商品取得
    public List<Product> findByCategoryId(Long categoryId) {
        return productMapper.findByCategoryId(categoryId);
    }

    // ID指定で商品取得
    public Product findById(Long id) {
        return productMapper.findById(id);
    }

    // 商品登録
    public int save(Product product) {
        return productMapper.insert(product);
    }

    public int deleteById(Long id) {
        return productMapper.deleteById(id);
    }
}
