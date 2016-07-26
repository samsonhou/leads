package com.jiezh.dao.weixin.user;

import com.jiezh.content.base.pub.web.GeneralBean;
import java.util.Date;

public class UserVO extends GeneralBean {
    private Long id;

    private String jzCode;

    private String name;

    private String status;

    private Date createDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJzCode() {
        return jzCode;
    }

    public void setJzCode(String jzCode) {
        this.jzCode = jzCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}