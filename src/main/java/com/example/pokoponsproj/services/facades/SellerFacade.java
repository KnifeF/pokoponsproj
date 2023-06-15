package com.example.pokoponsproj.services.facades;

import com.example.pokoponsproj.beans.Coupon;
import com.example.pokoponsproj.beans.Seller;
import com.example.pokoponsproj.enums.Types;
import com.example.pokoponsproj.repositories.CouponRepository;
import com.example.pokoponsproj.repositories.CustomerRepository;
import com.example.pokoponsproj.repositories.SellerRepository;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * holding the business logic for the seller
 */
@Service
@Scope("prototype")
public class SellerFacade extends ClientFacade {

    private int sellerId;

    /**
     * constructor for seller facade
     * @param customerRepo component for customer in the DAO layer
     * @param sellerRepo component for seller in the DAO layer
     * @param couponRepo component for coupon in the DAO layer
     */
    public SellerFacade(CustomerRepository customerRepo, SellerRepository sellerRepo, CouponRepository couponRepo) {
        super(customerRepo, sellerRepo, couponRepo);
    }

    /**
     * login for a seller
     * @param email seller email
     * @param password seller password
     * @return seller's id for successful login, otherwise -1
     * @throws SQLException exception for database access error or other errors.
     */
    @Override
    public int login(String email, String password) throws SQLException {
        Seller currSeller = sellerRepo.findByEmailAndPassword(email, password);
        if (currSeller != null) {
            sellerId = currSeller.getIdSeller();
            return sellerId;
        }
        return -1;
    }

    // CRUD operations and BL

    /**
     * add coupon
     * @param coup coupon obj
     * @throws SQLException exception for database access error or other errors.
     * @throws RuntimeException exception that can be thrown during the normal operation of the Java Virtual Machine.
     */
    public void addCoupon(Coupon coup) throws SQLException, RuntimeException {
        ArrayList<Coupon> sellerCoupons = getSellerCoupons();
        // https://howtodoinjava.com/java8/stream-anymatch-example/
        if (sellerCoupons.stream()
                .anyMatch(coupon -> coupon.getPokemonName().equals(coup.getPokemonName()))) {
            throw new RuntimeException("this pokopon name already exist");
        }
        couponRepo.save(coup);
    }

    /**
     * update a coupon
     * @param coup coupon obj
     */
    public void updateCoupon(Coupon coup) throws RuntimeException{
        if (!couponRepo.existsById(coup.getIdPokopon()))
            throw new RuntimeException("pokopon not found.");
        couponRepo.save(coup);
    }

    /**
     * delete a coupon
     * @param idPokopon id of pokopon to delete
     */
    public void deleteCoupon(int idPokopon) throws RuntimeException{
        if (!couponRepo.existsById(idPokopon))
            throw new RuntimeException("pokopon not found.");
        couponRepo.deleteById(idPokopon);
    }

    /**
     * get one coupon by it's id
     * @param idPokopon id of a coupon to find
     * @return Coupon obj
     */
    public Coupon getOneCoupon(int idPokopon) {
        return couponRepo.findById(idPokopon).orElseThrow();
    }

    /**
     * get list of all coupons
     * @return List<Coupon>
     */
    public List<Coupon> getAllCoupons() {
        return couponRepo.findAll();
    }


    /**
     * get seller's coupons
     * @return ArrayList<Coupon>
     * @throws SQLException exception for database access error or other errors.
     * @throws RuntimeException exception that can be thrown during the normal operation of the Java Virtual Machine.
     */
    public ArrayList<Coupon> getSellerCoupons() throws SQLException, RuntimeException {
        Seller seller = sellerRepo.findById(sellerId).orElseThrow(() -> new RuntimeException("seller not exist"));
        List<Coupon> sellerCoupons = seller.getCoupons();
        if (sellerCoupons == null) throw new RuntimeException("seller without coupons");
        return (ArrayList<Coupon>) sellerCoupons;
    }

    /**
     * get seller's coupons by type
     * @param type pokopon type (to filter relevant coupons)
     * @return ArrayList<Coupon>
     * @throws SQLException exception for database access error or other errors.
     * @throws RuntimeException exception that can be thrown during the normal operation of the Java Virtual Machine.
     */
    public ArrayList<Coupon> getSellerCoupons(Types type) throws SQLException, RuntimeException {
        Seller seller = sellerRepo.findById(sellerId).orElseThrow(() -> new RuntimeException("seller not exist"));
        List<Coupon> sellerCoupons = seller.getCoupons();
        if (sellerCoupons == null) throw new RuntimeException("seller without coupons");


        ArrayList<Coupon> sameTypeCoupons = new ArrayList<>();
        for (Coupon coup : sellerCoupons) {
            if (coup.getIdType().equals(type)) {
                sameTypeCoupons.add(coup);
            }
        }
        return sameTypeCoupons;
    }

    /**
     * get seller's coupons by max price (coupons below that price)
     * @param maxPrice maximum price for pokopon (to filter relevant coupons)
     * @return ArrayList<Coupon>
     * @throws SQLException exception for database access error or other errors.
     * @throws RuntimeException exception that can be thrown during the normal operation of the Java Virtual Machine.
     */
    public ArrayList<Coupon> getSellerCoupons(double maxPrice) throws SQLException, RuntimeException {
        Seller seller = sellerRepo.findById(sellerId).orElseThrow(() -> new RuntimeException("seller not exist"));
        List<Coupon> sellerCoupons = seller.getCoupons();
        if (sellerCoupons == null) throw new RuntimeException("seller without coupons");

        ArrayList<Coupon> belowMaxCoupons = new ArrayList<>();
        for (Coupon coup : sellerCoupons) {
            if (coup.getPrice() <= maxPrice) {
                belowMaxCoupons.add(coup);
            }
        }
        return belowMaxCoupons;
    }

    /**
     * get seller's details
     * @return Seller obj
     * @throws SQLException exception for database access error or other errors.
     * @throws RuntimeException exception that can be thrown during the normal operation of the Java Virtual Machine.
     */
    public Seller getSellerDetails() throws SQLException, RuntimeException {
        return sellerRepo.findById(sellerId).orElseThrow(() -> new RuntimeException("seller not exist"));
    }
}
