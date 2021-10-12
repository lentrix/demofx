package com.lentrix.demofx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.time.LocalDate;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
        Workbook wb = new HSSFWorkbook();

        Sheet sheet1 = wb.createSheet("First Sheet");
        Row row1 = sheet1.createRow(0);
        row1.createCell(0).setCellValue("This is just a test");
        row1.createCell(1).setCellValue(12345);
        row1.createCell(2).setCellValue(LocalDate.now());

        try(FileOutputStream fos = new FileOutputStream("excel_file.xls")) {
            wb.write(fos);
            wb.close();
            fos.close();
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}