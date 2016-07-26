
package com.jiezh.content.base.weixin.base;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.util.FileCopyUtils;
import com.jiezh.content.base.pub.Log;
import com.jiezh.content.base.weixin.tools.WeixinConfig;
import com.jiezh.content.base.weixin.tools.WeixinTools;
import com.jiezh.content.base.weixin.tools.encry.AesException;
import com.jiezh.content.base.weixin.tools.encry.WXBizMsgCrypt;

/**
 * 验证回调url
 * @author liangds
 * @since  2016年1月18日 下午3:31:09
 */
public class AgentAction{
	Log logger=new Log(AgentAction.class);
	public String agentId="1";//每一个应用对应一个ID
	public void execute(HttpServletRequest pRequest,HttpServletResponse pResponse) {
		try {
			Map<String, Object> param = _getParameters(pRequest);
			//1.url验证
			for (Entry<String, Object> entry : param.entrySet()) {
				logger.info(entry.getKey() + "\t:\t" + entry.getValue());
			}
			WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(WeixinConfig.getToken(agentId), WeixinConfig.getEncodingAesKey(agentId), WeixinConfig.getCorpId());
			String timestamp = pRequest.getParameter("timestamp");
			String nonce = pRequest.getParameter("nonce");
			String msg_signature =pRequest.getParameter("msg_signature");
			if(pRequest.getParameter("echostr")!=null){
				String echostr = pRequest.getParameter("echostr");
				String sEchoStr = wxcpt.VerifyURL(msg_signature, timestamp,nonce, echostr);
				if (echostr != null) { 
					out(pResponse,sEchoStr);
				}
			}
			else{
				//2.事件接收
				try {
					String xml = FileCopyUtils.copyToString(pRequest.getReader());
					String res = wxcpt.DecryptMsg(msg_signature.trim(), timestamp.trim(), nonce.trim(), xml);
					WeixinTools.parseXml(res);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (AesException e1) {
			e1.printStackTrace();
		}
	}
	public void out(HttpServletResponse response,String echostr){
		try {
			response.getWriter().write(echostr);
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				response.getWriter().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public  Map<String, Object> _getParameters(HttpServletRequest pRequest) {
		Map<String,Object> m = new HashMap<String,Object>();
		if(pRequest==null){
			return m;
		}
		@SuppressWarnings("rawtypes")
		Enumeration en =pRequest.getParameterNames();
		while (en.hasMoreElements()) {
			Object enN = en.nextElement();
			String para = pRequest.getParameter(enN.toString()).trim();
			m.put(enN.toString(), ("undefined".equals(para)) ? "" : para.trim());
		}
		return m;
	}
}
