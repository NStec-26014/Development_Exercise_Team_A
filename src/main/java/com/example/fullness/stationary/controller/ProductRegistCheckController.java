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
import com.example.fullness.stationary.controller.form.ProductRegistForm;
import com.example.fullness.stationary.entity.Product;
import com.example.fullness.stationary.service.ProductCategoryService;
import com.example.fullness.stationary.service.ProductService;

@Controller
@RequestMapping("/admin/product")
@SessionAttributes("productInputForm")
public class ProductRegistCheckController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;

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
            ProductCategory category = productCategoryService.findById(id.longValue());

            if (category != null) {
                form.setProductCategoryName(category.getName());
            }
        }

        model.addAttribute("productInputForm", form);
        return "admin/product/add_confirm";
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