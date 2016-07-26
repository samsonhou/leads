package com.jiezh.dao.base.organ;

import com.jiezh.content.base.pub.web.GeneralBean;
import java.util.Date;

public class OrganVO extends GeneralBean{
    private Integer id;

    private String organName;

    private String isnew;

    private String organCode;

    private Date createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public String getOrganCode() {
        return organCode;
    }

    public void setOrganCode(String organCode) {
        this.organCode = organCode;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

	/**
	 * @return the isnew
	 */
	public String getIsnew() {
		return isnew;
	}

	/**
	 * @param isnew the isnew to set
	 */
	public void setIsnew(String isnew) {
		this.isnew = isnew;
	}
    
}