package com.example.fullness.stationary.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.fullness.stationary.service.LoginFailureService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private LoginFailureService failureService;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .addFilterBefore(new OncePerRequestFilter() {
                @Override
                protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
                    if ("/admin/login".equals(request.getRequestURI()) && "POST".equalsIgnoreCase(request.getMethod())) {
                        String accountName = request.getParameter("accountName");
                        if (accountName != null && failureService.isLocked(accountName)) {
                            request.getSession().setAttribute("LOGIN_ERROR_MESSAGE", "アカウントがロックされています。しばらくたってから再度お試しください。");
                            response.sendRedirect(request.getContextPath() + "/admin/login?error");
                            return;
                        }
                    }
                    filterChain.doFilter(request, response);
                }
                }, UsernamePasswordAuthenticationFilter.class)
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
                .successHandler(new LoginSuccessHandler(failureService))
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

            // 失敗回数をインクリメント（アカウント名が存在する場合）
            if (accountName != null && !accountName.trim().isEmpty()) {
                failureService.incrementFailureCount(accountName);
            }

            boolean isAccountEmpty = (accountName == null || accountName.trim().isEmpty());
            boolean isPasswordEmpty = (password == null || password.isEmpty());

            // まず空欄チェックを優先し、未入力ならログイン画面へ戻す
            if (isAccountEmpty || isPasswordEmpty) {
                StringBuilder msg = new StringBuilder();
                if (isAccountEmpty) {
                    msg.append("アカウント名を入力してください");
                }
                if (isPasswordEmpty) {
                    if (msg.length() > 0) msg.append(" ");
                    msg.append("パスワードを入力してください");
                }

                if (accountName != null) {
                    request.getSession().setAttribute("LOGIN_ACCOUNT_NAME", accountName);
                } else {
                    request.getSession().removeAttribute("LOGIN_ACCOUNT_NAME");
                }

                request.getSession().setAttribute("LOGIN_ERROR_MESSAGE", msg.toString());
                response.sendRedirect(request.getContextPath() + "/admin/login?error");
                return;
            }

            // 空欄でなければ例外種別で振り分け（重大エラーは /admin/error へ）
            if (exception instanceof InternalAuthenticationServiceException) {
                response.sendRedirect(request.getContextPath() + "/admin/error");
                return;
            }

            if (accountName != null) {
                request.getSession().setAttribute("LOGIN_ACCOUNT_NAME", accountName);
            } else {
                request.getSession().removeAttribute("LOGIN_ACCOUNT_NAME");
            }

            String errorMessage = "アカウント名またはパスワードが正しくありません";
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