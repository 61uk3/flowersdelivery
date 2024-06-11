package com.example.flowersdelivery.ui;

import com.example.flowersdelivery.backend.entity.Flower;
import com.example.flowersdelivery.backend.entity.Stock;
import com.example.flowersdelivery.backend.entity.Store;
import com.example.flowersdelivery.backend.service.FlowerService;
import com.example.flowersdelivery.backend.service.StockService;
import com.example.flowersdelivery.backend.service.StoreService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Наличие цветов")
class FlowerView extends VerticalLayout {
    private FlowerService flowerService;
    private StoreService storeService;
    private StockService stockService;
    FlowerBook flowerBook;
    Button openFlowerBookBtn = new Button("Справочник");
    Grid<Stock> flowerGrid = new Grid<>(Stock.class);


    FlowerView(
            FlowerService flowerService,
            StoreService storeService,
            StockService stockService
    ) {
        this.flowerService = flowerService;
        this.storeService = storeService;
        this.stockService = stockService;

        flowerBook = new FlowerBook(flowerService);

        openFlowerBookBtn.addClickListener(e -> flowerBook.open());
        addClassName("flower-view");
        setSizeFull();
        configureGrid();

        Div content = new Div(flowerGrid);
        content.addClassName("content");
        content.setSizeFull();

        add(openFlowerBookBtn, content);
        flowerGrid.setItems(stockService.findAll());
    }

    private void configureGrid() {
        flowerGrid.addClassName("flower-grid");
        flowerGrid.setSizeFull();
        flowerGrid.removeColumnByKey("flower");
        flowerGrid.removeColumnByKey("store");
        flowerGrid.removeColumnByKey("quantity");
        flowerGrid.removeColumnByKey("id");

        flowerGrid.addColumn(stock -> {
            Flower flower = stock.getFlower();
            return flower == null ? "-" : flower.getFlowerName();
        }).setHeader("Цветок");
        flowerGrid.addColumn(stock -> {
            Flower flower = stock.getFlower();
            return flower == null ? "-" : flower.getFlowerColor();
        }).setHeader("Цвет");
        flowerGrid.addColumn(stock -> {
            Flower flower = stock.getFlower();
            return flower == null ? "-" : flower.getFlowerPrice();
        }).setHeader("Цена");
        flowerGrid.addColumn(stock -> {
            Store store = stock.getStore();
            return store == null ? "-" : store.getName();
        }).setHeader("Магазин");
        flowerGrid.addColumn(stock -> {
            Store store = stock.getStore();
            return store == null ? "-" : store.getAddress();
        }).setHeader("Адрес");
        flowerGrid.addColumn(Stock::getQuantity).setHeader("Количество");

        flowerGrid.getColumns().forEach(col -> col.setAutoWidth(true));
    }
}
