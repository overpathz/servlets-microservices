package com.example.productservice.servlet;

import com.example.productservice.entity.Product;
import com.example.productservice.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.List;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {
    private ProductService productService; // Посилання на сервіс продуктів
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void init() throws ServletException {
        // Ініціалізація сервісу продуктів (можливо, замість цього використовувати DI)
        productService = new ProductService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Product> products = productService.getAllProducts();
        // Конвертація списку продуктів в JSON та відправка відповіді
        String jsonResponse = convertToJson(products);
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Отримати дані про новий продукт з запиту та додати його
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));

        Product newProduct = new Product(name, description, price);
        productService.addProduct(newProduct);
        response.setStatus(HttpServletResponse.SC_CREATED); // Відповідь 201 Created
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Отримати дані про оновлений продукт з запиту та оновити його
        long id = Long.parseLong(request.getParameter("id"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));

        Product updatedProduct = new Product(id, name, description, price);
        productService.updateProduct(updatedProduct);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // Отримати ідентифікатор продукта для видалення
        long id = Long.parseLong(request.getParameter("id"));
        productService.deleteProduct(id);
    }

    @SneakyThrows
    private String convertToJson(List<Product> products) {
        return mapper.writeValueAsString(products);
    }
}

