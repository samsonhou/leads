package com.jiezh.dao.weixin.app;

import com.jiezh.content.base.pub.web.GeneralBean;
import java.util.Date;

public class WeixinAppVO extends GeneralBean {
    private Long id;

    private Long mainId;

    private Long appId;

    private String appName;

    private String token;

    private String encodingAesKey;

    private String callBackUrl;

    private String secret;

    private Date createDate;

    private String acceccToken;

    private String accessTicket;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMainId() {
        return mainId;
    }

    public void setMainId(Long mainId) {
        this.mainId = mainId;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEncodingAesKey() {
        return encodingAesKey;
    }

    public void setEncodingAesKey(String encodingAesKey) {
        this.encodingAesKey = encodingAesKey;
    }

    public String getCallBackUrl() {
        return callBackUrl;
    }

    public void setCallBackUrl(String callBackUrl) {
        this.callBackUrl = callBackUrl;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getAcceccToken() {
        return acceccToken;
    }

    public void setAcceccToken(String acceccToken) {
        this.acceccToken = acceccToken;
    }

    public String getAccessTicket() {
        return accessTicket;
    }

    public void setAccessTicket(String accessTicket) {
        this.accessTicket = accessTicket;
    }
}