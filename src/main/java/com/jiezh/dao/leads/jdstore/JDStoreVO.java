package com.jiezh.dao.leads.jdstore;

import java.math.BigDecimal;
import java.util.Date;

import com.jiezh.content.base.pub.web.GeneralBean;

public class JDStoreVO extends GeneralBean {
    private Long id;
    private String clientName; // 客户名称
    private String idcard; // 身份证号
    private String tel; // 手机号
    private String productType; // 产品类型
    private String orderNo; // 订单号
    private String orderStatus; // 订单状态
    private BigDecimal orderAmount; // 订单金额
    private Date payTime; // 支付时间
    private String storeId; // 门店ID
    private String storeName; // 门店名称
    private String signedUser; // 合同签署对象
    private String contractNo; // 合同号
    private String carType; // 车型
    private String carVin; // 车架号
    private Long createUser; // 录入人
    private Date createDate; // 录入日期
    private Long updateUser; // 修改人
    private Date updateDate; // 修改日期

    private Date lastMonthDate; // 导出截止日期

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
        this.clientName = clientName;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getSignedUser() {
        return signedUser;
    }

    public void setSignedUser(String signedUser) {
        this.signedUser = signedUser;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getCarVin() {
        return carVin;
    }

    public void setCarVin(String carVin) {
        this.carVin = carVin;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getLastMonthDate() {
        return lastMonthDate;
    }

    public void setLastMonthDate(Date lastMonthDate) {
        this.lastMonthDate = lastMonthDate;
    }

}
