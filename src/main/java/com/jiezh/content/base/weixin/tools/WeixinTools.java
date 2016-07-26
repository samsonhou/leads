package com.jiezh.content.base.weixin.tools;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.web.servlet.ModelAndView;

import com.jiezh.content.weixin.jieyi.pub.DaoUtil;
import com.jiezh.dao.weixin.app.WeixinAppVO;
/**
 * 工具类
 * @author liangds
 *
 */
public class WeixinTools {
	static Logger logger=Logger.getLogger(WeixinTools.class);
	private WeixinTools(){}

	/**
	 * 签名
	 * @param jsapi_ticket
	 * @param url
	 * @return
	 */
	 public static Map<String, String> sign(String jsapi_ticket, String url) {
		 Map<String, String> ret = new HashMap<String, String>();
		 String nonce_str = create_nonce_str();
	     String timestamp = create_timestamp();
	     String string1;
	     String signature = "";
	     string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp +"&url=" + url;
	     logger.info(string1);
	     try
	        {
	            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
	            crypt.reset();
	            crypt.update(string1.getBytes("UTF-8"));
	            signature = byteToHex(crypt.digest());
	        }
	        catch (NoSuchAlgorithmException e)
	        {
	            e.printStackTrace();
	        }
	        catch (UnsupportedEncodingException e)
	        {
	            e.printStackTrace();
	        }
	     	logger.info("signature>>"+signature);
	        ret.put("url", url);
	        ret.put("jsapi_ticket", jsapi_ticket);
	        ret.put("nonceStr", nonce_str);
	        ret.put("timestamp", timestamp);
	        ret.put("signature", signature);
		 return ret;
	 }
	 /**
	  * 获取签名后的信息
	  * @param url
	  * @return
	  */
	 public static void getSign(String url,String agentId,ModelAndView context){
		 String jsapi_ticket=WeixinConfig.getAccessTicket(agentId);
		 Map<String,String> m=sign(jsapi_ticket,WeixinConfig.config.get(Parameters.URL_PREFIX)+url);
		 context.addObject("jsapi_ticket", m.get("jsapi_ticket"));
		 context.addObject("nonceStr", "'"+m.get("nonceStr")+"'");
		 context.addObject("timestamp", "'"+m.get("timestamp")+"'");
		 context.addObject("signature", "'"+m.get("signature")+"'");
		 context.addObject("appId", "'"+WeixinConfig.getCorpId()+"'");
	 }
	 
	 private static String byteToHex(final byte[] hash) {
	        Formatter formatter = new Formatter();
	        for (byte b : hash)
	        {
	            formatter.format("%02x", b);
	        }
	        String result = formatter.toString();
	        formatter.close();
	        return result;
	    }
	 private static String create_nonce_str() {
	     return UUID.randomUUID().toString();
	 }
	 private static String create_timestamp() {
	     return Long.toString(System.currentTimeMillis() / 1000);
	 }
	 /**
	  * 解析进来的消息
	  * @param xml
	  * @return
	  * @throws Exception
	  */
	 public static Map<String, String> parseXml(String xml) throws Exception {
			// 解析结果存储在HashMap
			Map<String, String> map = new HashMap<String, String>();
			// 读取输入流
			SAXReader reader = new SAXReader();
			Document document = reader.read(new StringReader(xml));
			// 得到xml根元素
			Element root = document.getRootElement();
			// 得到根元素的所有子节点
			@SuppressWarnings("unchecked")
			List<Element> elementList = root.elements();
			// 遍历所有子节点
			logger.info("------------------------ xml ----------------------");
			for (Element e : elementList) {
				map.put(e.getName(), e.getText());
				logger.info(e.getName() + "\t:\t" + e.getText());
			}
			return map;
		}
	 /**
	  * 从请求中获取进来的url带参数
	  * @param request
	  * @return
	  */
	 public static String getUrl(HttpServletRequest request){
		 String url=request.getRequestURI();
		 if(url==null || "".equals(url)){
			 return "";
		 }
		 if(request.getQueryString()!=null && request.getQueryString().length()>0){
			url+="?"+request.getQueryString();
		 }
		 return url;
	 }


	 /**
	  * 
	  * @param appsId
	  * @param assessToken
	  * @param assessTicket
	  */
	 public static void saveTokenToDB(String appsId,String assessToken,String assessTicket){
		 try{
			 WeixinAppVO record=new WeixinAppVO();
			 record.setId(Long.parseLong(appsId));
			 record.setAcceccToken(assessToken);
			 record.setAccessTicket(assessTicket);
			 DaoUtil.instance().getAppDao().updateByPrimaryKeySelective(record);
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }
	 }
	 
	 public static boolean isWeixin(HttpServletRequest request) {
			String header = request.getHeader("User-Agent");
			if ((header != null) && (header.indexOf("MicroMessenger") > -1))
				return true;
			return ("WEIXIN".equals(request.getParameter("_CLIENT")));
		}
}
