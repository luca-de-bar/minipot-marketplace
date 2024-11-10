package com.spring.ecommerce.setup.models;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "cart_product",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<EcomProduct> products;

    //Class Constructor
    public Cart (){
        products = new ArrayList<>();
    }

    //Add To Cart
    public void addToCart(EcomProduct product){
        products.add(product);
    }

    //Remove from cart
    public void remove (EcomProduct product){
        products.remove(product);
    }

    //Remove all
    public void removeAll(){
        for (EcomProduct product : products){
            products.remove(product);
        }
    }
}
