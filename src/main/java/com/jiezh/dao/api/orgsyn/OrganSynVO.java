package com.jiezh.dao.api.orgsyn;

import java.util.Date;

import com.jiezh.content.base.pub.web.GeneralBean;

public class OrganSynVO extends GeneralBean {
    private Long id;
    private Long oragnId;
    private Long storeId;
    private Long cityId;
    private String storeName;
    private String storeAdd;
    private String orgCode;
    private String status;
    private Date createDate;
    private Date updateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOragnId() {
        return oragnId;
    }

    public void setOragnId(Long oragnId) {
        this.oragnId = oragnId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreAdd() {
        return storeAdd;
    }

    public void setStoreAdd(String storeAdd) {
        this.storeAdd = storeAdd;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
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

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

}
