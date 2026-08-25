package com.example.fullness.stationary;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.fullness.stationary.entity.Category;
import com.example.fullness.stationary.mapper.CategoryMapper;
import com.example.fullness.stationary.service.CategoryService;

//CategoryServiceの単体テストを行うクラス
@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;
    @Mock
    private CategoryMapper categoryMapper;

    // Serviceの登録メソッドを呼び出すときにMapperのinsertメソッドが正しく実行されることを検証
    @Test
    void saveCategory_shouldDelegateToCategoryMapper() {

        Category category = new Category("雑貨");

        categoryService.saveCategory(category);

        verify(categoryMapper).insert(category);
    }
}
