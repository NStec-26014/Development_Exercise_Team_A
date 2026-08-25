package com.example.fullness.stationary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.fullness.stationary.service.ProductService;

//商品検索画面遷移の処理
@Controller
@RequestMapping("/admin/product")
public class ProductSearchController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String showMaintenancePage(Model model) {
        model.addAttribute("products", productService.searchProducts(null));

        return "admin/search";
    }
}
