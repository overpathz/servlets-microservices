package com.example.discountservice.service;

import java.util.concurrent.ThreadLocalRandom;

public class DiscountService {
    public double getPrice(String productName) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return ThreadLocalRandom.current().nextDouble(2500);
    }
}
