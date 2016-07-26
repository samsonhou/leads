package com.jiezh.content.base.weixin.tools;

import net.sf.json.JSONObject;

public class GOauth2Core {
	public static String GET_CODE = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=CORPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_base&state=a123#wechat_redirect";

	public static String CODE_TO_USERINFO = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=ACCESS_TOKEN&code=CODE&agentid=AGENTID";

	public static String GetUserID(String access_token, String code, String agentid) {
		String UserId = null;
		String path = CODE_TO_USERINFO.replace("ACCESS_TOKEN", access_token).replace("CODE", code).replace("AGENTID",agentid);
		JSONObject jsonobject = WeixinUtil.HttpRequest(path, "GET", null);
		if (null != jsonobject)
			if (jsonobject.containsKey("UserId")) {
				UserId = jsonobject.getString("UserId");
				System.out.println("获取信息成功，o(∩_∩)o ————UserID:" + UserId);
			} else {
				int errorrcode = jsonobject.getInt("errcode");
				String errmsg = jsonobject.getString("errmsg");
				System.out.println("错误码：" + errorrcode + "————" + "错误信息：" + errmsg + "\tcode:" + code);
			}
		else {
			System.out.println("获取授权失败了，●﹏●，自己找原因。。。");
		}
		return UserId;
	}
}