package com.jiezh.content.base.login.model;

import java.util.List;

import com.jiezh.content.base.pub.web.GeneralBean;



public class GroupBean extends GeneralBean {
	private long userId;
	private long groupId;
	private String groupName;
	private List<ModelBean> list;
	
	public List<ModelBean> getList() {
		return list;
	}
	public void setList(List<ModelBean> list) {
		this.list = list;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getGroupId() {
		return groupId;
	}
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
}
