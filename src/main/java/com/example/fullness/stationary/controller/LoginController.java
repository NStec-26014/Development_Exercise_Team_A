package com.example.fullness.stationary.controller;

import com.example.fullness.stationary.entity.Employee;
import com.example.fullness.stationary.entity.EmployeeAccount;
import com.example.fullness.stationary.mapper.EmployeeAccountMapper;
import com.example.fullness.stationary.mapper.EmployeeMapper;

import jakarta.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    private final EmployeeAccountMapper employeeAccountMapper;
    private final EmployeeMapper employeeMapper;

    public LoginController(EmployeeAccountMapper employeeAccountMapper, EmployeeMapper employeeMapper) {
        this.employeeAccountMapper = employeeAccountMapper;
        this.employeeMapper = employeeMapper;
    }
    
    @GetMapping("/admin")
    public String admin(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = auth != null
                && auth.isAuthenticated()
                && !"anonymousUser".equals(auth.getName());

        String Name = "";
        if (isLoggedIn) {
            EmployeeAccount account = employeeAccountMapper.selectByName(auth.getName());
            if (account != null) {
                Integer employeeId = account.getEmployeeId() ;
                Employee employee = employeeMapper.selectById(employeeId);
                Name = employee.getName();
            } 
        }
        model.addAttribute("loggedIn", isLoggedIn);
        model.addAttribute("loginEmployeeName", Name);
        return "admin/menu";
    }
       @GetMapping("/admin/login")
    public String login(@RequestParam(value = "error", required = false) String error, 
                        HttpSession session, 
                        Model model) {
                        
        if (error != null) {
            String errorMessage = (String) session.getAttribute("LOGIN_ERROR_MESSAGE");
            
            if (errorMessage != null) {
                model.addAttribute("errorMessage", errorMessage);
                session.removeAttribute("LOGIN_ERROR_MESSAGE");
            } else {
                model.addAttribute("errorMessage", "ログインに失敗しました。");
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

