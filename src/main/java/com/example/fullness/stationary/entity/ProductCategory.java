package com.example.fullness.stationary.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * 商品カテゴリを表すエンティティクラス。
 *
 * <p>
 * 商品の分類情報を保持する。カテゴリ名を管理し、
 * 商品一覧画面の検索条件や画面表示用の選択肢として利用される。
 */
@Data
public class ProductCategory implements Serializable {

    private Long id; // 主キー（product_categoryテーブルのid）
    private String name; // カテゴリ名（例: "ペン", "ノート"）

    private static final long serialVersionUID = 1L;

    /**
     * デフォルトコンストラクタ。
     * MyBatis のマッピング処理で利用される。
     */
    public ProductCategory() {
    }

    /**
     * 新規登録時に利用するコンストラクタ。
     *
     * @param name カテゴリ名
     */
    public ProductCategory(String name) {
        this.name = name;
    }

    /**
     * 全フィールドを指定するコンストラクタ。
     *
     * @param id   カテゴリID
     * @param name カテゴリ名
     */
    public ProductCategory(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}