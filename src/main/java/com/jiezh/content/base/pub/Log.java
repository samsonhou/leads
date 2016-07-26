package com.jiezh.content.base.pub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 日记打印
 *
 */
public class Log {
	public Logger logger;
	public Log(Class c){
		logger=LoggerFactory.getLogger(c);
	}
	public  void info(String info){
		logger.info("==>>"+info);
	}
	
	public void error(String info){
		logger.error("==>>"+info);
	}
}
