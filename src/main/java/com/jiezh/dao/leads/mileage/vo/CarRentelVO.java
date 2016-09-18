package com.jiezh.dao.leads.mileage.vo;

import java.util.Date;

import com.jiezh.content.base.pub.web.GeneralBean;

/**
 * 
 * 租车信息
 * 
 * @className CarRentelVO
 * @author JXY
 * @version V1.0
 */
public class CarRentelVO extends GeneralBean {
    private Long id; // ID
    private Long customerid; // 客户ID
    private String customertel; // 客户电话
    private String carvin; // 车架号
    private String brandtype; // 品牌型号
    private Date deliverdate; // 交车日期

    private MileageVO mileageVO; // 行驶里程信息

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerid() {
        return customerid;
    }

    public void setCustomerid(Long customerid) {
        this.customerid = customerid;
    }

    public String getCustomertel() {
        return customertel;
    }

    public void setCustomertel(String customertel) {
        this.customertel = customertel;
    }

    public String getCarvin() {
        return carvin;
    }

    public void setCarvin(String carvin) {
        this.carvin = carvin;
    }

    public String getBrandtype() {
        return brandtype;
    }

    public void setBrandtype(String brandtype) {
        this.brandtype = brandtype;
    }

    public Date getDeliverdate() {
        return deliverdate;
    }

    public void setDeliverdate(Date deliverdate) {
        this.deliverdate = deliverdate;
    }

    public MileageVO getMileageVO() {
        return mileageVO;
    }

    public void setMileageVO(MileageVO mileageVO) {
        this.mileageVO = mileageVO;
    }

}
