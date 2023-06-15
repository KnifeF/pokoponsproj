package com.example.pokoponsproj.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.pokoponsproj.beans.Admin;
import com.example.pokoponsproj.beans.Client;
import com.example.pokoponsproj.beans.Customer;
import com.example.pokoponsproj.beans.Seller;
import com.example.pokoponsproj.services.auth.Credentials;
import com.example.pokoponsproj.services.auth.LoginManager.LoginManager;
import com.example.pokoponsproj.services.auth.Session;
import com.example.pokoponsproj.services.facades.AdminFacade;
import com.example.pokoponsproj.services.facades.ClientFacade;
import com.example.pokoponsproj.services.facades.CustomerFacade;
import com.example.pokoponsproj.services.facades.SellerFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

// rest controller for auth
@RestController
@RequestMapping("auth")
public class AuthController {
//    private AdminFacade adminFacade = new AdminFacade();

    private final LoginManager loginManager;

    private Map<String, Session> sessions;

    public AuthController(LoginManager loginManager) {
        this.loginManager = loginManager;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Credentials cred) {
        try {
            ClientFacade clientFacade = loginManager.login(cred.getEmail(), cred.getPassword(), cred.getClientType());

            String token = "";
            switch (cred.getClientType()) {
                case administrator:
                    token = getJWTToken(new Admin());
                    break;
                case seller:
                    token = getJWTToken(((SellerFacade)clientFacade).getSellerDetails());
                    break;
                case customer:
                    token = getJWTToken(((CustomerFacade)clientFacade).getCustomerDetails());
                    break;
            }
            sessions.put(token, new Session(token, clientFacade));
            return ResponseEntity.ok(token);

        } catch (SQLException | RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed login.");
        }
    }

    // https://www.javadoc.io/doc/com.auth0/java-jwt/3.1.0/com/auth0/jwt/algorithms/Algorithm.html#HMAC256-java.lang.String-

    private String getJWTToken(Client client) {
        if (client instanceof Seller) {
            Seller currSeller = (Seller) client;
            return JWT.create()
                    .withClaim("idSeller", currSeller.getIdSeller())
                    .withClaim("name", currSeller.getName())
                    .withClaim("email", currSeller.getEmail())
                    .withClaim("type", "seller")
                    .withIssuer("Pokopons")
                    .withIssuedAt(new Date())
                    .sign(Algorithm.HMAC256("watermelon"));
        }
        if (client instanceof Customer) {
            Customer currCustomer = (Customer) client;
            return JWT.create()
                    .withClaim("idCustomer", currCustomer.getIdCustomer())
                    .withClaim("firstName", currCustomer.getFirstName())
                    .withClaim("lastName", currCustomer.getLastName())
                    .withClaim("email", currCustomer.getEmail())
                    .withClaim("type", "customer")
                    .withIssuer("Pokopons")
                    .withIssuedAt(new Date())
                    .sign(Algorithm.HMAC256("watermelon"));

        }
        return JWT.create()
                .withClaim("email", "admin@admin.com")
                .withClaim("type", "admin")
                .withIssuer("Pokopons")
                .withIssuedAt(new Date())
                .sign(Algorithm.HMAC256("watermelon"));
    }
}
