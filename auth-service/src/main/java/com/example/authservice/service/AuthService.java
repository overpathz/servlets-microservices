package com.example.authservice.service;

import java.util.HashMap;
import java.util.Map;

public class AuthService {
    private static final AuthService INSTANCE = new AuthService();

    public static AuthService getINSTANCE() {
        return INSTANCE;
    }

    private final Map<String, String> userDatabase = new HashMap<>(); // База даних користувачів (логін - пароль)
    private final Map<String, String> tokenDatabase = new HashMap<>(); // База даних токенів (токен - логін)

    private AuthService() {
        userDatabase.put("adminUser", "password1");
        userDatabase.put("simpleUser", "password2");
    }

    public String authenticate(String username, String password) {
        // Перевіряємо користувача
        if (userDatabase.containsKey(username) && userDatabase.get(username).equals(password)) {
            // Генеруємо та повертаємо токен
            String token = generateToken(username);
            tokenDatabase.put(token, username);
            return token;
        }
        return null; // Невірні логін або пароль
    }

    public boolean validateToken(String token) {
        return tokenDatabase.containsKey(token);
    }

    public String getUsernameFromToken(String token) {
        return tokenDatabase.get(token);
    }

    private String generateToken(String username) {
        String token = "a-level-token" + System.currentTimeMillis();
        if (username.equals("adminUser")) {
            token += "admin";
        }
        return token;
    }
}
