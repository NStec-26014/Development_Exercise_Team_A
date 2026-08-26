package com.example.fullness.stationary.form;

import lombok.Data;

@Data
public class ProductRegistForm {

    private String name; // 商品名
    private Integer price; // 価格
    private Integer quantity; // 商品在庫数
    private int productCategoryId; // カテゴリID
    private String imageUrl; // 画像URL
    // private int stock;
    private String categoryName;

}
