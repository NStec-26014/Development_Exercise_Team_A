package com.example.fullness.stationary.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.fullness.stationary.entity.ProductCategory;

/**
 * カテゴリテーブルへのデータアクセスを定義する Mapper。
 *
 * <p>
 * 商品一覧画面のカテゴリ選択肢の生成や、カテゴリ登録時の永続化処理に利用される。
 * MyBatis により SQL を呼び出し、カテゴリ情報の取得と保存を担当する。
 */
@Mapper
public interface ProductCategoryMapper {

    /**
     * 全カテゴリ一覧を取得する。
     *
     * @return 全カテゴリ一覧
     */
    List<ProductCategory> findAll();

    /**
     * カテゴリIDをもとに単一カテゴリを取得する。
     *
     * @param id カテゴリID
     * @return カテゴリ情報
     */
    ProductCategory findById(Long id);

    /**
     * カテゴリを新規登録する。
     *
     * @param category 登録対象のカテゴリ情報
     * @return 登録件数
     */
    int insert(ProductCategory category);
}
