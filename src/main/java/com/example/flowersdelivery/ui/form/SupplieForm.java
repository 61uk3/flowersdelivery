package com.example.flowersdelivery.ui.form;

import com.example.flowersdelivery.backend.entity.Flower;
import com.example.flowersdelivery.backend.entity.Store;
import com.example.flowersdelivery.backend.entity.Supplie;
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
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class SupplieForm extends FormLayout {

    ComboBox<Flower> flower = new ComboBox<>("Цветок");
    ComboBox<Store> store = new ComboBox<>("Магазин");
    IntegerField quantity = new IntegerField("Количество");
    NumberField price = new NumberField("Цена");
    DatePicker supplyDate = new DatePicker("Дата поставки");
    Button save = new Button("Сохранить");
    Button delete = new Button("Удалить");
    Button close = new Button("Отмена");
    Button accept = new Button("Принять");
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

        save.addClickListener(click -> validateAndSave());
        accept.addClickListener(click -> fireEvent(new AcceptEvent(this, binder.getBean())));
        delete.addClickListener(click -> fireEvent(new DeleteEvent(this, binder.getBean())));
        close.addClickListener(click -> fireEvent(new CloseEvent(this)));

        return new HorizontalLayout(save, accept, delete, close);
    }

    private void validateAndSave() {
        if (binder.isValid()) {
            fireEvent(new SaveEvent(this, binder.getBean()));
        }
    }

    public static abstract class SupplieFormEvent extends ComponentEvent<SupplieForm> {
        private Supplie supplie;

        public SupplieFormEvent(SupplieForm source, Supplie supplie) {
            super(source, false);
            this.supplie = supplie;
        }

        public Supplie getSupplie() {
            return supplie;
        }
    }

    public static class SaveEvent extends SupplieFormEvent {
        public SaveEvent(SupplieForm source, Supplie supplie) {
            super(source, supplie);
        }
    }

    public static class DeleteEvent extends SupplieFormEvent {

        public DeleteEvent(SupplieForm source, Supplie supplie) {
            super(source, supplie);
        }
    }
    public static class AcceptEvent extends SupplieFormEvent {

        public AcceptEvent(SupplieForm source, Supplie supplie) {
            super(source, supplie);
        }
    }

    public static class CloseEvent extends SupplieFormEvent {
        public CloseEvent(SupplieForm source) {
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
    public Registration addAcceptListener(ComponentEventListener<AcceptEvent> listener) {
        return addListener(AcceptEvent.class, listener);
    }
}
