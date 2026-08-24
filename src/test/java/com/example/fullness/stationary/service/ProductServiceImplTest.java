package com.example.fullness.stationary.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.fullness.stationary.entity.Product;
import com.example.fullness.stationary.mapper.CategoryMapper;
import com.example.fullness.stationary.mapper.ProductMapper;
import com.example.fullness.stationary.service.Impl.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private CategoryMapper categoryMapper;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productServiceImpl;

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

    @Test
    void editProduct_shouldDelegateToProductMapper() {
        productServiceImpl.editProduct(product);

        verify(productMapper).edit(product);

    }

    // @InjectMocks
    // private ProductService productService;
    // @Mock
    // private ProductMapper productMapper;

    @Test
    void saveProduct_shouldDelegateToProductMapper() {

        Product product = new Product();

        productServiceImpl.saveProduct(product);

        verify(productMapper).insert(product);
    }

    @Test
    void deleteProductTest_OK() {
        long productId = 1001;
        when(productMapper.deleteById(productId)).thenReturn(1);
        boolean actual = productServiceImpl.deleteProduct(productId);
        assertEquals(true, actual);
        verify(productMapper).deleteById(productId);
    }

}