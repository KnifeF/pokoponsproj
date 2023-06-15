package com.example.pokoponsproj.services.facades;

import com.example.pokoponsproj.repositories.CouponRepository;
import com.example.pokoponsproj.repositories.CustomerRepository;
import com.example.pokoponsproj.repositories.SellerRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;


@Service
public abstract class ClientFacade {
    /**
     * holding the business logic for Admin
     */

    protected CustomerRepository customerRepo;
    protected SellerRepository sellerRepo;
    protected CouponRepository couponRepo;


    /**
     * constructor for client facade
     * @param customerRepo component for customer in the DAO layer
     * @param sellerRepo component for seller in the DAO layer
     * @param couponRepo component for coupon in the DAO layer
     */
    public ClientFacade(CustomerRepository customerRepo, SellerRepository sellerRepo, CouponRepository couponRepo) {
        this.customerRepo = customerRepo;
        this.sellerRepo = sellerRepo;
        this.couponRepo = couponRepo;
    }

    // add custom exception

    /**
     * login for the client
     * @param email
     * @param password
     * @return
     * @throws SQLException
     * @throws RuntimeException
     */
    public abstract int login(String email, String password) throws SQLException, RuntimeException;
}
