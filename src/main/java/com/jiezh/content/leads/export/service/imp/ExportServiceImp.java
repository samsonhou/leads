package com.jiezh.content.leads.export.service.imp;

import java.io.ByteArrayOutputStream;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;

import com.jiezh.content.leads.export.service.ExportService;
/**
 * 导出excel
 * @author liangds
 *
 */
@Service("leads.export.service.ExportService")
public class ExportServiceImp implements ExportService{

	/**
	 * 导出2003格式
	 * @return
	 * @throws Exception 
	 */
	public byte[] export2003() throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Workbook wb = new HSSFWorkbook();
		CellStyle headCellStyle = wb.createCellStyle();
		headCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		int rowIndex=0;
		int rowLength=100;
		int cellLength=20;
		//create sheet
		Sheet sheet = wb.createSheet("2003");
		
		//create header
		Row header=sheet.createRow(rowIndex++);
		for(int i=0;i<cellLength;i++){
			Cell cell=header.createCell(i);
			cell.setCellValue("head"+i);
			cell.setCellStyle(headCellStyle);
		}
		//create rows
		for(;rowIndex<rowLength;rowIndex++){
			Row row = sheet.createRow(rowIndex);
			//create cells
			for(int i=0;i<cellLength;i++){
				row.createCell(i).setCellValue(rowIndex+"测试"+i);
			}
		}
		wb.write(out);
		return out.toByteArray();
	}

	/**
	 * 导出2003以上的格式
	 * @return
	 * @throws Exception 
	 */
	public byte[] export() throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		SXSSFWorkbook wb = new SXSSFWorkbook(500);
		CellStyle headCellStyle = wb.createCellStyle();
		headCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		wb.setCompressTempFiles(true);
		int rowIndex = 0;
		int rowLength = 1000;
		int cellLength = 20;
		// create sheet
		Sheet sheet = wb.createSheet("2007");
		// create header
		Row header = sheet.createRow(rowIndex++);
		for (int i = 0; i < cellLength; i++) {
			Cell cell=header.createCell(i);
			cell.setCellValue("head"+i);
			cell.setCellStyle(headCellStyle);
		}
		// create row
		for (; rowIndex < rowLength; rowIndex++) {
			Row row = sheet.createRow(rowIndex);
			// create cells
			for (int i = 0; i < cellLength; i++) {
				row.createCell(i).setCellValue(rowIndex + "测试" + i);
			}
		}
		wb.write(out);
		return out.toByteArray();
	}

}
