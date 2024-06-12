package com.example.flowersdelivery.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "stores")
public class Store extends AbstractEntity {
    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "store", fetch = FetchType.EAGER)
    private List<Stock> stocks = new LinkedList<>();

    @OneToMany(mappedBy = "store", fetch = FetchType.EAGER)
    private List<Supplie> supplies = new LinkedList<>();
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNameWithAddress() {
        return name + " | " + address;
    }
}
