/**
 * 
 */
package com.jiezh.dao.base.holiday;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 节假日Dao接口
 * @author houjiaqiang
 * @since 2016年3月2日 上午10:21:43
 */
public interface HolidayDao {
	public List<HolidayVO> selectHolidayByYear(Map<String, String> map);
	public int selectIsExistByDate(Date date);
	public void updateHolidayById(HolidayVO holidayVo);
	public void deleteHolidayById(long selectId);
	public void batchInsert(List<HolidayVO> holidayList);
}
