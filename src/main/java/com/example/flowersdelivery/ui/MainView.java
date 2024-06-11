package com.example.flowersdelivery.ui;

import com.example.flowersdelivery.backend.entity.Flower;
import com.example.flowersdelivery.backend.service.FlowerService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
class MainView extends VerticalLayout {

    Grid<Flower> flowerGrid = new Grid<>(Flower.class);
    private FlowerService flowerService;

    MainView(FlowerService flowerService) {
        this.flowerService = flowerService;

        add(flowerGrid);
        flowerGrid.setItems(flowerService.findAll());
    }
}
