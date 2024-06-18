package com.example.flowersdelivery.ui.form;

import com.example.flowersdelivery.backend.service.FlowerService;
import com.example.flowersdelivery.backend.service.SaleService;
import com.example.flowersdelivery.backend.service.StoreService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.spring.annotation.UIScope;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
@UIScope
public class ReportsForm extends FormLayout {

    private Dialog topFlowerDialog;
    private Button report1 = new Button("Топ цветов", click -> {
        topFlowerDialog.open();
    });
    private Button report2 = new Button("Обороты", click -> generateExcel());
    private SaleService saleService;

    public ReportsForm(SaleService saleService, StoreService storeService) {
        this.saleService = saleService;
        topFlowerDialog = new Dialog(new TopFlowerForm(storeService.findAll(), saleService));
        topFlowerDialog.setHeight("65%");
        topFlowerDialog.setWidth("70%");
        add(report1, report2);
    }

    private void generateExcel() {
        List<Map<String, Object>> data = saleService.rotation();
        try {
            Workbook workbook = new HSSFWorkbook();

            Sheet sheet = workbook.createSheet("Лист 1");

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Название магазина");
            headerRow.createCell(1).setCellValue("Цена поставок");
            headerRow.createCell(2).setCellValue("Цена продаж");
            headerRow.createCell(3).setCellValue("Разница по сумме");

            int rowNum = 1;
            for (Map<String, Object> rowData : data) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue((String) rowData.get("storeName")); // Имя магазина
                row.createCell(1).setCellValue((Double) rowData.get("totalSupPrice")); // Разница
                row.createCell(2).setCellValue((Double) rowData.get("totalSalePrice")); // Разница
                row.createCell(3).setCellValue((Double) rowData.get("difference")); // Разница
            }


            FileOutputStream fileOut = new FileOutputStream("обороты.xls");
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
            System.out.println("Excel файл успешно создан!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
