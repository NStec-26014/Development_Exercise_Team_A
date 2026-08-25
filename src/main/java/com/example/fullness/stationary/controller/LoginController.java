package com.example.fullness.stationary.controller;

import com.example.fullness.stationary.entity.Employee;
import com.example.fullness.stationary.entity.EmployeeAccount;
import com.example.fullness.stationary.mapper.EmployeeAccountMapper;
import com.example.fullness.stationary.mapper.EmployeeMapper;

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
        return "admin/login";
    }

 
    @GetMapping("/admin/error")
    public String showError(Model model){
        model.addAttribute("errorMessage", "システムエラーが発生しました。管理者に連絡してください");
        return "admin/error";
    }
}

