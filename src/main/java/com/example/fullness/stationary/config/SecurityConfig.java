package com.example.fullness.stationary.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.InternalAuthenticationServiceException; 
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler; 

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/admin","/admin/menu", "/admin/login", "/admin/logout", "/admin/error", "/css/**", "/js/**", "/images/**").permitAll() 
                .requestMatchers("/admin/**").authenticated()
                .anyRequest().permitAll()
            )
            .formLogin(form -> form
                .loginPage("/admin/login")
                .loginProcessingUrl("/admin/login")
                .usernameParameter("accountName")
                .passwordParameter("password")
                .defaultSuccessUrl("/admin", true)
                .failureHandler(customAuthenticationFailureHandler())
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/admin/logout")
                .logoutSuccessUrl("/admin")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .clearAuthentication(true)

            );
            
        
        return http.build();
    }
    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        return (request, response, exception) -> {
            String accountName = request.getParameter("accountName");
            String password = request.getParameter("password");

            boolean isAccountEmpty = (accountName == null || accountName.trim().isEmpty());
            boolean isPasswordEmpty = (password == null || password.isEmpty());

            if (exception instanceof InternalAuthenticationServiceException) {
                response.sendRedirect(request.getContextPath() + "/admin/error");
                return;
            }

            String errorMessage = "";

            if (isAccountEmpty || isPasswordEmpty) {
                StringBuilder msg = new StringBuilder();
                if (isAccountEmpty) {
                    msg.append("アカウント名を入力してください");
                }
                if (isPasswordEmpty) {
                    if (msg.length() > 0) msg.append(" ");
                    msg.append("パスワードを入力してください");
                }
                errorMessage = msg.toString();
            } else {
                errorMessage = "アカウント名またはパスワードが正しくありません";
            }

            request.getSession().setAttribute("LOGIN_ERROR_MESSAGE", errorMessage);

            response.sendRedirect(request.getContextPath() + "/admin/login?error");
        };
    }
    

    @Bean
    @SuppressWarnings("deprecation")
    public PasswordEncoder passwordEncoder() {
        return new MessageDigestPasswordEncoder("SHA-512");
    }
}