package com.spring.ecommerce.setup.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public class ItemDTO {

    @NotEmpty
    @NotNull
    private String name;

    @NotEmpty
    @NotNull
    private String description;

    @NotNull
    private int quantity;

    @NotNull
    private BigDecimal price;

    @NotNull
    @NotEmpty
    private List<String> imagesUrl;

    //Constructor

    public ItemDTO() {
    }

    public @NotEmpty @NotNull String getName() {
        return name;
    }

    public void setName(@NotEmpty @NotNull String name) {
        this.name = name;
    }

    public @NotEmpty @NotNull String getDescription() {
        return description;
    }

    public void setDescription(@NotEmpty @NotNull String description) {
        this.description = description;
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
}
