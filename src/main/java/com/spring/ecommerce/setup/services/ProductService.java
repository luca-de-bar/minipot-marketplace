package com.spring.ecommerce.setup.services;

import com.spring.ecommerce.setup.DTO.EcomProductDTO;
import com.spring.ecommerce.setup.models.EcomProduct;
import com.spring.ecommerce.setup.repositories.EcomProductRepository;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private EcomProductRepository repository;

    @Autowired
    private StripeService stripeService;

    //STORE : call this method only for NEW products
    public EcomProduct storeProduct(EcomProduct ecomProduct) throws StripeException {
        String stripeProductId = stripeService.createStripeProduct(ecomProduct);
        ecomProduct.setStripeId(stripeProductId);
        return repository.save(ecomProduct);
    }

    //UPDATE
    public EcomProduct updateProduct(Long id, EcomProductDTO productDTO){
        EcomProduct product = repository.findById(id).get();

        //Update on DB
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setPhotoUrl(productDTO.getPhotoUrl());
        product.setQuantity(productDTO.getQuantity());

        return repository.save(product);
    }

    //FIND by ID
    public Optional<EcomProduct> findById(Long id){
        return repository.findById(id);
    }

}
