package com.example.fullness.stationary.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.fullness.stationary.entity.ProductCategory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ProductCategoryServiceTest {

    @Autowired
    private ProductCategoryService productCategoryService;

    @Test
    public void case1_getAllCategories_OK() {

        List<ProductCategory> result = productCategoryService.getAllCategories();

        assertTrue(result.size() >= 2);
        long bunguCount = result.stream()
                .filter(c -> c.getName().equals("文房具"))
                .count();
        assertTrue(bunguCount > 0);

        long otherCount = result.stream()
                .filter(c -> c.getName().equals("その他"))
                .count();
        assertTrue(otherCount > 0);
    }
}
