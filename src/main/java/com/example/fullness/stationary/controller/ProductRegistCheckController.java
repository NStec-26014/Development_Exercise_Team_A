package com.example.fullness.stationary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.fullness.stationary.entity.Category;
import com.example.fullness.stationary.entity.Product;
import com.example.fullness.stationary.form.ProductRegistForm;
import com.example.fullness.stationary.mapper.CategoryMapper;
import com.example.fullness.stationary.service.CategoryService;
import com.example.fullness.stationary.service.Impl.ProductServiceImpl;

@Controller
@RequestMapping("/admin/product")
@SessionAttributes("productInputForm")
public class ProductRegistCheckController {

    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private CategoryService categoryService;

    // 確認画面表示処理
    @GetMapping("/add/confirm")
    public String confirm(
            @ModelAttribute("productInputForm") ProductRegistForm form, Model model,
            RedirectAttributes redirectAttributes) {

        if (form == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "不正なアクセスです");
            return "redirect:/admin/product/add";
        }

        Integer id = form.getProductCategoryId();

        if (id != null) {
            Category category = categoryService.findById(id.longValue());

            if (category != null) {
                form.setCategoryName(category.getName());
            }
        }

        model.addAttribute("productInputForm", form);
        return "admin/add_confirm";
    }

    // 戻るボタン押下時
    @PostMapping(value = "/add/confirm", params = "action=back")
    public String back(@ModelAttribute("productInputForm") ProductRegistForm form,
            RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("productInputForm", form);
        return "redirect:/admin/product/add";

    }

    // DB登録処理
    @PostMapping("/add/confirm")
    public String complete(
            @ModelAttribute("productInputForm") ProductRegistForm form,
            RedirectAttributes redirectAttributes) {

        Product product = new Product();
        product.setProductCategoryId((long) form.getProductCategoryId());
        product.setName(form.getName());
        product.setPrice(form.getPrice());
        product.setImageUrl(form.getImageUrl());
        product.setDeleteFlag(0);

        productService.saveProduct(product);

        redirectAttributes.addFlashAttribute("productInputForm", form);
        return "redirect:/admin/product/add/complete";
    }
}
