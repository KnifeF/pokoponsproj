package com.example.pokoponsproj.repositories;

import com.example.pokoponsproj.beans.Customer;
import com.example.pokoponsproj.beans.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

/**
 * DAO layer for customer
 */
@Repository // @Component (spring) but in the DAO layer
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    // CRUD

    // native sql queries

    /**
     * exists by email
     * @param email customer email
     * @return true if customer found in db by his email, otherwise false
     */
    boolean existsByEmail(String email);

    /**
     * exists by email and password
     * @param email customer email
     * @param password customer password
     * @return Customer obj if customer found in db by his email and password, otherwise false
     */
    Customer findByEmailAndPassword(String email, String password);

}
