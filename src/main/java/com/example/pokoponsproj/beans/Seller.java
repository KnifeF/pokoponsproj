package com.example.pokoponsproj.beans;

import com.sun.istack.NotNull;
import javax.persistence.*;
import java.util.List;

@Entity // for Hibernate mapping
@Table(name = "sellers")
public class Seller extends Client {
    /**
     * Seller obj that represents a table 'sellers' in the db, and each instance corresponds to a row in that table
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idSeller;

    @NotNull
    private String name;

    @NotNull
    private String email;
    @NotNull
    private String password;

    @OneToMany(mappedBy = "seller", fetch = FetchType.EAGER)
    private List<Coupon> coupons;

    // constructors
    /***** HIBERNATE *****/
    public Seller() {}

    public Seller(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // relevant getters and setters
    public int getIdSeller() {
        return idSeller;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }

    /**
     * toString for Seller obj
     * @return seller relevant details (String)
     */
    @Override
    public String toString() {
        return "Seller{" +
                "idSeller=" + idSeller +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", coupons=" + coupons +
                '}';
    }
}
