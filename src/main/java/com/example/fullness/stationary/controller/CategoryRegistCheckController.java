package com.example.fullness.stationary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.fullness.stationary.entity.ProductCategory;
import com.example.fullness.stationary.form.CategoryRegistForm;
import com.example.fullness.stationary.service.ProductCategoryService;

@Controller
@RequestMapping("/admin/category")
@SessionAttributes("categoryInputForm")
public class CategoryRegistCheckController {

    @Autowired
    private ProductCategoryService categoryService;

    // 確認画面表示処理
    @GetMapping("/add/confirm")
    public String showConfirmForm(
            @ModelAttribute("categoryInputForm") CategoryRegistForm form, Model model,
            RedirectAttributes redirectAttributes) {
     
      try{
        if (form == null || form.getCategoryName() == null || form.getCategoryName().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "入力情報が見つかりません。再度入力してください。");
            return "redirect:/admin/category/add";
        }
        model.addAttribute("categoryInputForm", form);
        return "admin/category/confirm"; // セッションデータ不足の場合は入力画面へ、正常な場合は確認画面へ遷移
    
        }catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "登録処理に失敗しました。管理者に連絡してください。");
            return "redirect:/admin/error";
        }
    }

    // 戻るボタン押下時(確認画面から入力画面へ戻る)
    @PostMapping(value = "/add/confirm", params = "action=back")

    public String back(@ModelAttribute("categoryInputForm") CategoryRegistForm form,
            RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("categoryInputForm", form);
        return "redirect:/admin/category/add";

    }

    // DB登録処理
    @PostMapping("/add/confirm")
    public String complete(
            @ModelAttribute("categoryInputForm") CategoryRegistForm form,
            RedirectAttributes redirectAttributes, Model model) {
        try {
            ProductCategory category = new ProductCategory(form.getCategoryName());
            categoryService.saveProductCategory(category);

            redirectAttributes.addFlashAttribute("categoryInputForm", form);
            return "redirect:/admin/category/add/complete";
        } catch (Exception e) {

            model.addAttribute("errorMessage", "登録に失敗しました");

            return "admin/category/confirm"; // 登録成功時は完了画面へリダイレクトし、例外発生時は確認画面へ戻る
        }

    }
}
