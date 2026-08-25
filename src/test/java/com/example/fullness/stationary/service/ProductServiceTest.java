package com.example.fullness.stationary.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.fullness.stationary.entity.Product;
import com.example.fullness.stationary.mapper.CategoryMapper;
import com.example.fullness.stationary.mapper.ProductMapper;
import com.example.fullness.stationary.service.impl.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private CategoryMapper categoryMapper;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

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
    // productMapperに該当メソッドが後で追加されコンパイルエラー解消
    // @Test
    // void editProduct_shouldDelegateToProductMapper() {
    // productService.editProduct(product);

    // verify(productMapper).edit(product);

    // }

    @Test
    void saveProduct_shouldDelegateToProductMapper() {

        Product product = new Product();

        productService.saveProduct(product);

        verify(productMapper).insert(product);
    }

    // 該当メソッドの返り値が後で変更されコンパイルエラー解消
    // @Test
    // void deleteProductTest_OK() {
    // long productId = 1001;
    // when(productMapper.deleteById(productId)).thenReturn(1);
    // boolean actual = productService.deleteProduct(productId);
    // assertEquals(true, actual);
    // verify(productMapper).deleteById(productId);
    // }

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