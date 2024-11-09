package com.spring.ecommerce.setup.controllers;
import com.spring.ecommerce.setup.models.EcomProduct;
import com.spring.ecommerce.setup.services.ProductService;
import com.spring.ecommerce.setup.services.StripeService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    //CREATE
    @PostMapping("/create")
    public ResponseEntity<EcomProduct>  createProduct(@RequestBody EcomProduct ecomProduct) throws StripeException {
        EcomProduct newEcomProduct = productService.storeProduct(ecomProduct);
        return new ResponseEntity<>(newEcomProduct, HttpStatus.CREATED);
    }


    //EDIT
    @PutMapping("/edit/{id}")
    public ResponseEntity <EcomProduct> update (@PathVariable("id") Long id,
                                                @RequestBody EcomProduct product) throws StripeException {
        Optional<EcomProduct> existingProduct = productService.findById(id);
        if(existingProduct.isPresent()){
            product.setId(id); //Maintains the same id
            product.setStripeId(product.getStripeId()); //Maintains same stripe id
            EcomProduct updatedProduct = productService.saveProduct(product);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
