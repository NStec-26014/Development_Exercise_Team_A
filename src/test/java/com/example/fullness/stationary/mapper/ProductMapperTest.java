package com.example.fullness.stationary.mapper;

import static org.assertj.core.api.Assertions.assertThat;



import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.jdbc.Sql;

import com.example.fullness.stationary.entity.Product;

@MybatisTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class ProductMapperTest {

    @Autowired
    private ProductMapper productMapper;


    // 私が書く
    @Test
    @Sql(statements = {
            "DELETE FROM product;",
            "DELETE FROM product_category;",
            "INSERT INTO product_category (id, name) VALUES (1, '文房具');",
            "INSERT INTO product (id, product_category_id, name, price, image_url, delete_flag) VALUES (300, 1, '消しゴム', 120, '/images/eraser.png', 0);"
    })
    void edit_shouldUpdateProductInfo() {
        Product target = productMapper.findById(300L);
        target.setName("修正版消しゴム");
        target.setPrice(150);

        int updated = productMapper.edit(target);
        Product result = productMapper.findById(300L);

        assertThat(updated).isEqualTo(1);
        assertThat(result.getName()).isEqualTo("修正版消しゴム");
        assertThat(result.getPrice()).isEqualTo(150);
    }
}
