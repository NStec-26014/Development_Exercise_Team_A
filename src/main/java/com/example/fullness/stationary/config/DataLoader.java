// package com.example.fullness.stationary.config;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.stereotype.Component;

// import com.example.fullness.stationary.entity.Category;
// import com.example.fullness.stationary.entity.Product;
// import com.example.fullness.stationary.service.ProductService;

// @Component // Springが管理するBean（起動時に自動実行）
// public class DataLoader implements CommandLineRunner {
//     // CommandLineRunner → アプリ起動後に実行されるインターフェース

//     @Autowired
//     private ProductService productService;

//     @Override
//     public void run(String... args) throws Exception {
//         // アプリ起動時に1回だけ実行される

//         // カテゴリ登録
//         productService.saveCategory(new Category("ペン"));
//         productService.saveCategory(new Category("ノート"));
//         productService.saveCategory(new Category("その他"));

//         // 商品登録
//         productService.saveProduct(new Product(1L, "水性ボールペン(赤)", 120, "/images/pen_red.png"));
//         productService.saveProduct(new Product(1L, "水性ボールペン(青)", 120, "/images/pen_blue.png"));
//         // ... 続く

//         System.out.println("==== 初期データ投入完了 ====");
//     }
// }
