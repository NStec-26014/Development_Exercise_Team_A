package com.example.fullness.stationary.service;

import java.util.List;

import com.example.fullness.stationary.entity.ProductCategory;
import com.example.fullness.stationary.entity.Product;

public interface ProductService {

    /**
     * 指定カテゴリに応じた商品一覧をページ単位で取得する。
     *
     * @param id       検索対象カテゴリID。全件検索時は {@code 0} または {@code null}
     * @param page     表示ページ番号
     * @param pageSize 1ページあたりの件数
     * @return 条件に一致する商品一覧
     */
    List<Product> getProductsByProductCategoryWithPaging(Long id, int page, int pageSize);     

    int countProductsByProductCategory(Long id);

    /**
     * 商品IDをもとに単一の商品情報を取得する。
     *
     * @param id 商品ID
     * @return 該当商品情報
     */
    
    Product findById(Long id);

    List<ProductCategory> getAllCategories();

    List<Product> searchProducts(Long categoryId);

    // 商品登録
    void saveProduct(Product product);

    void saveProductCategory(ProductCategory category);

    // 商品削除が完了するとtrueを返す
    boolean deleteProduct(Long id);

    void editProduct(Product product);

}
