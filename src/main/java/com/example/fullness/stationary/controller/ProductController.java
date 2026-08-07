package com.example.fullness.stationary.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.fullness.stationary.entity.Category;
import com.example.fullness.stationary.entity.Product;
import com.example.fullness.stationary.service.ProductService;

@Controller
@RequestMapping("/admin/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String showProductList(
            @RequestParam(name = "category", required = false, defaultValue = "0") Long category,
            @RequestParam(name = "page", defaultValue = "1") int page,
            Model model) {

        int pageSize = 10;
        // 1. カテゴリ一覧取得
        List<Category> categories = productService.getAllCategories();

        // 2. 商品検索
        List<Product> products = productService.geProductsByCategoryWithPaging(category, page, pageSize);

        int totalCount = productService.countProductsByCategory(category);
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);
        // 3. Modelにデータを詰める
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("totalPages", totalPages);

        // 4. テンプレート名を返す
        return "admin/product"; // → templates/admin/product.html
    }

    @GetMapping("/delete/{id}")
    public String showDeletePage(@PathVariable Long id, Model model) {
        model.addAttribute("productId", id);
        return "admin/product-delete";
    }

    @GetMapping("/edit/{id}")
    public String showEditPage(@PathVariable Long id, Model model) {
        model.addAttribute("productId", id);
        return "admin/product-edit";
    }

}
