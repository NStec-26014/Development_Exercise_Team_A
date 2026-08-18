package com.example.fullness.stationary.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fullness.stationary.entity.ProductCategory;
import com.example.fullness.stationary.mapper.ProductCategoryMapper;
import com.example.fullness.stationary.service.ProductCategoryService;

/**
 * 商品カテゴリに関する業務ロジックを実装するサービスクラス。
 *
 * <p>
 * Controller からのカテゴリ一覧表示要求や新規登録要求を受け取り、
 * {@link ProductCategoryMapper} を通じてデータベースとのやり取りを行う。
 * 画面表示用のカテゴリ情報の取得と、カテゴリ追加処理の実行を担当する。
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    /**
     * 商品カテゴリのデータアクセスを担当する MyBatis Mapper。
     */
    @Autowired
    private ProductCategoryMapper categoryMapper;

    /**
     * 登録済みの全カテゴリ一覧を取得する。
     *
     * <p>
     * 商品一覧画面のカテゴリ選択肢作成や、カテゴリ管理画面の表示に利用される。
     * MyBatis の {@code findAll} を呼び出し、DB 上に存在するカテゴリを順序付きで返却する。
     *
     * @return 全カテゴリ一覧
     */
    @Override
    public List<ProductCategory> getAllCategories() {
        return categoryMapper.findAll();
    }

    /**
     * 新しいカテゴリをデータベースへ登録する。
     *
     * <p>
     * Controller から受け取ったカテゴリ情報をそのまま Mapper に渡し、
     * INSERT を実行する。登録結果の件数は画面側での処理に利用することを想定しているが、
     * 現在の実装ではその結果を呼び出し元へ返却せず、登録完了の確認を前提にした処理として扱う。
     *
     * @param category 登録対象のカテゴリ情報
     */
    @Override
    public void saveCategory(ProductCategory category) {
        categoryMapper.insert(category);
    }
}
