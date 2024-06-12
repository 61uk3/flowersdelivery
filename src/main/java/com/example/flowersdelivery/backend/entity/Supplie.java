package com.example.flowersdelivery.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity
@Table(name = "supplies")
public class Supplie extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne
    @JoinColumn(name = "flower_id")
    private Flower flower;

    @NotNull
    @Column(name = "quantity")
    private int quantity;

    @NotNull
    @Column(name = "price")
    private double price;

    @NotNull
    @Column(name = "supply_date")
    private Date supplyDate = new Date();

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Flower getFlower() {
        return flower;
    }

    public void setFlower(Flower flower) {
        this.flower = flower;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getSupplyDate() {
        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        try {
            return newDateFormat.parse(newDateFormat.format(supplyDate));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setSupplyDate(Date supplyDate) {
        this.supplyDate = supplyDate;
    }
}
