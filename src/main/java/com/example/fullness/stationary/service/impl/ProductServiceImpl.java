package com.example.fullness.stationary.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fullness.stationary.entity.ProductCategory;
import com.example.fullness.stationary.entity.Product;
import com.example.fullness.stationary.mapper.ProductCategoryMapper;
import com.example.fullness.stationary.mapper.ProductMapper;
import com.example.fullness.stationary.service.ProductService;

/**
 * 商品に関する業務ロジックを実装するサービスクラス。
 *
 * <p>
 * Controller から受け取ったカテゴリ条件・ページ情報を元に、
 * {@link ProductMapper} を呼び出して商品一覧の取得、件数計算、単一商品検索を実行する。
 * 画面表示に必要なデータを整形し、永続層と業務層の橋渡し役を担う。
 */
@Service
public class ProductServiceImpl implements ProductService {

    /**
     * 商品データアクセスを担当する MyBatis Mapper。
     */
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductCategoryMapper categoryMapper;

    /**
     * カテゴリ条件に応じて商品一覧をページ単位で取得する。
     *
     * <p>
     * 引数の {@code id} が {@code null} または {@code 0} の場合は全商品検索を実行し、
     * それ以外の場合は指定カテゴリの商品だけを検索する。ページ番号から OFFSET を計算し、
     * MyBatis の SQL へ渡して対象データを取得する。
     *
     * @param id       検索対象カテゴリID。全件表示時は {@code null} または {@code 0}
     * @param page     表示ページ番号（1始まり）
     * @param pageSize 1ページあたりの表示件数
     * @return 条件に合致する商品一覧
     */
    @Override
    public List<Product> getProductsByProductCategoryWithPaging(Long id, int page, int pageSize) {
        int offset = (page - 1) * pageSize;

        if (id == null || id == 0) {
            return productMapper.findAllWithPaging(offset, pageSize);
        } else {
            return productMapper.findByProductCategoryIdWithPaging(id, offset, pageSize);
        }
    }

    /**
     * 指定カテゴリに対する商品件数を取得する。
     *
     * <p>
     * カテゴリが未指定または {@code 0} の場合は全商品の件数を返し、
     * 指定カテゴリがある場合はそのカテゴリの商品数のみを返す。
     * 画面のページ数計算や一覧表示時の件数判定に利用される。
     *
     * @param id 商品カテゴリID。全件集計時は {@code null} または {@code 0}
     * @return 指定条件に一致する商品の総件数
     */
    @Override
    public int countProductsByProductCategory(Long id) {
        if (id == null || id == 0) {
            return productMapper.countAll();
        } else {
            return productMapper.countByProductCategoryId(id);
        }
    }

    /**
     * 指定された商品IDに紐づく商品情報を取得する。
     *
     * <p>
     * 商品詳細画面や編集画面で利用される単一商品の情報を返却する。
     * 取得対象が存在しない場合は {@code null} を返すため、呼び出し元で
     * 画面遷移可否やエラー処理を判断する。
     *
     * @param id 商品ID
     * @return 該当商品情報。存在しない場合は {@code null}
     */
    @Override
    public Product findById(Long id) {
        return productMapper.findById(id);
    }

    public List<ProductCategory> getAllCategories() {
        return categoryMapper.findAll();
    }

    // 商品検索（カテゴリIDで絞り込み）
    public List<Product> searchProducts(Long categoryId) {
        if (categoryId == null || categoryId == 0) {
            return productMapper.findAll();
        }
        return productMapper.findByProductCategoryId(categoryId);
    }

    // 商品登録
    public void saveProduct(Product product) {
        productMapper.insert(product);
    }

    // カテゴリ登録
    public void saveProductCategory(ProductCategory category) {
        categoryMapper.insert(category);
    }

    // 商品削除（論理削除）
    public boolean deleteProduct(Long id) {
        if (productMapper.deleteById(id) == 1) {
            return true;
        } else {
            return false;
        }
    }

    // 商品修正
    public void editProduct(Product product) {
        productMapper.edit(product);

        productMapper.updateStock(product.getId(), product.getQuantity());
    }
}