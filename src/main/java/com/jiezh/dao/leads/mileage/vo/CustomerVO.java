package com.jiezh.dao.leads.mileage.vo;

import java.util.Date;
import java.util.List;

import com.jiezh.content.base.pub.web.GeneralBean;

/**
 * 
 * 客户信息
 * 
 * @className CustomerVO
 * @author JXY
 * @version V1.0
 */
public class CustomerVO extends GeneralBean {
    private Long id; // ID
    private String subsidiary; // 子公司
    private String store; // 门店
    private String customername; // 客户名称
    private String tel; // 客户电话
    private String investigator; // 调研人
    private String investigatorsex; // 调研人性别 0 女 1 男
    private Date investigationdate; // 调研日期
    private String connectstatus; // 通话情况
    private CarRentelVO carRentelVO; // 租车信息

    private List<SatisfactionVO> questionList; // 满意度调查

    private String needremind; // 是否应该提醒

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubsidiary() {
        return subsidiary;
    }

    public void setSubsidiary(String subsidiary) {
        this.subsidiary = subsidiary;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getInvestigator() {
        return investigator;
    }

    public void setInvestigator(String investigator) {
        this.investigator = investigator;
    }

    public String getInvestigatorsex() {
        return investigatorsex;
    }

    public void setInvestigatorsex(String investigatorsex) {
        this.investigatorsex = investigatorsex;
    }

    public Date getInvestigationdate() {
        return investigationdate;
    }

    public void setInvestigationdate(Date investigationdate) {
        this.investigationdate = investigationdate;
    }

    public String getConnectstatus() {
        return connectstatus;
    }

    public void setConnectstatus(String connectstatus) {
        this.connectstatus = connectstatus;
    }

    public CarRentelVO getCarRentelVO() {
        return carRentelVO;
    }

    public void setCarRentelVO(CarRentelVO carRentelVO) {
        this.carRentelVO = carRentelVO;
    }

    public List<SatisfactionVO> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<SatisfactionVO> questionList) {
        this.questionList = questionList;
    }

    public String getNeedremind() {
        return needremind;
    }

    public void setNeedremind(String needremind) {
        this.needremind = needremind;
    }

}
