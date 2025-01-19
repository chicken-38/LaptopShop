package com.laptopshop.laptopshop.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.laptopshop.laptopshop.domain.Cart;
import com.laptopshop.laptopshop.domain.CartDetail;
import com.laptopshop.laptopshop.domain.Order;
import com.laptopshop.laptopshop.domain.OrderDetail;
import com.laptopshop.laptopshop.helper.ImageProcessing;

@Service
public class OrderServiceImpl implements OrderService {
    private final CartService cartService;
    private final ImageProcessing imageProcessing;

    public OrderServiceImpl(CartService cartService,
            ImageProcessing imageProcessing) {
        this.cartService = cartService;
        this.imageProcessing = imageProcessing;
    }

    @Override
    public Order getOrder(long id) {

        Cart cart = this.cartService.findByUserId(id);
        List<CartDetail> cartDetails = cart.getCartDetail();

        Order order = new Order();
        List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
        double totalPrice = 0;

        for (CartDetail cartDetail : cartDetails) {
            cartDetail.getProduct().setImageURL(this.imageProcessing.getUrl(cartDetail.getProduct().getImage()));
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProduct(cartDetail.getProduct());
            orderDetail.setQuantity(cartDetail.getQuantity());
            orderDetail.setPrice(cartDetail.getProduct().getPrice() * cartDetail.getQuantity());
            totalPrice += orderDetail.getPrice();
            orderDetails.add(orderDetail);
        }

        order.setOrderDetails(orderDetails);
        order.setTotalPrice(totalPrice);

        return order;
    }

}
