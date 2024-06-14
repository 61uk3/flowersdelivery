package com.example.flowersdelivery.ui.form;

import com.example.flowersdelivery.backend.DTOs.FlowerSalesDTO;
import com.example.flowersdelivery.backend.entity.Flower;
import com.example.flowersdelivery.backend.entity.Sale;
import com.example.flowersdelivery.backend.entity.Store;
import com.example.flowersdelivery.backend.service.SaleService;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@UIScope
public class TopFlowerForm extends VerticalLayout {
    private ComboBox<Store> storeComboBox = new ComboBox<>("Магазин");
    private Grid<FlowerSalesDTO> saleGrid = new Grid<>(FlowerSalesDTO.class);
    private SaleService saleService;

    public TopFlowerForm(List<Store> stores, SaleService saleService) {
        this.saleService = saleService;
        storeComboBox.setItems(stores);
        storeComboBox.setItemLabelGenerator(Store::getName);

        configureGrid();
        configureComboBox();
        add(storeComboBox, saleGrid);
        updateSaleGrid();
    }

    private void updateSaleGrid() {
        if (storeComboBox.getValue() != null) {
            saleGrid.setItems(saleService.findTopFlower(storeComboBox.getValue().getId()));
        }
    }

    private void configureComboBox() {
        storeComboBox.addValueChangeListener(e -> updateSaleGrid());
    }

    private void configureGrid() {
        saleGrid.removeColumnByKey("flower");
        saleGrid.removeColumnByKey("totalQuantity");

        saleGrid.addColumn(dto -> dto.getFlower().getFlowerName()).setHeader("Цветок");
        saleGrid.addColumn(FlowerSalesDTO::getTotalQuantity).setHeader("Количество продаж");
    }
}
