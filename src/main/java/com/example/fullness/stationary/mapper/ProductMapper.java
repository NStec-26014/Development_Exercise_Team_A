package com.example.fullness.stationary.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.fullness.stationary.entity.Product;

@Mapper
public interface ProductMapper {

    List<Product> findAllWithPaging(@Param("offset") int offset,
            @Param("pageSize") int pageSize);

    List<Product> findByCategoryIdWithPaging(@Param("categoryId") Long categoryId,
            @Param("offset") int offset,
            @Param("pageSize") int pageSize);

    int countAll();

    int countByCategoryId(@Param("categoryId") Long categoryId);

    Product findById(Long id);

    void insert(Product product);

    void deleteById(Long id);
}