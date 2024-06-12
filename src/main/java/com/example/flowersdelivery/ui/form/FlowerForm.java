package com.example.flowersdelivery.ui.form;


import com.example.flowersdelivery.backend.entity.Flower;
import com.example.flowersdelivery.backend.entity.Stock;
import com.example.flowersdelivery.backend.entity.Store;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;

import java.util.List;

public class FlowerForm extends FormLayout {
    ComboBox<Flower> flower = new ComboBox<>("Цветок");
    IntegerField quantity = new IntegerField("Количество");
    ComboBox<Store> store = new ComboBox<>("Магазин");

    Button save = new Button("Сохранить");
    Button delete = new Button("Удалить");
    Button close = new Button("Отмена");

    Binder<Stock> binder = new BeanValidationBinder<>(Stock.class);

    public FlowerForm(List<Store> stores, List<Flower> flowers) {
        addClassName("flower-form");

        binder.bindInstanceFields(this);

        flower.setItems(flowers);
        flower.setItemLabelGenerator(Flower::getNameColorPrice);
        store.setItems(stores);
        store.setItemLabelGenerator(Store::getNameWithAddress);

        add(
                flower,
                quantity,
                store,
                buttonLayout()
        );
    }

    public void setFlower(Stock stock) {
        binder.setBean(stock);
    }

    private Component buttonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(save, delete, close);
    }
}
