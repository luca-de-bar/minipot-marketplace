package com.spring.ecommerce.setup.controllers;

import com.spring.ecommerce.setup.models.EcomProduct;
import com.spring.ecommerce.setup.services.StripePriceService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/payment")
public class PaymentController {

    @Autowired
    private StripePriceService priceService;

    @PostMapping("/checkout")
    public String createCheckoutSession(EcomProduct ecomProduct) throws StripeException {

        String priceId =  priceService.getDefaultPrice(ecomProduct);

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
