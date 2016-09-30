package com.jiezh.dao.base.sysvar;

import com.jiezh.content.base.pub.web.GeneralBean;

public class SysvarVO extends GeneralBean {
    private Long id;

    private String sysvarCode;

    private String sysvarName;

    private String sysvarValue;

    private String sysvarType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSysvarCode() {
        return sysvarCode;
    }

    public void setSysvarCode(String sysvarCode) {
        this.sysvarCode = sysvarCode == null ? null : sysvarCode.trim();
    }

    public String getSysvarName() {
        return sysvarName;
    }

    public void setSysvarName(String sysvarName) {
        this.sysvarName = sysvarName == null ? null : sysvarName.trim();
    }

    public String getSysvarValue() {
        return sysvarValue;
    }

    public void setSysvarValue(String sysvarValue) {
        this.sysvarValue = sysvarValue == null ? null : sysvarValue.trim();
    }

    public String getSysvarType() {
        return sysvarType;
    }

    public void setSysvarType(String sysvarType) {
        this.sysvarType = sysvarType == null ? null : sysvarType.trim();
    }
}