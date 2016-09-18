package com.jiezh.content.leads.mileage.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.jiezh.content.base.pub.Env;

public class ExcelUtil {

    private final static String EXCEL_XLS = ".xls"; // 2003版以下的Excel

    private final static String EXCEL_XLSX = ".xlsx"; // 2007版以上的Excel

    // ######################## EXCEL导出 #########################

    public static byte[] export2Excel(List<String> titles, List<String> getMethods, List<Object> dataList) throws Exception {
        return export2Excel("sheet1", titles, getMethods, dataList, null);
    }

    public static byte[] export2Excel(List<String> titles, List<String> getMethods, List<Object> dataList, Map<Integer, Integer> widthMap)
        throws Exception {
        return export2Excel("sheet1", titles, getMethods, dataList, widthMap);
    }

    public static byte[] export2Excel(List<String> titles, List<Map<Integer, Object>> dataList) throws Exception {
        return export2Excel("sheet1", titles, dataList, null);
    }

    public static byte[] export2Excel(List<String> titles, List<Map<Integer, Object>> dataList, Map<Integer, Integer> widthMap) throws Exception {
        return export2Excel("sheet1", titles, dataList, widthMap);
    }

    /**
     * 数据导出到EXCEL表格 - 填充已格式化的Excel表格数据
     * 
     * @param sheetName Sheet页名称
     * @param titles 标题列
     * @param dataList Excel表格数据
     * @param widthMap 指定列宽
     * @return
     * @throws Exception
     */
    public static byte[] export2Excel(String sheetName, List<String> titles, List<Map<Integer, Object>> dataList, Map<Integer, Integer> widthMap)
        throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            Workbook workbook = new HSSFWorkbook();
            // 创建sheet页
            Sheet sheet = createExcel(workbook, sheetName, titles, widthMap);
            // 写入数据
            for (int i = 0; i < dataList.size(); i++) {
                Row row = sheet.createRow(i + 1); // 从第二行开始填充
                Map<Integer, Object> valueMap = dataList.get(i);
                for (Map.Entry<Integer, Object> entry : valueMap.entrySet()) {
                    Cell dataCell = row.createCell(entry.getKey());
                    setCellValue(dataCell, entry.getValue());
                    dataCell.setCellStyle(initDataStyle(workbook));
                }
            }
            workbook.write(out);
            return out.toByteArray();
        } catch (IOException e) {
            LogFactory.getLog(ExcelUtil.class).error("写入数据出错！", e);
            throw new Exception("导出数据错误！");
        }
    }

    /**
     * 数据导出到EXCEL表格 - 动态将数据添加到Excel表格
     * 
     * @param sheetName Sheet页名称
     * @param titles 标题列
     * @param methodNames GET方法名
     * @param dataList 需要导出的数据
     * @param widthMap 指定列宽
     * @return
     * @throws Exception
     */
    public static byte[] export2Excel(String sheetName, List<String> titles, List<String> methodNames, List<Object> dataList,
        Map<Integer, Integer> widthMap) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            Workbook workbook = new HSSFWorkbook();
            Sheet sheet = createExcel(workbook, sheetName, titles, widthMap);
            // 写入数据
            for (int i = 0; i < dataList.size(); i++) {
                Row row = sheet.createRow(i + 1); // 从第二行开始填充
                for (int j = 0; j < methodNames.size(); j++) {
                    Cell dataCell = row.createCell(j);
                    setCellValue(dataCell, invokeGetMethod(dataList.get(i), methodNames.get(j)));
                    dataCell.setCellStyle(initDataStyle(workbook));
                }
            }
            workbook.write(out);
            return out.toByteArray();
        } catch (IOException e) {
            LogFactory.getLog(ExcelUtil.class).error("写入数据出错！", e);
            throw new Exception("导出数据错误！");
        }
    }

    // 构建EXCEL表格
    private static Sheet createExcel(Workbook workbook, String sheetName, List<String> titles, Map<Integer, Integer> widthMap) {
        // 创建sheet页
        Sheet sheet = workbook.createSheet(sheetName);
        // 创建表头 - 第一行作为表头
        Row titleRow = sheet.createRow(0);
        titleRow.setHeight((short) 350);
        // 写入表头名称
        for (int i = 0; i < titles.size(); i++) {
            Cell titleCell = titleRow.createCell(i);
            titleCell.getSheet().autoSizeColumn(i);
            titleCell.setCellValue(titles.get(i));
            titleCell.setCellStyle(initTitleStyle(workbook));
        }
        // 设置制定列的宽度
        if (widthMap != null) {
            Set<Entry<Integer, Integer>> set = widthMap.entrySet();
            for (Entry<Integer, Integer> entry : set) {
                int width = entry.getValue().intValue() > 255 ? 255 : entry.getValue().intValue();
                sheet.setColumnWidth(entry.getKey().intValue(), width * 256);
            }
        }
        return sheet;
    }

    // 设置数值类型
    private static void setCellValue(Cell dataCell, Object value) {
        if (value == null) {
            dataCell.setCellType(Cell.CELL_TYPE_BLANK);
            dataCell.setCellValue("");
        } else if (value instanceof BigDecimal) {
            dataCell.setCellType(Cell.CELL_TYPE_NUMERIC);
            dataCell.setCellValue(((BigDecimal) value).doubleValue());
        } else if (value instanceof Integer) {
            dataCell.setCellType(Cell.CELL_TYPE_NUMERIC);
            dataCell.setCellValue(((Integer) value).intValue());
        } else if (value instanceof java.util.Date) {
            if (DateFormat.getTimeInstance().format(value).equals("0:00:00")) {
                value = new SimpleDateFormat("yyyy-MM-dd").format(value);
            } else {
                value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(value);
            }
            dataCell.setCellType(Cell.CELL_TYPE_STRING);
            dataCell.setCellValue((String) value);
        } else {
            dataCell.setCellType(Cell.CELL_TYPE_STRING);
            dataCell.setCellValue((String) value);
        }
    }

    // 设置表头字体
    private static Font initTitleFont(Workbook workbook) {
        Font titleFont = workbook.createFont(); // 表头字体
        titleFont.setFontName("宋体");
        titleFont.setFontHeightInPoints((short) 12);
        titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        titleFont.setCharSet(Font.DEFAULT_CHARSET);
        titleFont.setColor(IndexedColors.BLACK.index);
        return titleFont;
    }

    // 设置内容字体
    private static Font initDataFont(Workbook workbook) {
        Font dataFont = workbook.createFont(); // 表头字体
        dataFont.setFontName("宋体");
        dataFont.setFontHeightInPoints((short) 11);
        dataFont.setCharSet(Font.DEFAULT_CHARSET);
        dataFont.setColor(IndexedColors.BLACK.index);
        return dataFont;
    }

    // 设置表头样式
    private static CellStyle initTitleStyle(Workbook workbook) {
        CellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setAlignment(CellStyle.ALIGN_CENTER); // 表头样式
        titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        titleStyle.setFont(initTitleFont(workbook));
        titleStyle.setWrapText(true);
        return titleStyle;
    }

    // 设置内容表格样式
    private static CellStyle initDataStyle(Workbook workbook) {
        CellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setAlignment(CellStyle.ALIGN_LEFT);
        dataStyle.setVerticalAlignment(CellStyle.VERTICAL_TOP);
        dataStyle.setFont(initDataFont(workbook));
        dataStyle.setWrapText(true);
        return dataStyle;
    }

    // 动态执行对象的GET方法
    public static Object invokeGetMethod(Object obj, String methodName) throws Exception {
        try {
            Method method = obj.getClass().getMethod(methodName);
            method.setAccessible(true);
            return method.invoke(obj, new Class[] {});
        } catch (NoSuchMethodException | SecurityException e) {
            LogFactory.getLog(ExcelUtil.class).error("GET方法无效，请检查方法名或方法范围！", e);
            throw new Exception("导出数据错误！");
        } catch (IllegalAccessException e) {
            LogFactory.getLog(ExcelUtil.class).error("GET方法无效，没有此方法的访问权限！", e);
            throw new Exception("导出数据错误！");
        } catch (IllegalArgumentException e) {
            LogFactory.getLog(ExcelUtil.class).error("方法参数不正确！", e);
            throw new Exception("导出数据错误！");
        } catch (InvocationTargetException e) {
            LogFactory.getLog(ExcelUtil.class).error("执行GET方法时发生错误！", e);
            throw new Exception("导出数据错误！");
        }
    }

    // ######################## EXCEL导入 #########################

    /**
     * EXCEL文件导入
     * 
     * @param in
     * @param fileName
     * @return
     * @throws Exception
     */
    public static List<List<Object>> importFromExcel(InputStream in, String fileName, Map<String, Object> paraMap) throws Exception {
        List<List<Object>> rowList = new ArrayList<List<Object>>(100);
        try {
            int srownum = (Integer) paraMap.get(Env.ROW_START_KEY) - 1; // 统计开始行
            int scolnum = convert2Number(paraMap.get(Env.COL_START_KEY)) - 1; // 统计开始列
            int ecolnum = convert2Number(paraMap.get(Env.COL_END_KEY)); // 统计结束列
            // 创建Excel工作薄
            Workbook workbook = getWorkbook(in, fileName);
            // 遍历Excel中所有的sheet页
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);
                if (sheet == null) {
                    throw new Exception("导入文件与模板不匹配，无法获取Sheet页信息！");
                }
                // 遍历当前sheet页中的所有行
                for (int j = srownum; j < sheet.getPhysicalNumberOfRows(); j++) {
                    Row row = sheet.getRow(j);
                    if (row == null || row.getFirstCellNum() < 0) {
                        // 过滤掉空行
                        continue;
                    } else {
                        // 遍历所有的列
                        List<Object> cellList = new ArrayList<Object>();
                        for (int k = scolnum; k < ecolnum; k++) {
                            Cell cell = row.getCell(k);
                            if (cell == null) {
                                cellList.add("");
                                continue;
                            }
                            cellList.add(getCellValue(cell));
                        }
                        rowList.add(cellList);
                    }
                }
            }
            return rowList;
        } catch (Exception e) {
            LogFactory.getLog(ExcelUtil.class).error(e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }

    // 创建EXCEL工作薄对象
    public static Workbook getWorkbook(InputStream is, String fileName) throws Exception {
        Workbook workbook = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (EXCEL_XLS.equals(fileType)) {
            workbook = new HSSFWorkbook(is); // 2003-
        } else if (EXCEL_XLSX.equals(fileType)) {
            workbook = new XSSFWorkbook(is); // 2007+
        } else {
            throw new Exception("导入文件格式不正确！");
        }
        return workbook;
    }

    // 获取EXCEL表格内容值
    public static Object getCellValue(Cell cell) throws Exception {
        String value = null;
        DecimalFormat intFormat = new DecimalFormat("#"); // 格式化number String字符
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd"); // 日期格式化
        DecimalFormat decimalFormat = new DecimalFormat("#.##"); // 格式化数字
        try {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    value = cell.getRichStringCellValue().getString();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    // 是否日期类型
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        value = dateFormat.format(cell.getDateCellValue());
                    } else {
                        // 处理数字类型
                        String tempValue = cell.getNumericCellValue() + "";
                        if (tempValue.endsWith(".0")) {
                            value = intFormat.format(cell.getNumericCellValue());
                        } else {
                            value = decimalFormat.format(cell.getNumericCellValue());
                        }
                    }
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    value = cell.getBooleanCellValue() ? "1" : "0";
                    break;
                case Cell.CELL_TYPE_BLANK:
                    value = "";
                    break;
                default:
                    break;
            }
            return value == null ? "" : value.trim();
        } catch (Exception e) {
            LogFactory.getLog(ExcelUtil.class).error(e.getMessage(), e);
            throw new Exception("导入数据类型转换错误！");
        }
    }

    public static int convert2Number(Object col) throws Exception {
        int colnum = 0;
        if (col instanceof String) {
            String colstr = ((String) col).toUpperCase();
            for (int i = 0; i < colstr.length(); i++) {
                char c = colstr.charAt(colstr.length() - i - 1);
                int num = (int) (c - 'A' + 1) * (int) Math.pow(26, i);
                colnum += num;
            }
        } else if (col instanceof Integer) {
            colnum = (Integer) col;
        } else {
            throw new Exception("开始列/结束列填写错误！");
        }
        return colnum;
    }
}
