package com.example.fullness.stationary.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.fullness.stationary.entity.Product;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ProductMapperTest {

    @Autowired
    private ProductMapper productMapper;

    @Test
    public void Case1_findAllWithPaging_OK() {

        List<Product> result = productMapper.findAllWithPaging(0, 10);

        assertEquals(10, result.size());
    }

    @Test
    public void Case2_findByCategoryIdWithPaging_OK() {

        List<Product> result = productMapper.findByCategoryIdWithPaging(1L, 0, 10);

        assertEquals(10, result.size());
        for (Product product : result) {
            assertEquals(1, product.getProductCategoryId());
        }
    }

    @Test
    public void Case3_countAll_OK() {

        int result = productMapper.countAll();

        assertEquals(32, result);
    }

    @Test
    public void Case4_countByCategoryId_OK() {

        int result = productMapper.countByCategoryId(1L);

        assertEquals(30, result);
    }

    @Test
    public void Case5_findById_OK() {

        Product result = productMapper.findById(1001L);

        assertNotNull(result);
        assertEquals(1001, result.getId());
        assertEquals("水性ボールペン(黒)", result.getName());
    }
}