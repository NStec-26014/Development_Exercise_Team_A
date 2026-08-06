package com.example.fullness.stationary.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.fullness.stationary.entity.EmployeeAccount;

/**
 * 社員アカウントマッパー
 */
@Mapper
public interface EmployeeAccountMapper {

    /**
     * アカウント名で社員アカウント情報を取得
     *
     * @param name アカウント名
     * @return 社員アカウント情報（存在しない場合null）
     */
    EmployeeAccount findByName(@Param("name") String name);

    /**
     * ログイン試行回数を更新（将来の拡張用）
     *
     * @param name アカウント名
     */
    void updateLoginAttempts(@Param("name") String name);

    /**
     * アカウントをロック（将来の拡張用）
     *
     * @param name アカウント名
     */
    void lockAccount(@Param("name") String name);
}

