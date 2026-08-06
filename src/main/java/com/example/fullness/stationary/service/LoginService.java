package com.example.fullness.stationary.service;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.example.fullness.stationary.entity.EmployeeAccount;


import com.example.fullness.stationary.mapper.EmployeeAccountMapper;


/**

ログインサービス

BP002 担当者ログイン画面の処理を実装

文字数バリデーションなし版
*/
@Service
public class LoginService {

@Autowired
private EmployeeAccountMapper employeeAccountMapper;
/*

認証処理

@param username アカウント名

@param password パスワード

@return 認証成功した場合の社員アカウント情報

@throws LoginException 認証失敗時*/
public EmployeeAccount authenticate(String username, String password) {
        // 1. 入力値バリデーション（必須入力チェックのみ）
        if (username == null || username.trim().isEmpty()) {
            throw new LoginException("アカウント名を入力してください");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new LoginException("パスワードを入力してください");
        }
        // 2. データベースからアカウント情報を取得
        EmployeeAccount account = null;
        try {
            account = employeeAccountMapper.findByName(username.trim());
        } catch (Exception e) {
            // DBエラーの場合
            throw new LoginException("システムエラーが発生しました。管理者に連絡してください", e);
        }
        // 3. アカウントの存在チェック
        if (account == null) {
            throw new LoginException("アカウント名またはパスワードが正しくありません");
        }
        // 4. パスワードの照合
        boolean isPasswordMatch = PasswordUtil.verifyPassword(password, account.getPassword());
        if (!isPasswordMatch) {
            throw new LoginException("アカウント名またはパスワードが正しくありません");
        }
        // 5. 認証成功
        return account;
    }
}