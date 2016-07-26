package com.jiezh.content.base.pub.author;

import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.context.SecurityContextHolder;
import com.jiezh.content.base.pub.util.SpringUtil;
import com.jiezh.content.base.pub.web.WebTools;
import com.jiezh.dao.base.login.LoginDao;
import com.jiezh.dao.base.login.LoginVO;

public class UserLoginLog {
	private static LoginDao loginDao=(LoginDao) SpringUtil.getBean("loginDao");
	/**
	 * 登录记录日记
	 * @param user
	 */
	public static void createLoginLog(AuthorUser user){
		try{
			LoginVO loginvo=new LoginVO();
			loginvo.setUserId(user.getUserId());
			loginvo.setUserCode(user.getUserCode());
			loginvo.setLoginIp(user.getLoginIp());
			loginvo.setUuid(user.getUuid());
			loginvo.setSessionId(user.getSessionId());
			loginDao.insert(loginvo);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 登出记录日是记
	 * @param sessionId
	 */
	public static void updateLogout(String sessionId){
		try{
			loginDao.updateBySessionId(sessionId);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 更新session
	 * @param sessionId
	 */
	public static void updateSession(String sessionId){
		try{
			AuthorUser authorUser=WebTools.getLoginUser(SecurityContextHolder.getContext().getAuthentication());
			if(authorUser!=null){
				Map<String,String> map=new HashMap<String,String>();
				map.put("sessionId", sessionId);
				map.put("uuid", authorUser.getUuid());
				loginDao.updateByUuid(map);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 更新访问时间
	 * @param sessionId
	 */
	public static void updateLastRequest(String sessionId){
		try{
			loginDao.updateLastRequest(sessionId);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static void updateLoginIp(String ip,String uuid){
		try{
			Map<String,String> map=new HashMap<String,String>();
			map.put("loginIp", ip);
			map.put("uuid",uuid);
			loginDao.updateLoginIp(map);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
}
