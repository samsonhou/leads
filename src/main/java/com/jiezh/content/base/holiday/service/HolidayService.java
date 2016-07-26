/**
 * 
 */
package com.jiezh.content.base.holiday.service;

import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.dao.base.holiday.HolidayVO;

/**
 * 节假日维护service类接口
 * @author houjiaqiang
 * @since 2016年2月22日 下午5:09:53
 */
public interface HolidayService {
	public PageInfo<HolidayVO> queryHoliday(int currentPage,String startDate,String endDate);
	public int modifyHolidayById(HolidayVO holidayVo);
	public void removeHolidayById(long selectId);
	public String importHoliday(MultipartFile file,AuthorUser user) throws Exception;
}
