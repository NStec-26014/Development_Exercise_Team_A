package com.example.fullness.stationary.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;

import com.example.fullness.stationary.entity.Employee;
import com.example.fullness.stationary.entity.EmployeeAccount;
import com.example.fullness.stationary.mapper.EmployeeAccountMapper;
import com.example.fullness.stationary.mapper.EmployeeMapper;


@Controller
public class AdminMenuController {
    private final EmployeeAccountMapper employeeAccountMapper;
    private final EmployeeMapper employeeMapper;

    public AdminMenuController(EmployeeAccountMapper employeeAccountMapper, EmployeeMapper employeeMapper) {
        this.employeeAccountMapper = employeeAccountMapper;
        this.employeeMapper = employeeMapper;
    }

    @GetMapping("/admin")
    public String menu(Model model) {
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

        
}



