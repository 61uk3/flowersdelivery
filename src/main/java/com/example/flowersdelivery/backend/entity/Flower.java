package com.example.flowersdelivery.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "flowers")
public class Flower extends AbstractEntity {
    @NotNull
    @Column(name = "name")
    private String flowerName = "";

    @NotNull
    @Column(name = "color")
    private String flowerColor = "";

    @NotNull
    @Column(name = "price")
    private float flowerPrice = 0.0f;

    @OneToMany(mappedBy = "flower", fetch = FetchType.EAGER)
    private List<Stock> stocks = new LinkedList<>();

    @OneToMany(mappedBy = "flower", fetch = FetchType.EAGER)
    private List<Supplie> supplies = new LinkedList<>();

    public String getFlowerName() {
        return flowerName;
    }

    public void setFlowerName(String flowerName) {
        this.flowerName = flowerName;
    }

    public String getFlowerColor() {
        return flowerColor;
    }

    public void setFlowerColor(String flowerColor) {
        this.flowerColor = flowerColor;
    }

    public float getFlowerPrice() {
        return flowerPrice;
    }

    public void setFlowerPrice(float flowerPrice) {
        this.flowerPrice = flowerPrice;
    }
}
