package com.example.fullness.stationary.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.fullness.stationary.entity.Category;

@Mapper
public interface CategoryMapper {

    List<Category> findAll();

    Category findById(Long id);

    int insert(Category category);

    Category findByCategoryName(String categoryName);
}
