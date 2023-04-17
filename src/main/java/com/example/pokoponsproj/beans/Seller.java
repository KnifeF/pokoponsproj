package com.example.pokoponsproj.beans;

import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "sellers")
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idSeller;

    private String name;

    private String email;

    private String password;

    @OneToMany(mappedBy = "sellers", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Coupon> coupons;

}
