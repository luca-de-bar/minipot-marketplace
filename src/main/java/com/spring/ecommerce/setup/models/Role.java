package com.spring.ecommerce.setup.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Role {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "role")
    private List<User> users;

    //Base constructor
    public Role (Integer id, String name){
        this.id = id;
        this.name = name;
    }

    //Empty constructor
    public Role(){}

}
