package com.jiezh.content.leads.export.web;

import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.jiezh.content.base.pub.web.WebAction;
import com.jiezh.content.leads.export.service.ExportService;

/**
 * excel 导出
 * 2003 及以上
 * @author liangds
 *
 */

@Controller
@Scope("prototype")
@RequestMapping("/leads/export")
public class ExportController extends WebAction {
	
	@Autowired
	ExportService exportService;
	
	@RequestMapping("excel/xls")
	public void excel2003() throws Exception {
		response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(("2003测试"+".xls").getBytes(), "iso-8859-1"));
        OutputStream outputStream=response.getOutputStream();
        outputStream.write(exportService.export2003());
        outputStream.close();
	}
	@RequestMapping("excel/xlsx")
	public void excel() throws Exception {
		response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(("2007测试"+".xlsx").getBytes(), "iso-8859-1"));
        OutputStream outputStream=response.getOutputStream();
        outputStream.write(exportService.export());
        outputStream.close();
	}

}
