package com.jiezh.content.leads.exchange;

import java.util.concurrent.ArrayBlockingQueue;
/**
 * 催促队列工具类
 * @author liangds
 * @since  2016年1月20日 上午9:28:02
 */
public class QueueTools {
	private QueueTools(){};
	private static ArrayBlockingQueue<ExchangeBean> queue=new ArrayBlockingQueue<ExchangeBean>(500);
	
	public static void put(ExchangeBean item){
		try {
			queue.put(item);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public static ExchangeBean get(){
		try {
			return queue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getTaskId(){
		ExchangeBean bean=null;
		bean=queue.size()>0?get():new ExchangeBean();
		String result="{\"userId\":"+bean.getUserId()+",\"taskId\":"+bean.getTaskId()+"}";
		return result;
	}
	
}
