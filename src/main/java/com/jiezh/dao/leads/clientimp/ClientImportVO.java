package com.jiezh.dao.leads.clientimp;

import java.util.Date;

import com.jiezh.content.base.pub.web.GeneralBean;

public class ClientImportVO extends GeneralBean {
    private Long id;

    private String clientName;

    private String tel;

    private String stationNo;

    private String city;

    private String fromTypeBig;

    private Long fromType;

    private String status;

    private Date createdTime;

    private Long createdUserId;

    private Date updatedTime;

    private Long updatedUserId;

    private String remark;

    private String fromTypeDesc;

    private String createdUserName;

    private String idNo;
	
	private String channel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName == null ? null : clientName.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getStationNo() {
        return stationNo;
    }

    public void setStationNo(String stationNo) {
        this.stationNo = stationNo == null ? null : stationNo.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getFromTypeBig() {
        return fromTypeBig;
    }

    public void setFromTypeBig(String fromTypeBig) {
        this.fromTypeBig = fromTypeBig == null ? null : fromTypeBig.trim();
    }

    public Long getFromType() {
        return fromType;
    }

    public void setFromType(Long fromType) {
        this.fromType = fromType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Long getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(Long createdUserId) {
        this.createdUserId = createdUserId;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Long getUpdatedUserId() {
        return updatedUserId;
    }

    public void setUpdatedUserId(Long updatedUserId) {
        this.updatedUserId = updatedUserId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getFromTypeDesc() {
        return fromTypeDesc;
    }

    public void setFromTypeDesc(String fromTypeDesc) {
        this.fromTypeDesc = fromTypeDesc;
    }

    public String getCreatedUserName() {
        return createdUserName;
    }

    public void setCreatedUserName(String createdUserName) {
        this.createdUserName = createdUserName;
    }
    
    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
    
    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

}
