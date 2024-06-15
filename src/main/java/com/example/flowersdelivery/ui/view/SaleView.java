package com.example.flowersdelivery.ui.view;

import com.example.flowersdelivery.backend.entity.Flower;
import com.example.flowersdelivery.backend.entity.Sale;
import com.example.flowersdelivery.backend.entity.Stock;
import com.example.flowersdelivery.backend.entity.Store;
import com.example.flowersdelivery.backend.service.FlowerService;
import com.example.flowersdelivery.backend.service.SaleService;
import com.example.flowersdelivery.backend.service.StoreService;
import com.example.flowersdelivery.ui.MainLayout;
import com.example.flowersdelivery.ui.form.ReportsForm;
import com.example.flowersdelivery.ui.form.SaleForm;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

import java.text.SimpleDateFormat;
import java.util.Date;

@PermitAll
@Route(value = "sale", layout = MainLayout.class)
@PageTitle("Доставка")
public class SaleView extends VerticalLayout {

    private Dialog reportDialog;
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
        addClassName("sale-view");
        setSizeFull();
        configureGrid();

        saleForm = new SaleForm(flowerService.findAll(), storeService.findAll());
        saleForm.addSaveListener(this::saveContact);
        saleForm.addDeleteListener(this::deleteContact);
        saleForm.addCloseListener(e -> closeEditor());

        reportDialog = new Dialog(new ReportsForm(saleService, storeService));

        Div content = new Div(saleGrid, saleForm);
        content.addClassName("content");
        content.setSizeFull();

        add(toolBar(), content);
        updateListSale();
        closeEditor();
    }

    private void deleteContact(SaleForm.DeleteEvent deleteEvent) {
        saleService.delete(deleteEvent.getSale());
        updateListSale();
        closeEditor();
    }

    private void saveContact(SaleForm.SaveEvent saveEvent) {
        saleService.save(saveEvent.getSale());
        updateListSale();
        closeEditor();
    }

    private HorizontalLayout toolBar() {
        Button addSaleBtn = new Button("Добавить", click -> addSale());
        Button reportBtn = new Button("Отчеты", click -> {reportDialog.open();});

        HorizontalLayout toolBar = new HorizontalLayout(reportBtn, addSaleBtn);
        return toolBar;
    }

    private void addSale() {
        saleGrid.asSingleSelect().getValue();
        editSale(new Sale());
    }

    private void updateListSale() {
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
        saleGrid.removeColumnByKey("flowerPrice");

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
        saleGrid.addColumn(Sale::getFlowerPrice).setHeader("Цена");
        saleGrid.addColumn(Sale::getQuantity).setHeader("Количество");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        saleGrid.addColumn(sale -> {
            Date saleDate = sale.getSaleDate();
            return saleDate == null ? "-" : dateFormat.format(saleDate);
        }).setHeader("Дата");
        saleGrid.addColumn(Sale::getDeliveryAddress).setHeader("Адрес доставки");

        saleGrid.getColumns().forEach(col -> col.setAutoWidth(true));

        saleGrid.asSingleSelect().addValueChangeListener(e -> editSale(e.getValue()));
    }

    private void closeEditor() {
        saleForm.setSale(null);
        saleForm.setVisible(false);
        removeClassName("editing");
    }

    private void editSale(Sale sale) {
        if (sale == null) {
            closeEditor();
        } else {
            saleForm.setSale(sale);
            saleForm.setVisible(true);
            addClassName("editing");
        }

    }
}
