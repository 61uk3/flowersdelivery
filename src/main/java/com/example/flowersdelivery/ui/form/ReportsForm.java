package com.example.flowersdelivery.ui.form;

import com.example.flowersdelivery.backend.service.FlowerService;
import com.example.flowersdelivery.backend.service.SaleService;
import com.example.flowersdelivery.backend.service.StoreService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class ReportsForm extends FormLayout {

    private Dialog topFlowerDialog;
    private Button report1 = new Button("Топ цветов", click -> {topFlowerDialog.open();});
    private Button report2 = new Button("Обороты");

    public ReportsForm(SaleService saleService, StoreService storeService) {
        topFlowerDialog = new Dialog(new TopFlowerForm(storeService.findAll(), saleService));
        topFlowerDialog.setHeight("65%");
        topFlowerDialog.setWidth("70%");
        add(report1, report2);
    }
}
