package utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelUtility {

    private static XSSFWorkbook workbook;
    private static Sheet sheet;
    private static String filePath;

    // Constructor
    public ExcelUtility(String excelPath, String sheetName) {
        try {
            filePath = excelPath;
            FileInputStream fis = new FileInputStream(excelPath);
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                throw new RuntimeException("Sheet: " + sheetName + " does not exist in " + excelPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get Row Count
    public int getRowCount() {
        return sheet.getPhysicalNumberOfRows();
    }

    // Get Column Count
    public int getColumnCount() {
        return sheet.getRow(0).getPhysicalNumberOfCells();
    }

    // Read Cell Data
    public String getCellData(int rowNum, int colNum) {
        try {
            Row row = sheet.getRow(rowNum);
            Cell cell = row.getCell(colNum);

            DataFormatter formatter = new DataFormatter();
            return formatter.formatCellValue(cell);
        } catch (Exception e) {
            return "";
        }
    }

    // Write Cell Data
    public void setCellData(int rowNum, int colNum, String data) {
        try {
            Row row = sheet.getRow(rowNum);

            if (row == null) {
                row = sheet.createRow(rowNum);
            }

            Cell cell = row.getCell(colNum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            cell.setCellValue(data);

            FileOutputStream fos = new FileOutputStream(filePath);
            workbook.write(fos);
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Close Workbook
    public void close() {
        try {
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
