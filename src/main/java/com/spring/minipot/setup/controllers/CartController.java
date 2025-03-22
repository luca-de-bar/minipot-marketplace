package com.spring.minipot.setup.controllers;

import com.spring.minipot.setup.models.Cart;
import com.spring.minipot.setup.models.Item;
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
