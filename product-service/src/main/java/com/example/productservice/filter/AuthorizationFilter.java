package com.example.productservice.filter;


import com.example.productservice.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/products/*")
public class AuthorizationFilter implements Filter {
    private final ObjectMapper mapper = new ObjectMapper();
    private final AuthService authService = new AuthService();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        /*
        POST

        POST /products (json)

        admin
         */

        // беремо токен з хедеру Authorization
        String token = httpRequest.getHeader("Authorization");

        if (token != null && authService.isTokenValid(token)) {
            // токен валідний. Тепер перевіряємо роль
            if (isAdminUser(token)) {
                // якщо користувач адмін, дозволяємо POST запити
                if (httpRequest.getMethod().equals("POST")) {
                    chain.doFilter(request, response);
                } else {
                    // для інших типів запитів також дозволяємо
                    chain.doFilter(request, response);
                }
            } else {
                // якщо користувач не адмін, забороняємо POST запити
                if (httpRequest.getMethod().equals("POST")) {
                    httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    httpResponse.getWriter().write("Permission denied for POST requests");
                } else {
                    // для інших типів запитів дозволяємо доступ
                    chain.doFilter(request, response);
                }
            }
        } else {
            // якщо токена немає або він не валідний, повертаємо помилку
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("Unauthorized access");
        }
    }

    @Override
    public void destroy() {
        // cleanup
    }

    private boolean isAdminUser(String token) {
        return token.contains("admin");
    }
}
