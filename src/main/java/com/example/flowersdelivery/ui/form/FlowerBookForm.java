package com.example.flowersdelivery.ui.form;

import com.example.flowersdelivery.backend.entity.Flower;
import com.example.flowersdelivery.backend.entity.Stock;
import com.example.flowersdelivery.backend.service.FlowerService;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class FlowerBookForm extends FormLayout {
    TextField flowerName = new TextField("Цветок");
    TextField flowerColor = new TextField("Цвет");
    Button save = new Button("Сохранить");
    Button close = new Button("Отмена");

    Binder<Flower> binder = new BeanValidationBinder<>(Flower.class);

    public FlowerBookForm() {
        binder.bindInstanceFields(this);

        add(flowerName, flowerColor, buttonLayout());
    }

    public void setFlower(Flower flower) {
        binder.setBean(flower);
    }

    private HorizontalLayout buttonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(click -> validateAndSave());
        close.addClickListener(click -> fireEvent(new CloseEvent(this)));

        return new HorizontalLayout(save, close);
    }

    private void validateAndSave() {
        if (binder.isValid()) {
            fireEvent(new SaveEvent(this, binder.getBean()));
        }
    }

    public static abstract class FlowerBookFormEvent extends ComponentEvent<FlowerBookForm> {
        private Flower flower;

        public FlowerBookFormEvent(FlowerBookForm source, Flower flower) {
            super(source, false);
            this.flower = flower;
        }

        public Flower getFlower() {
            return flower;
        }
    }
    public static class SaveEvent extends FlowerBookFormEvent {
        public SaveEvent(FlowerBookForm source, Flower flower) {
            super(source, flower);
        }
    }

    public static class CloseEvent extends FlowerBookFormEvent {
        public CloseEvent(FlowerBookForm source) {
            super(source, null);
        }
    }
    public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) {
        return addListener(SaveEvent.class, listener);
    }
    public Registration addCloseListener(ComponentEventListener<CloseEvent> listener) {
        return addListener(CloseEvent.class, listener);
    }
}