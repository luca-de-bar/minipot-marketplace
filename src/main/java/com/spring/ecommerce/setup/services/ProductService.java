package com.spring.ecommerce.setup.services;

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

    //STORE : Only for NEW products
    public EcomProduct storeProduct(EcomProduct ecomProduct) throws StripeException {
        String stripeProductId = stripeService.createStripeProduct(ecomProduct);
        ecomProduct.setStripeId(stripeProductId);
        return repository.save(ecomProduct);
    }

    //SAVE : to save changes on EXISTING products
    public EcomProduct saveProduct(EcomProduct ecomProduct){
        return repository.save(ecomProduct);
    }

    //FIND by ID
    public Optional<EcomProduct> findById(Long id) throws StripeException {
        return repository.findById(id);
    }

}
