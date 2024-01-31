package com.example.productservice.service;

import com.example.productservice.util.ExternalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AuthService {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final HttpClient httpClient = HttpClient.newBuilder().build();

    @SneakyThrows
    public boolean isTokenValid(String token) {
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.noBody())
                .uri(URI.create(ExternalService.AUTH.getUrl()))
                .header("Authorization", token)
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String jsonString = response.body();
        ValidationResult result = mapper.readValue(jsonString, ValidationResult.class);
        return result.isValid();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class ValidationResult {
        private boolean valid;
    }
}
