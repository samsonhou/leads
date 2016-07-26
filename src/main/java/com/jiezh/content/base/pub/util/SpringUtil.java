package com.jiezh.content.base.pub.util;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
/**
 * spring工具类,提供所有有关spring的操作
 */
public class SpringUtil implements ApplicationContextAware {

	private static ApplicationContext context;
	public void setApplicationContext(ApplicationContext _context)
			throws BeansException {
			context = _context;
	}

	/**
	 * 通过Id获取容器中的bean
	 * @param beanName
	 * @return Object
	 */
	public static Object getBean(String beanName) {
		try{
			if(context==null){
				return null;
			}
			return context.getBean(beanName);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 返回Spring上下文
	 * @return
	 */
	public static ApplicationContext getContext(){
		return context;
	}
	
	public static SqlSessionTemplate getSqlTemplate(){
		return ((SqlSession)getBean("sqlTemplate")).getSqlTemplate();
	}
}