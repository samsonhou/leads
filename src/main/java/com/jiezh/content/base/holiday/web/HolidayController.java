package com.jiezh.content.base.holiday.web;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.jiezh.content.base.holiday.service.HolidayService;
import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.content.base.pub.web.WebAction;
import com.jiezh.dao.base.holiday.HolidayVO;
import com.jiezh.dao.base.user.UserVO;

/**
 * 
 * 节假日维护
 * 
 * @author houjiaqiang
 * @since 2016年3月1日 下午5:14:53
 */

@Controller
@Scope("prototype")
@RequestMapping("/base/holiday/")
public class HolidayController extends WebAction {

	@Autowired
	HolidayService holidayService;

	/**
	 * 节假日查询
	 * 
	 * @param
	 * @return Exception
	 */
	@RequestMapping("index")
	public ModelAndView index() throws Exception {
		ModelAndView mv = new ModelAndView("base/holiday/list");
		Page<UserVO> page = new Page<UserVO>();
		mv.addObject("page", new PageInfo<UserVO>(page));
		return mv;
	}

	/**
	 * 
	 * 下载节假日模板
	 * 
	 * @param
	 * @return Exception
	 */
	@RequestMapping("downTemplate")
	public void downTemplate() throws Exception {
		String path = this.request.getSession().getServletContext().getRealPath("/") + "/res/pub/template/节假日模板.xls";
		InputStream fis = new BufferedInputStream(new FileInputStream(path));
		byte[] buffer = new byte[fis.available()];
		fis.read(buffer);
		fis.close();
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition",
				"attachment;filename=" + new String(("节假日模板.xls").getBytes(), "iso-8859-1"));
		OutputStream outputStream = response.getOutputStream();
		outputStream.write(buffer);
		outputStream.close();
		buffer = null;
	}

	/**
	 * 
	 * 根据年份查询节假日
	 * 
	 * @param
	 * @return Exception
	 */
	@RequestMapping("queryList")
	public ModelAndView queryList() throws Exception {
		ModelAndView mv = new ModelAndView("base/holiday/list");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		mv.addObject("startDate", startDate);
		mv.addObject("endDate", endDate);
		int currenPage = 1;
		if (request.getParameter("currenPage") != null && !"".equals(request.getParameter("currenPage"))) {
			currenPage = Integer.parseInt(request.getParameter("currenPage"));
		}
		mv.addObject("page", holidayService.queryHoliday(currenPage, startDate,endDate));
		return mv;
	}

	/**
	 * 修改节假日
	 * 
	 * @param
	 * @return Exception
	 */
	@RequestMapping("editHoliday")
	public ModelAndView editHoliday() throws Exception {
		AuthorUser user = this.getUser();
		HolidayVO formVo = (HolidayVO) this.getBean(HolidayVO.class);
		formVo.setModifyUser(user.getUserId());
		int flag = holidayService.modifyHolidayById(formVo);
		request.setAttribute("duplicatFlag", flag);
		return queryList();
	}

	/**
	 * 删除节假日
	 * 
	 * @param
	 * @return Exception
	 */
	@RequestMapping("deleteHoliday")
	public ModelAndView deleteHoliday() throws Exception {
		Long selectId = Long.valueOf(request.getParameter("selectID"));
		holidayService.removeHolidayById(selectId);
		return queryList();
	}

	/**
	 * 通过模板文件导入节假日
	 * 
	 * @param
	 * @return Exception
	 */
	@RequestMapping("importExcel")
	public ModelAndView importExcel(@RequestParam(value = "holidayExcel") MultipartFile file) throws Exception {
		AuthorUser user = this.getUser();
		String error = holidayService.importHoliday(file, user);
		request.setAttribute("error", error);
		return queryList();
	}
}
