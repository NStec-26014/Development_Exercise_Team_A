package com.example.fullness.stationary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminMenuController {

    @GetMapping("/admin")
    public String showAdminMenu() {
        return "admin/menu"; // → templates/admin/menu.html
    }

}
