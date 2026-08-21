package com.example.fullness.stationary.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Autowired;
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

import com.example.fullness.stationary.entity.Category;
import com.example.fullness.stationary.entity.Product;
import com.example.fullness.stationary.form.ProductEditForm;
import com.example.fullness.stationary.service.ProductService;
import com.example.fullness.stationary.validator.ProductEditValidator;

@Controller
@RequestMapping("/admin/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductEditValidator productEditValidator;

    @ModelAttribute("productEditForm")
    public ProductEditForm productEditForm() {
        return new ProductEditForm();
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/admin/product";
    }

    @GetMapping("")
    public String showProductList(
            @RequestParam(name = "category", required = false, defaultValue = "0") Long category,

            Model model) {
        // 1. カテゴリ一覧取得
        List<Category> categories = productService.getAllCategories();

        // 2. 商品検索
        List<Product> products = productService.searchProducts(category);

        // 3. Modelにデータを詰める
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        model.addAttribute("selectedCategory", category);

        // 4. テンプレート名を返す
        return "admin/search"; // → templates/admin/search.html
    }

    @PostMapping("/delete")
    public String deleteProduct(
            @RequestParam Long id, // 削除対象の商品ID
            @RequestParam(required = false, defaultValue = "0") Long category) {

        // 消すとき
        productService.deleteProduct(id);

        // リダイレクト（削除後、同じカテゴリの一覧に戻る）
        return "redirect:/admin/product?category=" + category;
    }

    @PostMapping("/edit")
    public String editProduct(
            @ModelAttribute("form") ProductEditForm form,
            BindingResult bindingResult,
            @RequestParam(name = "category", required = false, defaultValue = "0") Long category,
            Model model) {

        productEditValidator.validate(form, bindingResult);

        if (bindingResult.hasErrors()) {
            List<Category> categories = productService.getAllCategories();
            List<Product> products = productService.searchProducts(category);
            model.addAttribute("categories", categories);
            model.addAttribute("products", products);
            model.addAttribute("selectedCategory", category);
            return "admin/product/edit_form";
        }

        // ⭕ ここから下に2行追加します！
        // 確認画面でもカテゴリの一覧情報が必要なので、データベースから取得してModelに詰めます
        List<Category> categories = productService.getAllCategories();
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

    @GetMapping("/edit/{id}")
    public String showProductList(
            @PathVariable("id") Long id, // 修正対象の商品ID
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

        List<Category> categories = productService.getAllCategories();
        List<Product> products = productService.searchProducts(category);
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        model.addAttribute("selectedCategory", category);

        return "admin/product/edit_form";
    }

    
        // 💡 完了画面（edit_complete.html）を表示するための設定で
    @GetMapping("/edit/complete")
    public String showEditCompletePage() {
        // templates/admin/edit_complete.html を表示する
        return "admin/product/edit_complete"; 
    }

    

}
