package com.example.fullness.stationary.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.fullness.stationary.entity.Product;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.jdbc.Sql;

import com.example.fullness.stationary.entity.Product;

@MybatisTest

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductMapperTest {

    @Autowired
    private ProductMapper productMapper;

    @Test
    public void Case1_findAllWithPaging_OK() {

        List<Product> result = productMapper.findAllWithPaging(0, 10);

        assertEquals(10, result.size());
    }

    @Test
    public void Case2_findByCategoryIdWithPaging_OK() {

        List<Product> result = productMapper.findByCategoryIdWithPaging(1L, 0, 10);

        assertEquals(10, result.size());
        for (Product product : result) {
            assertEquals(1, product.getProductCategoryId());
        }
    }

    @Test
    public void Case3_countAll_OK() {

        int result = productMapper.countAll();

        assertEquals(32, result);
    }

    @Test
    public void Case4_countByCategoryId_OK() {

        int result = productMapper.countByCategoryId(1L);

        assertEquals(30, result);
    }

    @Test
    public void Case5_findById_OK() {

        Product result = productMapper.findById(1001L);

        assertNotNull(result);
        assertEquals(1001, result.getId());
        assertEquals("水性ボールペン(黒)", result.getName());
    }
}
    // @Sql(statements = {
    // "DELETE FROM product;",
    // "DELETE FROM product_category;",
    // "INSERT INTO product_category (id, name) VALUES (1, '文房具');",
    // "INSERT INTO product (id, product_category_id, name, price, image_url,
    // delete_flag) VALUES (300, 1, '消しゴム', 120, '/images/eraser.png', 0);"
    // })
    void testEditProduct() {
        // 💡 1. 確実に存在する既存の商品データ（例: ID 1005、水性ボールペン(黄)など）を用意します
        Product target = new Product();
        target.setId(1005L);
        target.setProductCategoryId(1L);
        target.setName("修正版消しゴム"); // テスト用に名前を変える
        target.setPrice(150);

        // 💡 2. 編集（更新）処理を実行します
        productMapper.edit(target);

        // 💡 3. 本当に更新されたか、データベースからもう一度引っ張ってきて確かめます
        Product actual = productMapper.findById(1005L);

        // 🔍 4. 検証：名前と価格が、指定した通りに書き換わっているかチェックします
        assertNotNull(actual);
        assertEquals("修正版消しゴム", actual.getName());
        assertEquals(150, actual.getPrice());
    }



    @Test
    void testInsert_OK1() {
        // 1. テストデータを用意
        Product product = new Product();
        product.setProductCategoryId(1L);
        product.setName("テストボールペン");
        product.setPrice(500);
        product.setImageUrl(null);
        product.setDeleteFlag(0);

        // 2. 登録を実行
        productMapper.insert(product);

        // 3. 全件リストの中から、名前が「テストボールペン」のデータを探す
        Product actual = null;
        for (Product p : productMapper.findAll()) {
            if ("テストボールペン".equals(p.getName())) {
                actual = p;
                break;
            }
        }

        // 🔍 4. 中身を1つずつ細かく検証する
        assertNotNull(actual, "登録したはずの『テストボールペン』がデータベースに見つかりませんでした");
        assertEquals("テストボールペン", actual.getName());
        assertEquals(500, actual.getPrice());
        assertEquals(1L, actual.getProductCategoryId());
        assertNull(actual.getImageUrl());
        assertEquals(0, actual.getDeleteFlag());
    }

}
