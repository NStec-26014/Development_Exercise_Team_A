package com.example.fullness.stationary.controller.form;

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

    // public Long getId() {
    // return id;
    // }

    // public void setId(Long id) {
    // this.id = id;
    // }

    // public String getName() {
    // return name;
    // }

    // public void setName(String name) {
    // this.name = name;
    // }

    // public Integer getPrice() {
    // return price;
    // }

    // public void setPrice(Integer price) {
    // this.price = price;
    // }

    // public Integer getQuantity() {
    // return quantity;
    // }

    // public void setQuantity(Integer quantity) {
    // this.quantity = quantity;
    // }

    // public Long getProductCategoryId() {
    // return productCategoryId;
    // }

    // public void setProductCategoryId(Long productCategoryId) {
    // this.productCategoryId = productCategoryId;
    // }

    // public String getImageUrl() {
    // return imageUrl;
    // }

    // public void setImageUrl(String imageUrl) {
    // this.imageUrl = imageUrl;
    // }
}
