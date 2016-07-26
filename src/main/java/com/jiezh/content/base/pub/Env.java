package com.jiezh.content.base.pub;
/**
 * 系统变量
 * @author liangds
 *
 */
public class Env {
	public static final String WEB_ROOT="/leads";
	public static final int PAGE_SIZE=10;//默认分页数
	
	public static final String ROLE_MANAGE="1"; //管理员
	public static final String ROLE_SALE="2"; //销售
	public static final String ROLE_CLIENT="3"; //客服
	
	public static String getWebRoot(){
		return WEB_ROOT;
	}
}
