package com.spring.ecommerce.setup.services;

import com.spring.ecommerce.setup.models.EcomProduct;
import com.stripe.exception.StripeException;
import com.stripe.model.Product;
import com.stripe.param.ProductCreateParams;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class StripeService {

    //CREATE Stripe Product and return its ID
    public void createStripeProduct(EcomProduct ecomProduct) throws StripeException {

        //Convert BigDecimal to Long for Stripe API
        Long priceInCents = ecomProduct.getPrice()
                .multiply(new BigDecimal("100"))
                .longValue();

        ProductCreateParams params =
                ProductCreateParams.builder()
                        .setName(ecomProduct.getName())
                        .setDescription(ecomProduct.getDescription())
                        .addImage(ecomProduct.getPhotoUrl())
                        .setId(ecomProduct.getId().toString())
                        .setDefaultPriceData(
                                ProductCreateParams.DefaultPriceData.builder()
                                        .setUnitAmount(priceInCents)
                                        .setCurrency("eur")
                                        .build()
                        )
                        .build();
        Product.create(params);
    }

    //EDIT
}
