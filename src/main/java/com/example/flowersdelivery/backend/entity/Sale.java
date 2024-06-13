package com.example.flowersdelivery.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity
@Table(name = "sales")
public class Sale extends AbstractEntity {
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
    @Column(name = "sale_date")
    private Date saleDate = new Date();

    @NotNull
    @Column(name = "delivery_address")
    private String deliveryAddress;

    @NotNull
    @Column(name = "price")
    private double flowerPrice = 0.0d;

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

    public Date getSaleDate() {
        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        try {
            String formattedDate = newDateFormat.format(saleDate);
            Date parseDate = newDateFormat.parse(formattedDate);
            return parseDate;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public double getFlowerPrice() {
        return flowerPrice;
    }

    public void setFlowerPrice(double flowerPrice) {
        this.flowerPrice = flowerPrice;
    }
}
