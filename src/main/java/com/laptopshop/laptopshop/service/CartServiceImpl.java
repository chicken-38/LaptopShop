package com.laptopshop.laptopshop.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.laptopshop.laptopshop.domain.Cart;
import com.laptopshop.laptopshop.domain.CartDetail;
import com.laptopshop.laptopshop.domain.Order;
import com.laptopshop.laptopshop.domain.Product;
import com.laptopshop.laptopshop.domain.dto.CartDTO;
import com.laptopshop.laptopshop.helper.ImageProcessing;
import com.laptopshop.laptopshop.repository.CartDetailRepository;
import com.laptopshop.laptopshop.repository.CartRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class CartServiceImpl implements CartService {
    private final ProductService productService;
    private final CartDetailRepository cartDetailRepository;
    private final CartRepository cartRepository;
    private final ImageProcessing imageProcessing;

    public CartServiceImpl(ProductService productService,
            CartDetailRepository cartDetailRepository,
            CartRepository cartRepository,
            ImageProcessing imageProcessing) {
        this.productService = productService;
        this.cartDetailRepository = cartDetailRepository;
        this.cartRepository = cartRepository;
        this.imageProcessing = imageProcessing;
    }

    @Override
    public int addToCart(long userId, long productId) {

        Cart cart = this.findByUserId(userId);
        Product product = this.productService.getProductById(productId);

        this.cartDetailRepository.findByCartAndProduct(cart, product)
                .ifPresentOrElse(cartDetail -> {
                    cartDetail.setQuantity(cartDetail.getQuantity() + 1);
                    this.saveCartDetail(cartDetail);
                }, () -> {
                    CartDetail cartDetail = new CartDetail();
                    cartDetail.setCart(cart);
                    cartDetail.setProduct(product);
                    cartDetail.setQuantity(1);
                    cart.setSum(cart.getSum() + 1);
                    this.cartRepository.save(cart);
                    this.saveCartDetail(cartDetail);
                });
        return cart.getSum();
    }

    @Override
    public Cart getCartInfo(long id) {
        Cart cart = this.findByUserId(id);
        List<CartDetail> cartDetails = cart.getCartDetail();

        for (CartDetail cartDetail : cartDetails) {
            cartDetail.getProduct().setImageURL(this.imageProcessing.getUrl(cartDetail.getProduct().getImage()));
            cartDetail.setPrice(cartDetail.getProduct().getPrice() * cartDetail.getQuantity());
        }

        return cart;
    }

    @Override
    public double getTotalPrice(Cart cart) {
        double totalPrice = 0;

        List<CartDetail> cartDetails = cart.getCartDetail();
        for (CartDetail cartDetail : cartDetails) {
            totalPrice += cartDetail.getPrice();
        }

        return totalPrice;
    }

    @Override
    public int deleteCartDetail(long userId, long cartDetailId) {

        Cart cart = this.findByUserId(userId);

        if (this.cartDetailRepository.deleteByUserIdAndCartDetailId(cart.getId(),
                cartDetailId) == 0) {
            throw new RuntimeException("The deleted record does not exist.");
        }

        cart.setSum(cart.getSum() - 1);
        this.cartRepository.save(cart);

        return cart.getSum();
    }

    @Override
    public Cart findByUserId(long id) {
        return this.cartRepository.findByUserId(id).orElseThrow(() -> new RuntimeException("Cart not found."));
    }

    public void saveCartDetail(CartDetail cartDetail) {
        this.cartDetailRepository.save(cartDetail);
    }

    @Override
    public CartDTO getCartDTO(HttpServletRequest request) {
        HttpSession session = request.getSession();
        long userId = (long) session.getAttribute("id");
        Cart cart = this.getCartInfo(userId);
        List<CartDetail> cartDetails = cart.getCartDetail().stream().collect(Collectors.toList());
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartDetail(cartDetails);
        return cartDTO;
    }

    @Override
    public Order getOrder(CartDTO cartDTO, HttpServletRequest request) {

        return null;
    }

}
