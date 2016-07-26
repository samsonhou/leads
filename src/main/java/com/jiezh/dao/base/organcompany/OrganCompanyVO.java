package com.jiezh.dao.base.organcompany;

import com.jiezh.content.base.pub.web.GeneralBean;
import java.util.Date;

public class OrganCompanyVO extends GeneralBean {

    private String isnew;

    private String organId = "";

    private String organCode = "";

    private String organName = "";

    private String parentId = "";

    private String name = "";

    private String abbrName = "";

    private String address = "";

    private String status = "";

    private Date createDate;

    // add by houjq for lzm-89
    private String organLevel;

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public String getOrganCode() {
        return organCode;
    }

    public void setOrganCode(String organCode) {
        this.organCode = organCode;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbrName() {
        return abbrName;
    }

    public void setAbbrName(String abbrName) {
        this.abbrName = abbrName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the organName
     */
    public String getOrganName() {
        return organName;
    }

    /**
     * @param organName the organName to set
     */
    public void setOrganName(String organName) {
        this.organName = organName;
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

    public String getOrganLevel() {
        return organLevel;
    }

    public void setOrganLevel(String organLevel) {
        this.organLevel = organLevel;
    }

}
