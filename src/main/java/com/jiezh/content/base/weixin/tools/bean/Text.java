/**
 * 
 */
package com.jiezh.content.base.weixin.tools.bean;

/**
 * @author liangds
 * @since  2016年2月27日 下午9:18:22
 */
public class Text extends Message{
	public Text(){
		setMsgtype("text");
	}
	private String content="";
	private String safe="0";
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSafe() {
		return safe;
	}
	public void setSafe(String safe) {
		this.safe = safe;
	}
	
	public String getJson(){
		StringBuffer sb=new StringBuffer("{");
		sb.append("\"touser\": \""+getTouser()+"\",");
		sb.append("\"toparty\": \""+getToparty()+"\",");
		sb.append("\"totag\": \""+getTotag()+"\",");
		sb.append("\"msgtype\": \""+getMsgtype()+"\",");
		sb.append("\"agentid\": "+getAgentid()+",");
		sb.append("\"text\": "+"{\"content\":\""+getContent()+"\"}"+",");
		sb.append("\"safe\": \""+getSafe()+"\"");
		return sb.append("}").toString();
	
	}
	
}
