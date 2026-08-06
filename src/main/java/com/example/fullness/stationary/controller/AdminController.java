package com.example.fullness.stationary.controller;


import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

/**

管理画面メニューコントローラー

BP001 管理画面メニュー
*/
    @Controller
    public class AdminController {
/*

管理画面メニュー表示

URL: /admin

@param session HTTPセッション

@return メニュー画面
*/
        @GetMapping("/admin")
        public String showAdminMenu(HttpSession session) {
            // ログインチェックはLoginInterceptorで実施済み
            return "admin/menu";
        }
    }