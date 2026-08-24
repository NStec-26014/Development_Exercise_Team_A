package com.example.fullness.stationary.service;

import java.util.List;

import com.example.fullness.stationary.entity.Product;

/**
 * 商品に関する業務処理のインターフェース。
 *
 * <p>
 * Controller はこのインターフェースに依存し、
 * 実際の実装は {@code service.impl} 配下の実装クラスに委譲する。
 */
public interface ProductService {

    /**
     * 指定カテゴリに応じた商品一覧をページ単位で取得する。
     *
     * @param id       検索対象カテゴリID。全件検索時は {@code 0} または {@code null}
     * @param page     表示ページ番号
     * @param pageSize 1ページあたりの件数
     * @return 条件に一致する商品一覧
     */
    List<Product> getProductsByCategoryWithPaging(Long id, int page, int pageSize);

    /**
     * 指定カテゴリの商品総件数を取得する。
     *
     * @param id 検索対象カテゴリID
     * @return 商品件数
     */
    int countProductsByCategory(Long id);

    /**
     * 商品IDをもとに単一の商品情報を取得する。
     *
     * @param id 商品ID
     * @return 該当商品情報
     */
    Product findById(Long id);

    // 商品検索（カテゴリIDで絞り込み）
    public List<Product> searchProducts(Long categoryId) {
        if (categoryId == null || categoryId == 0) {
            return productMapper.findAll();
        }
        return productMapper.findByCategoryId(categoryId);
    }

    // 商品登録
    public void saveProduct(Product product) {
        productMapper.insert(product);
    }

    // カテゴリ登録
    public void saveCategory(Category category) {
        categoryMapper.insert(category);
    }

    // 商品削除
    public void deleteProduct(Long id) {
        productMapper.deleteById(id);
    }

    // 商品修正
    public void editProduct(Product product) {
        productMapper.edit(product);

        productMapper.updateStock(product.getId(), product.getQuantity());
    }

    // 商品詳細取得
    public Product findById(Long id) {
        return productMapper.findById(id);
    }
}