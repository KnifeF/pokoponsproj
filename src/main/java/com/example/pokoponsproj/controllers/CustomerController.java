package com.example.pokoponsproj.controllers;

import com.example.pokoponsproj.beans.Coupon;
import com.example.pokoponsproj.enums.Types;
import com.example.pokoponsproj.services.auth.Session;
import com.example.pokoponsproj.services.facades.AdminFacade;
import com.example.pokoponsproj.services.facades.CustomerFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Map;

/**
 * This REST service exposes CRUD methods that related to 'Customer' (role in the system) through given API endpoints.
 * The service will generally return raw data back to the client, in JSON representations usually.
 * The service returns the data right in the HTTP response body.
 */
@RestController
@RequestMapping("customer")
public class CustomerController extends ClientController {

    private HttpServletRequest req;

    private Map<String, Session> sessions;

    public CustomerController() {
    }

    public CustomerController(HttpServletRequest req, Map<String, Session> sessions) {
        this.req = req;
        this.sessions = sessions;
    }

    public CustomerFacade getFacade(HttpServletRequest req) {
        String token = req.getHeader("Authorization").replace("Bearer ", "");

        Session session = sessions.get(token);
        session.setLastActivity(System.currentTimeMillis());

        return (CustomerFacade) session.getFacade();
    }

    // need to add auth through request
    // need to add relevant custom exceptions


//    public boolean login(String email, String password) {
//        return true;
//    }

    // CRUD operations exposed through endpoints (related to 'customer')


    /**
     * purchase pokopon - CREATE operation
     * @param coupon pokopon
     * @return ResponseEntity
     */
    @PostMapping("/purchase_pokopon")
    public ResponseEntity<?> purchaseCoupon(@RequestBody Coupon coupon) {
        try {
            CustomerFacade customerFacade = getFacade(req);
            customerFacade.purchaseCoupon(coupon);
            return ResponseEntity.ok("pokopon is purchased.");
        } catch (SQLException | RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("error occurred when trying to purchase a coupon");
        }
    }

    /**
     * get customer coupons
     * @return ResponseEntity
     */
    @GetMapping("/my_pokopons")
    public ResponseEntity<?> getCustomerCoupons() {
        try {
            CustomerFacade customerFacade = getFacade(req);
            return ResponseEntity.ok(customerFacade.getCustomerCoupons());
        } catch (SQLException | RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred on attempt to get coupons");
        }

    }


    /**
     * get customer coupons by type
     * @param type type
     * @return ResponseEntity
     */
    @GetMapping("/pokopons_type/{type}")
    public ResponseEntity<?> getCustomerCoupons(@PathVariable Types type) {
        try {
            CustomerFacade customerFacade = getFacade(req);
            return ResponseEntity.ok(customerFacade.getCustomerCoupons(type));
        } catch (SQLException | RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred on attempt to get coupons");
        }
    }

    /**
     * get customer coupons by max price
     * @param maxPrice max price
     * @return ResponseEntity
     */
    @GetMapping("/pokopons_maxp/{maxPrice}")
    public ResponseEntity<?> getCustomerCoupons(@PathVariable double maxPrice) {
        try {
            CustomerFacade customerFacade = getFacade(req);
            return ResponseEntity.ok(customerFacade.getCustomerCoupons(maxPrice));
        } catch (SQLException | RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred on attempt to get coupons");
        }
    }

    /**
     * get all coupons
     * @return ResponseEntity
     */
    @GetMapping("/pokopons")
    public ResponseEntity<?> getAllCoupons() {
        try {
            CustomerFacade customerFacade = getFacade(req);
            return ResponseEntity.ok(customerFacade.getAllCoupons());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred on attempt to get coupons");
        }
    }

    /**
     * get customer details
     * @return ResponseEntity
     */
    @GetMapping("/details")
    public ResponseEntity<?> getCustomerDetails() {
        try {
            CustomerFacade customerFacade = getFacade(req);
            return ResponseEntity.ok(customerFacade.getCustomerDetails());
        } catch (SQLException | RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred on attempt to get coupons");
        }
    }
}
