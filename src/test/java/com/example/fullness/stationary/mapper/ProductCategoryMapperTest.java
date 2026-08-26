package com.example.fullness.stationary.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.fullness.stationary.entity.ProductCategory;

//CategoryMapperの単体テストを行うクラス
@SpringBootTest
public class ProductCategoryMapperTest {

    @Autowired
    private ProductCategoryMapper categoryMapper;

    // 新規カテゴリを登録し、自動採番されたIDでデータが正しく取得できることを検証
    @Test
    void testInsert_OK1() {

        ProductCategory category = new ProductCategory();
        category.setName("雑貨");

        categoryMapper.insert(category);

        ProductCategory actual = categoryMapper.findById(category.getId());

        assertNotNull(actual);
        assertEquals(category.getId(), actual.getId());
        assertEquals("雑貨", actual.getName());
    }

}
