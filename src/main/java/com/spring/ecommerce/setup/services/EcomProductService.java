package com.spring.ecommerce.setup.services;

import com.spring.ecommerce.setup.models.EcomProduct;
import com.spring.ecommerce.setup.repositories.EcomProductRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.Product;
import com.stripe.param.ProductCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EcomProductService {


    @Autowired
    private EcomProductRepository repository;

    public void createStripeProduct(EcomProduct ecomProduct) throws StripeException {
        ProductCreateParams params =
                ProductCreateParams.builder()
                        .setName(ecomProduct.getName())
                        .setDescription(ecomProduct.getDescription())
                        .addImage(ecomProduct.getStripePhotoUrl())
                        .setDefaultPriceData(
                                ProductCreateParams.DefaultPriceData.builder()
                                        .setUnitAmount(ecomProduct.getPrice())
                                        .setCurrency("eur")
                                        .build()
                        )
                        .build();
        Product.create(params);
    }

    public EcomProduct storeProduct(EcomProduct product){
       return repository.save(product);
    }

}
