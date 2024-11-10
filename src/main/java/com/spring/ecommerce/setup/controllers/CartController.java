package com.spring.ecommerce.setup.controllers;

import com.spring.ecommerce.setup.models.Cart;
import com.spring.ecommerce.setup.models.EcomProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/cart")
public class CartController {

    Cart cart = new Cart();

    @PostMapping("/add")
    public String addToCart(EcomProduct product){
        cart.addToCart(product);

        return product.getName() + " è stato aggiunto al carrello";
    }
}
