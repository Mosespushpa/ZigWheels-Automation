package utilities;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import pageObjects.UpComingBikes;

public class ExcelUtility {
    FileInputStream file;
    XSSFWorkbook workbook;
    XSSFSheet sheet;
    XSSFRow row;
    XSSFCell cell;
    String data="";
    String path;
    public ExcelUtility(String path){
        this.path=path;
    }

    public String getCellValue(String sheetName,int rowNum, int cellNum) throws IOException {

        file=new FileInputStream(path);
        workbook=new XSSFWorkbook(file);
        sheet=workbook.getSheet(sheetName);
        row=sheet.getRow(rowNum);
        cell=row.getCell(cellNum);

        DataFormatter df=new DataFormatter();
        try{
            data=df.formatCellValue(cell);
        }
        catch (Exception e) {
            data="";
        }

        workbook.close();
        file.close();

        return data;

    }

    public void setCellValue(String sheetName, int rowNum, int cellNum, String data) throws IOException {
        FileInputStream file = new FileInputStream(path);
        workbook = new XSSFWorkbook(file);
        sheet = workbook.getSheet(sheetName);

        row = sheet.getRow(rowNum);
        if (row == null) {
            row = sheet.createRow(rowNum);
        }

        cell = row.createCell(cellNum);
        cell.setCellValue(data);

        file.close();
        FileOutputStream out = new FileOutputStream(path);
        workbook.write(out);
        out.close();
        workbook.close();
    }

}
