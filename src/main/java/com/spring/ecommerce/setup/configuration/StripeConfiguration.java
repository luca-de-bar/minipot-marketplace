package com.spring.ecommerce.setup.configuration;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfiguration {

    @Value("${stripe.api.key}")
    private String apiKey;

    @PostConstruct
    public void init(){
        Stripe.apiKey = apiKey;
    }
}
