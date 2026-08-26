package com.example.fullness.stationary.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.fullness.stationary.entity.ProductCategory;
import com.example.fullness.stationary.controller.form.ProductEditForm;
import com.example.fullness.stationary.entity.Product;
import com.example.fullness.stationary.validator.ProductEditValidator;

import jakarta.servlet.http.HttpSession;

import com.example.fullness.stationary.service.ProductCategoryService;
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

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    ProductEditValidator productEditValidator;

    @ModelAttribute("productEditForm")
    public ProductEditForm productEditForm() {
        return new ProductEditForm();
    }

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
    @GetMapping({ "", "/" })
    public String showProductList(
            @RequestParam(name = "category", required = false, defaultValue = "0") Long category,
            @RequestParam(name = "page", defaultValue = "1") int page,
            Model model) {

        int pageSize = 10;

        // 1. カテゴリー一覧取得
        List<ProductCategory> categories = productCategoryService.getAllCategories();

        // 2. 商品検索
        List<Product> products = productService.getProductsByProductCategoryWithPaging(category, page, pageSize);

        int totalCount = productService.countProductsByProductCategory(category);

        // 【安全ガード】もし商品総数が0件なら、最大ページ数は強制的に「1」にする
        int totalPages = (totalCount == 0) ? 1 : (int) Math.ceil((double) totalCount / pageSize);

        // 3. Modelにデータを詰める（HTML側の変数名と100%完全に一致させました）
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("selectedProductCategory", category);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page); // ⭕ ここを正しく実際のページ番号（page）に直しました！

        return "admin/product";
    }

    // 確認画面を表示するメソッド
    @GetMapping("/delete/{id}")

    public String ShowProductDeleteConfirm(@PathVariable("id") Long id, HttpSession session, Model model) {
        // Product型の情報をidを基に取得して追加する
        model.addAttribute("product", productService.findById(id));
        String categoryName = productCategoryService
                .findById(productService.findById(id).getProductCategoryId())
                .getName();
        model.addAttribute("categoryName", categoryName);
        session.setAttribute("deleteId", id);
        session.setAttribute("deleteName", productService.findById(id).getName());

        // 確認画面に遷移する
        return ("admin/product/delete_confirm");
    }

    // 削除を実行するメソッド
    @PostMapping("/delete/doDelete")
    public String doDeleteProduct(HttpSession session, Model model) {
        // 削除が完了（true）が返ってきたときに完了画面に遷移する
        boolean success = productService.deleteProduct((Long) session.getAttribute("deleteId"));
        if (success) {
            return ("redirect:/admin/product/delete/complete");
        }
        // エラーメッセージを保持させる
        return "redirect:/admin/product/delete/confirm";

    }

    // 完了画面を表示するメソッド
    @GetMapping("/delete/complete")
    public String ShowProductDeleteComplete(HttpSession session, Model model) {
        // 商品名に入る情報を取得
        model.addAttribute("deleteName", session.getAttribute("deleteName"));

        // 完了画面に遷移する
        return ("admin/product/delete_complete");
    }

    @PostMapping("/edit")
    public String editProduct(
            @ModelAttribute("form") ProductEditForm form,
            BindingResult bindingResult,
            @RequestParam(name = "category", required = false, defaultValue = "0") Long category,
            Model model) {

        productEditValidator.validate(form, bindingResult);

        if (bindingResult.hasErrors()) {
            List<ProductCategory> categories = productService.getAllCategories();
            List<Product> products = productService.searchProducts(category);
            model.addAttribute("categories", categories);
            model.addAttribute("products", products);
            model.addAttribute("selectedProductCategory", category);
            return "admin/product/edit_form";
        }

        // ⭕ ここから下に2行追加します！
        // 確認画面でもカテゴリの一覧情報が必要なので、データベースから取得してModelに詰めます
        List<ProductCategory> categories = productService.getAllCategories();
        model.addAttribute("categories", categories);

        Product product = new Product();
        product.setId(form.getId());
        product.setName(form.getName());
        product.setPrice(form.getPrice());
        product.setProductCategoryId(form.getProductCategoryId());
        product.setImageUrl(form.getImageUrl());
        product.setQuantity(form.getQuantity());

        product.setDeleteFlag(0);

        productService.editProduct(product);

        model.addAttribute("form", form);
        return "admin/product/edit_confirm";
    }

    @PostMapping("/edit/confirm")
    public String editConfirm(
            @ModelAttribute("form") ProductEditForm form,
            BindingResult bindingResult,
            @RequestParam(name = "action", required = false) String action,
            RedirectAttributes redirectAttributes,
            Model model) {
        try {

            // 💡確認画面（edit_confirm.html）側で「戻る」が押されて飛んできた場合の処理
            if ("back".equals(action)) {
                model.addAttribute("categories", productService.getAllCategories());
                model.addAttribute("form", form);
                // return "admin/product/edit_form"; // 入力画面（edit_form.html）をそのまま表示
                return "redirect:/admin/product/edit/" + form.getId();
            }

            // 💡確認画面側で「完了」が押されて飛んできた場合の処理（DB保存）
            else if ("complete".equals(action)) {

                try {

                    Product product = new Product();
                    product.setId(form.getId());
                    product.setName(form.getName());
                    product.setPrice(form.getPrice());
                    product.setDeleteFlag(0);
                    product.setProductCategoryId(form.getProductCategoryId());
                    product.setImageUrl(form.getImageUrl());
                    product.setQuantity(form.getQuantity());

                    productService.editProduct(product);

                    redirectAttributes.addFlashAttribute("productName", form.getName());
                    return "redirect:/admin/product/edit/complete";

                } catch (Exception ex) {
                    // ========================================================
                    // ★【ここを修正】完了ボタン時のDB切断を個別キャッチ！
                    // ========================================================
                    // フラッシュ属性に仕様書通りのメッセージを込めます
                    redirectAttributes.addFlashAttribute("errorMessage", "登録処理に失敗しました。管理者に連絡してください。");
                    redirectAttributes.addFlashAttribute("form", form);

                    // ➔ エラー画面ではなく、確認画面のURL（GET）にリダイレクトしてその場に留めます！
                    return "redirect:/admin/product/edit/confirm";
                }
            }

            // この時点で、単価に「q」が入っていると bindingResult にエラーが自動記録されています
            if (bindingResult.hasErrors()) {

                List<String> errorMessages = new ArrayList<>();

                // 💡 単価に文字エラーがあれば、仕様書通りのメッセージを追加
                if (bindingResult.hasFieldErrors("price")) {
                    errorMessages.add("正しい価格形式で入力してください");
                }
                // 💡 在庫数に文字エラーがあれば、仕様書通りのメッセージを追加
                if (bindingResult.hasFieldErrors("quantity")) {
                    errorMessages.add("正しい在庫数形式で入力してください");
                }

                // ★【ここが最重要！】HTMLが認識できる名前（errorMessages）でモデルに詰め込みます
                model.addAttribute("errorMessages", errorMessages);

                // 💡 画面を動かすために必要な「カテゴリ一覧」と「フォーム情報」も一緒に渡して戻します
                model.addAttribute("categories", productService.getAllCategories());
                model.addAttribute("form", form); // ← ★これがないと、入力した「p」が消えたり画面がバグる原因になります

                return "admin/product/edit_form";
            }

            productService.getAllCategories();
            // 💡通常の遷移（入力画面から最初にデータが送られてきたとき）
            // 入力内容（form）を、次のGETリクエストへ安全に引き渡します
            redirectAttributes.addFlashAttribute("form", form);

            // ★ここでブラウザに「URLを /edit/confirm に変えて開き直して！」と命令（リダイレクト）します
            return "redirect:/admin/product/edit/confirm";
        } catch (Exception e) {
            // ========================================================
            // 【catchブロック】入力画面から飛んできた後のDB切断をここで一括キャッチ！
            // ========================================================

            // 1. 仕様書通りのエラーメッセージ[MSG030]をセット
            model.addAttribute("errorMessage", "データの取得に失敗しました");

            // 2. エラー画面[BP000]へ遷移させる
            return "admin/error";
        }
    }

    @GetMapping("/edit/confirm")
    public String showConfirmPage(
            @ModelAttribute("form") ProductEditForm form, // ①からリダイレクトされたデータが自動で入ります
            jakarta.servlet.http.HttpServletRequest request,
            Model model) {

        java.util.Map<String, ?> flashMap = org.springframework.web.servlet.support.RequestContextUtils
                .getInputFlashMap(request);
        if (flashMap != null && flashMap.containsKey("errorMessage")) {
            // 1. 本命のエラーメッセージを画面に渡す
            model.addAttribute("errorMessage", flashMap.get("errorMessage"));

            // 2. ★【超簡単】ダミーデータを作らず、既存の「form」をそのまま使い回して画面に渡します！
            model.addAttribute("categories", form);

            return "admin/product/edit_confirm";
        }

        try {
            if (form.getId() == null) {
                return "redirect:/admin/product";
            }
            // ★ここでDBからカテゴリ一覧を取得する際、DBが切断されているとエラーになります！
            model.addAttribute("categories", productService.getAllCategories());
            return "admin/product/edit_confirm";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "データの取得に失敗しました");
            return "admin/error";
        }
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
    public String showEditForm(
            @PathVariable(value = "id") Long id, // 修正対象の商品ID
            @RequestParam(name = "category", required = false, defaultValue = "0") Long category,
            @RequestParam(name = "page", defaultValue = "1") int page,
            RedirectAttributes redirectAttributes,
            Model model) {

        try {
            // ★【追加・確認】「戻る」ボタンからリダイレクトされて、すでにフォーム（入力内容）が届いている場合
            if (model.containsAttribute("form")) {
                model.addAttribute("categories", productService.getAllCategories());
                return "admin/product/edit_form"; // そのまま入力画面を表示（URLからはすでにconfirmが消えています）
            }

            Product product = productService.findById(id);
            if (product == null || product.getDeleteFlag() == 1) {

                // 仕様書通りのエラーメッセージ[MSG036]をフラッシュ属性にセット
                redirectAttributes.addFlashAttribute("errorMessage", "指定された商品は存在しません");

                // ➔ 一覧画面[BP006]（/admin/product）へリダイレクトして戻す！
                return "redirect:/admin/product";

            }

            ProductEditForm form = new ProductEditForm();
            form.setId(product.getId());
            form.setName(product.getName());
            form.setPrice(product.getPrice());
            form.setQuantity(product.getQuantity());
            form.setProductCategoryId(product.getProductCategoryId());
            form.setImageUrl(product.getImageUrl());

            model.addAttribute("form", form);

            List<ProductCategory> categories = productService.getAllCategories();
            List<Product> products = productService.searchProducts(category);
            model.addAttribute("categories", categories);
            model.addAttribute("products", products);
            model.addAttribute("selectedProductCategory", category);

            return "admin/product/edit_form";

        } catch (Exception e) { // 👈 tryを閉じる波括弧 ②
            model.addAttribute("errorMessage", "データの取得に失敗しました");
            return "admin/error";
        }
    }

    // 💡 完了画面（edit_complete.html）を表示するための設定で
    @GetMapping("/edit/complete")
    public String showEditCompletePage(Model model) {
        // ★【安全装置】もし正規のルート（完了ボタン）から送られてくる「productName」が届いていなかったら
        if (!model.containsAttribute("productName")) {
            // ➔ トップ画面（メニュー画面：/admin）へ強制リダイレクトして追い返します！
            return "redirect:/admin";
        }

        // 正常にボタンを押してきた時だけ、完了画面を表示します
        // templates/admin/edit_complete.html を表示する
        return "admin/product/edit_complete";
    }

    public String showEditPage(@PathVariable Long id, Model model) {
        model.addAttribute("productId", id);
        return "admin/product-edit";
    }

}