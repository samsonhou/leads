/**
 * 
 */
package com.jiezh.content.base.weixin.tools.bean;

/**
 * news消息
 * @author liangds
 * @since  2016年2月27日 下午9:18:48
 */
public class News extends Message{ 
	public News(){
		setMsgtype("news");
	}
	private String title="";
	private String description="";
	private String url="";
	private String picurl="";
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPicurl() {
		return picurl;
	}
	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}
	
	public String getJson(){
		StringBuffer sb=new StringBuffer("{");
		sb.append("\"touser\": \""+getTouser()+"\",");
		sb.append("\"toparty\": \""+getToparty()+"\",");
		sb.append("\"totag\": \""+getTotag()+"\",");
		sb.append("\"msgtype\": \""+getMsgtype()+"\",");
		sb.append("\"agentid\": "+getAgentid()+",");
		sb.append("\"news\":{");
			sb.append("\"articles\":[");
				sb.append("{");
					sb.append("\"title\": \""+getTitle()+"\",");
					sb.append("\"description\": \""+getDescription()+"\",");
					sb.append("\"url\": \""+getUrl()+"\",");
					sb.append("\"picurl\": \""+getPicurl()+"\",");
				sb.append("}");
			sb.append("]");
		sb.append("}");
		return sb.append("}").toString();
	
	
	}
}
