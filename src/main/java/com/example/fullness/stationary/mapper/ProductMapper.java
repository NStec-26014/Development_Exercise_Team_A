package com.example.fullness.stationary.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.fullness.stationary.entity.Product;

@Mapper
public interface ProductMapper {

    List<Product> findAll();

    List<Product> findByCategoryId(Long categoryId);

    Product findById(Long id);

    int insert(Product product);

    int edit(Product product);

    int deleteById(Long id);

    int updateStock(@Param("id") Long id, @Param("quantity") Integer quantity);
}
