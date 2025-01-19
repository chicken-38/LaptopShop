package com.laptopshop.laptopshop.service;

import com.laptopshop.laptopshop.domain.Cart;
import com.laptopshop.laptopshop.domain.Order;
import com.laptopshop.laptopshop.domain.dto.CartDTO;

import jakarta.servlet.http.HttpServletRequest;

public interface CartService {
    public int addToCart(long userId, long productId);

    public Cart findByUserId(long id);

    public Cart getCartInfo(long id);

    public double getTotalPrice(Cart cart);

    public int deleteCartDetail(long userId, long cartDetailId);

    public CartDTO getCartDTO(HttpServletRequest request);

    public Order getOrder(CartDTO cart, HttpServletRequest request);
}
