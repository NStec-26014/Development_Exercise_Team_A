package com.example.fullness.stationary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.fullness.stationary.service.impl.ProductServiceImpl;
import com.example.fullness.stationary.service.impl.ProductCategoryServiceImpl;
import com.example.fullness.stationary.controller.form.ProductRegistForm;
import com.example.fullness.stationary.service.ProductCategoryService;
import com.example.fullness.stationary.service.ProductService;
import com.example.fullness.stationary.validator.ProductRegistValidator;

@Controller
@RequestMapping("/admin/product")
@SessionAttributes("productInputForm") // productInputFormというモデルを属性をセッションに保存する
public class ProductRegistInputController {

    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private ProductRegistValidator productResistValidator; // 依存注入
    @Autowired
    private ProductCategoryServiceImpl productCategoryServiceImpl;

    @ModelAttribute("productInputForm")
    public ProductRegistForm productResistForm() {
        return new ProductRegistForm(); // productInputForm をモデルに追加するためのメソッド
    }

    @InitBinder("productInputForm")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(productResistValidator);
    } // 自動でバリデーションを有効にする

    // 入力画面を表示するための実装
    @GetMapping("/add")
    public String showRegisterForm(Model model) {

        if (!model.containsAttribute("productInputForm")) {
            model.addAttribute("productInputForm", new ProductRegistForm());
        }

        try {
            model.addAttribute("categories", productCategoryServiceImpl.getAllCategories());
            // サービスクラスのカテゴリ一覧を取得
        } catch (Exception e) {

            model.addAttribute("errorMessage", "カテゴリ情報の取得に失敗しました");
        } // 商品カテゴリデータ取得エラー
        return "admin/add_form";
    }

    // 完了ボタン押下時の処理
    @PostMapping("/add")
    public String submitAddForm(
            @ModelAttribute("productInputForm") ProductRegistForm form,
            BindingResult result,
            Model model, RedirectAttributes redirectAttributes) {

        productResistValidator.validate(form, result); // 入力チェックを実行

        if (result.hasErrors()) {
            model.addAttribute("categories", productCategoryServiceImpl.getAllCategories());

            return "admin/add_form"; // エラーなら入力画面に戻す
        }

        redirectAttributes.addFlashAttribute("productInputForm", form);
        return "redirect:/admin/product/add/confirm"; // エラーがなければ確認画面へ遷移
    }

    // キャンセルリンク押下時の処理
    @GetMapping("/product")
    public String cancel() {

        return "redirect:/admin/product";
    }
}
