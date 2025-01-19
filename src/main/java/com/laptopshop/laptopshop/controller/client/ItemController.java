package com.laptopshop.laptopshop.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.laptopshop.laptopshop.domain.dto.CartDTO;
import com.laptopshop.laptopshop.service.CartService;
import com.laptopshop.laptopshop.service.ProductService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ItemController {
    private final ProductService productService;
    private final CartService cartService;

    public ItemController(ProductService productService,
            CartService cartService) {
        this.productService = productService;
        this.cartService = cartService;
    }

    @GetMapping("/product/{id}")
    public String getProductDetailPage(@PathVariable long id, Model model) {
        try {
            model.addAttribute("product", this.productService.getProductById(id));
            return "client/product/detail";
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return "client/homepage/404";
        }
    }

    @PostMapping("/addToCart/{productId}")
    public String addToCart(@PathVariable long productId, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        long userId = (long) session.getAttribute("id");
        session.setAttribute("sum", this.cartService.addToCart(userId, productId));
        return "redirect:/";
    }

    @GetMapping("/cart")
    public String getCartPage(Model model, HttpServletRequest request) {

        model.addAttribute("cart", this.cartService.getCartDTO(request));

        return "client/cart/show";
    }

    @GetMapping("/delete-cart-product/{cartDetailId}")
    public String deleteCartDetail(@PathVariable long cartDetailId, HttpServletRequest request) {

        HttpSession session = request.getSession();
        long userId = (long) session.getAttribute("id");
        session.setAttribute("sum", this.cartService.deleteCartDetail(userId, cartDetailId));

        return "redirect:/cart";
    }

    @PostMapping("/place-order")
    public String getPlaceOrder(@ModelAttribute CartDTO cart, HttpServletRequest request) {

        return "client/order/checkout";
    }

}
