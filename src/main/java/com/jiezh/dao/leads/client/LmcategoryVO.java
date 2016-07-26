package com.jiezh.dao.leads.client;

import java.util.List;

import com.jiezh.content.base.pub.web.GeneralBean;

public class LmcategoryVO extends GeneralBean {

    private long id;

    private String title;

    private long pid;
    
    private String ptitle;
    
    private long childrenCount;
    
    private  List<LmcategoryVO> childrenList;
    
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

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

	public String getPtitle() {
		return ptitle;
	}

	public void setPtitle(String ptitle) {
		this.ptitle = ptitle;
	}

	public long getChildrenCount() {
		return childrenCount;
	}

	public void setChildrenCount(long childrenCount) {
		this.childrenCount = childrenCount;
	}

	public List<LmcategoryVO> getChildrenList() {
		return childrenList;
	}

	public void setChildrenList(List<LmcategoryVO> childrenList) {
		this.childrenList = childrenList;
	}
    
}