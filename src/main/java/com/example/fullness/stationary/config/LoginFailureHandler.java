// package com.example.fullness.stationary.config;

// import java.io.IOException;

// import org.springframework.beans.factory.annotation.Autowired;
// import com.example.fullness.stationary.service.LoginFailureService;

// import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
// import org.springframework.stereotype.Component;

// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import org.springframework.security.core.AuthenticationException;

// @Component
// public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

//     @Autowired
//     private LoginFailureService failureService;

//     @Override
//     public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
//         String username = request.getParameter("accountName"); // フォームの項目名に合わせて変更
//         if (username != null) {
//             failureService.incrementFailureCount(username);
//         }
        
//         super.setDefaultFailureUrl("/admin/login?error");
//         super.onAuthenticationFailure(request, response, exception);
//     }
// }