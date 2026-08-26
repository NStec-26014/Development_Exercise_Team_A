package com.example.fullness.stationary.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductEditForm {

    private Long id; // 商品ID（主キー）
    private String name; // 商品名（例: "水性ボールペン(赤)"）
    private Integer price; // 価格
    private Integer quantity; // 商品在庫数
    private Long productCategoryId; // カテゴリID
    private String imageUrl; // 画像URL

}
