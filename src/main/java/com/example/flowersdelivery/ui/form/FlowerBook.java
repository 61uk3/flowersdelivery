package com.example.flowersdelivery.ui.form;

import com.example.flowersdelivery.backend.entity.Flower;
import com.example.flowersdelivery.backend.service.FlowerService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class FlowerBook extends VerticalLayout {
    Dialog flowerBookDialog = new Dialog();
    FlowerBookForm flowerBookForm;
    Button addFlower = new Button("Добавить");
    Grid<Flower> flowerGrid = new Grid<>(Flower.class);
    private FlowerService flowerService;

    public FlowerBook(FlowerService flowerService) {
        this.flowerService = flowerService;
        addClassName("flower-book");
        configureFlowerGrid();

        flowerBookForm = new FlowerBookForm();
        flowerBookForm.addSaveListener(this::saveFlower);
        flowerBookForm.addCloseListener(e -> closeEditor());

        flowerBookDialog.add(flowerBookForm);

        addFlower.addClickListener(event -> addNewFlower());

        add(addFlower, flowerGrid);
        updateFlowerGrid();
    }

    private void updateFlowerGrid() {
        flowerGrid.setItems(flowerService.findAll());
    }

    private void saveFlower(FlowerBookForm.SaveEvent saveEvent) {
        flowerService.save(saveEvent.getFlower());
        updateFlowerGrid();
        closeEditor();
    }

    private void closeEditor() {
        flowerBookDialog.close();
    }

    private void configureFlowerGrid() {
        addClassName("flower-book-grid");
        flowerGrid.removeColumnByKey("id");
        flowerGrid.removeColumnByKey("flowerName");
        flowerGrid.removeColumnByKey("flowerColor");
        flowerGrid.removeColumnByKey("nameColor");

        flowerGrid.addColumn(Flower::getFlowerName).setHeader("Цветок");
        flowerGrid.addColumn(Flower::getFlowerColor).setHeader("Цвет");

        flowerGrid.asSingleSelect().addValueChangeListener(e -> editFlower(e.getValue()));
    }

    private void editFlower(Flower flower) {
        flowerGrid.asSingleSelect().clear();
        flowerBookForm.setFlower(flower);
        flowerBookDialog.open();
    }

    private void addNewFlower() {
        flowerBookForm.setFlower(new Flower());
        flowerBookDialog.open();
    }
}