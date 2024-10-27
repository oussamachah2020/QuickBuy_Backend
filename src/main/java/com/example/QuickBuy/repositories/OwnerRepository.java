package com.example.QuickBuy.repositories;

import com.example.QuickBuy.models.owner.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner, String> {
    Optional<Owner> findUserByEmail(String email);
}
