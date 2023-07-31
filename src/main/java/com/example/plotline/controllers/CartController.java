package com.example.plotline.controllers;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.plotline.entity.CartItem;
import com.example.plotline.entity.Product;
import com.example.plotline.entity.User;
import com.example.plotline.services.CartService;
import com.example.plotline.services.ProductService;
import com.example.plotline.services.UserService;

import java.util.List;

@Controller
public class CartController {
    private final CartService cartService;
    private final UserService userService;
    private final ProductService productService;

    public CartController(CartService cartService, UserService userService, ProductService productService) {
        this.cartService = cartService;
        this.userService = userService;
        this.productService = productService;
    }

    @GetMapping("/cart")
    public String showCart(Model model) {
        User currentUser = getCurrentUser();
        List<CartItem> cartItems = cartService.getCartItemsForCurrentUser(currentUser);
        double totalPrice = cartService.calculateTotalPrice(cartItems);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice);
        return "cart";
    }

    @PostMapping("/addToCart")
    public String addToCart(@RequestParam("productId") Long productId, @RequestParam("quantity") int quantity) {
        User currentUser = getCurrentUser();
        Product product = productService.getProductById(productId);
        cartService.addToCart(currentUser, product, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/updateCartItem")
    public String updateCartItem(@RequestParam("cartItemId") Long cartItemId, @RequestParam("quantity") int quantity) {
        CartItem cartItem = cartService.getCartItemById(cartItemId);
        cartService.updateCartItem(cartItem, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/removeCartItem")
    public String removeCartItem(@RequestParam("cartItemId") Long cartItemId) {
        CartItem cartItem = cartService.getCartItemById(cartItemId);
        cartService.removeCartItem(cartItem);
        return "redirect:/cart";
    }

    @PostMapping("/generateBill")
    public String generateBill(Model model) {
        User currentUser = getCurrentUser();
        List<CartItem> cartItems = cartService.getCartItemsForCurrentUser(currentUser);
        double totalPrice = cartService.calculateTotalPrice(cartItems);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice);
        return "bill";
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return userService.findByUsername(username);
    }
}