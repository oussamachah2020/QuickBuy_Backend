package com.example.QuickBuy.controllers;

import com.example.QuickBuy.models.order.Order;
import com.example.QuickBuy.models.order.OrderStatus;
import com.example.QuickBuy.services.Order.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/pass")
    public Object passOrder(@RequestBody Order req) {
        if (req == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Order request cannot be null");
        }
        try {
            Order passedOrder = orderService.passAnOrder(req);
            return ResponseEntity.ok(passedOrder);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @PutMapping("/status/{orderId}")
    public ResponseEntity<String> updateOrderStatus(@PathVariable String orderId, @RequestBody OrderStatus status) {
        if(orderId != null) {
            orderService.updateOrderStatus(orderId, status);
            return ResponseEntity.status(200).body("Order Updated Successfully");
        }
        return ResponseEntity.status(400).body("status or order id is missing");
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getOrders(@RequestParam(required = false) String status) {
        OrderStatus orderStatus = null;

        // Convert to OrderStatus if a valid value is provided
        if (status != null && !status.isEmpty()) {
            try {
                orderStatus = OrderStatus.valueOf(status);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(Collections.emptyList());
            }
        }

        List<Order> orders = orderService.getOrders(orderStatus);
        return ResponseEntity.ok(orders);
    }


}
