package com.example.QuickBuy.repositories;

import com.example.QuickBuy.models.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {
    Optional<Product> findProductByOwnerId(String ownerId);
}
