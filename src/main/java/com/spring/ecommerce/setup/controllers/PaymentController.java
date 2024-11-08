package com.spring.ecommerce.setup.controllers;

import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.model.checkout.Session;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/payment")
public class PaymentController {


    @PostMapping("/checkout")
    public Session createCheckoutSession() throws StripeException {
        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setSuccessUrl("https://example.com/success")
                        .addLineItem(
                                SessionCreateParams.LineItem.builder()
                                        .setPrice("price_id")
                                        .setQuantity(2L)
                                        .build()
                        )
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .build();
        return Session.create(params);
    }
}
