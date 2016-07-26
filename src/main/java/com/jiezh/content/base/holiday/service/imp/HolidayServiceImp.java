/**
 * 
 */
package com.jiezh.content.base.holiday.service.imp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiezh.content.base.holiday.service.HolidayService;
import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.content.base.pub.util.ExcelImportUtil;
import com.jiezh.dao.base.holiday.HolidayDao;
import com.jiezh.dao.base.holiday.HolidayVO;

/**
 * 锁定用户service实现类
 * 
 * @author houjiaqiang
 * @since 2016年2月22日 下午5:13:39
 */
@Service("base.user.holidayService")
public class HolidayServiceImp implements HolidayService {

	@Autowired
	HolidayDao holidayDao;

	/**
	 * 查询节假日
	 */
	@Override
	public PageInfo<HolidayVO> queryHoliday(int currentPage, String startDate, String endDate) {
		PageHelper.startPage(currentPage, 10);
		Map<String, String> map = new HashMap<String, String>();
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		Page<HolidayVO> page = (Page<HolidayVO>) holidayDao.selectHolidayByYear(map);
		if (page == null) {
			page = new Page<HolidayVO>();
		}
		return new PageInfo<HolidayVO>(page);
	}

	/**
	 * 修改节假日
	 */
	@Override
	public int modifyHolidayById(HolidayVO holidayVo) {
		int flag = 0;
		flag = holidayDao.selectIsExistByDate(holidayVo.getHoliday());
		if (flag > 0) {
			return flag;
		}

		holidayDao.updateHolidayById(holidayVo);
		return flag;
	}

	/**
	 * 删除节假日
	 * 
	 * @param selectId
	 *            ID
	 */
	@Override
	public void removeHolidayById(long selectId) {
		holidayDao.deleteHolidayById(selectId);
	}

	/**
	 * 导入节假日
	 */
	@Override
	public String importHoliday(MultipartFile file, AuthorUser user) throws Exception {
		ExcelImportUtil excelUtil = new ExcelImportUtil();
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("xlsStartRow", "1");
		paramMap.put("xlsStartCol", "0");
		paramMap.put("xlsSheet", "0");
		paramMap.put("xlsMaxRow", "10000");
		paramMap.put("xlsIsEmptyRow", "0");
		List<Object[]> list = excelUtil.importContentFromExcelInputStream(file.getInputStream(), paramMap,
				file.getOriginalFilename());
		List<HolidayVO> holidayList = new ArrayList<HolidayVO>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String error = null;
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			HolidayVO holidayVo = new HolidayVO();
			Date holiday = sdf.parse(obj[0].toString());
			int flag = holidayDao.selectIsExistByDate(holiday);
			if (flag != 0) {
				error = "第" + (i + 1) + "行日期重复！";
				break;
			}
			if (obj[0] != null)
				try {
					holidayVo.setHoliday(sdf.parse(obj[0].toString()));
				} catch (ParseException e) {
					error = "第" + (i + 1) + "行日期格式不正确！";
					e.printStackTrace();
					break;
				}
			if (obj[1] != null)
				holidayVo.setHolidayType("假日".equals(obj[1].toString()) ? "1"
						: "节日".equals(obj[1].toString()) ? "2" : "自定义".equals(obj[1].toString()) ? "3" : "4");
			if (holidayVo.getHolidayType().equals("4")) {
				error = "第" + (i + 1) + "行节假日类型不正确！";
				break;
			}

			holidayVo.setCreateUser(user.getUserId());
			holidayVo.setModifyUser(user.getUserId());
			holidayList.add(holidayVo);
		}
		if (error == null)
			holidayDao.batchInsert(holidayList);
		return error;
	}

}
