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

@Repository // @Component (spring) but in the DAO layer
@Transactional
public interface CouponRepository extends JpaRepository<Coupon, Integer> {
    // CRUD

    @Modifying
    @Query(value = "insert into customers_vs_pokopons (id_customer, id_pokopon) values(:idCustomer, :idPokopon)", nativeQuery = true)
    void addCouponPurchase(@Param("idCustomer") int idCustomer, @Param("idPokopon") int idPokopon);

    @Modifying
    @Query(value = "delete from customers_vs_pokopons where id_pokopon=:idPokopon", nativeQuery = true)
    void deleteCouponPurchaseByCouponId(@Param("idPokopon") int idPokopon);

}
