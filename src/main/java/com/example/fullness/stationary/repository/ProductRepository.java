package com.example.fullness.stationary.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.fullness.stationary.entity.Product;

@Repository
public class ProductRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // ResultSetをProductに変換
    private static final RowMapper<Product> ROW_MAPPER = new RowMapper<Product>() {
        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            Product product = new Product();
            product.setId(rs.getLong("id"));
            product.setProductCategoryId(rs.getLong("product_category_id"));
            product.setName(rs.getString("name"));
            product.setPrice(rs.getInt("price"));
            product.setImageUrl(rs.getString("image_url"));
            product.setDeleteFlag(rs.getInt("delete_flag"));
            return product;
        }
    };

    // 全商品取得（削除済み除く）
    public List<Product> findAll() {
        String sql = "SELECT id, product_category_id, name, price, image_url, delete_flag " +
                "FROM product WHERE delete_flag = 0 ORDER BY id";
        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    // カテゴリID指定で商品取得
    public List<Product> findByCategoryId(Long categoryId) {
        String sql = "SELECT id, product_category_id, name, price, image_url, delete_flag " +
                "FROM product WHERE product_category_id = ? AND delete_flag = 0 ORDER BY id";
        return jdbcTemplate.query(sql, ROW_MAPPER, categoryId);
        // WHERE句で絞り込み、?にcategoryIdが入る
    }

    // ID指定で商品取得
    public Product findById(Long id) {
        String sql = "SELECT id, product_category_id, name, price, image_url, delete_flag " +
                "FROM product WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, ROW_MAPPER, id);
    }

    // 商品登録
    public int save(Product product) {
        String sql = "INSERT INTO product (product_category_id, name, price, image_url, delete_flag) " +
                "VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                product.getProductCategoryId(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl(),
                0); // delete_flagは必ず0（有効）
    }

    public int deleteById(Long id) {
        String sql = "UPDATE product SET delete_flag = 1 WHERE id = ?";
        return jdbcTemplate.update(sql, id);

    }
}
