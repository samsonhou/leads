package com.jiezh.content.base.pub.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 读取Excel表格的功能类
 * @author houjiaqiang
 */
public class ExcelImportUtil {
    private POIFSFileSystem fs;
    private Workbook wb;
    private Sheet sheet;
    private Row row;
    private List<Object[]> xlsList = null;
    private int startRow = 0;
    private int startCol = 0;
    private int startSheet = 0;
    private int maxRow = 0;
    private int keyCol = 0;
    FormulaEvaluator evaluator = null;
    CellValue  cellValue =null;
    

    /**
     * 读取Excel表格表头的内容
     * 
     * @param InputStream
     * @return String 表头内容的数组
     * 
     */
    public String[] readExcelTitle(InputStream is) {
        //Formula
        if(evaluator == null){
            evaluator = wb.getCreationHelper().createFormulaEvaluator();
        }
        
        sheet = wb.getSheetAt(0);
        row = sheet.getRow(0);
        // 标题总列数
        int colNum = row.getPhysicalNumberOfCells();
        String[] header = new String[colNum];
        for (int i = 0; i < colNum; i++) {
            header[i] = getStringCellValue(row.getCell(i));
        }
        return header;
    }

    /**
     * 读取Excel数据内容
     * 
     * @param InputStream
     * @return Map 包含单元格数据内容的Map对象
     */
    public List<Object[]> readExcelContent() {
        if(wb == null){
            return null;
        }
        
        //设置所excel中读取的sheet序列
        sheet = wb.getSheetAt(startSheet);
        
        //Formula
        if(evaluator == null){
            evaluator = wb.getCreationHelper().createFormulaEvaluator();
        }
        
        //得到总行数
        int rowNum = sheet.getLastRowNum();
        row = sheet.getRow(0);
        int colNum = row.getPhysicalNumberOfCells();
        if(colNum>0){
            xlsList = new ArrayList<Object[]>();
        }
        Object[] objArr = null;
        
        //正文内容应该从第二行开始,第一行为表头的标题
        int i = 0;
        int j = 0;
        
        //最大行数限制
        rowNum = rowNum > maxRow ? maxRow : rowNum;
        for (i = startRow; i <= rowNum; i++) {
            row = sheet.getRow(i);
            j = startCol;
            objArr = new Object[colNum-startCol];
            //判断当前行是否为空行
            if("".equals(getStringCellValue(row.getCell(keyCol)))){
                break;
            }
            while (j < colNum) {
                objArr[j] = StringUtils.trim(getStringCellValue(row.getCell(j)));
                j++;
            }
            xlsList.add(objArr);
        }
        return xlsList;
    }

