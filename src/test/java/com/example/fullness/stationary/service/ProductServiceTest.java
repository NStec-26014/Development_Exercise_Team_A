package com.example.fullness.stationary.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.fullness.stationary.entity.Product;
import com.example.fullness.stationary.mapper.ProductCategoryMapper;
import com.example.fullness.stationary.mapper.ProductMapper;
import com.example.fullness.stationary.service.impl.ProductServiceImpl;
import com.example.fullness.stationary.service.impl.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)

class ProductServiceTest {

    @Mock
    private ProductCategoryMapper categoryMapper;

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

    @Test
    void editProduct_shouldDelegateToProductMapper() {
        productService.editProduct(product);

        verify(productMapper).edit(product);

    }

    @Test
    void saveProduct_shouldDelegateToProductMapper() {

        Product product = new Product();

        productService.saveProduct(product);
    }
     

    
    // 該当メソッドの返り値が後で変更されコンパイルエラー解消
    @Test
    void deleteProductTest_OK() {
        long productId = 1001;
        when(productMapper.deleteById(productId)).thenReturn(1);
        boolean actual = productService.deleteProduct(productId);
        assertEquals(true, actual);
        verify(productMapper).deleteById(productId);
    }

    // uc011マージの際に実装
    @Test
    public void case1_getProductsByProductCategoryWithPaging_OK() {
        // 【準備】テスト用のダミーの商品データを10件分リストにして作る
        List<Product> mockList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Product p = new Product();
            // 最初の商品だけは検証用（93〜94行目）に合わせる
            p.setId(i == 0 ? 1001L : 1002L + i);
            p.setName(i == 0 ? "水性ボールペン(黒)" : "ダミー商品");
            p.setProductCategoryId(1L);
            mockList.add(p);
        }

        // 【最重要】Mapperが呼ばれたら、作った10件のリストを返すようにMockitoに命じる
        when(productMapper.findByProductCategoryIdWithPaging(1L, 0, 10)).thenReturn(mockList);

        // 実行
        List<Product> result = productService.getProductsByProductCategoryWithPaging(1L, 1, 10);

        // 検証 (これで無事に合格します！)
        assertEquals(10, result.size());
        for (Product product : result) {
            assertEquals(1, product.getProductCategoryId());
        }
        assertEquals(1001, result.get(0).getId());
        assertEquals("水性ボールペン(黒)", result.get(0).getName());
    }

    @Test
    public void case2_countProductsByProductCategory_OK() {
        // 【準備】countメソッドが呼ばれたら「30」を返すようにMockitoに命じる
        when(productMapper.countByProductCategoryId(1L)).thenReturn(30); // ※メソッド名は実際のものに合わせてください

        int result = productService.countProductsByProductCategory(1L);

        assertEquals(30, result);
    }

    @Test
    public void case3_findById_OK() {
        // 【準備】findByIdが呼ばれたら、setUp()で作った「消しゴム」を「水性ボールペン(黒)」に書き換えて返すようにする
        product.setId(1001L);
        product.setName("水性ボールペン(黒)");
        product.setPrice(120);

        when(productMapper.findById(1001L)).thenReturn(product); // ※メソッド名は実際のものに合わせてください

        Product result = productService.findById(1001L);

        assertNotNull(result);
        assertEquals(1001, result.getId());
        assertEquals("水性ボールペン(黒)", result.getName());
        assertEquals(1, result.getProductCategoryId());
        assertEquals(120, result.getPrice());
    }

}
