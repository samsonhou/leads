package com.jiezh.dao.leads.client;

import com.jiezh.content.base.pub.web.GeneralBean;
import java.util.Date;

public class ClientTraceVO extends GeneralBean {
    private long id;

    private String title;

    private long tId;

    private Date redate;

    private String uId;

    // add by houjiq start 战败自动分配
    private String rank;

    private String reason;

    private long sid;
    // add by houjiq end 战败自动分配

    // 是否到店
    private String idd;
    // 到店时间
    private Date firstTimeComing;
    // 是否通过风控
    private String ifk;
    // 产品
    private String product;
    // 购车意愿
    private String will;
    // 是否提交材料
    private String isSubMaterial;
    // 是否提车
    private String isGetCar;
    // 提车时间
    private Date getCarDate;
    // 车牌号
    private String carno;

    // 大定金支付情况
    private String innDeposit;
    private Integer times;

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long gettId() {
        return tId;
    }

    public void settId(long tId) {
        this.tId = tId;
    }

    public Date getRedate() {
        return redate;
    }

    public void setRedate(Date redate) {
        this.redate = redate;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

    public String getIdd() {
        return idd;
    }

    public void setIdd(String idd) {
        this.idd = idd;
    }

    public Date getFirstTimeComing() {
        return firstTimeComing;
    }

    public void setFirstTimeComing(Date firstTimeComing) {
        this.firstTimeComing = firstTimeComing;
    }

    public String getIfk() {
        return ifk;
    }

    public void setIfk(String ifk) {
        this.ifk = ifk;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getWill() {
        return will;
    }

    public void setWill(String will) {
        this.will = will;
    }

    public String getIsSubMaterial() {
        return isSubMaterial;
    }

    public void setIsSubMaterial(String isSubMaterial) {
        this.isSubMaterial = isSubMaterial;
    }

    public String getIsGetCar() {
        return isGetCar;
    }

    public void setIsGetCar(String isGetCar) {
        this.isGetCar = isGetCar;
    }

    public Date getGetCarDate() {
        return getCarDate;
    }

    public void setGetCarDate(Date getCarDate) {
        this.getCarDate = getCarDate;
    }

    public String getCarno() {
        return carno;
    }

    public void setCarno(String carno) {
        this.carno = carno;
    }

    public String getInnDeposit() {
        return innDeposit;
    }

    public void setInnDeposit(String innDeposit) {
        this.innDeposit = innDeposit;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

}