    /**
     * 获取单元格数据内容为字符串类型的数据
     * 
     * @param cell
     *            Excel单元格
     * @return String 单元格数据内容
     */
    private String getStringCellValue(Cell cell) {
        String strCell = "";
        if (cell == null)
            return "";
        switch (cell.getCellType()) {
        case Cell.CELL_TYPE_STRING:
            strCell = cell.getRichStringCellValue().getString();
            break;
        case Cell.CELL_TYPE_NUMERIC:
            // strCell = String.valueOf(cell.getNumericCellValue());
            strCell = getDate(cell);
            break;
        case Cell.CELL_TYPE_BOOLEAN:
            strCell = String.valueOf(cell.getBooleanCellValue());
            break;
        case Cell.CELL_TYPE_BLANK:
            strCell = "";
            break;
        case Cell.CELL_TYPE_FORMULA:
             cellValue = evaluator.evaluate(cell);
             strCell =getFormulaCellValue(cell);
            break;
        default:
            strCell = "";
            break;
        }
        if (strCell.equals("") || strCell == null) {
            return "";
        }
        
        return strCell;
    }
    /**
     * 获取单元格数据内容为字符串类型的数据
     * 
     * @param cell
     *            Excel单元格
     * @return String 单元格数据内容
     */
    private String getFormulaCellValue(Cell cell) {
        String strCell = "";
        switch (cellValue.getCellType()) {
            case Cell.CELL_TYPE_BOOLEAN:
                strCell = String.valueOf(cellValue.getBooleanValue());
                break;
            case Cell.CELL_TYPE_NUMERIC:
                strCell = String.valueOf(cellValue.getNumberValue());
                if(strCell.endsWith(".0")){
                    strCell = strCell.replace(".0", "");
                }
                break;
            case Cell.CELL_TYPE_STRING:
                strCell = String.valueOf(cellValue.getStringValue());
                break;
            case Cell.CELL_TYPE_BLANK:
                break;
            case Cell.CELL_TYPE_ERROR:
                break;
                  // CELL_TYPE_FORMULA will never happen
            case Cell.CELL_TYPE_FORMULA:
                 break;
         }
        return strCell;
    }
    public String getDate(Cell cell) {
        if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
            double d = cell.getNumericCellValue();
            Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(d);
            return new SimpleDateFormat("yyyy-MM-dd").format(date);
        } else {
            return String.valueOf((long)cell.getNumericCellValue());
        }
    }

    public String getInt(Cell cell) {
        String str_value = cell.getNumericCellValue() + "";
        int dotPosition = str_value.indexOf(".");
        String str_dot = str_value.substring(dotPosition + 1);
        String ret_value = null;
        if (str_dot.length() == 1 && str_dot.equals("0")) {
            ret_value = str_value.substring(0, dotPosition);
        } else {
            ret_value = str_value;
        }

        return ret_value;
    }
    
    public List<Object[]> importContentFromExcel(File file,Map<String,String> paramMap) throws Exception {
        List<Object[]> list= null;
        
        try {
            
            //Map方式获取相关配置信息(公共配置)
            if(paramMap!=null){
                startRow = Integer.valueOf(paramMap.get("xlsStartRow"));
                startCol = Integer.valueOf(paramMap.get("xlsStartCol"));
                startSheet = Integer.valueOf(paramMap.get("xlsSheet"));
                maxRow = Integer.valueOf(paramMap.get("xlsMaxRow"));
                keyCol = Integer.valueOf(paramMap.get("xlsIsEmptyRow"));
            }
            if(startRow < 1 || maxRow < 1){
                return null;
            };
            
            String exName=file.getName();
            if(exName.lastIndexOf(".")>0){
                exName = exName.substring(exName.lastIndexOf(".")+1).toLowerCase();  
            }else{
                return null;
            }
            
            wb = null;
            InputStream is= new FileInputStream(file);
            if(exName.equals("xlsx")){
                wb = new XSSFWorkbook(is);
            }else if(exName.equals("xls")){
                fs = new POIFSFileSystem(is);
                wb = new HSSFWorkbook(fs);
                
            }
            
            if(wb == null){
                return null;
            }
            
            list = this.readExcelContent();
            
            Object[] obj= null;
            for(int i=0 ; i<list.size(); i++){
                obj=(Object[])list.get(i);
                String strRow=null;
                for(Object str : obj){
                    strRow+="\t"+str.toString();
                }
                System.out.println("第"+i+"行:\t"+strRow);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
       
        return list;
    }
    /**
     * 
     * 导入excel将数据转换为list
     * @param map 
     *  paramMap.put("xlsStartRow", "1"); 读取数据起始行
	 *	paramMap.put("xlsStartCol", "0"); 读取数据起始列
	 *	paramMap.put("xlsSheet", "0"); 读取数据其实sheet页
	 *	paramMap.put("xlsMaxRow", "10000"); 读取最大行数
	 *	paramMap.put("xlsIsEmptyRow", "0"); 设置空行
     * @return
     * Exception
     */
    public List<Object[]> importContentFromExcelInputStream(InputStream is,Map<String,String> paramMap, String fileName) throws Exception {
        List<Object[]> list= null;
        
        try {
            if(StringUtils.isBlank(fileName)){
                return null;
            }
            
            //Map方式获取相关配置信息(公共配置)
            if(paramMap!=null){
                startRow = Integer.valueOf(paramMap.get("xlsStartRow"));
                startCol = Integer.valueOf(paramMap.get("xlsStartCol"));
                startSheet = Integer.valueOf(paramMap.get("xlsSheet"));
                maxRow = Integer.valueOf(paramMap.get("xlsMaxRow"));
                keyCol = Integer.valueOf(paramMap.get("xlsIsEmptyRow"));
            }
            if(startRow < 1 || maxRow < 1){
                return null;
            };
            String exName = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
            
            
            wb = null;
            if("xlsx".equals(exName)){
                wb = new XSSFWorkbook(is);
            }else if("xls".equals(exName)){
                fs = new POIFSFileSystem(is);
                wb = new HSSFWorkbook(fs);
                
            }
            
            if(wb == null){
                return null;
            }
            
            list = this.readExcelContent();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
       
        return list;
    }
}