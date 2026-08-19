package com.example.fullness.stationary.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.fullness.stationary.entity.ProductCategory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ProductCategoryMapperTest {

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Test
    public void Case1_findAll_OK() {

        List<ProductCategory> result = productCategoryMapper.findAll();

        assertTrue(result.size() >= 2);
        assertTrue(result.stream().anyMatch(c -> c.getName().equals("文房具")));
        assertTrue(result.stream().anyMatch(c -> c.getName().equals("その他")));
    }

    @Test
    public void Case2_findById_OK() {

        ProductCategory result = productCategoryMapper.findById(1L);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("文房具", result.getName());
    }
}