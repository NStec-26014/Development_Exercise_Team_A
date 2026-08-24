package com.example.fullness.stationary.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.stereotype.Service;

import com.example.fullness.stationary.entity.Product;
import com.example.fullness.stationary.mapper.ProductMapper;
import com.example.fullness.stationary.service.Impl.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productServiceImpl;
    @Mock
    private ProductMapper productMapper;

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
