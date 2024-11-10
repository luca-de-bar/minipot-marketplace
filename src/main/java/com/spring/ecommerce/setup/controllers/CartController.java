package com.spring.ecommerce.setup.controllers;

import com.spring.ecommerce.setup.models.Cart;
import com.spring.ecommerce.setup.models.EcomProduct;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/cart")
public class CartController {


    @PostMapping("/add")
    public void addToCart(EcomProduct product){
        Cart cart = new Cart();
        cart.addToCart(product);
    }

    @DeleteMapping("remove/{id}")
    public void remove (@PathVariable("id") Long id, EcomProduct product){

    }
}
