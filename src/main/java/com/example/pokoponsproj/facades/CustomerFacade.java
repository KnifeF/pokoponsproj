package com.example.pokoponsproj.facades;

import com.example.pokoponsproj.beans.Coupon;
import com.example.pokoponsproj.beans.Customer;
import com.example.pokoponsproj.beans.Types;
import com.example.pokoponsproj.repositories.CouponRepository;
import com.example.pokoponsproj.repositories.CustomerRepository;
import com.example.pokoponsproj.repositories.SellerRepository;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

@Service
@Scope("Prototype")
public class CustomerFacade extends ClientFacade {

    private int customerId;

    public CustomerFacade(CustomerRepository customerRepo, SellerRepository sellerRepo, CouponRepository couponRepo) {
        super(customerRepo, sellerRepo, couponRepo);
    }


    @Override
    public int login(String email, String password) throws SQLException {
        Customer currCustomer = customerRepo.findByEmailAndPassword(email, password);
        if (currCustomer != null) {
            customerId = currCustomer.getIdCustomer();
            return customerId;
        }
        return -1;
    }


    public void purchaseCoupon(Coupon coup) throws SQLException, RuntimeException {
        if (!customerRepo.existsById(customerId)) throw new RuntimeException("customer not exist");

//        Customer currCustomer = customerRepo.findById(customerId)
//                .orElseThrow(() -> new RuntimeException("customer not exist"));

        if (getCustomerCoupons().stream().anyMatch(coupon -> coupon.getIdPokopon() == coup.getIdPokopon()))
            throw new RuntimeException("more than one purchase of coupon is not allowed");

        int amount = coup.getAmount();
        if (amount == 0)
            throw new RuntimeException("Out of stock");
        if (coup.getEndDate().before(new Date()))
            throw new RuntimeException("Expired coupon");

        // update amount of this coupons
        coup.setAmount(amount - 1);
        couponRepo.save(coup);


        //purchase
        couponRepo.addCouponPurchase(customerId, coup.getIdPokopon());
    }


    public ArrayList<Coupon> getCustomerCoupons() throws SQLException, RuntimeException {
        Customer customer = customerRepo.findById(customerId).orElseThrow(() ->
                new RuntimeException("customer not found"));

        return (ArrayList<Coupon>) customer.getCoupons();
    }


    public ArrayList<Coupon> getCustomerCoupons(Types type) throws SQLException, RuntimeException {

        ArrayList<Coupon> coupons = getCustomerCoupons();
        ArrayList<Coupon> sameTypeCoupons = new ArrayList<>();

        if (coupons.size() > 0) {
            for (Coupon coupon : coupons) {
                if (coupon.getIdType().equals(type)) {
                    sameTypeCoupons.add(coupon);
                }
            }
        }

        return sameTypeCoupons;
    }

    public ArrayList<Coupon> getCustomerCoupons(double maxPrice) throws SQLException, RuntimeException {

        ArrayList<Coupon> coupons = getCustomerCoupons();
        ArrayList<Coupon> belowMaxCoupons = new ArrayList<>();

        if (coupons.size() > 0) {
            for (Coupon coupon : coupons) {
                if (coupon.getPrice() <= maxPrice) {
                    belowMaxCoupons.add(coupon);
                }
            }
        }

        return belowMaxCoupons;
    }

    public Customer getCustomerDetails() throws SQLException, RuntimeException {
        return customerRepo.findById(customerId).orElseThrow(() -> new RuntimeException("customer not found"));
    }

    public int getCustomerId() {
        return customerId;
    }


}
