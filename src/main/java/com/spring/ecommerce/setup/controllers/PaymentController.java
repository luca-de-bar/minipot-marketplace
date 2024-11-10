package com.spring.ecommerce.setup.controllers;

import com.spring.ecommerce.setup.models.Item;
import com.spring.ecommerce.setup.services.ItemService;
import com.spring.ecommerce.setup.services.StripePriceService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/payment")
public class PaymentController {

    @Autowired
    private StripePriceService priceService;

    @Autowired
    private ItemService itemService;

    @PostMapping("/{id}")
    public String createCheckoutSession(@PathVariable("id") Long id) throws StripeException {

        //Find product to pay for
        Optional<Item> product = itemService.findById(id);
        String priceId =  priceService.getDefaultPrice(product.get());

        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setSuccessUrl("https://example.com/success")
                        .addLineItem(
                                SessionCreateParams.LineItem.builder()
                                        .setPrice(priceId)
                                        .setQuantity(1L)
                                        .build()
                        )
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .build();
        Session session = Session.create(params);
        return session.getUrl();
    }
}
