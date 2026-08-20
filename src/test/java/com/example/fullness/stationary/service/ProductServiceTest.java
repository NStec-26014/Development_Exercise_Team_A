package com.example.fullness.stationary.service;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.fullness.stationary.entity.Product;
import com.example.fullness.stationary.mapper.CategoryMapper;
import com.example.fullness.stationary.mapper.ProductMapper;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductMapper productMapper;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private ProductService productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1007L);
        product.setName("消しゴム");
        product.setPrice(120);
        product.setQuantity(10);
        product.setProductCategoryId(1L);
        product.setImageUrl("/images/eraser.png");
        product.setDeleteFlag(0);
    }

    // 私が書く
    @Test
    void editProduct_shouldDelegateToProductMapper() {
        productService.editProduct(product);

        verify(productMapper).edit(product);
    }

}
