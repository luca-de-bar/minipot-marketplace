package com.spring.ecommerce.setup.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class EcomProductDTO {

    @NotNull(message = "Cannot be null")
    @NotEmpty(message = "Cannot be empty")
    private String name;

    @NotNull(message = "Cannot be null")
    @NotEmpty(message = "Cannot be empty")
    private String description;

    @NotNull
    private BigDecimal price;

    @NotNull(message = "Cannot be null")
    private int quantity;

    @NotNull(message = "Cannot be null")
    @NotEmpty(message = "Cannot be empty")
    private String photoUrl;
}
