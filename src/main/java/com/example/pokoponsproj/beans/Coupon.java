package com.example.pokoponsproj.beans;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity // for Hibernate mapping
@Table(name = "pokopons")
public class Coupon {

    // seller = company_id // pokopon = coupon_id //
    private int idPokopon;
    //    private int idSeller;
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

    //    @ManyToMany(mappedBy = "coupons", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
//    @ManyToMany
//    private Set<Customer> customers;
//    @ManyToMany(fetch = FetchType.EAGER)
    @ManyToMany
    private List<Customer> customers;

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

    public int getIdPokopon() {
        return idPokopon;
    }

    public String getPokemonName() {
        return pokemonName;
    }

    public void setPokemonName(String pokemonName) {
        this.pokemonName = pokemonName;
    }

    public Types getIdType() {
        return idType;
    }

    public void setIdType(Types idType) {
        this.idType = idType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

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
