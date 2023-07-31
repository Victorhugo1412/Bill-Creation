package com.example.plotline.services;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.plotline.entity.CartItem;
import com.example.plotline.entity.Product;
import com.example.plotline.entity.User;
import com.example.plotline.repository.CartItemRepository;

@Service
public class CartService {
    private final CartItemRepository cartItemRepository;

    public CartService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public List<CartItem> getCartItemsForCurrentUser(User user) {
        return cartItemRepository.findByUser(user);
    }

    public double calculateTotalPrice(List<CartItem> cartItems) {
        double totalPrice = 0;
        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }
        return totalPrice;
    }

    public void addToCart(User user, Product product, int quantity) {
        CartItem cartItem = new CartItem();
        cartItem.setUser(user);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
    }

    public void updateCartItem(CartItem cartItem, int quantity) {
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
    }

    public void removeCartItem(CartItem cartItem) {
        cartItemRepository.delete(cartItem);
    }
    
    public CartItem getCartItemById(Long cartItemId) {
        return cartItemRepository.findById(cartItemId).orElse(null);
    }
}
