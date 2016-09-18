package com.jiezh.dao.leads.mileage.vo;

public class QuestionVO {

    private Long id;
    private String shortname; // 简短问题名称
    private String question; // 问题
    private String optiona; // 选项A
    private String optionb; // 选项B
    private String optionc; // 选项C
    private String optiond; // 选项D
    private String optione; // 选项E
    private String inputoption; // 带扩展的选项

    private String answer; // 答案
    private String detailanswer; // 扩展答案

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOptiona() {
        return optiona;
    }

    public void setOptiona(String optiona) {
        this.optiona = optiona;
    }

    public String getOptionb() {
        return optionb;
    }

    public void setOptionb(String optionb) {
        this.optionb = optionb;
    }

    public String getOptionc() {
        return optionc;
    }

    public void setOptionc(String optionc) {
        this.optionc = optionc;
    }

    public String getOptiond() {
        return optiond;
    }

    public void setOptiond(String optiond) {
        this.optiond = optiond;
    }

    public String getOptione() {
        return optione;
    }

    public void setOptione(String optione) {
        this.optione = optione;
    }

    public String getInputoption() {
        return inputoption;
    }

    public void setInputoption(String inputoption) {
        this.inputoption = inputoption;
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
