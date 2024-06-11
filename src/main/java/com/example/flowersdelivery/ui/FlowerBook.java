package com.example.flowersdelivery.ui;

import com.example.flowersdelivery.backend.entity.Flower;
import com.example.flowersdelivery.backend.service.FlowerService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class FlowerBook extends Dialog {
    Button addFlowBtn = new Button("Добавить");
    private FlowerService flowerService;
    Grid<Flower> flowerGrid = new Grid<>(Flower.class);

    public FlowerBook(FlowerService flowerService) {
        this.flowerService = flowerService;
        addClassName("flower-dialog");
        setWidth("50%");
        setHeight("70%");

        configureGrid();
        add(addFlowBtn, flowerGrid);
        flowerGrid.setItems(flowerService.findAll());
    }

    private void configureGrid() {
        flowerGrid.addClassName("flower-book-grid");
        flowerGrid.setSizeFull();
        flowerGrid.removeColumnByKey("id");
        flowerGrid.removeColumnByKey("flowerName");
        flowerGrid.removeColumnByKey("flowerColor");
        flowerGrid.removeColumnByKey("flowerPrice");

        flowerGrid.addColumn(Flower::getFlowerName).setHeader("Цветок");
        flowerGrid.addColumn(Flower::getFlowerColor).setHeader("Цвет");
    }
}
