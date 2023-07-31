package com.example.plotline.controllers;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.plotline.entity.CartItem;
import com.example.plotline.entity.User;
import com.example.plotline.services.CartService;
import com.example.plotline.services.UserService;

@Controller
public class BillController {
    private final CartService cartService;
    private final UserService userService;

    public BillController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @GetMapping("/generateBill")
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