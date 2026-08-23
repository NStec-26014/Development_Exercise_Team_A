package com.example.fullness.stationary.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import com.example.fullness.stationary.entity.Product;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductMapperTest {

    @Autowired
    private ProductMapper productMapper;

    @Test
    void testInsert_OK1() {

        Product product = new Product();
        product.setProductCategoryId(1L);
        product.setName("テストボールペン");
        product.setPrice(500);
        product.setImageUrl(null);
        product.setDeleteFlag(0);

        productMapper.insert(product);

        Product actual = productMapper.findById(product.getId());

        assertNotNull(actual);
        assertEquals(product.getId(), actual.getId());
        assertEquals(1, actual.getProductCategoryId());
        assertEquals("テストボールペン", actual.getName());
        assertEquals(500, actual.getPrice());
        assertNull(actual.getImageUrl());
        assertEquals(0, actual.getDeleteFlag());
    }
}
