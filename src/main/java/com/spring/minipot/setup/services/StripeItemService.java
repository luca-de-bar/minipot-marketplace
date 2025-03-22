package com.spring.minipot.setup.services;

import com.spring.minipot.setup.models.Item;
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
public class StripeItemService {

    @Autowired
    private StripePriceService priceService;


    //CREATE Stripe Product
    public void createStripeProduct(Item product) throws StripeException {
        //Convert price from BigDecimal to Long for Stripe API
        Long priceInCents = product.getPrice()
                .multiply(new BigDecimal("100"))
                .longValue();
        ProductCreateParams params =
                ProductCreateParams.builder()
                        .setName(product.getName())
                        .setDescription(product.getDescription())
                        .addAllImage(product.getImagesUrl())
                        .setDefaultPriceData(
                                ProductCreateParams.DefaultPriceData.builder()
                                        .setUnitAmount(priceInCents) //I used here converted price
                                        .setCurrency("eur")
                                        .build()
                        )
                        .build();
        Product stripeProduct = Product.create(params);
        product.setStripeId(stripeProduct.getId());
    }




    //EDIT
    public void updateStripeProduct(Item product) throws StripeException {
        Product stripeProduct = Product.retrieve(product.getStripeId());

        //If product archived, throw Exception
        if(product.getArchived()){
            throw new UnsupportedOperationException("Cannot edit an archived product");
        }

        for (Price price : priceService.getAllPrices(product).getData()) {
            //Both converted in Long for easy compare
            Long oldPrice = price.getUnitAmountDecimal().longValue();
            Long newPrice = product.getPrice().multiply(new BigDecimal("100")).longValue();

            //If prices are different, create new one and disable old
            if(!oldPrice.equals(newPrice)){
                String newPriceId = priceService.createStripePrice(product);
                ProductUpdateParams params =
                        ProductUpdateParams.builder()
                                .setDefaultPrice(newPriceId)
                                .build();
                stripeProduct.update(params);
                PriceUpdateParams updateParams = PriceUpdateParams.builder()
                        .setActive(false)
                        .build();
                price.update(updateParams);
            }
            break;
        }
        //Update Product params
        ProductUpdateParams params =
                ProductUpdateParams.builder()
                        .setName(product.getName())
                        .setDescription(product.getDescription())
                        .addAllImage(product.getImagesUrl())
                        .build();
        stripeProduct.update(params);
    }


    //ARCHIVE
    public void archiveStripeProduct(Item product) throws StripeException {
        Product stripeProduct = Product.retrieve(product.getStripeId());

        ProductUpdateParams params = ProductUpdateParams.builder().setActive(false).build();
        stripeProduct.update(params);
    }


    //UN-ARCHIVE?
    public void unarchiveStripeProduct(Item product) throws StripeException {
        Product stripeProduct = Product.retrieve(product.getStripeId());

        ProductUpdateParams params = ProductUpdateParams.builder().setActive(true).build();
        stripeProduct.update(params);
    }
}
