package io.minipot.spring.backend.controllers;

import io.minipot.spring.backend.models.Cart;
import io.minipot.spring.backend.models.Item;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/cart")
public class CartController {

    Cart cart = new Cart();

    @PostMapping("/add")
    public String addToCart(Item product){
        cart.addToCart(product);
        return product.getName() + " è stato aggiunto al carrello";
    }
}
