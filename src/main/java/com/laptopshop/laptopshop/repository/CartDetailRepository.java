package com.laptopshop.laptopshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.laptopshop.laptopshop.domain.Cart;
import com.laptopshop.laptopshop.domain.CartDetail;
import com.laptopshop.laptopshop.domain.Product;

import jakarta.transaction.Transactional;

public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {
    Optional<CartDetail> findByCartAndProduct(Cart cart, Product product);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM cart_detail WHERE id = :cartDetailId AND cart_id = :cartId", nativeQuery = true)
    int deleteByUserIdAndCartDetailId(long cartId, long cartDetailId);

}
