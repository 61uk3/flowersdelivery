package com.example.flowersdelivery.ui.form;

import com.example.flowersdelivery.backend.entity.Flower;
import com.example.flowersdelivery.backend.entity.Sale;
import com.example.flowersdelivery.backend.entity.Store;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;

import java.util.List;

public class SaleForm extends FormLayout {
    ComboBox<Flower> flower = new ComboBox<>("Цветок");
    ComboBox<Store> store = new ComboBox<>("Магазин");
    IntegerField quantity = new IntegerField("Количество");
    DatePicker saleDate = new DatePicker("Дата доставки");
    TextField deliveryAddress = new TextField("Адрес доставки");
    Button save = new Button("Сохранить");
    Button delete = new Button("Удалить");
    Button close = new Button("Отмена");
    Binder<Sale> binder = new BeanValidationBinder<>(Sale.class);
    public SaleForm(
            List<Flower> flowers,
            List<Store> stores
    ) {
        addClassName("sale-form");

        binder.bindInstanceFields(this);
        store.setItems(stores);
        store.setItemLabelGenerator(Store::getNameWithAddress);
        flower.setItems(flowers);
        flower.setItemLabelGenerator(Flower::getNameColorPrice);

        add(
                store,
                flower,
                quantity,
                saleDate,
                deliveryAddress,
                buttonLayout()
        );

    }

    public void setSale(Sale sale) {
        binder.setBean(sale);
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
