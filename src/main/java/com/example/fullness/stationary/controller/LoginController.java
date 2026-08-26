package com.example.fullness.stationary.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/admin/login")
    public String login(@RequestParam(value = "error", required = false) String error,
            HttpSession session,
            Model model) {

        if (error != null) {
            String errorMessage = (String) session.getAttribute("LOGIN_ERROR_MESSAGE");

            if (errorMessage != null) {
                model.addAttribute("errorMessage", errorMessage);
                session.removeAttribute("LOGIN_ERROR_MESSAGE");
            }
        }

        String accountName = (String) session.getAttribute("LOGIN_ACCOUNT_NAME");
        if (accountName != null) {
            model.addAttribute("accountName", accountName);
            session.removeAttribute("LOGIN_ACCOUNT_NAME");
        }
        return "admin/login";
    }

    @GetMapping("/admin/error")
    public String showError(Model model) {
        return "admin/error";
    }
}
