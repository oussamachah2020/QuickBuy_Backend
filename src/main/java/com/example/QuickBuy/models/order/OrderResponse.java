package com.example.QuickBuy.models.order;

public class OrderResponse {
    private Order order;
    private String message;

    // Constructors
    public OrderResponse(Order order) {
        this.order = order;
    }

    public OrderResponse(String message) {
        this.message = message;
    }

    // Getters
    public Order getOrder() {
        return order;
    }

    public String getMessage() {
        return message;
    }

    // Method to check if the response has an order or just a message
    public boolean hasOrder() {
        return order != null;
    }
}

