package com.spring.ecommerce.setup.services;

import com.spring.ecommerce.setup.models.EcomProduct;
import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.model.PriceCollection;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.PriceListParams;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class StripePriceService {

    //GET PRICES FOR SELECTED PRODUCT
    public PriceCollection getAllPrices(EcomProduct ecomProduct) throws StripeException {
        PriceListParams params = PriceListParams.builder()
                .setProduct(ecomProduct.getId().toString())
                .build();
        return Price.list(params);
    }

    //GET DEFAULT PRICE FOR SELECTED PRODUCT
    public String getDefaultPrice(EcomProduct ecomProduct) throws StripeException {
        PriceListParams params = PriceListParams.builder()
                .setProduct(ecomProduct.getId().toString())
                .setActive(true)
                .setLimit(1L)
                .build();
        PriceCollection prices = Price.list(params);

        //Return the default price
        return prices.getData().getFirst().toString();
    }



    //UPDATE PRICE RELATED TO PRODUCT
    public String updateStripePrice (EcomProduct ecomProduct) throws StripeException{

        // Convert price from BigDecimal to Long for Stripe API
        Long priceInCents = ecomProduct.getPrice()
                .multiply(new BigDecimal("100"))
                .longValue();

        //Create new price
        PriceCreateParams createParams   = PriceCreateParams.builder()
                .setUnitAmount(priceInCents)
                .setActive(true)
                .setCurrency("eur")
                .setProduct(ecomProduct.getId().toString())
                .build();


        Price newPrice = Price.create(createParams);
        return newPrice.getId();
    }


}
