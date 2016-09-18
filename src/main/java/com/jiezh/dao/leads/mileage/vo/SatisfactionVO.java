package com.jiezh.dao.leads.mileage.vo;

import com.jiezh.content.base.pub.web.GeneralBean;

/**
 * 
 * 客户满意度VO
 * 
 * @className SatisfactionVO
 * @author JXY
 * @version V1.0
 */
public class SatisfactionVO extends GeneralBean {
    private Long id;
    private Long customerid; // 客户ID
    private String customertel; // 客户电话
    private Integer questionid; // 对应问题
    private String answer; // 答案选项
    private String detailanswer; // 答案描述

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

    public Integer getQuestionid() {
        return questionid;
    }

    public void setQuestionid(Integer questionid) {
        this.questionid = questionid;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getDetailanswer() {
        return detailanswer;
    }

    public void setDetailanswer(String detailanswer) {
        this.detailanswer = detailanswer;
    }

}
