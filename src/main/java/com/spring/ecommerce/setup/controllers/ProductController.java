package com.spring.ecommerce.setup.controllers;
import com.spring.ecommerce.setup.DTO.EcomProductDTO;
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
    @Autowired
    private StripeService stripeService;

    //CREATE
    @PostMapping("/create")
    public ResponseEntity<EcomProduct>  createProduct(@RequestBody EcomProduct ecomProduct) throws StripeException {
        EcomProduct newEcomProduct = productService.storeProduct(ecomProduct);
        stripeService.createStripeProduct(ecomProduct);
        return new ResponseEntity<>(newEcomProduct, HttpStatus.CREATED);
    }


    //EDIT
    @PutMapping("/edit/{id}")
    public ResponseEntity <EcomProduct> update (@PathVariable("id") Long id,
                                                @RequestBody EcomProductDTO product) throws StripeException {
        //Find product
        Optional<EcomProduct> existingProduct = productService.findById(id);
        if(existingProduct.isPresent()){
            //Save changes
            EcomProduct updatedProduct = productService.updateProduct(id,product);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        }
        //IF product not found
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
