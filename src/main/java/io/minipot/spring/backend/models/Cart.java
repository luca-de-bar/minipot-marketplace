package io.minipot.spring.backend.models;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
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
    private List<Item> products;

    //Class Constructor
    public Cart (){
        products = new ArrayList<>();
    }

    //Add To Cart
    public void addToCart(Item product){
        products.add(product);
    }

    //Remove from cart
    public void remove (Item product){
        products.remove(product);
    }

    //Remove all
    public void removeAll(){
        for (Item product : products){
            products.remove(product);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Item> getProducts() {
        return products;
    }

    public void setProducts(List<Item> products) {
        this.products = products;
    }
}
