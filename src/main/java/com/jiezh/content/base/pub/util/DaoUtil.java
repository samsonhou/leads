package com.jiezh.content.base.pub.util;


import com.jiezh.dao.base.cache.CacheDao;

/**
 * 公用的DAO可以放在这里
 * @author liangds
 *
 */
public class DaoUtil {
	private static class LazyHolder{
		 private static final DaoUtil INSTANCE = new DaoUtil(); 
	}
	private DaoUtil (){}
	public static final DaoUtil instance() {    
	     return LazyHolder.INSTANCE;    
    }  
	public CacheDao cacheDao() {
		return (CacheDao) SpringUtil.getBean("cacheDao");
	}
	
}
