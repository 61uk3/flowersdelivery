package com.example.flowersdelivery.ui;


import com.example.flowersdelivery.backend.entity.Flower;
import com.example.flowersdelivery.backend.entity.Store;
import com.example.flowersdelivery.backend.entity.Supplie;
import com.example.flowersdelivery.backend.service.FlowerService;
import com.example.flowersdelivery.backend.service.StoreService;
import com.example.flowersdelivery.backend.service.SupplieService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "supplies", layout = MainLayout.class)
@PageTitle("Поставки")
public class SupplieView extends VerticalLayout {
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

        Div content = new Div(supplieGrid);
        content.addClassName("content");
        content.setSizeFull();

        add(content);
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
        supplieGrid.addColumn(Supplie::getSupplyDate).setHeader("Дата поставки");

        supplieGrid.getColumns().forEach(col -> col.setAutoWidth(true));
    }
}
