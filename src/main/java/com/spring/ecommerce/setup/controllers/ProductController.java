package com.spring.ecommerce.setup.controllers;
import com.spring.ecommerce.setup.models.EcomProduct;
import com.spring.ecommerce.setup.services.EcomProductService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/product")
public class ProductController {

    @Autowired
    private EcomProductService service;

    @PostMapping("/create")
    public ResponseEntity<EcomProduct>  createProduct(@RequestBody EcomProduct product ) throws StripeException {
        service.createStripeProduct(product);
        EcomProduct newProduct = service.storeProduct(product);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

}
