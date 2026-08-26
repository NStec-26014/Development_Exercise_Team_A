package com.example.fullness.stationary.config;

import com.example.fullness.stationary.service.LoginFailureService;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final LoginFailureService failureService;

    public LoginSuccessHandler(LoginFailureService failureService) {
        this.failureService = failureService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        failureService.resetFailureCount(authentication.getName());
        setDefaultTargetUrl("/admin");
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
