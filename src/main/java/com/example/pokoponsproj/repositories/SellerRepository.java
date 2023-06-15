package com.example.pokoponsproj.repositories;

import com.example.pokoponsproj.beans.Coupon;
import com.example.pokoponsproj.beans.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

/**
 * DAO layer for seller
 */
@Repository // @Component (spring) but in the DAO layer
public interface SellerRepository extends JpaRepository<Seller, Integer> {
    // CRUD

    // native sql queries

    /**
     * exists by email
     * @param email email
     * @return true if seller found in db by his email, otherwise false
     */
    boolean existsByEmail(String email);

    /**
     * exists by name
     * @param name name
     * @return true if seller found in db by his name, otherwise false
     */
    boolean existsByName(String name);

    /**
     * exists by email and password
     * @param email email
     * @param password password
     * @return true if seller found in db by his email and password, otherwise false
     */
    Seller findByEmailAndPassword(String email, String password);

}
