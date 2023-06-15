package com.example.pokoponsproj.services.facades;

import com.example.pokoponsproj.beans.Coupon;
import com.example.pokoponsproj.beans.Customer;
import com.example.pokoponsproj.enums.Types;
import com.example.pokoponsproj.repositories.CouponRepository;
import com.example.pokoponsproj.repositories.CustomerRepository;
import com.example.pokoponsproj.repositories.SellerRepository;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * holding the business logic for Admin
 */
@Service
@Scope("prototype")
public class CustomerFacade extends ClientFacade {

    private int customerId;

    /**
     * constructor for customer facade
     *
     * @param customerRepo component for customer in the DAO layer
     * @param sellerRepo   component for seller in the DAO layer
     * @param couponRepo   component for coupon in the DAO layer
     */
    public CustomerFacade(CustomerRepository customerRepo, SellerRepository sellerRepo, CouponRepository couponRepo) {
        super(customerRepo, sellerRepo, couponRepo);
    }


    /**
     * login for customer
     *
     * @param email    user email
     * @param password user password
     * @return id of the customer for a successful login, otherwise -1
     * @throws SQLException exception for database access error or other errors.
     */
    @Override
    public int login(String email, String password) throws SQLException {
        Customer currCustomer = customerRepo.findByEmailAndPassword(email, password);
        if (currCustomer != null) {
            customerId = currCustomer.getIdCustomer();
            return customerId;
        }
        return -1;
    }

    // CRUD operations and BL

    /**
     * purchase coupon logic
     *
     * @param coup coupon obj
     * @throws SQLException     exception for database access error or other errors.
     * @throws RuntimeException exception that can be thrown during the normal operation of the Java Virtual Machine.
     */
    public void purchaseCoupon(Coupon coup) throws SQLException, RuntimeException {
        if (!customerRepo.existsById(customerId)) throw new RuntimeException("customer not exist");


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


    /**
     * get a list of customer's coupons
     *
     * @return ArrayList<Coupon>
     * @throws SQLException     exception for database access error or other errors.
     * @throws RuntimeException exception that can be thrown during the normal operation of the Java Virtual Machine.
     */
    public ArrayList<Coupon> getCustomerCoupons() throws SQLException, RuntimeException {
        Customer customer = customerRepo.findById(customerId).orElseThrow(() ->
                new RuntimeException("customer not found"));

        return (ArrayList<Coupon>) customer.getCoupons();
    }


    /**
     * get a filtered list of customer's coupons by given type
     *
     * @param type pokopon type
     * @return ArrayList<Coupon>
     * @throws SQLException     exception for database access error or other errors.
     * @throws RuntimeException exception that can be thrown during the normal operation of the Java Virtual Machine.
     */
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

    /**
     * get a filtered list of customer's coupons by max price
     *
     * @param maxPrice maximum price
     * @return ArrayList<Coupon>
     * @throws SQLException     exception for database access error or other errors.
     * @throws RuntimeException exception that can be thrown during the normal operation of the Java Virtual Machine.
     */
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

    // arraylist or list
    /**
     * get a list of all coupons
     * @return ArrayList<Coupon>
     */
    public ArrayList<Coupon> getAllCoupons() {
        return (ArrayList<Coupon>) couponRepo.findAll();
    }

    /**
     * get customer details
     *
     * @return Customer obj
     * @throws SQLException
     * @throws RuntimeException
     */
    public Customer getCustomerDetails() throws SQLException, RuntimeException {
        return customerRepo.findById(customerId).orElseThrow(() -> new RuntimeException("customer not found"));
    }

    /**
     * get customer id
     *
     * @return id of the customer
     */
    public int getCustomerId() {
        return customerId;
    }


}
