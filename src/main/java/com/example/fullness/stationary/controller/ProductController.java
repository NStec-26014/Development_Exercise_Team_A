package com.example.fullness.stationary.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.fullness.stationary.entity.Category;
import com.example.fullness.stationary.entity.Product;
import com.example.fullness.stationary.service.ProductService;

/**
 * 管理者向けの商品管理画面コントローラ。
 *
 * <p>
 * 本クラスは以下の責務を持つ。
 * <ul>
 * <li>商品一覧画面の初期表示</li>
 * <li>カテゴリ条件による商品検索</li>
 * <li>ページング情報の取得と画面への受け渡し</li>
 * <li>商品削除・編集画面への遷移</li>
 * </ul>
 *
 * <p>
 * URL ルーティングは {@code /admin/product} を基点とし、一覧表示と個別画面の表示を担当する。
 * 画面表示には Thymeleaf のテンプレートを返し、モデルへ表示用データをセットしてビューへ渡す。
 */
@Controller
@RequestMapping("/admin/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * 商品一覧画面を表示する。
     *
     * <p>
     * 以下の処理を行う。
     * <ol>
     * <li>カテゴリ一覧を取得し、検索フォームの選択肢として利用する</li>
     * <li>リクエストパラメータの category と page をもとに商品一覧を取得する</li>
     * <li>カテゴリに紐づく商品件数を算出し、総ページ数を計算する</li>
     * <li>取得したデータを Model にセットして {@code admin/product} テンプレートを返す</li>
     * </ol>
     *
     * @param category 検索対象カテゴリID。未指定または {@code 0} の場合は全件検索を行う
     * @param page     表示ページ番号。未指定時は 1 として扱う
     * @param model    表示用のモデルオブジェクト。カテゴリ一覧、商品一覧、選択中カテゴリ、総ページ数を格納する
     * @return 商品一覧画面を表す Thymeleaf URL {@code admin/product}
     */
    @GetMapping
    public String showProductList(
            @RequestParam(name = "category", required = false, defaultValue = "0") Long category,
            @RequestParam(name = "page", defaultValue = "1") int page,
            Model model) {

        int pageSize = 10;
        // 1. カテゴリ一覧取得
        List<Category> categories = productService.getAllCategories();

        // 2. 商品検索
        List<Product> products = productService.geProductsByCategoryWithPaging(category, page, pageSize);

        int totalCount = productService.countProductsByCategory(category);
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);
        // 3. Modelにデータを詰める
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("totalPages", totalPages);

        // 4. テンプレート名を返す
        return "admin/product"; // → templates/admin/product.html
    }

    /**
     * 商品削除確認画面を表示する。
     *
     * <p>
     * 一覧画面の削除リンクから遷移される画面であり、削除対象の商品IDを画面に渡す。
     * 実削除処理自体はこのコントローラでは行わず、別の処理に任せる想定で、削除確認画面の表示のみを担当する。
     *
     * @param id    削除対象の商品ID
     * @param model 削除対象IDをビューへ渡すためのモデル
     * @return 商品削除確認画面URL {@code admin/product-delete}
     */
    @GetMapping("/delete/{id}")
    public String showDeletePage(@PathVariable Long id, Model model) {
        model.addAttribute("productId", id);
        return "admin/product-delete";
    }

    /**
     * 商品修正画面を表示する。
     *
     * <p>
     * 一覧画面または関連画面から商品修正画面への遷移時に呼ばれる。
     * 対象の商品IDをモデルへ格納し、対応する編集フォームテンプレートへ遷移させる。
     *
     * @param id    編集対象の商品ID
     * @param model 編集対象IDをビューへ渡すためのモデル
     * @return 商品修正画面URL {@code admin/product-edit}
     */
    @GetMapping("/edit/{id}")
    public String showEditPage(@PathVariable Long id, Model model) {
        model.addAttribute("productId", id);
        return "admin/product-edit";
    }

}
