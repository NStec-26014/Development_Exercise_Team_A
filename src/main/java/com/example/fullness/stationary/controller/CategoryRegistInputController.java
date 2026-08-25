package com.example.fullness.stationary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.fullness.stationary.form.CategoryRegistForm;
import com.example.fullness.stationary.service.ProductCategoryService;

@Controller
@RequestMapping("/admin/category")
@SessionAttributes("categoryInputForm")
public class CategoryRegistInputController {

    @Autowired
    private ProductCategoryService categoryService;

    @ModelAttribute("categoryInputForm")
    public CategoryRegistForm categoryRegistForm() {
        return new CategoryRegistForm();
    }

    // 入力画面表示処理
    @GetMapping("/add")
    public String showRegisterForm(Model model) {
        return "admin/category/form";
    }

    // 完了ボタン押下時
    @PostMapping("/add")
    public String submitAddForm(
            @Validated @ModelAttribute("categoryInputForm") CategoryRegistForm form,
            BindingResult result, RedirectAttributes redirectAttributes) {

        // バリデーションチェック
        if (result.hasErrors()) {
            return "admin/category/form";
        }

        // 例外：カテゴリ名重複
        if (categoryService.isDuplicate(form.getCategoryName())) {
            result.rejectValue("categoryName", "error.duplicate",
                    "入力されたカテゴリ名は既に登録されています");
            return "admin/category/form";
        }

        // エラーがない場合、確認画面へ遷移
        redirectAttributes.addFlashAttribute("categoryInputForm", form);
        return "redirect:/admin/category/add/confirm";
    }

    // キャンセルリンク押下時（メニューへ戻る）
    @GetMapping("/category")
    public String cancel(SessionStatus sessionStatus) {

        sessionStatus.setComplete();
        return "redirect:/admin";
    }

}
