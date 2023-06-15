package com.example.pokoponsproj.controllers;

import com.example.pokoponsproj.beans.Coupon;
import com.example.pokoponsproj.enums.Types;
import com.example.pokoponsproj.services.auth.Session;
import com.example.pokoponsproj.services.facades.AdminFacade;
import com.example.pokoponsproj.services.facades.CustomerFacade;
import com.example.pokoponsproj.services.facades.SellerFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Map;

/**
 * This REST service exposes CRUD methods that related to 'Seller' (role in the system) through given API endpoints.
 * The service will generally return raw data back to the client, in JSON representations usually.
 * The service returns the data right in the HTTP response body.
 */
@RestController
@RequestMapping("seller")
public class SellerController {

    private HttpServletRequest req;

    private Map<String, Session> sessions;
    public SellerController() {
    }

    public SellerController(HttpServletRequest req, Map<String, Session> sessions) {
        this.req = req;
        this.sessions = sessions;
    }

    public SellerFacade getFacade(HttpServletRequest req) {
        String token = req.getHeader("Authorization").replace("Bearer ", "");

        Session session = sessions.get(token);
        session.setLastActivity(System.currentTimeMillis());

        return (SellerFacade) session.getFacade();
    }


    //    public boolean login(String email, String password) {
//        return true;
//    }

    // need to add auth through request
    // need to add relevant custom exceptions


    // CRUD operations exposed through endpoints (related to 'seller')
    /**
     * add coupon - CREATE operation
     * @param coupon pokopon
     * @return ResponseEntity
     */
    @PostMapping("/add_pokopon")
    public ResponseEntity<?> addCoupon(@RequestBody Coupon coupon) {
        try {
            SellerFacade sellerFacade = getFacade(req);
            sellerFacade.addCoupon(coupon);
            return ResponseEntity.ok("pokopon is added.");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * update coupon - UPDATE operation
     * @param coupon Coupon obj
     * @return ResponseEntity
     */
    @PutMapping("/update_pokopon")
    public ResponseEntity<?> updateCoupon(@RequestBody Coupon coupon) {
        try {
            SellerFacade sellerFacade = getFacade(req);
            sellerFacade.updateCoupon(coupon);
            return ResponseEntity.ok("pokopon is updated.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error updating pokopon.");
        }
    }

    /**
     * delete coupon - DELETE operation
     * @param couponID id of pokopon
     * @return ResponseEntity
     */
    @DeleteMapping("/del_pokopon/{couponID}")
    public ResponseEntity<?> deleteCoupon(@PathVariable int couponID) {
        try {
            SellerFacade sellerFacade = getFacade(req);
            sellerFacade.deleteCoupon(couponID);
            return ResponseEntity.ok("pokopon is deleted.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * get one coupon - READ operation
     * @param idPokopon id of pokopon
     * @return ResponseEntity
     */
    @GetMapping("/one_pokopon/{idPokopon}")
    public ResponseEntity<?> getOneCoupon(@PathVariable int idPokopon) {
        try {
            SellerFacade sellerFacade = getFacade(req);
            return ResponseEntity.ok(sellerFacade.getOneCoupon(idPokopon));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pokopon not found.");
        }

    }

    /**
     * get seller's coupons - READ operation
     * @return ResponseEntity
     */
    @GetMapping("/pokopons")
    public ResponseEntity<?> getSellerCoupons() {
        try {
            SellerFacade sellerFacade = getFacade(req);
            return ResponseEntity.ok(sellerFacade.getSellerCoupons());
        } catch (RuntimeException | SQLException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred on attempt to get coupons");
        }
    }

    /**
     * get seller's coupons filtered by pokopon type - READ operation
     * @param type pokopon type (enum)
     * @return ResponseEntity
     */
    @GetMapping("/pokopons_type/{type}")
    public ResponseEntity<?> getSellerCoupons(@PathVariable Types type) {
        try {
            SellerFacade sellerFacade = getFacade(req);
            return ResponseEntity.ok(sellerFacade.getSellerCoupons(type));
        } catch (RuntimeException | SQLException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred on attempt to get coupons");
        }
    }


    /**
     * get seller's coupons that are below max price - READ operation
     * @param maxPrice max price
     * @return ResponseEntity
     */
    @GetMapping("/pokopons_maxp/{maxPrice}")
    public ResponseEntity<?> getSellerCoupons(@PathVariable double maxPrice) {
        try {
            SellerFacade sellerFacade = getFacade(req);
            return ResponseEntity.ok(sellerFacade.getSellerCoupons(maxPrice));
        } catch (RuntimeException | SQLException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred on attempt to get coupons");
        }
    }

    /**
     * get seller details - READ operation
     * @return ResponseEntity
     */
    @GetMapping("/details")
    public ResponseEntity<?> getSellerDetails() {
        try {
            SellerFacade sellerFacade = getFacade(req);
            return ResponseEntity.ok(sellerFacade.getSellerDetails());
        } catch (RuntimeException | SQLException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred on attempt to get seller details");
        }
    }
}
