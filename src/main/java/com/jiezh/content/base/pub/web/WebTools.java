package com.jiezh.content.base.pub.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.content.base.pub.util.DaoUtil;
/**
 * web工具类
 * @author liangds
 *
 */
public class WebTools {

	/**
	 * 获取登录用户信息
	 * @param authentication
	 * @return
	 */
	public static AuthorUser getLoginUser(Authentication authentication){
		AuthorUser authorUser=null;
		if (authentication != null && authentication.getPrincipal() != null) {
			Object principal = authentication.getPrincipal();
			if (principal instanceof UserDetails) {
				authorUser = (AuthorUser) authentication.getPrincipal();
			}
		}
		return authorUser;
	}
	/**
	 * 获取登录用户名
	 * @param authentication
	 * @return
	 */
	public static String getLoginUserName(Authentication authentication) {
		String result = "";
		AuthorUser authorUser=getLoginUser(authentication);
		if(authorUser!=null){
			result=authorUser.getUserName();
		}
		return result;
	}
	/**
	 * 获取登录用户ID
	 * @param authentication
	 * @return
	 */
	public static long getLoginUserId(Authentication authentication) {
		long userId = 0;
		AuthorUser authorUser=getLoginUser(authentication);
		if(authorUser!=null){
			userId=authorUser.getUserId();
		}
		return userId;
	}

	/**
	 * 获取IP
	 * @param request
	 * @return
	 */
	public static  String getIpAddr(HttpServletRequest request) {
		if(request==null) return "未知的IP";
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
	
	/**
	 * 产生随机数
	 * @param length
	 * @return
	 */
	public static String getRandomChar(int length) {
		char[] chr = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
				'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
				'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
				'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
				'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
				'X', 'Y', 'Z' };
		Random random = new Random();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			buffer.append(chr[random.nextInt(62)]);
		}
		return buffer.toString();
	}

	/**
	 * 查找机构层级
	 * @param organId
	 * @return
	 */
	public static String getOrganLevel(String organCode,String defValue,String currOrganId){
		StringBuffer sb=new StringBuffer("");
		Map<String,String> map=new HashMap<String,String>();
		map.put("organId",defValue);
		map.put("organCode", organCode);
		List<Map<String,String>> items=DaoUtil.instance().cacheDao().getOrganLevel(map);
		boolean flag=false;
		for(int i=0;i<items.size();i++){
			Map<String,String> item=items.get(i);
			if(currOrganId.equals(item.get("ORGAN_ID")) || flag){
				sb.append(item.get("ORGAN_ID"));
				if(i!=items.size()-1){
					sb.append("-");
				}
				flag=true;
			}
		}
		return sb.toString();
	}
}
