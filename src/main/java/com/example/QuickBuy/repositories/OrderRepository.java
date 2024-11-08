package com.example.QuickBuy.repositories;

import com.example.QuickBuy.models.order.Order;
import com.example.QuickBuy.models.order.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, String> {
    @Query("SELECT o FROM Order o WHERE o.status = :status")
    Optional<List<Order>> findOrdersByStatus(@Param("status") OrderStatus status);

}
