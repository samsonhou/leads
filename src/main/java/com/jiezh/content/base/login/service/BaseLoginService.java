package com.jiezh.content.base.login.service;

import java.util.List;
import com.jiezh.content.base.login.model.GroupBean;

public interface BaseLoginService {
	public List<GroupBean> getGroupByUserId(long userId);
	public String getOrganName(String organId,String organCode);
}
