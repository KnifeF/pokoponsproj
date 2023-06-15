package com.example.pokoponsproj.services.facades;

import com.example.pokoponsproj.beans.Coupon;
import com.example.pokoponsproj.beans.Customer;
import com.example.pokoponsproj.beans.Seller;
import com.example.pokoponsproj.repositories.CouponRepository;
import com.example.pokoponsproj.repositories.CustomerRepository;
import com.example.pokoponsproj.repositories.SellerRepository;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * holding the business logic for Admin
 */

@Service
@Scope("prototype")
public class AdminFacade extends ClientFacade {

    /**
     * constructor for admin facade
     * @param customerRepo component for customer in the DAO layer
     * @param sellerRepo component for seller in the DAO layer
     * @param couponRepo component for coupon in the DAO layer
     */
    public AdminFacade(CustomerRepository customerRepo, SellerRepository sellerRepo, CouponRepository couponRepo) {
        super(customerRepo, sellerRepo, couponRepo);
    }

    // add custom exception

    /**
     * login for admin
     * @param email login email
     * @param password login password
     * @return number that represent a successful login
     * @throws SQLException exception for database access error or other errors.
     * @throws RuntimeException exception that can be thrown during the normal operation of the Java Virtual Machine.
     */
    @Override
    public int login(String email, String password) throws SQLException, RuntimeException {
        if (email.equals("admin@admin.com") && password.equals("admin")) {
            System.out.println("Login Success!");
            return 1;
        }
//        throw new CustomException("login failed");
        throw new RuntimeException("failed login");
    }

    // CRUD operations and BL

    /**
     * add a seller
     * @param seller seller obj
     * @throws SQLException exception for database access error or other errors.
     * @throws RuntimeException exception that can be thrown during the normal operation of the Java Virtual Machine.
     */
    public Seller addSeller(Seller seller) throws SQLException, RuntimeException {
        //check if seller exist
        if (sellerRepo.existsByEmail(seller.getEmail()) || sellerRepo.existsByName(seller.getName())) {
            throw new RuntimeException("seller is already exist");
        }
        sellerRepo.save(seller);

        return seller;
    }

    /**
     * update a seller
     * @param seller seller obj
     * @throws SQLException exception for database access error or other errors.
     * @throws RuntimeException exception that can be thrown during the normal operation of the Java Virtual Machine.
     */
    public Seller updateSeller(Seller seller) throws SQLException, RuntimeException {
        Seller oldSeller = sellerRepo.findById(seller.getIdSeller()).orElseThrow(() ->
                new RuntimeException("seller not found, you need existing seller in order to update"));
        if (oldSeller.getName().equals(seller.getName())) {
            sellerRepo.save(seller);
            return seller;
        } else throw new RuntimeException("Replacing a seller name is not allowed");
    }

    /**
     * delete a seller
     * @param idSeller id of the seller to delete
     * @throws SQLException exception for database access error or other errors.
     * @throws RuntimeException exception that can be thrown during the normal operation of the Java Virtual Machine.
     */
    public void deleteSeller(int idSeller) throws SQLException, RuntimeException {
        Seller currSeller = sellerRepo.findById(idSeller).orElseThrow(() -> new RuntimeException("seller not found"));

        List<Coupon> sellerCoupons = currSeller.getCoupons();

        if(!sellerCoupons.isEmpty()) {
            // deleting purchase history of this seller's coupons
            sellerCoupons.stream().forEach(coupon -> couponRepo.deleteCouponPurchaseByCouponId(coupon.getIdPokopon()));
        }

        // update seller to be without coupons
        currSeller.setCoupons(null);
        sellerRepo.save(currSeller);

        // delete seller
        sellerRepo.deleteById(idSeller);
    }

    /**
     * get list of all sellers
     * @return ArrayList<Seller>
     * @throws SQLException exception for database access error or other errors.
     */
    public ArrayList<Seller> getAllSellers() throws SQLException {
        List<Seller> sellerList = sellerRepo.findAll();
        return (ArrayList<Seller>) sellerList;
    }

    /**
     * get one seller by his id
     * @param sellerId id of the seller to find
     * @return Seller obj
     * @throws SQLException exception for database access error or other errors.
     * @throws RuntimeException exception that can be thrown during the normal operation of the Java Virtual Machine.
     */
    public Seller getOneSeller(int sellerId) throws SQLException, RuntimeException {
        return sellerRepo.findById(sellerId).orElseThrow(() -> new RuntimeException("seller not found"));
    }

    /**
     * add customer
     * @param customer customer obj
     * @throws SQLException exception for database access error or other errors.
     * @throws RuntimeException exception that can be thrown during the normal operation of the Java Virtual Machine.
     */
    public Customer addCustomer(Customer customer) throws SQLException, RuntimeException {
        //check if customer exist
        if (customerRepo.existsByEmail(customer.getEmail())) {
            throw new RuntimeException("customer is already exist");
        }
        customerRepo.save(customer);
        return customer;
    }

    /**
     * update a customer
     * @param customer customer obj
     * @throws SQLException exception for database access error or other errors.
     * @throws RuntimeException exception that can be thrown during the normal operation of the Java Virtual Machine.
     */
    public Customer updateCustomer(Customer customer) throws SQLException, RuntimeException {
        if (customerRepo.existsById(customer.getIdCustomer())) {
            customerRepo.save(customer);
            return customer;
        } else throw new RuntimeException("customer not found, you need existing customer in order to update");
    }

    /**
     * delete a customer
     * @param idCustomer id of a customer
     * @throws SQLException exception for database access error or other errors.
     * @throws RuntimeException exception that can be thrown during the normal operation of the Java Virtual Machine.
     */
    public void deleteCustomer(int idCustomer) throws SQLException, RuntimeException {

        // deleting related coupons for deleted customer, and update customer record
        Customer currCustomer = customerRepo.findById(idCustomer).orElseThrow(() -> new RuntimeException("customer not found"));

        List<Coupon> customerCoupons = currCustomer.getCoupons();
        if(!customerCoupons.isEmpty()) {
            // deleting purchase history of this customer coupons
            customerCoupons.stream().forEach(coupon -> couponRepo.deleteCouponPurchaseByCouponId(coupon.getIdPokopon()));
        }

        // update customer to be without coupons
        currCustomer.setCoupons(null);
        customerRepo.save(currCustomer);

        // delete customer
        customerRepo.deleteById(idCustomer);

    }

    /**
     * get list of all customers
     * @return ArrayList<Customer>
     * @throws SQLException exception for database access error or other errors.
     */
    public ArrayList<Customer> getAllCustomers() throws SQLException {
        List<Customer> customerList = customerRepo.findAll();
        return (ArrayList<Customer>) customerList;
    }

    /**
     * get one customer
     * @param idCustomer id of the customer to find
     * @return Customer obj
     * @throws SQLException exception for database access error or other errors.
     * @throws RuntimeException exception that can be thrown during the normal operation of the Java Virtual Machine.
     */
    public Customer getOneCustomer(int idCustomer) throws SQLException, RuntimeException {
        return customerRepo.findById(idCustomer).orElseThrow(() -> new RuntimeException("customer not found"));
    }


}
