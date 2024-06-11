package com.example.flowersdelivery.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

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
