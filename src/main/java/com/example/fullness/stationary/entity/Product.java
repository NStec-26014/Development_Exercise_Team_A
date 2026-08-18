package com.example.fullness.stationary.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * 商品情報を表すエンティティクラス。
 *
 * <p>
 * 商品の識別子、所属カテゴリ、商品名、価格、画像URL、削除状態を保持する。
 * DB の product テーブルと対応し、画面表示・登録・更新・削除処理の主要データとして利用される。
 */
@Data
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id; // 商品ID（主キー）
    private Long productCategoryId; // カテゴリID（外部キー）
    private String name; // 商品名
    private Integer price; // 価格
    private String imageUrl; // 画像URL
    private Integer deleteFlag; // 0有効, 1削除済み

    /**
     * デフォルトコンストラクタ。
     * MyBatis がデータをマッピングする際に利用される。
     */
    public Product() {
    }

    /**
     * 新規登録時に利用するコンストラクタ。
     *
     * @param productCategoryId 商品が属するカテゴリID
     * @param name              商品名
     * @param price             価格
     * @param imageUrl          画像URL
     */
    public Product(Long productCategoryId, String name, Integer price, String imageUrl) {
        this.productCategoryId = productCategoryId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.deleteFlag = 0;
    }

    /**
     * DB から取得した商品情報を保持するためのコンストラクタ。
     *
     * @param id                商品ID
     * @param productCategoryId カテゴリID
     * @param name              商品名
     * @param price             価格
     * @param imageUrl          画像URL
     * @param deleteFlag        削除状態 0:有効, 1:削除済み
     */
    public Product(Long id, Long productCategoryId, String name, Integer price,
            String imageUrl, Integer deleteFlag) {
        this.id = id;
        this.productCategoryId = productCategoryId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.deleteFlag = deleteFlag;
    }
}