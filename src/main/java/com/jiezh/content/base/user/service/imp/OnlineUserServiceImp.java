package com.jiezh.content.base.user.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;

import com.jiezh.content.base.pub.Log;
import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.content.base.pub.author.UserLoginLog;
import com.jiezh.content.base.pub.util.DaoUtil;
import com.jiezh.content.base.pub.util.DateUtil;
import com.jiezh.dao.base.cache.CacheDao;

@Service("base.user.onlineUserService")
public class OnlineUserServiceImp {
	Log log=new Log(OnlineUserServiceImp.class);
	@Autowired
	SessionRegistry sessionRegistry;
	
	/**
	 * 查询所有
	 * @return
	 */
	public List<AuthorUser> getAllUser(){
		List<Object> list=sessionRegistry.getAllPrincipals();
		List<AuthorUser> all=new ArrayList<AuthorUser>();
		CacheDao cacheDao=DaoUtil.instance().cacheDao();
		Map<String,String> parm=new HashMap<String, String>();
		for(int i=0; i<list.size(); i++){
			AuthorUser userTemp=(AuthorUser) list.get(i); 
			List<SessionInformation> sessionInformationList = sessionRegistry.getAllSessions(userTemp, false);
			if (sessionInformationList!=null){
				 for (int j=0; j<sessionInformationList.size(); j++) { 
					 AuthorUser userTemp2=(AuthorUser) sessionInformationList.get(j).getPrincipal();
					 AuthorUser loginUser=new AuthorUser();
					 loginUser.setUuid(userTemp2.getUuid());
					 loginUser.setUserId(userTemp2.getUserId());
					 loginUser.setUserCode(userTemp2.getUserCode());
					 loginUser.setUserName(userTemp2.getUserName());
					 loginUser.setRealName(userTemp2.getRealName());
					 loginUser.setJzCode(userTemp2.getJzCode());
					 parm.put("organId", userTemp2.getOrganId());
					 parm.put("organCode", userTemp2.getOrganCode());
					 loginUser.setOrganId(cacheDao.getOrganByOrganId(parm).get("NAME"));
					 loginUser.setStatus(userTemp2.getStatus());
					 loginUser.setOrganCode(userTemp2.getOrganCode());
					 loginUser.setRole(userTemp2.getRole());
					 loginUser.setLastRequest(DateUtil.date2String(sessionInformationList.get(j).getLastRequest(), "yyyy-MM-dd HH:mm:ss"));
					 loginUser.setExpired(sessionInformationList.get(j).isExpired());
					 loginUser.setSessionId(sessionInformationList.get(j).getSessionId());
					 loginUser.setLoginIp(userTemp2.getLoginIp());
					 log.info(sessionInformationList.get(j).getSessionId()+"/"+userTemp2.getLoginIp());
					 all.add(loginUser);
				 }
			}
		}
		return all;
	}
	
	/**
	 * 强制登出
	 * @param userCode
	 * @param sessionId
	 */
	public void reject(String userCode,String sessionId){
		List<Object> list=sessionRegistry.getAllPrincipals();
		for(int i=0; i<list.size(); i++){ 
			AuthorUser userTemp=(AuthorUser) list.get(i); 
			if(userCode.equals(userTemp.getUserCode())){
			List<SessionInformation> sessionInformationList = sessionRegistry.getAllSessions(userTemp, false);
			if (sessionInformationList!=null){
				 for (int j=0; j<sessionInformationList.size(); j++) {
					 if(sessionId.equals(sessionInformationList.get(j).getSessionId())){
						 log.info(userCode+" is logout sessionId is "+sessionId);
						 sessionInformationList.get(j).expireNow(); 
						 sessionRegistry.removeSessionInformation(sessionInformationList.get(j).getSessionId()); 
						 UserLoginLog.updateLogout(sessionId);
					 }
				 }
			}
			}
		}
	}
}
