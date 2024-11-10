package com.spring.ecommerce.setup.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class EcomProductDTO {

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
}
