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

import com.example.fullness.stationary.entity.Category;
import com.example.fullness.stationary.form.CategoryRegistForm;
import com.example.fullness.stationary.service.CategoryService;

@Controller
@RequestMapping("/admin/category")
@SessionAttributes("categoryInputForm")
public class CategoryRegistCheckController {

    @Autowired
    private CategoryService categoryService;

    // 確認画面表示処理
    @GetMapping("/add/confirm")
    public String showConfirmForm(
            @ModelAttribute("categoryInputForm") CategoryRegistForm form, Model model,
            RedirectAttributes redirectAttributes) {

        // if (form == null || form.getCategoryName() == null ||
        // form.getCategoryName().isEmpty()) {
        // return "redirect:/admin/product/add";
        // }
        model.addAttribute("categoryInputForm", form);
        return "admin/category/confirm";
    }

    // 戻るボタン押下時
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
            RedirectAttributes redirectAttributes) {
        Category category = new Category(form.getCategoryName());
        categoryService.saveCategory(category);

        redirectAttributes.addFlashAttribute("categoryInputForm", form);
        return "redirect:/admin/category/add/complete";
    }
}
