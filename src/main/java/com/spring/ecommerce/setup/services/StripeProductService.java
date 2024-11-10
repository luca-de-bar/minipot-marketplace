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
                        .setDefaultPriceData(
                                ProductCreateParams.DefaultPriceData.builder()
                                        .setUnitAmount(priceInCents) //I used here converted price
                                        .setCurrency("eur")
                                        .build()
                        )
                        .build();

        Product product = Product.create(params);
        ecomProduct.setStripeId(product.getId());
    }

    //EDIT
    public void updateStripeProduct(EcomProduct ecomProduct) throws StripeException {
        Product stripeProductID = Product.retrieve(ecomProduct.getStripeId());

        for (Price price : priceService.getAllPrices(ecomProduct).getData()) {

            //Both converted in Long for easy compare
            Long oldPrice = price.getUnitAmountDecimal().longValue();
            Long newPrice = ecomProduct.getPrice().multiply(new BigDecimal("100")).longValue();

            //If prices are different, create new one and disable old
            if(!oldPrice.equals(newPrice)){
                String newPriceId = priceService.createStripePrice(ecomProduct);
                ProductUpdateParams params =
                        ProductUpdateParams.builder()
                                .setDefaultPrice(newPriceId)
                                .build();
                stripeProductID.update(params);

                PriceUpdateParams updateParams = PriceUpdateParams.builder()
                        .setActive(false)
                        .build();
                price.update(updateParams);
                break;
            }
        }
        //Update Product params
        ProductUpdateParams params =
                ProductUpdateParams.builder()
                        .setName(ecomProduct.getName())
                        .setDescription(ecomProduct.getDescription())
                        .addAllImage(ecomProduct.getImagesUrl())
                        .build();

        stripeProductID.update(params);
    }

    //ARCHIVE
    public void archiveStripeProduct(EcomProduct ecomProduct) throws StripeException {
        Product stripeProduct = Product.retrieve(ecomProduct.getStripeId());
        ProductUpdateParams params = ProductUpdateParams.builder().setActive(false).build();
        stripeProduct.update(params);
    }
}
