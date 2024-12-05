package com.spring.ecommerce.setup.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Role {

    @Id
    private Integer id;

    @Column(nullable = false)
    @NotEmpty
    private String name;
}
