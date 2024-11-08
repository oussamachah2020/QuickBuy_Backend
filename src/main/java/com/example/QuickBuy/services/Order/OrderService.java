package com.example.QuickBuy.services.Order;

import com.example.QuickBuy.models.order.Order;
import com.example.QuickBuy.models.order.OrderStatus;
import com.example.QuickBuy.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public Order passAnOrder(Order req) {
        Order order = new Order();

        order.setClientName(req.getClientName());
        order.setClientPhone(req.getClientPhone());
        order.setClientAddress(req.getClientAddress());
        order.setStatus(req.getStatus());
        order.setProductId(req.getProductId());

        return repository.save(order);
    }

    public String updateOrderStatus(String orderId, OrderStatus status) {
        try {
            return repository.findById(orderId)
                    .map(order -> {
                        order.setStatus(status);
                        repository.save(order);
                        return "Order status updated";
                    })
                    .orElse("Order does not exist");
        } catch (Exception e) {
            // Log the exception if needed, e.g., log.error("Error updating order", e);
            return "Error updating order: " + e.getMessage();
        }
    }

    public List<Order> getOrders(OrderStatus status) {
        if (status == null) {
            return repository.findAll();
        } else {
            return repository.findOrdersByStatus(status).orElse(Collections.emptyList());
        }
    }

}
