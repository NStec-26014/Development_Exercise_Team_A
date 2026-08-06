package com.example.fullness.stationary.controller;


import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;

import com.example.fullness.stationary.entity.EmployeeAccount;
import com.example.fullness.stationary.service.LoginService;

import jakarta.servlet.http.HttpSession;

/**

ログインコントローラー

BP002 担当者ログイン画面

*/
@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;
/*

ログイン画面表示

URL: /admin/login

@return ログイン画面
*/
    @GetMapping
    ("/admin/login")
    public String showLoginPage(HttpSession session, Model model) 
    {
        // セッションとエラーメッセージをクリア（ページロード時の初期化）
        session.removeAttribute("loginUser");
        session.removeAttribute("employeeId");
        model.addAttribute("errorMessage", "");
        return "admin/login";
    }

    /**

    ログイン処理

    ログインボタン押下時の処理

    @param username アカウント名（入力項目）

    @param password パスワード（入力項目）

    @param session HTTPセッション

    @param model モデル

    @return 成功時: /admin にリダイレクト、失敗時: ログイン画面

    */

    @PostMapping("/admin/login")
    public String login(@RequestParam(name = "username", required = false) String username,
    @RequestParam(name = "password", required = false) String password,HttpSession session,Model model){
        

        try {
        // 入力値のバリデーションと認証
        EmployeeAccount account = loginService.authenticate(username, password);
        // セッションに担当者情報を保存
        session.setAttribute("loginUser", account.getName());
        session.setAttribute("employeeId", account.getEmployeeId());

        // メニュー画面へリダイレクト（BP001へ遷移）
        return "redirect:/admin";

        }catch (LoginException e){
            // 認証失敗時: エラーメッセージを表示してログイン画面に戻る
            model.addAttribute("errorMessage", e.getMessage());

            model.addAttribute("username", username); // 入力値を保持

            return "admin/login";
        }

    }

    /**

    ログアウト処理

    @param session HTTPセッション

    @return ログイン画面へリダイレクト*/
    @GetMapping("/admin/logout")
    public String logout(HttpSession session) {
        // セッション破棄
        session.invalidate();
        return "redirect:/admin/login";
    }
}






