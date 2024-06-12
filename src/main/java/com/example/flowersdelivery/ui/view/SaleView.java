package com.example.flowersdelivery.ui.view;

import com.example.flowersdelivery.backend.entity.Flower;
import com.example.flowersdelivery.backend.entity.Sale;
import com.example.flowersdelivery.backend.entity.Stock;
import com.example.flowersdelivery.backend.entity.Store;
import com.example.flowersdelivery.backend.service.FlowerService;
import com.example.flowersdelivery.backend.service.SaleService;
import com.example.flowersdelivery.backend.service.StoreService;
import com.example.flowersdelivery.ui.MainLayout;
import com.example.flowersdelivery.ui.form.SaleForm;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "sale", layout = MainLayout.class)
@PageTitle("Доставка")
public class SaleView extends VerticalLayout {
    private SaleForm saleForm;
    Grid<Sale> saleGrid = new Grid<>(Sale.class);
    private SaleService saleService;
    private FlowerService flowerService;
    private StoreService storeService;

    public SaleView(
            SaleService saleService,
            FlowerService flowerService,
            StoreService storeService
    ) {
        this.saleService = saleService;
        this.flowerService = flowerService;
        this.storeService = storeService;
        addClassName("flower-view");
        setSizeFull();
        configureGrid();

        saleForm = new SaleForm(flowerService.findAll(), storeService.findAll());

        Div content = new Div(saleGrid, saleForm);
        content.addClassName("content");
        content.setSizeFull();

        add(content);
        saleGrid.setItems(saleService.findAll());
    }

    private void configureGrid() {
        saleGrid.addClassName("sale-grid");
        saleGrid.setSizeFull();
        saleGrid.removeColumnByKey("flower");
        saleGrid.removeColumnByKey("store");
        saleGrid.removeColumnByKey("quantity");
        saleGrid.removeColumnByKey("id");
        saleGrid.removeColumnByKey("saleDate");
        saleGrid.removeColumnByKey("deliveryAddress");

        saleGrid.addColumn(sale -> {
            Store store = sale.getStore();
            return store == null ? "-" : store.getName();
        }).setHeader("Магазин");
        saleGrid.addColumn(sale -> {
            Store store = sale.getStore();
            return store == null ? "-" : store.getAddress();
        }).setHeader("Адрес магазина");
        saleGrid.addColumn(sale -> {
            Flower flower = sale.getFlower();
            return flower == null ? "-" : flower.getFlowerName();
        }).setHeader("Цветок");
        saleGrid.addColumn(sale -> {
            Flower flower = sale.getFlower();
            return flower == null ? "-" : flower.getFlowerColor();
        }).setHeader("Цвет");
        saleGrid.addColumn(sale -> {
            Flower flower = sale.getFlower();
            return flower == null ? "-" : flower.getFlowerPrice();
        }).setHeader("Цена");
        saleGrid.addColumn(Sale::getQuantity).setHeader("Количество");
        saleGrid.addColumn(Sale::getSaleDate).setHeader("Дата");
        saleGrid.addColumn(Sale::getDeliveryAddress).setHeader("Адрес доставки");

        saleGrid.getColumns().forEach(col -> col.setAutoWidth(true));

        saleGrid.asSingleSelect().addValueChangeListener(e -> editSale(e.getValue()));
    }

    private void editSale(Sale sale) {
        saleForm.setSale(sale);
    }
}
