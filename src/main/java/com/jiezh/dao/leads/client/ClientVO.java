package com.jiezh.dao.leads.client;

import com.jiezh.content.base.pub.web.GeneralBean;
import java.util.Date;

public class ClientVO extends GeneralBean {
    private long id;

    private String clientName;

    private String tel;

    private String sex;

    private String personid;

    private String email;

    private int fromtype;

    private String title;

    private Date qdate;

    private int bigPid;

    private int smallPid;

    private int rid;

    private String ifurgent;

    private String cityid;
    // 机构ID
    private String companyid;

    private int sid;

    private Date ddate;

    private Date nextdate;

    private String rank;

    private String status;

    private String qq;

    private String weixin;

    private String phone;

    private String reason;

    private String reasonCont;

    private String contractno;

    private Date allotdate;
    // add by houjq
    private String agingTrackType;

    private String init_rank;

    // add by houjiq
    private String isIncome;

    // add by houjq for JZLM-74 增加5字段
    // 租赁产品
    private String product;
    // 购车意愿
    private String will;
    // 是否提交材料
    private String isSubMaterial;
    // 是否提车
    private String isGetCar;
    // 提车时间
    private Date getCarDate;

    // add by houjq for JZLM-85
    // 定金支付情况
    private String depositStatus;

    // add by houjq for JZLM-91
    // 手机号1
    private String tel1;

    // 车牌号
    private String carno;
    // 线索来源大类
    private String fromtypeBig;
    // 首次来电时间
    private Date firstTimeComing;
    // 渠道名称
    private String channel;

    // 滴滴订单号
    private String orderNo;
    // 是否结算
    private String isCharged;
    // 大定金支付情况
    private String innDeposit;
    //是否可回收
    private String isRecycle;
    
    public String getCarno() {
        return carno;
    }

    public void setCarno(String carno) {
        this.carno = carno;
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

    public String getIsIncome() {
        return isIncome;
    }

    public void setIsIncome(String isIncome) {
        this.isIncome = isIncome;
    }

    public String getIsDeal() {
        return isDeal;
    }

    public void setIsDeal(String isDeal) {
        this.isDeal = isDeal;
    }

    private String isDeal;

    private String cadTmp;

    public String getCadTmp() {
        return cadTmp;
    }

    public void setCadTmp(String cadTmp) {
        this.cadTmp = cadTmp;
    }

    public String getInit_rank() {
        return init_rank;
    }

    public void setInit_rank(String init_rank) {
        this.init_rank = init_rank;
    }

    /**
     * @return the agingTrackType String
     */
    public String getAgingTrackType() {
        return agingTrackType;
    }

    /**
     * @param agingTrackType the agingTrackType to set
     */
    public void setAgingTrackType(String agingTrackType) {
        this.agingTrackType = agingTrackType;
    }

    public Date getAllotdate() {
        return allotdate;
    }

    public void setAllotdate(Date allotdate) {
        this.allotdate = allotdate;
    }

    public String getContractno() {
        return contractno;
    }

    public void setContractno(String contractno) {
        this.contractno = contractno;
    }

    public String getIdd() {
        return idd;
    }

    public void setIdd(String idd) {
        this.idd = idd;
    }

    public String getIfk() {
        return ifk;
    }

    public void setIfk(String ifk) {
        this.ifk = ifk;
    }

    private String idd;

    private String ifk;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReasonCont() {
        return reasonCont;
    }

    public void setReasonCont(String reasonCont) {
        this.reasonCont = reasonCont;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPersonid() {
        return personid;
    }

    public void setPersonid(String personid) {
        this.personid = personid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getFromtype() {
        return fromtype;
    }

    public void setFromtype(int fromtype) {
        this.fromtype = fromtype;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getQdate() {
        return qdate;
    }

    public void setQdate(Date qdate) {
        this.qdate = qdate;
    }

    public int getBigPid() {
        return bigPid;
    }

    public void setBigPid(int bigPid) {
        this.bigPid = bigPid;
    }

    public int getSmallPid() {
        return smallPid;
    }

    public void setSmallPid(int smallPid) {
        this.smallPid = smallPid;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getIfurgent() {
        return ifurgent;
    }

    public void setIfurgent(String ifurgent) {
        this.ifurgent = ifurgent;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public Date getDdate() {
        return ddate;
    }

    public void setDdate(Date ddate) {
        this.ddate = ddate;
    }

    public Date getNextdate() {
        return nextdate;
    }

    public void setNextdate(Date nextdate) {
        this.nextdate = nextdate;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDepositStatus() {
        return depositStatus;
    }

    public void setDepositStatus(String depositStatus) {
        this.depositStatus = depositStatus;
    }

    public String getTel1() {
        return tel1;
    }

    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }

    public String getFromtypeBig() {
        return fromtypeBig;
    }

    public void setFromtypeBig(String fromtypeBig) {
        this.fromtypeBig = fromtypeBig;
    }

    public Date getFirstTimeComing() {
        return firstTimeComing;
    }

    public void setFirstTimeComing(Date firstTimeComing) {
        this.firstTimeComing = firstTimeComing;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getIsCharged() {
        return isCharged;
    }

    public void setIsCharged(String isCharged) {
        this.isCharged = isCharged;
    }

    public String getInnDeposit() {
        return innDeposit;
    }

    public void setInnDeposit(String innDeposit) {
        this.innDeposit = innDeposit;
    }

    public String getIsRecycle() {
        return isRecycle;
    }

    public void setIsRecycle(String isRecycle) {
        this.isRecycle = isRecycle;
    }

}
