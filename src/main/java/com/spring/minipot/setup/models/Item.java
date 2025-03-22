package com.spring.minipot.setup.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "product")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    @JsonIgnore
    private String stripeId;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String description;

    @NotNull
    @JsonIgnore
    private Boolean archived = false;

    @NotNull
    private int quantity;

    @NotNull
    private BigDecimal price;

    @NotNull
    @NotEmpty
    @ElementCollection
    @CollectionTable(name = "product_image", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image_url")
    private List<String> imagesUrl;

    @ManyToMany(mappedBy = "products")
    @JsonIgnore
    private List<Cart> carts;

    public Item(){

    }

    public @NotNull @NotEmpty String getStripeId() {
        return stripeId;
    }

    public void setStripeId(@NotNull @NotEmpty String stripeId) {
        this.stripeId = stripeId;
    }

    public @NotNull @NotEmpty String getName() {
        return name;
    }

    public void setName(@NotNull @NotEmpty String name) {
        this.name = name;
    }

    public @NotNull @NotEmpty String getDescription() {
        return description;
    }

    public void setDescription(@NotNull @NotEmpty String description) {
        this.description = description;
    }

    public @NotNull Boolean getArchived() {
        return archived;
    }

    public void setArchived(@NotNull Boolean archived) {
        this.archived = archived;
    }

    @NotNull
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(@NotNull int quantity) {
        this.quantity = quantity;
    }

    public @NotNull BigDecimal getPrice() {
        return price;
    }

    public void setPrice(@NotNull BigDecimal price) {
        this.price = price;
    }

    public @NotNull @NotEmpty List<String> getImagesUrl() {
        return imagesUrl;
    }

    public void setImagesUrl(@NotNull @NotEmpty List<String> imagesUrl) {
        this.imagesUrl = imagesUrl;
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }
}
