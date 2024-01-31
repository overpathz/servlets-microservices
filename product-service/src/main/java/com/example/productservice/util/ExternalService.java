package com.example.productservice.util;

import lombok.Getter;

@Getter
public enum ExternalService {
    AUTH("http://localhost:8092/validate"),
    DISCOUNT("http://localhost:8094/discount?productName=");

    private final String url;
    ExternalService(String url) {
        this.url = url;
    }
}
