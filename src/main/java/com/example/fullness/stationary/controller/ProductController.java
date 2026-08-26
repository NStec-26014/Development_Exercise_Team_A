package com.example.fullness.stationary.controller;

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
    @GetMapping("/")
    public String showProductList(
            @RequestParam(name = "category", required = false, defaultValue = "0") Long category,
            @RequestParam(name = "page", defaultValue = "1") int page,
            Model model) {

        int pageSize = 10;
        // 1. カテゴリ一覧取得
        List<ProductCategory> categories = productCategoryService.getAllCategories();

        // 2. 商品検索
        List<Product> products = productService.getProductsByProductCategoryWithPaging(category, page, pageSize);

        int totalCount = productService.countProductsByProductCategory(category);
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);
        // 3. Modelにデータを詰める
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("selectedProductCategory", category);
        model.addAttribute("totalPages", totalPages);
        return "admin/product"; // → templates/admin/product.html
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

    // 💡 入力画面の form で指定したURL「/admin/product/edit/confirm」をここで待ち受けます
    @PostMapping("/edit/confirm")
    public String editConfirm(
            @ModelAttribute("form") ProductEditForm form,
            @RequestParam(name = "action", required = false) String action, // 💡HTMLの name="action" をここで受け取ります
            RedirectAttributes redirectAttributes,
            Model model) {
        // 1. 💡 もし「戻る」ボタン（value="back"）が押されていた場合
        if ("back".equals(action)) {
            model.addAttribute("categories", productService.getAllCategories());
            model.addAttribute("form", form); // 入力内容をそのままキープ
            return "admin/product/edit_form"; // ➔ 入力画面（edit_form）へ戻します！
        }

        // 2. 💡 もし「完了」ボタン（value="complete"）が押されていた場合（保存処理）
        else if ("complete".equals(action)) {
            Product product = new Product();
            product.setId(form.getId());
            product.setName(form.getName());
            product.setPrice(form.getPrice());
            product.setDeleteFlag(0);
            product.setProductCategoryId(form.getProductCategoryId());
            product.setImageUrl(form.getImageUrl());
            product.setQuantity(form.getQuantity());

            // データベースを更新します
            productService.editProduct(product);

            // 💡 完了画面の ${productName} に修正後の商品名を届けます！
            redirectAttributes.addFlashAttribute("productName", form.getName());

            // 保存が終わったら、完了画面へ自動で戻します（リダイレクト）
            return "redirect:/admin/product/edit/complete";

        }
        return "redirect:/admin/product";
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
            Model model) {

        Product product = productService.findById(id);
        if (product == null) {
            return "redirect:/admin/product?category=" + category;
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
    }

    // 💡 完了画面（edit_complete.html）を表示するための設定で
    @GetMapping("/edit/complete")
    public String showEditCompletePage() {
        // templates/admin/edit_complete.html を表示する
        return "admin/product/edit_complete";
    }

    public String showEditPage(@PathVariable Long id, Model model) {
        model.addAttribute("productId", id);
        return "admin/product-edit";
    }

}