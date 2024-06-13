package com.example.flowersdelivery.ui.view;


import com.example.flowersdelivery.backend.entity.Flower;
import com.example.flowersdelivery.backend.entity.Store;
import com.example.flowersdelivery.backend.entity.Supplie;
import com.example.flowersdelivery.backend.service.FlowerService;
import com.example.flowersdelivery.backend.service.StoreService;
import com.example.flowersdelivery.backend.service.SupplieService;
import com.example.flowersdelivery.ui.MainLayout;
import com.example.flowersdelivery.ui.form.SupplieForm;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.text.SimpleDateFormat;
import java.util.Date;

@Route(value = "supplies", layout = MainLayout.class)
@PageTitle("Поставки")
public class SupplieView extends VerticalLayout {
    private SupplieForm supplieForm;
    Grid<Supplie> supplieGrid = new Grid<>(Supplie.class);
    private SupplieService supplieService;
    private StoreService storeService;
    private FlowerService flowerService;

    public SupplieView(
            SupplieService supplieService,
            StoreService storeService,
            FlowerService flowerService
    ) {
        this.supplieService = supplieService;
        this.storeService = storeService;
        this.flowerService = flowerService;
        addClassName("supplie-view");
        setSizeFull();
        configureGrid();

        supplieForm = new SupplieForm(flowerService.findAll(), storeService.findAll());
        supplieForm.addSaveListener(this::saveContact);
        supplieForm.addDeleteListener(this::deleteContact);
        supplieForm.addCloseListener(e -> closeEditor());

        Div content = new Div(supplieGrid, supplieForm);
        content.addClassName("content");
        content.setSizeFull();

        add(toolBar(), content);
        updateListSupplie();
        closeEditor();
    }

    private void deleteContact(SupplieForm.DeleteEvent deleteEvent) {
        supplieService.delete(deleteEvent.getSupplie());
        updateListSupplie();
        closeEditor();
    }

    private void saveContact(SupplieForm.SaveEvent saveEvent) {
        supplieService.save(saveEvent.getSupplie());
        updateListSupplie();
        closeEditor();
    }

    private HorizontalLayout toolBar() {
        Button addSupplieBtn = new Button("Добавить", click -> addSupplie());

        HorizontalLayout toolBar = new HorizontalLayout(addSupplieBtn);
        return toolBar;
    }

    private void addSupplie() {
        supplieGrid.asSingleSelect().getValue();
        editSupplie(new Supplie());
    }

    private void updateListSupplie() {
        supplieGrid.setItems(supplieService.findAll());
    }

    private void configureGrid() {
        supplieGrid.addClassName("supplie-grid");
        supplieGrid.setSizeFull();
        supplieGrid.removeColumnByKey("flower");
        supplieGrid.removeColumnByKey("store");
        supplieGrid.removeColumnByKey("quantity");
        supplieGrid.removeColumnByKey("price");
        supplieGrid.removeColumnByKey("supplyDate");
        supplieGrid.removeColumnByKey("id");

        supplieGrid.addColumn(supplie -> {
            Store store = supplie.getStore();
            return store == null ? "-" : store.getName();
        }).setHeader("Магазин");
        supplieGrid.addColumn(supplie -> {
            Store store = supplie.getStore();
            return store == null ? "-" : store.getAddress();
        }).setHeader("Адрес");
        supplieGrid.addColumn(supplie -> {
            Flower flower = supplie.getFlower();
            return flower == null ? "-" : flower.getFlowerName();
        }).setHeader("Цветок");
        supplieGrid.addColumn(supplie -> {
            Flower flower = supplie.getFlower();
            return flower == null ? "-" : flower.getFlowerColor();
        }).setHeader("Цвет");
        supplieGrid.addColumn(Supplie::getQuantity).setHeader("Количество");
        supplieGrid.addColumn(Supplie::getPrice).setHeader("Цена");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        supplieGrid.addColumn(supplie -> {
            Date supplieDate = supplie.getSupplyDate();
            return supplieDate == null ? "-" : dateFormat.format(supplieDate);
        }).setHeader("Дата поставки");

        supplieGrid.getColumns().forEach(col -> col.setAutoWidth(true));

        supplieGrid.asSingleSelect().addValueChangeListener(e -> editSupplie(e.getValue()));
    }

    private void editSupplie(Supplie supplie) {
        if (supplie == null) {
            closeEditor();
        } else {
            supplieForm.setSupplie(supplie);
            supplieForm.setVisible(true);
            addClassName("editing");
        }
    }
    private void closeEditor() {
        supplieForm.setSupplie(null);
        supplieForm.setVisible(false);
        removeClassName("editing");
    }
}
