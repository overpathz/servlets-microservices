package com.example.discountservice.servlet;

import com.example.discountservice.service.DiscountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;

@WebServlet("/discount")
public class DiscountServlet extends HttpServlet {
    private final DiscountService discountService = new DiscountService();
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String productName = req.getParameter("productName");
        double discountPrice = discountService.getPrice(productName);
        Discount discount = new Discount(discountPrice);
        String jsonString = mapper.writeValueAsString(discount);
        resp.setContentType("application/json");
        resp.getWriter().println(jsonString);
    }

    @Data
    @AllArgsConstructor
    static class Discount {
        private double price;
    }
}
