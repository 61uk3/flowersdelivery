package com.example.flowersdelivery.ui.form;

import com.example.flowersdelivery.backend.entity.Flower;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class FlowerBookForm extends Dialog {
    TextField flower = new TextField("Цветок");
    TextField flowerColor = new TextField("Цвет");
    Button save = new Button("Сохранить");
    Button delete = new Button("Удалить");
    Button close = new Button("Отмена");

    Binder<Flower> binder = new BeanValidationBinder<>(Flower.class);
    public FlowerBookForm() {
        addClassName("flower-book-form");

        binder.bindInstanceFields(this);

        add(
                flower,
                flowerColor,
                buttonLayout()
        );
    }

    public void setFlower(Flower flower) {
        binder.setBean(flower);
    }

    private com.vaadin.flow.component.Component buttonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(save, delete, close);
    }
}
