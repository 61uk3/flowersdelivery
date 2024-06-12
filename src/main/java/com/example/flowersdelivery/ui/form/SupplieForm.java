package com.example.flowersdelivery.ui.form;

import com.example.flowersdelivery.backend.entity.Flower;
import com.example.flowersdelivery.backend.entity.Store;
import com.example.flowersdelivery.backend.entity.Supplie;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;

import java.util.List;

public class SupplieForm extends FormLayout {

    ComboBox<Flower> flower = new ComboBox<>("Цветок");
    ComboBox<Store> store = new ComboBox<>("Магазин");
    IntegerField quantity = new IntegerField("Количество");
    NumberField price = new NumberField("Цена");
    DatePicker supplyDate = new DatePicker("Дата поствки");
    Button save = new Button("Сохранить");
    Button delete = new Button("Удалить");
    Button close = new Button("Отмена");
    Binder<Supplie> binder = new BeanValidationBinder<>(Supplie.class);

    public SupplieForm(List<Flower> flowers,
                       List<Store> stores
    ) {
        addClassName("supplie-form");

        binder.bindInstanceFields(this);
        flower.setItems(flowers);
        flower.setItemLabelGenerator(Flower::getNameColor);
        store.setItems(stores);
        store.setItemLabelGenerator(Store::getNameWithAddress);

        add(
                store,
                flower,
                quantity,
                price,
                supplyDate,
                buttonLayout()
        );

    }

    public void setSupplie(Supplie supplie) {
        binder.setBean(supplie);
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
