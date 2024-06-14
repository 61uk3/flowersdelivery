package com.example.flowersdelivery.ui.view;

import com.example.flowersdelivery.backend.entity.Flower;
import com.example.flowersdelivery.backend.entity.Stock;
import com.example.flowersdelivery.backend.entity.Store;
import com.example.flowersdelivery.backend.service.FlowerService;
import com.example.flowersdelivery.backend.service.StockService;
import com.example.flowersdelivery.backend.service.StoreService;
import com.example.flowersdelivery.ui.MainLayout;
import com.example.flowersdelivery.ui.form.FlowerBook;
import com.example.flowersdelivery.ui.form.FlowerForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Наличие цветов")
public class FlowerView extends VerticalLayout {
    private Dialog flowerBook = new Dialog();
    private FlowerService flowerService;
    private StoreService storeService;
    private StockService stockService;

    private FlowerForm flowerForm;
    private Grid<Stock> flowerGrid = new Grid<>(Stock.class);

    FlowerView(
            FlowerService flowerService,
            StoreService storeService,
            StockService stockService
    ) {
        this.flowerService = flowerService;
        this.storeService = storeService;
        this.stockService = stockService;

        flowerForm = new FlowerForm(storeService.findAll(), flowerService.findAll());
        flowerForm.addSaveListener(this::saveFlower);
        flowerForm.addDeleteListener(this::deleteFlower);
        flowerForm.addCloseListener(e -> closeEditor());

        configureFlowerBook();
        flowerBook.add(new FlowerBook(flowerService));
        flowerBook.addDialogCloseActionListener(e -> updateListFlowerAndCloseDialog());

        addClassName("flower-view");
        setSizeFull();
        configureGrid();

        Div content = new Div(flowerGrid, flowerForm);
        content.addClassName("content");
        content.setSizeFull();

        add(toolBar(), content);
        updateListFlower();
        closeEditor();
    }

    private void configureFlowerBook() {
        flowerBook.setWidth("70%");
        flowerBook.setHeight("60%");
    }

    private void deleteFlower(FlowerForm.DeleteEvent deleteEvent) {
        stockService.delete(deleteEvent.getStock());
        updateListFlower();
        closeEditor();
    }

    private void saveFlower(FlowerForm.SaveEvent saveEvent) {
        stockService.save(saveEvent.getStock());
        updateListFlower();
        closeEditor();
    }

    private HorizontalLayout toolBar() {
        Button openFlowerBookBtn = new Button("Справочник", click -> flowerBook.open());
        Button addFlowerBtn = new Button("Добавить", click -> addFlower());

        return new HorizontalLayout(openFlowerBookBtn, addFlowerBtn);
    }

    private void updateListFlower() {
        flowerGrid.setItems(stockService.findAll());
    }
    private void updateListFlowerAndCloseDialog() {
        UI.getCurrent().getPage().reload();
        flowerBook.close();
    }

    private void configureGrid() {
        flowerGrid.addClassName("flower-grid");
        flowerGrid.setSizeFull();
        flowerGrid.removeColumnByKey("flower");
        flowerGrid.removeColumnByKey("store");
        flowerGrid.removeColumnByKey("quantity");
        flowerGrid.removeColumnByKey("id");
        flowerGrid.removeColumnByKey("flowerPrice");

        flowerGrid.addColumn(stock -> {
            Flower flower = stock.getFlower();
            return flower == null ? "-" : flower.getFlowerName();
        }).setHeader("Цветок");
        flowerGrid.addColumn(stock -> {
            Flower flower = stock.getFlower();
            return flower == null ? "-" : flower.getFlowerColor();
        }).setHeader("Цвет");
        flowerGrid.addColumn(Stock::getFlowerPrice).setHeader("Цена");
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

        flowerGrid.asSingleSelect().addValueChangeListener(e -> editFlower(e.getValue()));
    }

    private void addFlower() {
        flowerGrid.asSingleSelect().getValue();
        editFlower(new Stock());
    }

    private void editFlower(Stock stock) {
        if (stock == null) {
            closeEditor();
        } else {
            flowerForm.setFlower(stock);
            flowerForm.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        flowerForm.setFlower(null);
        flowerForm.setVisible(false);
        removeClassName("editing");
    }
}
