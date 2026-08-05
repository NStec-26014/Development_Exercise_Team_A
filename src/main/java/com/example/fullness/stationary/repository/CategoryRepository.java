package com.example.fullness.stationary.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.fullness.stationary.entity.Category;
import com.example.fullness.stationary.mapper.CategoryMapper;

@Repository
public class CategoryRepository {

    @Autowired
    private CategoryMapper categoryMapper;

    // 全カテゴリ取得
    public List<Category> findAll() {
        return categoryMapper.findAll();
    }

    // ID指定でカテゴリ取得
    public Category findById(Long id) {
        return categoryMapper.findById(id);
    }

    // カテゴリ登録
    public int save(Category category) {
        return categoryMapper.insert(category);
    }
}