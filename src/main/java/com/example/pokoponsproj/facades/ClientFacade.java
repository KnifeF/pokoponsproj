package com.example.pokoponsproj.facades;

import com.example.pokoponsproj.repositories.CouponRepository;
import com.example.pokoponsproj.repositories.CustomerRepository;
import com.example.pokoponsproj.repositories.SellerRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public abstract class ClientFacade {
    protected CustomerRepository customerRepo;
    protected SellerRepository sellerRepo;
    protected CouponRepository couponRepo;


    public ClientFacade(CustomerRepository customerRepo, SellerRepository sellerRepo, CouponRepository couponRepo) {
        this.customerRepo = customerRepo;
        this.sellerRepo = sellerRepo;
        this.couponRepo = couponRepo;
    }

    // add custom exception
    public abstract int login(String email, String password) throws SQLException, RuntimeException;
}
