package com.jiezh.dao.leads.mileage.vo;

import com.jiezh.content.base.pub.web.GeneralBean;

public class MileageVO extends GeneralBean {
    private Long id;
    private Long carid; // 车辆ID
    private Integer mileage; // 行驶里程
    private Integer lastmileage; // 上次行驶里程
    private String isremind; // 是否已提醒 0 未提醒 1 已提醒
    private Integer remindtimes; // 提醒次数

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCarid() {
        return carid;
    }

    public void setCarid(Long carid) {
        this.carid = carid;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public Integer getLastmileage() {
        return lastmileage;
    }

    public void setLastmileage(Integer lastmileage) {
        this.lastmileage = lastmileage;
    }

    public String getIsremind() {
        return isremind;
    }

    public void setIsremind(String isremind) {
        this.isremind = isremind;
    }

    public Integer getRemindtimes() {
        return remindtimes;
    }

    public void setRemindtimes(Integer remindtimes) {
        this.remindtimes = remindtimes;
    }

}
