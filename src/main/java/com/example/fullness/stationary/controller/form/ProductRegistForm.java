package com.example.fullness.stationary.controller.form;

import lombok.Data;

@Data
public class ProductRegistForm {

    private String name; // 商品名
    private Integer price; // 価格
    private Integer quantity; // 商品在庫数
    private int productCategoryId; // カテゴリID
    private String imageUrl; // 画像URL
    private String productCategoryName; // カテゴリ名

}
