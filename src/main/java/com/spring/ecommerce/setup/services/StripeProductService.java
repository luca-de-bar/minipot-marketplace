package com.spring.ecommerce.setup.services;

import com.spring.ecommerce.setup.models.EcomProduct;
import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.param.PriceUpdateParams;
import com.stripe.param.ProductCreateParams;
import com.stripe.param.ProductUpdateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class StripeProductService {

    @Autowired
    private StripePriceService priceService;


    //CREATE Stripe Product
    public void createStripeProduct(EcomProduct ecomProduct) throws StripeException {

        //Convert price from BigDecimal to Long for Stripe API
        Long priceInCents = ecomProduct.getPrice()
                .multiply(new BigDecimal("100"))
                .longValue();

        ProductCreateParams params =
                ProductCreateParams.builder()
                        .setName(ecomProduct.getName())
                        .setDescription(ecomProduct.getDescription())
                        .addAllImage(ecomProduct.getImagesUrl())
                        .setId(ecomProduct.getId().toString())
                        .setDefaultPriceData(
                                ProductCreateParams.DefaultPriceData.builder()
                                        .setUnitAmount(priceInCents) //I used here converted price
                                        .setCurrency("eur")
                                        .build()
                        )
                        .build();
        Product.create(params);
    }


    //EDIT
    public void updateStripeProduct(EcomProduct ecomProduct) throws StripeException {

        Product stripeProduct = Product.retrieve(ecomProduct.getId().toString());
        String newPriceId = priceService.updateStripePrice(ecomProduct);

        //Update name, description and image
        ProductUpdateParams params =
                ProductUpdateParams.builder()
                        .setName(ecomProduct.getName())
                        .setDescription(ecomProduct.getDescription())
                        .addAllImage(ecomProduct.getImagesUrl())
                        .setDefaultPrice(newPriceId)
                        .build();
        stripeProduct.update(params);

        //Archive old prices
        for (Price price : priceService.getAllPrices(ecomProduct).getData()) {
            if(!price.getId().equals(newPriceId)){
                PriceUpdateParams updateParams = PriceUpdateParams.builder()
                        .setActive(false)
                        .build();

                price.update(updateParams);
            }
        }
    }

    //ARCHIVE
    public void archiveStripeProduct(Long id) throws StripeException {
        Product stripeProduct = Product.retrieve(id.toString());
        ProductUpdateParams params = ProductUpdateParams.builder().setActive(false).build();
        stripeProduct.update(params);
    }
}
