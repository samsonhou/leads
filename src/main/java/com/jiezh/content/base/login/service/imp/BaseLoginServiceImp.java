package com.jiezh.content.base.login.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jiezh.content.base.login.model.GroupBean;
import com.jiezh.content.base.login.service.BaseLoginService;
import com.jiezh.dao.base.cache.CacheDao;

@Service("base.user.BaseLoginService")
public class BaseLoginServiceImp implements BaseLoginService{
	
	@Autowired
	CacheDao cacheDao;
	/**
	 * 通过userId获取用户菜单组
	 */
	public List<GroupBean> getGroupByUserId(long userId){
		//获取组
		List<GroupBean> listGroup=cacheDao.getModuleGroup(userId);
		if(listGroup==null || listGroup.size()==0){
			listGroup=new ArrayList<GroupBean>();
		}
		//获取组下的菜单
		for(int i=0;i<listGroup.size();i++){
			GroupBean bean=listGroup.get(i);
			bean.setList(cacheDao.getModuleUrl(bean.getGroupId()));
		}
		return listGroup;
	}
	/**
	 * 取机构名称
	 * @param organId
	 * @param organCode
	 * @return
	 */
	public String getOrganName(String organId,String organCode){
		Map<String,String> map=new HashMap<String,String>();
		map.put("organId", organId);
		map.put("organCode", organCode);
		if("ALL".equals(organCode)){
			return "总公司";
		}else{
			return cacheDao.getOrganByOrganId(map).get("ABBR_NAME").toString();
		}
	}
}
