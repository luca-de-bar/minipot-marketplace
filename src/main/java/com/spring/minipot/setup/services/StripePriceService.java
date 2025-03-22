package com.spring.minipot.setup.services;

import com.spring.minipot.setup.models.Item;
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
    public PriceCollection getAllPrices(Item product) throws StripeException {
        PriceListParams params = PriceListParams.builder()
                .setProduct(product.getStripeId())
                .build();
        return Price.list(params);
    }



    //GET DEFAULT PRICE FOR SELECTED PRODUCT
    public String getDefaultPrice(Item product) throws StripeException {
        PriceListParams params = PriceListParams.builder()
                .setProduct(product.getStripeId())
                .setActive(true)
                .setLimit(1L)
                .build();
        PriceCollection prices = Price.list(params);

        return prices.getData().getFirst().getId();
    }



    //CREATE NEW PRICE FOR A PRODUCT
    public String createStripePrice (Item product) throws StripeException{

        Long priceInCents = product.getPrice()
                .multiply(new BigDecimal("100"))
                .longValue();

        PriceCreateParams createParams   = PriceCreateParams.builder()
                .setUnitAmount(priceInCents)
                .setActive(true)
                .setCurrency("eur")
                .setProduct(product.getStripeId())
                .build();


        Price newPrice = Price.create(createParams);
        return newPrice.getId();
    }


}
