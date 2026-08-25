package com.example.fullness.stationary.service;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.fullness.stationary.entity.ProductCategory;
import com.example.fullness.stationary.mapper.ProductCategoryMapper;
import com.example.fullness.stationary.service.impl.ProductCategoryServiceImpl;

//CategoryServiceの単体テストを行うクラス
@ExtendWith(MockitoExtension.class)
public class ProductCategoryServiceTest {

    @InjectMocks
    private ProductCategoryServiceImpl categoryService;
    @Mock
    private ProductCategoryMapper categoryMapper;

    // Serviceの登録メソッドを呼び出すときにMapperのinsertメソッドが正しく実行されることを検証
    @Test
    void saveCategory_shouldDelegateToCategoryMapper() {

        ProductCategory category = new ProductCategory("雑貨");

        categoryService.saveProductCategory(category);

        verify(categoryMapper).insert(category);
    }
}
