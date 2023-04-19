package com.example.pokoponsproj.repositories;

import com.example.pokoponsproj.beans.Customer;
import com.example.pokoponsproj.beans.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository // @Component (spring) but in the DAO layer
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    // CRUD

    boolean existsByEmail(String email);

    Customer findByEmailAndPassword(String email, String password);


//    int isCustomerExists(String email, String password);
}
