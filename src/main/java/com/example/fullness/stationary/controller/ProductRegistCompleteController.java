package com.example.fullness.stationary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.example.fullness.stationary.controller.form.ProductRegistForm;

@Controller
@Transactional
@RequestMapping("/admin/product")
@SessionAttributes("productInputForm")
public class ProductRegistCompleteController {
    // 完了画面表示処理
    @GetMapping("/add/complete")
    public String complete(
            @ModelAttribute("productInputForm") ProductRegistForm form, Model model, SessionStatus sessionStatus) {
        if (form == null) {
            return "redirect:/admin/product/add";
        }
        model.addAttribute("productInputForm", form);
        sessionStatus.setComplete();
        return "admin/add_complete";
    }

    // 入力画面へ戻る処理
    @GetMapping("/add/back-to-input")
    public String backToInput(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return "redeirect:/admin/product/add";
    }

    // 商品検索画面へ戻る処理
    @GetMapping("/add/back-to-search")
    public String backToSearch(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return "redeirect:/admin/search";
    }

    // メニュー画面へ戻る処理
    @GetMapping("/add/back-to-menu")
    public String backToMenu(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return "redeirect:/admin";
    }

}
