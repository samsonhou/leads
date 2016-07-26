package com.jiezh.dao.leads.client.update;

import java.util.Date;

import com.jiezh.content.base.pub.web.GeneralBean;

public class ClientUpdateVO extends GeneralBean {
    private Long id;

    private Long clientId;

    private String field;

    private String fieldBefore;

    private String fieldAfter;

    private String updateIndex;

    private Date createdTime;

    private Long createdUserId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field == null ? null : field.trim();
    }

    public String getFieldBefore() {
        return fieldBefore;
    }

    public void setFieldBefore(String fieldBefore) {
        this.fieldBefore = fieldBefore == null ? null : fieldBefore.trim();
    }

    public String getFieldAfter() {
        return fieldAfter;
    }

    public void setFieldAfter(String fieldAfter) {
        this.fieldAfter = fieldAfter == null ? null : fieldAfter.trim();
    }

    public String getUpdateIndex() {
        return updateIndex;
    }

    public void setUpdateIndex(String updateIndex) {
        this.updateIndex = updateIndex == null ? null : updateIndex.trim();
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
}