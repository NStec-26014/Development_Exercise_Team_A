package com.example.fullness.stationary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.example.fullness.stationary.controller.form.CategoryRegistForm;

@Controller
@Transactional
@RequestMapping("/admin/category")
@SessionAttributes("categoryInputForm")
public class CategoryRegistCompleteController {

    // 完了画面表示処理
    @GetMapping("/add/complete")
    public String complete(
            @ModelAttribute("categoryInputForm") CategoryRegistForm form, Model model, SessionStatus sessionStatus) {

        if (form == null || form.getCategoryName() == null || form.getCategoryName().isEmpty()) {

            CategoryRegistForm dummyForm = new CategoryRegistForm();
            dummyForm.setCategoryName("---");
            model.addAttribute("categoryInputForm", dummyForm);
        } else {

            model.addAttribute("categoryInputForm", form);
        } // リロード等でのデータ欠陥を防止

        return "admin/category/complete";
    }

    // メニュー画面へ戻る処理
    @GetMapping("/add/back-to-menu")
    public String backToMenu(SessionStatus sessionStatus) {
        sessionStatus.setComplete(); // セッションデータを完全にクリア
        return "redirect:/admin";
    }

}
