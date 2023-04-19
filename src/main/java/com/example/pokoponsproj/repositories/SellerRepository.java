package com.example.pokoponsproj.repositories;

import com.example.pokoponsproj.beans.Coupon;
import com.example.pokoponsproj.beans.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository // @Component (spring) but in the DAO layer
public interface SellerRepository extends JpaRepository<Seller, Integer> {
    // CRUD
    boolean existsByEmail(String email);
    boolean existsByName(String name);
    Seller findByEmailAndPassword(String email, String password);
//    int isSellerExists(String email, String password);

}
