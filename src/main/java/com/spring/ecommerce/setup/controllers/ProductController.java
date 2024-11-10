package com.spring.ecommerce.setup.controllers;
import com.spring.ecommerce.setup.DTO.EcomProductDTO;
import com.spring.ecommerce.setup.models.EcomProduct;
import com.spring.ecommerce.setup.services.ProductService;
import com.spring.ecommerce.setup.services.StripeProductService;
import com.stripe.exception.StripeException;
import jakarta.validation.Valid;
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
    private StripeProductService stripeProductService;

    //CREATE
    @PostMapping("/create")
    public ResponseEntity<EcomProduct>  createProduct(@RequestBody EcomProduct ecomProduct) throws StripeException {
        stripeProductService.createStripeProduct(ecomProduct);
        EcomProduct newEcomProduct = productService.storeProduct(ecomProduct);
        return new ResponseEntity<>(newEcomProduct, HttpStatus.CREATED);
    }

    //EDIT
    @PutMapping("/edit/{id}")
    public ResponseEntity <EcomProduct> update (@PathVariable("id") Long id, @RequestBody EcomProductDTO product) throws StripeException {
        //Find product
        Optional<EcomProduct> existingProduct = productService.findById(id);

        if(existingProduct.isPresent()){
            EcomProduct updatedProduct = productService.updateProduct(id,product);
            stripeProductService.updateStripeProduct(existingProduct.get());
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        }
        //If product not found
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    //ARCHIVE
    @DeleteMapping("/archive/{id}")
    public ResponseEntity<EcomProduct> delete (@PathVariable("id") Long id) throws StripeException {
        Optional<EcomProduct> product = productService.findById(id);

        if(product.isPresent()){
            productService.archiveProduct(id);
            stripeProductService.archiveStripeProduct(product.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
