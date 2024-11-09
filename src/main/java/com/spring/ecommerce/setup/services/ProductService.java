package com.spring.ecommerce.setup.services;

import com.spring.ecommerce.setup.models.EcomProduct;
import com.spring.ecommerce.setup.repositories.EcomProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private EcomProductRepository repository;

    //STORE : call this method only for NEW products
    public EcomProduct storeProduct(EcomProduct ecomProduct){
        return repository.save(ecomProduct);
    }

    //UPDATE
    public EcomProduct updateProduct(EcomProduct ecomProduct){
        return repository.save(ecomProduct);
    }

    //DELETE
    public void deleteProduct(Long id){
        repository.deleteById(id);
    }

    //FIND by ID
    public Optional<EcomProduct> findById(Long id){
        return repository.findById(id);
    }

}
