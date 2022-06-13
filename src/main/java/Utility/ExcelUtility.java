package Utility;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class ExcelUtility {
    private FileInputStream fis;
    private FileOutputStream fileOut;
    private Workbook wb;
    private Sheet sh;
    private Cell cell;
    private Row row;
    private String excelFilePath;
    private Map<String, Integer> columns = new HashMap<>();

    /**
     * set Excel File
     * @param excelPath
     * @param sheetName
     * @throws Exception
     */
    public void setExcelFile(String excelPath, String sheetName) throws Exception{
        try {
            File f = new File(excelPath);

            if (!f.exists()) {
                f.createNewFile();
                System.out.println("File doesn't exist, so created!");
            }

            fis = new FileInputStream(excelPath);
            wb = WorkbookFactory.create(fis);
            sh = wb.getSheet(sheetName);
            if (sh == null) {
                sh = wb.createSheet(sheetName);
            }
            this.excelFilePath = excelPath;

            sh.getRow(0).forEach(cell ->{
                columns.put(cell.getStringCellValue(), cell.getColumnIndex());
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Get data of cell with rownum and colnum
     * @param rownum
     * @param colnum
     * @return
     * @throws Exception
     */
    public String getCellData(int rownum, int colnum) throws  Exception {
        try {
            cell = sh.getRow(rownum).getCell(colnum);
            String cellData = null;
            switch (cell.getCellType()) {
                case STRING:
                    cellData = cell.getStringCellValue();
                    break;
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)){
                        cellData = String.valueOf(cell.getDateCellValue());
                    } else {
                        cellData = String.valueOf((long)cell.getNumericCellValue());
                    }
                    break;
                case BOOLEAN:
                    cellData = Boolean.toString(cell.getBooleanCellValue());
                    break;
                case BLANK:
                    cellData = "";
                    break;
            }
            return cellData;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Get date of cell with columnName and rowNum
     * @param columnName
     * @param rowNum
     * @return
     * @throws Exception
     */
    public String getCellData(String columnName, int rowNum) throws Exception{
        return getCellData(rowNum, columns.get(columnName));
    }

    /**
     * set data of cell
     * @param text
     * @param rowNum
     * @param colNum
     * @throws Exception
     */
    public void setCellData(String text, int rowNum, int colNum) throws Exception {
        try {
            row = sh.getRow(rowNum);
            if (row == null){
                row = sh.createRow(rowNum);
            }
            cell = row.getCell(colNum);

            if (cell == null) {
                cell = row.createCell(colNum);
            }
            cell.setCellValue(text);

            fileOut = new FileOutputStream(excelFilePath);
            wb.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (Exception e) {
            throw (e);
        }
    }

    /**
     * Get last number of rows
     * @return
     */
    public int getLastRowNum() {
        return sh.getLastRowNum();
    }
}
