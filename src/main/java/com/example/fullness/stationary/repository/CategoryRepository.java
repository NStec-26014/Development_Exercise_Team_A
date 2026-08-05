package com.example.fullness.stationary.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

//import javax.swing.tree.RowMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.fullness.stationary.entity.Category;

@Repository
public class CategoryRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final RowMapper<Category> ROW_MAPPER = new RowMapper<Category>() {
        @Override
        public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
            Category category = new Category();
            category.setId(rs.getLong("id")); // SQLのid列を取得
            category.setName(rs.getString("name")); // SQLのname列を取得
            return category;
        }
    };

    // 全カテゴリ取得
    public List<Category> findAll() {
        String sql = "SELECT id, name FROM product_category ORDER BY id";
        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    // ID指定でカテゴリ取得
    public Category findById(Long id) {
        String sql = "SELECT id, name FROM product_category WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, ROW_MAPPER, id);

    }

    // カテゴリ登録
    public int save(Category category) {
        String sql = "INSERT INTO product_category (name) VALUES (?)";
        return jdbcTemplate.update(sql, category.getName());
    }
}