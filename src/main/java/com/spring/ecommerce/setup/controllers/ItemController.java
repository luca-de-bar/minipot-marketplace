package com.spring.ecommerce.setup.controllers;
import com.spring.ecommerce.setup.DTO.ItemDTO;
import com.spring.ecommerce.setup.models.Item;
import com.spring.ecommerce.setup.services.ItemService;
import com.spring.ecommerce.setup.services.StripeItemService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/product")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private StripeItemService stripeItemService;

    //CREATE
    @PostMapping("/create")
    public ResponseEntity<Item>  createProduct(@RequestBody Item item) throws StripeException {
        stripeItemService.createStripeProduct(item);
        Item newProduct = itemService.storeProduct(item);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    //EDIT
    @PutMapping("/edit/{id}")
    public ResponseEntity <Item> update (@PathVariable("id") Long id, @RequestBody ItemDTO product) throws StripeException {
        //Find product
        Optional<Item> existingProduct = itemService.findById(id);

        if(existingProduct.isPresent()){
            Item updatedProduct = itemService.updateProduct(id,product);
            stripeItemService.updateStripeProduct(existingProduct.get());
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        }
        //If product not found
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    //ARCHIVE
    @DeleteMapping("/archive/{id}")
    public ResponseEntity<Item> delete (@PathVariable("id") Long id) throws StripeException {
        Optional<Item> product = itemService.findById(id);

        if(product.isPresent()){
            itemService.archiveProduct(id);
            stripeItemService.archiveStripeProduct(product.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
