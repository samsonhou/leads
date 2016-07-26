/**
 * 
 */
package com.jiezh.dao.base.holiday;

import java.util.Date;

import com.jiezh.content.base.pub.web.GeneralBean;

/**
 * 节假日映射类
 * @author houjiaqiang
 * @since 2016年3月2日 上午10:13:16
 */
public class HolidayVO extends GeneralBean{
	/* 数据ID */
	private long id;
	/* 节假日 */
	private Date holiday;
	/* 节假日类型 */
	private String holidayType;
	/* 创建时间 */
	private Date createDate;
	/* 创建人 */
	private long createUser;
	/* 修改时间 */
	private Date modifyDate;
	/* 修改人 */
	private long modifyUser;
	
	/* 创建人姓名 */
	private String modifyUserName;
	
	public HolidayVO(){
		
	}

	/**
	 * @return the id long
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the holiday Date
	 */
	public Date getHoliday() {
		return holiday;
	}

	/**
	 * @param holiday the holiday to set
	 */
	public void setHoliday(Date holiday) {
		this.holiday = holiday;
	}

	/**
	 * @return the holidayType String
	 */
	public String getHolidayType() {
		return holidayType;
	}

	/**
	 * @param holidayType the holidayType to set
	 */
	public void setHolidayType(String holidayType) {
		this.holidayType = holidayType;
	}

	/**
	 * @return the createDate Date
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the createUser long
	 */
	public long getCreateUser() {
		return createUser;
	}

	/**
	 * @param createUser the createUser to set
	 */
	public void setCreateUser(long createUser) {
		this.createUser = createUser;
	}

	/**
	 * @return the modifyDate Date
	 */
	public Date getModifyDate() {
		return modifyDate;
	}

	/**
	 * @param modifyDate the modifyDate to set
	 */
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	/**
	 * @return the modifyUser long
	 */
	public long getModifyUser() {
		return modifyUser;
	}

	/**
	 * @param modifyUser the modifyUser to set
	 */
	public void setModifyUser(long modifyUser) {
		this.modifyUser = modifyUser;
	}

	/**
	 * @return the modifyUserName String
	 */
	public String getModifyUserName() {
		return modifyUserName;
	}

	/**
	 * @param modifyUserName the modifyUserName to set
	 */
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	
	
	
}
