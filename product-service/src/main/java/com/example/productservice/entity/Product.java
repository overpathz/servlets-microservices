package com.example.productservice.entity;

import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

@Data
public class Product {
    private static final AtomicInteger idCounter = new AtomicInteger();
    private long id;
    private String name;
    private String description;
    private double price;

    public Product(long id, String name, String description, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Product(String name, String description, double price) {
        this.id = idCounter.incrementAndGet();
        this.name = name;
        this.description = description;
        this.price = price;
    }
}

