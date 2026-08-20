// package com.example.fullness.stationary.controller;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;

// import com.example.fullness.stationary.entity.Category;
// import com.example.fullness.stationary.entity.Product;
// import com.example.fullness.stationary.service.ProductService;

// @Controller
// @RequestMapping("/admin/product")
// public class ProductController {

// @Autowired
// private ProductService productService;

// @GetMapping
// public String showProductList(
// @RequestParam(required = false, defaultValue = "0") Long category,

// Model model) {
// // 1. カテゴリ一覧取得
// List<Category> categories = productService.getAllCategories();

// // 2. 商品検索
// List<Product> products = productService.searchProducts(category);

// // 3. Modelにデータを詰める
// model.addAttribute("categories", categories);
// model.addAttribute("products", products);
// model.addAttribute("selectedCategory", category);

// // 4. テンプレート名を返す
// return "admin/product"; // → templates/admin/product.html
// }

// @PostMapping("/delete")
// public String deleteProduct(
// @RequestParam Long id, // 削除対象の商品ID
// @RequestParam(required = false, defaultValue = "0") Long category) {

// 消すとき
// productService.deleteProduct(id);

// // リダイレクト（削除後、同じカテゴリの一覧に戻る）
// return "redirect:/admin/product?category=" + category;
// }
// }
