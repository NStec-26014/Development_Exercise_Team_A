package com.example.fullness.stationary.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fullness.stationary.entity.Category;
import com.example.fullness.stationary.entity.Product;
import com.example.fullness.stationary.mapper.CategoryMapper;
import com.example.fullness.stationary.mapper.ProductMapper;

/**
 * 商品・カテゴリに関する業務処理を担うサービスクラス。
 *
 * <p>
 * 本クラスは Controller からの要求を受け、
 * {@link ProductMapper} と {@link CategoryMapper} を利用してデータアクセスを実行する。
 * 画面表示に必要な一覧取得、カテゴリ検索、ページング計算、登録・削除・詳細取得などを一括して管理する。
 */
@Service
public class ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 全カテゴリを取得する。
     *
     * <p>
     * 商品一覧画面の検索フォームで利用するカテゴリ一覧を取得する。
     *
     * @return 全カテゴリのリスト
     */
    public List<Category> getAllCategories() {
        return categoryMapper.findAll();
    }

    /**
     * 指定カテゴリに応じた商品一覧をページ単位で取得する。
     *
     * <p>
     * {@code id} が {@code null} または {@code 0} の場合は全商品を対象とし、
     * それ以外の場合は指定カテゴリの商品を抽出する。
     *
     * @param id       検索対象カテゴリID。全件検索時は {@code 0} または {@code null}
     * @param page     表示ページ番号
     * @param pageSize 1ページあたりの件数
     * @return 条件に一致する商品一覧
     */
    public List<Product> geProductsByCategoryWithPaging(Long id, int page, int pageSize) {
        int offset = (page - 1) * pageSize;

        if (id == null || id == 0) {
            return productMapper.findAllWithPaging(offset, pageSize);
        } else {
            return productMapper.findByCategoryIdWithPaging(id, offset, pageSize);

        }
    }

    /**
     * 指定カテゴリの商品総件数を取得する。
     *
     * @param id 検索対象カテゴリID。全件検索時は {@code 0} または {@code null}
     * @return 商品件数
     */
    public int countProductsByCategory(Long id) {
        if (id == null || id == 0) {
            return productMapper.countAll();
        } else {
            return productMapper.countByCategoryId(id);
        }
    }

    /**
     * 商品を登録する。
     *
     * @param product 登録対象の商品情報
     */
    public void saveProduct(Product product) {
        productMapper.insert(product);
    }

    /**
     * カテゴリを登録する。
     *
     * @param category 登録対象のカテゴリ情報
     */
    public void saveCategory(Category category) {
        categoryMapper.insert(category);
    }

    // /**
    // * 商品を論理削除または削除対象として処理する。
    // *
    // * @param id 削除対象の商品ID
    // */
    // public void deleteProduct(Long id) {
    // productMapper.deleteById(id);
    // }

    /**
     * 商品IDをもとに単一の商品情報を取得する。
     *
     * @param id 商品ID
     * @return 該当商品情報
     */
    public Product findById(Long id) {
        return productMapper.findById(id);
    }
}