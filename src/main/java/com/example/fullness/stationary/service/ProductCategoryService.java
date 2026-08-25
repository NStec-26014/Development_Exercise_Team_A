package com.example.fullness.stationary.service;

import java.util.List;

import com.example.fullness.stationary.entity.ProductCategory;

/**
 * カテゴリに関する業務処理のインターフェース。
 *
 * <p>
 * Controller はこのインターフェースに依存し、
 * 実装は {@code service.impl} 配下の実装クラスに委譲する。
 */
public interface ProductCategoryService {

    /**
     * 全カテゴリを取得する。
     *
     * @return 全カテゴリ一覧
     */
    List<ProductCategory> getAllCategories();

    // /**
    // * カテゴリを登録する。
    // *
    // * @param category 登録対象のカテゴリ情報
    // */
    // void saveCategory(ProductCategory category);
}