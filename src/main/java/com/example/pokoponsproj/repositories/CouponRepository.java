package com.example.pokoponsproj.repositories;

import com.example.pokoponsproj.beans.Coupon;
import com.example.pokoponsproj.beans.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

/**
 * DAO layer for coupon
 */
@Repository // @Component (spring) but in the DAO layer
@Transactional
public interface CouponRepository extends JpaRepository<Coupon, Integer> {
    // CRUD

    // native sql queries
    /**
     * adding a coupon purchase to db
     * @param idCustomer id of the customer
     * @param idPokopon id of the coupon
     */
    @Modifying
    @Query(value = "insert into customers_vs_pokopons (id_customer, id_pokopon) values(:idCustomer, :idPokopon)", nativeQuery = true)
    void addCouponPurchase(@Param("idCustomer") int idCustomer, @Param("idPokopon") int idPokopon);

    /**
     * deleting a coupon purchase from db by given id
     * @param idPokopon id of the coupon
     */
    @Modifying
    @Query(value = "delete from customers_vs_pokopons where id_pokopon=:idPokopon", nativeQuery = true)
    void deleteCouponPurchaseByCouponId(@Param("idPokopon") int idPokopon);

}
