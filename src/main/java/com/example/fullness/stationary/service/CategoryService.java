package com.example.fullness.stationary.service;

import java.util.List;

import com.example.fullness.stationary.entity.Category;

public interface CategoryService {
    List<Category> getAllCategories();

    Category findById(Long id);

    void saveCategory(Category category);
}
