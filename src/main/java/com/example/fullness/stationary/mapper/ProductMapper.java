package com.example.fullness.stationary.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.fullness.stationary.entity.Product;

/**
 * 商品テーブルへのデータアクセスを定義する Mapper。
 *
 * <p>
 * MyBatis により SQL を実行し、商品一覧取得、カテゴリ別絞り込み、件数取得、
 * 単一商品の取得、登録、削除処理（担当ではない）を担当する。
 *
 * <p>
 * 画面表示や業務処理で必要とされる商品情報を SQL ベースで取得・更新する責務を持つ。
 */
@Mapper
public interface ProductMapper {

        /**
         * 商品をページ単位で全件取得する。
         *
         * @param offset   取得開始位置
         * @param pageSize 1ページあたりの件数
         * @return 商品一覧
         */
        List<Product> findAllWithPaging(@Param("offset") int offset,
                        @Param("pageSize") int pageSize);

        /**
         * 指定カテゴリの商品をページ単位で取得する。
         *
         * @param categoryId カテゴリID
         * @param offset     取得開始位置
         * @param pageSize   1ページあたりの件数
         * @return 該当カテゴリの商品一覧
         */
        List<Product> findByCategoryIdWithPaging(@Param("categoryId") Long categoryId,
                        @Param("offset") int offset,
                        @Param("pageSize") int pageSize);

        /**
         * 全商品件数を取得する。
         *
         * @return 全商品件数
         */
        int countAll();

        /**
         * 指定カテゴリの商品件数を取得する。
         *
         * @param categoryId カテゴリID
         * @return 対象カテゴリの商品件数
         */
        int countByCategoryId(@Param("categoryId") Long categoryId);

        /**
         * 商品IDをキーに単一の商品を取得する。
         *
         * @param id 商品ID
         * @return 商品情報
         */
        Product findById(Long id);

        // /**
        // * 商品を新規登録する。
        // *
        // * @param product 登録対象の商品情報
        // */
        // void insert(Product product);

        // /**
        // * 商品IDをもとに商品を削除する。
        // *
        // * @param id 削除対象の商品ID
        // */
        // void deleteById(Long id);
}