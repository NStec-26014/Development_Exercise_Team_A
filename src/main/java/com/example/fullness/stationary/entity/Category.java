package com.example.fullness.stationary.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id; // 主キー（product_categoryテーブルのid）
    private String name; // カテゴリ名（例: "ペン", "ノート"）

    public Category() {
    }

    // 新規登録用
    public Category(String name) {
        this.name = name;
    }

    // 全フィールド指定用
    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
