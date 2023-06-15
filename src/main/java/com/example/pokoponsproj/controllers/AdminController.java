package com.example.pokoponsproj.controllers;

import com.example.pokoponsproj.beans.Customer;
import com.example.pokoponsproj.beans.Seller;
import com.example.pokoponsproj.services.auth.Session;
import com.example.pokoponsproj.services.facades.AdminFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * This REST service exposes CRUD methods that related to 'Admin' (role in the system) through given API endpoints.
 * The service will generally return raw data back to the client, in JSON representations usually.
 * The service returns the data right in the HTTP response body.
 */
@RestController
@RequestMapping("admin")
public class AdminController extends ClientController {

    // provides http specific methods
    // To be able to access information about the HTTP request, we can declare a HttpServletRequest object
    // as a parameter for our endpoint. This allows us to see details such as the path, query parameters,
    // cookies, and headers.
    private HttpServletRequest req;

    private Map<String, Session> sessions;

    public AdminController() {
    }

    public AdminController(HttpServletRequest req, Map<String, Session> sessions) {
        this.req = req;
        this.sessions = sessions;
    }

    public AdminFacade getFacade(HttpServletRequest req) {
        String token = req.getHeader("Authorization").replace("Bearer ", "");

        Session session = sessions.get(token);
        session.setLastActivity(System.currentTimeMillis());

        return (AdminFacade) session.getFacade();
    }

    /**
     * adding a seller
     * @param seller seller
     * @return ResponseEntity
     */
    @PostMapping("/add_seller")
    public ResponseEntity<?> addSeller(@RequestBody Seller seller) {
        try {
            AdminFacade adminFacade = getFacade(req);
            return ResponseEntity.ok(adminFacade.addSeller(seller));
        } catch (SQLException | RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Seller is already exist.");
        }

    }

    /**
     * updating a seller
     * @param seller seller
     * @return ResponseEntity
     */
    @PutMapping("/update_seller")
    public ResponseEntity<?> updateSeller(@RequestBody Seller seller) {
        try {
            AdminFacade adminFacade = getFacade(req);
            return ResponseEntity.ok(adminFacade.updateSeller(seller));
        } catch (SQLException | RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    /**
     * deleting a seller
     * @param idSeller id of the seller
     * @return ResponseEntity
     */
    @DeleteMapping("/del_seller/{idSeller}")
    public ResponseEntity<?> delSeller(@PathVariable int idSeller) {
        try {
            AdminFacade adminFacade = getFacade(req);
            adminFacade.deleteSeller(idSeller);
            return ResponseEntity.ok("Seller is deleted.");
        } catch (SQLException | RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    /**
     * get all sellers
     * @return ResponseEntity
     */
    @GetMapping("/sellers")
    public ResponseEntity<?> getAllSellers() {
        try {
            AdminFacade adminFacade = getFacade(req);
            return ResponseEntity.ok(adminFacade.getAllSellers());
        } catch (SQLException | RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred with getting sellers.");
        }

    }

    // get one seller by his id
    @GetMapping("/one_seller/{idSeller}")
    public ResponseEntity<?> getOneSeller(@PathVariable int idSeller) {
        try {
            AdminFacade adminFacade = getFacade(req);
            return ResponseEntity.ok(adminFacade.getOneSeller(idSeller));
        } catch (SQLException | RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // add customer
    @PostMapping("/add_customer")
    public ResponseEntity<?> addCustomer(@RequestBody Customer customer) {
        try {
            AdminFacade adminFacade = getFacade(req);
            return ResponseEntity.ok(adminFacade.addCustomer(customer));
        } catch (SQLException | RuntimeException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Customer is already exist.");
        }

    }

    // update customer
    @PutMapping("/update_customer")
    public ResponseEntity<?> updateCustomer(@RequestBody Customer customer) {
        try {
            AdminFacade adminFacade = getFacade(req);
            return ResponseEntity.ok(adminFacade.updateCustomer(customer));
        } catch (SQLException | RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found.");
        }

    }

    // delete a customer by his id
    @DeleteMapping("/del_customer/{idCustomer}")
    public ResponseEntity<?> delCustomer(@PathVariable int idCustomer) {
        try {
            AdminFacade adminFacade = getFacade(req);
            adminFacade.deleteCustomer(idCustomer);
            return ResponseEntity.ok("Customer is deleted");
        } catch (SQLException | RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found.");
        }

    }

    // get all customers
    @GetMapping("/customers")
    public ResponseEntity<?> getAllCustomers() {
        try {
            AdminFacade adminFacade = getFacade(req);
            return ResponseEntity.ok(adminFacade.getAllCustomers());
        } catch (SQLException | RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error occurred for getting customers");
        }
    }

    // get one customer
    @GetMapping("/one_customer/{idCustomer}")
    public ResponseEntity<?> getOneCustomer(@PathVariable int idCustomer) {
        try {
            AdminFacade adminFacade = getFacade(req);
            return ResponseEntity.ok(adminFacade.getOneCustomer(idCustomer));
        } catch (SQLException | RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found.");
        }

    }

}
