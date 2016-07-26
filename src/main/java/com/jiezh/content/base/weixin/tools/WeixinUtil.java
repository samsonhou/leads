
package com.jiezh.content.base.weixin.tools;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import com.jiezh.content.base.pub.Log;
import com.jiezh.content.base.weixin.tools.bean.News;
import com.jiezh.content.base.weixin.tools.bean.Text;

import net.sf.json.JSONObject;

public class WeixinUtil {
	public static final String access_token_url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=CorpID&corpsecret=SECRET";
	static Log logger=new Log(WeixinUtil.class);
	public static JSONObject HttpRequest(String request, String RequestMethod, String output) {
		System.out.println("HttpRequest>>:"+request+"//"+output);
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			URL url = new URL(request);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setRequestMethod(RequestMethod);
			if (output != null) {
				OutputStream out = connection.getOutputStream();
				out.write(output.getBytes("UTF-8"));
				out.close();
			}

			InputStream input = connection.getInputStream();
			InputStreamReader inputReader = new InputStreamReader(input, "UTF-8");
			BufferedReader reader = new BufferedReader(inputReader);
			String line;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}

			reader.close();
			inputReader.close();
			input.close();
			input = null;
			connection.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (Exception localException) {
		}
		return jsonObject;
	}

	public static AccessToken getAccessToken(String corpID, String secret) {
		AccessToken accessToken = null;
		String requestUrl = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=CorpID&corpsecret=SECRET".replace("CorpID", corpID).replace("SECRET", secret);
		JSONObject jsonObject = HttpRequest(requestUrl, "GET", null);
		logger.info(jsonObject.toString());
		if (null != jsonObject) {
			try {
				accessToken = new AccessToken();
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
				logger.info("获取token成功:" + jsonObject.getString("access_token") + "————" + jsonObject.getInt("expires_in"));
			} catch (Exception e) {
				accessToken = null;

				String error = String.format("获取token失败 errcode:{} errmsg:{}",new Object[] { Integer.valueOf(jsonObject.getInt("errcode")), jsonObject.getString("errmsg") });
				logger.info(error);
			}
		}
		return accessToken;
	}


	public static String URLEncoder(String str) {
		String result = str;
		try {
			result = URLEncoder.encode(result, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static JSONObject PostMessage(String access_token, String RequestMt, String RequestURL, String outstr) {
		RequestURL = RequestURL.replace("ACCESS_TOKEN", access_token);
		JSONObject jsonobject = HttpRequest(RequestURL, RequestMt, outstr);
		if ((null != jsonobject) && (0 != jsonobject.getInt("errcode"))) {
			System.out.println("errcode:"+jsonobject.getInt("errcode"));
		}
		return jsonobject;
	}
	/**
	 * 发送消息
	 * @param agentId
	 * @param outstr
	 * @return
	 */
	public static JSONObject postMsg(String agentId,String outstr){
		logger.info("postMsg json>>:"+outstr);
		String url=Parameters.MSG_SENT_URL.replace("ACCESS_TOKEN", WeixinConfig.getAccessToken(agentId));
		JSONObject jsonobject = HttpRequest(url, "POST", outstr);
		if ((null != jsonobject) && (0 != jsonobject.getInt("errcode"))) {
			System.out.println("errcode:"+jsonobject.getInt("errcode"));
		}
		return jsonobject;
	}
	
	/**
	 * 回复
	 * @return
	 */
	public static String subscribe(String toUserName,String fromUser,String content){
		StringBuffer sb=new StringBuffer("<xml>");
		sb.append("<ToUserName><![CDATA["+fromUser+"]]></ToUserName>");
		sb.append("<FromUserName><![CDATA["+toUserName+"]]></FromUserName>");
		sb.append("<CreateTime>"+System.currentTimeMillis()+"</CreateTime>");
		sb.append("<MsgType><![CDATA[text]]></MsgType>");
		sb.append("<Content><![CDATA["+content+"]]></Content>");
		return sb.append("</xml>").toString();
	}
	/**
	 * 消息 text
	 * @param txt
	 * @return
	 */
	public static String postTxt(String agentId,Text txt){
		postMsg(agentId,txt.getJson());
		return "";
	}
	
	/**
	 * news
	 * @param agentId
	 * @param news
	 * @return
	 */
	public static String postNews(String agentId,News news){
		postMsg(agentId,news.getJson());
		return "";
	}
}