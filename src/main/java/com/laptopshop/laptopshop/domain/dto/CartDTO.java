package com.laptopshop.laptopshop.domain.dto;

import java.util.List;

import com.laptopshop.laptopshop.domain.CartDetail;

public class CartDTO {
    private List<CartDetail> cartDetail;
    private List<Integer> cartDetailId;

    public List<Integer> getCartDetailId() {
        return cartDetailId;
    }

    public void setCartDetailId(List<Integer> cartDetailId) {
        this.cartDetailId = cartDetailId;
    }

    public List<CartDetail> getCartDetail() {
        return cartDetail;
    }

    public void setCartDetail(List<CartDetail> cartDetail) {
        this.cartDetail = cartDetail;
    }

}
