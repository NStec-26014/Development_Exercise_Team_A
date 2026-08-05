package com.example.fullness.stationary.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.fullness.stationary.entity.Category;
import com.example.fullness.stationary.entity.Product;
import com.example.fullness.stationary.repository.CategoryRepository;
import com.example.fullness.stationary.repository.ProductRepository;

@Component // Springが管理するBean（起動時に自動実行）
public class DataLoader implements CommandLineRunner {
    // CommandLineRunner → アプリ起動後に実行されるインターフェース

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
        // アプリ起動時に1回だけ実行される

        // カテゴリ登録
        categoryRepository.save(new Category("ペン"));
        categoryRepository.save(new Category("ノート"));
        categoryRepository.save(new Category("その他"));

        // 商品登録
        productRepository.save(new Product(1L, "水性ボールペン(赤)", 120, "/images/pen_red.png"));
        productRepository.save(new Product(1L, "水性ボールペン(青)", 120, "/images/pen_blue.png"));
        // ... 続く

        System.out.println("==== 初期データ投入完了 ====");
    }
}
