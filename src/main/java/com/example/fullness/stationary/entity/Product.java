package com.example.fullness.stationary.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id; // 商品ID（主キー）
    private Long productCategoryId; // カテゴリID（外部キー）
    private String name; // 商品名（例: "水性ボールペン(赤)"）
    private Integer price; // 価格（例: 120）
    private String imageUrl; // 画像URL（例: "/images/pen_red.png"）
    private Integer deleteFlag; // 論理削除フラグ（0有効, 1削除済み）

    private Integer quantity; // 在庫数（例: 100）

    public Product() {
    }

    // 新規登録用
    public Product(Long productCategoryId, String name, Integer price, String imageUrl) {
        this.productCategoryId = productCategoryId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.deleteFlag = 0;
    }

    // DB取得データ用
    public Product(Long id, Long productCategoryId, String name, Integer price,
            String imageUrl, Integer deleteFlag, Integer quantity) {
        this.id = id;
        this.productCategoryId = productCategoryId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.deleteFlag = deleteFlag;
        this.quantity = quantity;
    }
}