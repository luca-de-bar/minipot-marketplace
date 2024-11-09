package com.spring.ecommerce.setup.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "products")
public class EcomProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String description;

    @NotNull
    private Boolean active = true;

    @NotNull
    private int quantity;

    @NotNull
    private BigDecimal price;

    @NotNull
    @NotEmpty
    @ElementCollection
    @Column(name = "image_url")
    private List<String> imagesUrl;

    public EcomProduct (){

    }
}
