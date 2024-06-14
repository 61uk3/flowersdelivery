package com.example.flowersdelivery.backend.DTOs;

import com.example.flowersdelivery.backend.entity.Flower;

public class FlowerSalesDTO {
    private Flower flower;
    private Long totalQuantity;

    public FlowerSalesDTO(Flower flower, Long totalQuantity) {
        this.flower = flower;
        this.totalQuantity = totalQuantity;
    }

    public Flower getFlower() {
        return flower;
    }

    public Long getTotalQuantity() {
        return totalQuantity;
    }
}
