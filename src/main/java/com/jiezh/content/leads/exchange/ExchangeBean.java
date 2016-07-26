/**
 * 
 */
package com.jiezh.content.leads.exchange;

/**
 * @author liangds
 * @since  2016年1月20日 上午11:10:58
 */
public class ExchangeBean {
	private String userId="-100";
	private String taskId="-1000";
	public ExchangeBean(){
		
	}
	public ExchangeBean(String userId,String taskId){
		this.userId=userId;
		this.taskId=taskId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
}
