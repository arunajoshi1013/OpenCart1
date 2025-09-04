package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.*;


public class ExcelUtility {
	
	
	public  FileInputStream fi;
    public FileOutputStream fo;
    public  XSSFWorkbook workbook;
    public XSSFSheet sheet;
    public XSSFRow row;
    public  XSSFCell cell;
    public  CellStyle style;
	 String path;
    
    
    public ExcelUtility(String path) {
    	
    	this.path=path;
    }

    // ✅ Corrected getRowCount()
    public  int getRowCount(String sheetName) throws IOException {
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);
        int rowcount = sheet.getLastRowNum(); // ✅ Correct: returns last row index
        workbook.close();
        fi.close();
        return rowcount;
    }

    // ✅ Get cell count in a specific row
    public  int getCellCount(String sheetName, int rownum) throws IOException {
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(rownum);
        int cellcount = (row != null) ? row.getLastCellNum() : 0;
        workbook.close();
        fi.close();
        return cellcount;
    }

    // ✅ Read cell data
    public  String getCellData( String sheetName, int rownum, int colnum) throws IOException {
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(rownum);
        cell = (row != null) ? row.getCell(colnum) : null;

        String data;
        try {
            DataFormatter formatter = new DataFormatter();
            data = formatter.formatCellValue(cell);
        } catch (Exception e) {
            data = "";
        }

        workbook.close();
        fi.close();
        return data;
    }

    // ✅ Write cell data
    public  void setCellData(String sheetName, int rownum, int colnum, String data) throws IOException {
    	File xlfile=new File(path);
    	if(!xlfile.exists())   //if file not exists create a new file
    	{
    		workbook = new XSSFWorkbook();
    		fo = new FileOutputStream(path);
    		workbook.write(fo);
           
    	}
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        
        
        if(workbook.getSheetIndex(sheetName)==-1)  //if sheet not exist then create a new sheet
          {
        	  workbook.createSheet(sheetName);
        	    sheet = workbook.getSheet(sheetName);
          }
        
       
      if(sheet.getRow(rownum)==null)     //if no row create a new row
      {
	   sheet.createRow(rownum);
	   row = sheet.getRow(rownum);
     }
       

        cell = row.createCell(colnum);
        cell.setCellValue(data);

        fo = new FileOutputStream(path);
        workbook.write(fo);
        workbook.close();
        fi.close();
        fo.close();
    }

    // ✅ Fill green color
    public  void fillGreenColor( String sheetName, int rownum, int colnum) throws IOException {
        fillColor(sheetName,rownum, colnum, IndexedColors.GREEN);
    }

    // ✅ Fill red color
    public void fillRedColor( String sheetName, int rownum, int colnum) throws IOException {
        fillColor(sheetName, rownum, colnum, IndexedColors.RED);
    }

    // ✅ Reusable method to fill cell color
    public void fillColor( String sheetName, int rownum, int colnum, IndexedColors color) throws IOException {
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);

        row = sheet.getRow(rownum);
        if (row == null) {
            row = sheet.createRow(rownum);
        }

        cell = row.getCell(colnum);
        if (cell == null) {
            cell = row.createCell(colnum);
        }

        style = workbook.createCellStyle();
        style.setFillForegroundColor(color.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cell.setCellStyle(style);

        fo = new FileOutputStream(path);
        workbook.write(fo);
        workbook.close();
        fi.close();
        fo.close();
    }
}


