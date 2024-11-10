package com.spring.ecommerce.setup.services;

import com.spring.ecommerce.setup.DTO.ItemDTO;
import com.spring.ecommerce.setup.models.Item;
import com.spring.ecommerce.setup.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository repository;

    //STORE : call this method only for NEW products
    public Item storeProduct(Item product){
        return repository.save(product);
    }

    //UPDATE
    public Item updateProduct(Long id, ItemDTO productDTO){

        Item product = repository.findById(id).get();

        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setQuantity(productDTO.getQuantity());
        product.setPrice(productDTO.getPrice());
        product.setImagesUrl(productDTO.getImagesUrl());

        return repository.save(product);
    }

    //ARCHIVE
    public void archiveProduct(Long id){
        Item product = findById(id).get();
        product.setArchived(true);

        repository.save(product);
    }

    //UN-ARCHIVE?
    public void unarchiveProduct(Long id){
        Item product = findById(id).get();
        product.setArchived(false);

        repository.save(product);
    }

    //FIND by ID
    public Optional<Item> findById(Long id){
        return repository.findById(id);
    }
}
