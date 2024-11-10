package com.spring.ecommerce.setup.services;

import com.spring.ecommerce.setup.DTO.EcomProductDTO;
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
    public EcomProduct updateProduct(Long id,EcomProductDTO productDTO){

        EcomProduct product = repository.findById(id).get();

        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setQuantity(productDTO.getQuantity());
        product.setPrice(productDTO.getPrice());
        product.setImagesUrl(productDTO.getImagesUrl());

        return repository.save(product);
    }

    //ARCHIVE
    public void archiveProduct(Long id){
        EcomProduct product = findById(id).get();
        product.setActive(false);

        repository.save(product);
    }

    //FIND by ID
    public Optional<EcomProduct> findById(Long id){
        return repository.findById(id);
    }
}
