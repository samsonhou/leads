package com.jiezh.dao.weixin.main;

import com.jiezh.content.base.pub.web.GeneralBean;
import java.math.BigDecimal;

public class MainVO extends GeneralBean {
    private BigDecimal id;

    private String corpId;

    private String name;

    private String projectUrl;

    private String projectName;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProjectUrl() {
        return projectUrl;
    }

    public void setProjectUrl(String projectUrl) {
        this.projectUrl = projectUrl;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}