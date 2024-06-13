package com.example.flowersdelivery.ui.form;

import com.example.flowersdelivery.backend.entity.Flower;
import com.example.flowersdelivery.backend.entity.Sale;
import com.example.flowersdelivery.backend.entity.Store;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

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
        flower.setItemLabelGenerator(Flower::getNameColor);

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

        save.addClickListener(click -> validateAndSave());
        delete.addClickListener(click -> fireEvent(new DeleteEvent(this, binder.getBean())));
        close.addClickListener(click -> fireEvent(new CloseEvent(this)));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        if (binder.isValid()) {
            fireEvent(new SaveEvent(this, binder.getBean()));
        }
    }

    public static abstract class SaleFormEvent extends ComponentEvent<SaleForm> {
        private Sale sale;

        public SaleFormEvent(SaleForm source, Sale sale) {
            super(source, false);
            this.sale = sale;
        }

        public Sale getSale() {
            return sale;
        }
    }

    public static class SaveEvent extends SaleFormEvent {

        public SaveEvent(SaleForm source, Sale sale) {
            super(source, sale);
        }
    }

    public static class DeleteEvent extends SaleFormEvent {

        public DeleteEvent(SaleForm source, Sale sale) {
            super(source, sale);
        }
    }

    public static class CloseEvent extends SaleFormEvent {
        public CloseEvent(SaleForm source) {
            super(source, null);
        }
    }
    public Registration addDeleteListener(ComponentEventListener<DeleteEvent> listener) {
        return addListener(DeleteEvent.class, listener);
    }

    public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) {
        return addListener(SaveEvent.class, listener);
    }
    public Registration addCloseListener(ComponentEventListener<CloseEvent> listener) {
        return addListener(CloseEvent.class, listener);
    }
}
