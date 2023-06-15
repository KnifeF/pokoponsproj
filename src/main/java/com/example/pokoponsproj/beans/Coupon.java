package com.example.pokoponsproj.beans;

import com.example.pokoponsproj.enums.Types;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity // for Hibernate mapping
@Table(name = "pokopons")
public class Coupon {
    /**
     * Coupon obj that represents a table 'pokopons' in the db, and each instance corresponds to a row in that table
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPokopon;
    @NotNull
    private String pokemonName;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private Types idType;

    @NotNull
    private int amount;

    @NotNull
    private double price;

    private String description;

    @NotNull
    private Date startDate, endDate;

    private String image;

    @ManyToOne
    private Seller seller;

    @ManyToMany
    private List<Customer> customers;

    // constructors for Coupon obj
    /***** HIBERNATE *****/
    public Coupon() {}

    public Coupon(String pokemonName, Types idType, int amount, double price, String description, Date startDate, Date endDate, String image, Seller seller) {
        this.pokemonName = pokemonName;
        this.idType = idType;
        this.amount = amount;
        this.price = price;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.image = image;
        this.seller = seller;
    }

    /**
     * get coupon's id
     * @return idPokopon
     */
    public int getIdPokopon() {
        return idPokopon;
    }

    /**
     * get coupon's name/title
     * @return pokopon name
     */
    public String getPokemonName() {
        return pokemonName;
    }

    /**
     * set coupon's name/title
     * @param pokemonName pokopon name
     */
    public void setPokemonName(String pokemonName) {
        this.pokemonName = pokemonName;
    }

    /**
     * get pokopon type
     * @return Types (enum)
     */
    public Types getIdType() {
        return idType;
    }

    /**
     * set pokopon type
     * @param idType pokopon type - Types (enum)
     */
    public void setIdType(Types idType) {
        this.idType = idType;
    }

    /**
     * get amount of pokopon (left in stock)
     * @return pokopon amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * set amount of pokopon (left in stock)
     * @param amount pokopon amount
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * get price of pokopon
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * set price of pokopon
     * @param price price of pokopon
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * get coupon description
     * @return the description (String)
     */
    public String getDescription() {
        return description;
    }

    /**
     * set coupon description
     * @param description the description (String)
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * get coupon start date
     * @return start date (java.util.Date)
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * set coupon start date
     * @param startDate start date (java.util.Date)
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * get coupon end date
     * @return end date (java.util.Date)
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * set coupon end date
     * @param endDate end date (java.util.Date)
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * get pokopon image
     * @return image (String)
     */
    public String getImage() {
        return image;
    }

    /**
     * set pokopon image
     * @param image image (String)
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * get seller
     * @return Seller
     */
    public Seller getSeller() {
        return seller;
    }

    /**
     * set seller
     * @param seller Seller
     */
    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    /**
     * get customers
     * @return List<Customer>
     */
    public List<Customer> getCustomers() {
        return customers;
    }

    /**
     * set customers
     * @param customers List<Customer>
     */
    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    /**
     * toString that represent the pokopon details
     * @return String
     */
    @Override
    public String toString() {
        return "Coupon{" +
                "idPokopon=" + idPokopon +
                ", pokemonName='" + pokemonName + '\'' +
                ", idType=" + idType +
                ", amount=" + amount +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", image='" + image + '\'' +
                ", seller=" + seller +
                '}';
    }
}
