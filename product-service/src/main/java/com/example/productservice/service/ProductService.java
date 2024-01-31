package com.example.productservice.service;

import com.example.productservice.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private final List<Product> products; // Імітована база даних для прикладу
    private final DiscountService discountService = new DiscountService();

    public ProductService() {
        // Ініціалізація списку продуктів (можливо, замість цього використовувати реальну базу даних)
        products = new ArrayList<>();
        products.add(new Product("Iphone", "Опис продукту 1", 10.99));
        products.add(new Product("MacBook", "Опис продукту 2", 15.99));
        products.add(new Product("Intel", "Опис продукту 3", 20.99));
    }

    public List<Product> getAllProducts() {
        for (Product product : products) {
            setProductPriceWithDiscount(product);
        }
        return products;
    }

    public Product getProductById(long id) {
        for (Product product : products) {
            if (product.getId() == id) {
                setProductPriceWithDiscount(product);
                return product;
            }
        }
        return null; // Якщо продукт не знайдено
    }

    private void setProductPriceWithDiscount(Product product) {
        product.setPrice(getPriceWithDiscount(product));
    }

    private double getPriceWithDiscount(Product product) {
        double priceWithDiscount = discountService.getPriceWithDiscount(product.getName());
        return priceWithDiscount;
    }

    public void addProduct(Product product) {
        // Додавання продукту до списку (можливо, замість цього зберігати в базі даних)
        products.add(product);
    }

    public void updateProduct(Product updatedProduct) {
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            if (product.getId() == updatedProduct.getId()) {
                // Оновлення продукту в списку
                products.set(i, updatedProduct);
                return;
            }
        }
        // Якщо продукт не знайдено, можна викинути виняток або обробити інакше
    }

    public void deleteProduct(long id) {
        products.removeIf(product -> product.getId() == id);
    }
}

