package com.example.fullness.stationary.entity;

import lombok.Data;

@Data
public class ProductStock {

    private long id; // 商品在庫ID
    private Integer productId; // 商品ID(外部キー)
    private Integer quantity; // 商品在庫数

}
