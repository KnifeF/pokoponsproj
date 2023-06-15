package com.example.pokoponsproj.beans;

import com.sun.istack.NotNull;
import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity // for Hibernate mapping
@Table(name = "customers")
public class Customer extends Client {
    /**
     * Customer obj that represents a table 'customers' in the db, and each instance corresponds to a row in that table
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCustomer;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String email;
    @NotNull
    private String password;

    @ManyToMany(mappedBy = "customers", fetch = FetchType.EAGER)
    private List<Coupon> coupons;


    // constructors for customer obj
    /***** HIBERNATE *****/
    public Customer() {
    }

    public Customer(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    /**
     * get id of the customer
     * @return the id
     */
    public int getIdCustomer() {
        return idCustomer;
    }

    /**
     * get first name of the customer
     * @return first name (String)
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * set first name of the customer
     * @param firstName first name (String)
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * get last name of the customer
     * @return last name (String)
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * set last name of the customer
     * @param lastName last name (String)
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * get the email of the customer
     * @return email (String)
     */
    public String getEmail() {
        return email;
    }

    /**
     * set the email
     * @param email email (String)
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * get the password
     * @return password (String)
     */
    public String getPassword() {
        return password;
    }

    /**
     * set the password
     * @param password password (String)
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * get list of coupons
     * @return List<Coupon>
     */
    public List<Coupon> getCoupons() {
        return coupons;
    }

    /**
     * set list of coupons
     * @param coupons List<Coupon>
     */
    public void setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }

    /**
     * toString for customer obj
     * @return String that represent this customer details
     */
    @Override
    public String toString() {
        return "Customer{" +
                "idCustomer=" + idCustomer +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", coupons=" + coupons +
                '}';
    }
}
