/**
 * 
 */
package com.jiezh.content.weixin.jieyi.pub;

import com.jiezh.content.base.pub.util.SpringUtil;
import com.jiezh.dao.weixin.agent.AgentDao;
import com.jiezh.dao.weixin.app.WeixinAppDao;
import com.jiezh.dao.weixin.main.MainDao;
import com.jiezh.dao.weixin.user.WeixinUserDao;

/**
 * @author liangds
 * @since  2016年2月24日 下午4:07:08
 */
public class DaoUtil {
	private static class LazyHolder{
		 private static final DaoUtil INSTANCE = new DaoUtil(); 
	}
	private DaoUtil (){}
	public static final DaoUtil instance() {    
	     return LazyHolder.INSTANCE;    
    }  

	public MainDao getMainDao(){
		return (MainDao) SpringUtil.getBean("mainDao");
	}
	
	public WeixinAppDao getAppDao(){
		return (WeixinAppDao) SpringUtil.getBean("weixinAppDao");
	}
	
	public AgentDao getAgentDao(){
		return (AgentDao) SpringUtil.getBean("agentDao");
	}
	
	public WeixinUserDao getWeixinUserDao(){
		return (WeixinUserDao) SpringUtil.getBean("weixinUserDao");
	}
}