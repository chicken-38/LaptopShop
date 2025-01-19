package com.laptopshop.laptopshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.laptopshop.laptopshop.domain.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query(value = "SELECT * FROM carts WHERE user_id = :id", nativeQuery = true)
    Optional<Cart> findByUserId(long id);

}
