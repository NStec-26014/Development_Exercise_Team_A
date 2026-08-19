package com.example.fullness.stationary.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.fullness.stationary.entity.Product;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    public void case1_getProductsByCategoryWithPaging_OK() {
        // 実行
        List<Product> result = productService.getProductsByCategoryWithPaging(1L, 1, 10);

        // 検証
        assertEquals(10, result.size());
        for (Product product : result) {
            assertEquals(1, product.getProductCategoryId());
        }
        assertEquals(1001, result.get(0).getId());
        assertEquals("水性ボールペン(黒)", result.get(0).getName());
    }

    @Test
    public void case2_countProductsByCategory_OK() {

        int result = productService.countProductsByCategory(1L);

        assertEquals(30, result);
    }

    @Test
    public void case3_findById_OK() {

        Product result = productService.findById(1001L);

        assertNotNull(result);
        assertEquals(1001, result.getId());
        assertEquals("水性ボールペン(黒)", result.getName());
        assertEquals(1, result.getProductCategoryId());
        assertEquals(120, result.getPrice());
    }
}
