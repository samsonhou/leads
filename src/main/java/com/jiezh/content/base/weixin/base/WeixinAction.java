package com.jiezh.content.base.weixin.base;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import com.jiezh.content.base.pub.Log;
import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.content.base.pub.util.BeanUtil;
import com.jiezh.content.base.pub.web.GeneralBean;
import com.jiezh.content.base.weixin.tools.GOauth2Core;
import com.jiezh.content.base.weixin.tools.WeixinConfig;
import com.jiezh.content.base.weixin.tools.WeixinTools;
import com.jiezh.content.weixin.jieyi.pub.DaoUtil;


/**
 * weixi base action 
 * 所有weixi请求的action都要继承它
 * @author liangds
 *
 */
public abstract class WeixinAction{
	Log logger=new Log(WeixinAction.class);
	private String weixinLoginInfo="";
	private String agentId="0";//应用ID 只能在index方法里面取得到
	private String WX_USER_KEY="WX_USER_KEY";
	private String WX_USER_KEY_USERNAME="WX_USER_KEY_USERNAME";

	public String getWeixinLoginInfo() {
		return weixinLoginInfo;
	}
	public void setWeixinLoginInfo(String weixinLoginInfo) {
		this.weixinLoginInfo = weixinLoginInfo;
	}
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	 @ModelAttribute
	 public void setReq(HttpServletRequest request) throws Exception{
	      request.setAttribute("contextPath", request.getContextPath());
	 }  
	/**
	 * 初始化是处理weixin请求
	 * 所有的请求都需要带上 state=?s
	 */
	public void init(HttpServletRequest request,HttpServletResponse response) {
		logger.info("WeixinAction ini");
		logger.info(WeixinTools.getUrl(request));
		String result="";
		if (WeixinTools.isWeixin(request)) {
			Map<String, Object> param = _getParameters(request);
			
			String agentId=(String) param.get("state");
			logger.info("state>>:"+agentId);
			if(agentId==null || "".equals(agentId)){
				result="未知的应用ID！";
				setWeixinLoginInfo(result);
				return;
			}else{
				setAgentId(agentId);
			}
			logger.info("agentId>>:"+agentId);
			
			String code = (String) param.get("code");
			logger.info("code>>:"+code);
			//code区分是从wx进来的 还是内部跳转
			//内部跳转
			if(code==null){
				//
			}
			//从wx菜单进来
			else{
				code = code.trim();
				if ("authdeny".equals(code)) {
					result="该应用未授权!";
					setWeixinLoginInfo(result);
					return;
				}
				String UserID = GOauth2Core.GetUserID(WeixinConfig.getAccessToken(agentId), code, agentId);
				if (UserID == null || "".equals(UserID)) {
					result="获取weixin用户信息失败！";
					setWeixinLoginInfo(result);
					return;
				}
				logger.info("login userId>>:"+UserID);
				setWeixinLoginInfo(setUser(request,UserID,agentId));
			}
		}else{
			result="获取用户信息失败！";
			setWeixinLoginInfo(result);
		}
		logger.info("result>>:"+result);
	}
	
	public  Map<String, Object> _getParameters(HttpServletRequest request) {
		Map<String,Object> m = new HashMap<String,Object>();
		if(request==null){
			return m;
		}
		Enumeration<?> en =request.getParameterNames();
		while (en.hasMoreElements()) {
			Object enN = en.nextElement();
			String para = request.getParameter(enN.toString()).trim();
			m.put(enN.toString(), ("undefined".equals(para)) ? "" : para.trim());
		}
		return m;
	}
	/**
	 * 成功返回页面
	 * @param returnUrl 点返回时跳转的页面
	 * @param msg
	 * @return
	 */
	public ModelAndView successful(String returnUrl,String msg){
		ModelAndView mv=new ModelAndView("weixin/pub/successful");
		mv.addObject("url", returnUrl);
		mv.addObject("msg", msg);
		return mv;
	}
	/**
	 * 错误提示页面
	 * @param returnUrl
	 * @param msg
	 * @return
	 */
	public ModelAndView error(String returnUrl,String msg){
		ModelAndView mv=new ModelAndView("weixin/pub/error");
		mv.addObject("url", returnUrl);
		mv.addObject("msg", msg);
		return mv;
	}
	
	public Object getBean(Class<? extends  GeneralBean> aClass,HttpServletRequest request){
    	return BeanUtil.getBean(aClass, request);
    }
    
    public List<?> getBeanList(Class<? extends  GeneralBean> aClass,HttpServletRequest request){
    	return BeanUtil.getBeanList(aClass, request, true);
    }
	
    private String setUser(HttpServletRequest request,String userId,String agentId){
    	AuthorUser aAuthorUser=(AuthorUser) request.getSession().getAttribute(WX_USER_KEY);
    	if(aAuthorUser==null){
    		Map<String,String> param=new HashMap<String,String>();
			param.put("userCode", userId);
			param.put("agentId", agentId);
			String userCode=DaoUtil.instance().getWeixinUserDao().getUserByCode(param);
			if(userCode==null || "".equals(userCode)){
				return "跟踪回复微信端未被授权!";
			}
			aAuthorUser=com.jiezh.content.base.pub.util.DaoUtil.instance().cacheDao().getUserByUserName(userCode);
			if(aAuthorUser==null){
				return "无法找到系统对应用户!";
			}else if("0".equals(aAuthorUser.getEnable())){
				return "用户已失效,请联系管理员!";
			}else if("1".equals(aAuthorUser.getIsLock())){
				return "您的账号已被锁定,请联系管理员解锁 !";
			}
			request.getSession().setAttribute(WX_USER_KEY, aAuthorUser);
			request.getSession().setAttribute(WX_USER_KEY_USERNAME, aAuthorUser.getRealName());
    	}
    	return "";
    }
    
    public AuthorUser getUser(HttpServletRequest request){
    	AuthorUser aAuthorUser=(AuthorUser) request.getSession().getAttribute(WX_USER_KEY);
    	return aAuthorUser;
    }
    
    public long getUserId(HttpServletRequest request){
    	AuthorUser aAuthorUser=getUser(request);
    	if(aAuthorUser==null){
    		return 0;
    	}else{
    		return aAuthorUser.getUserId();
    	}
    }
    
    public void checkSession(HttpServletRequest request) throws Exception{
    	if(getUser(request)==null){
    		throw new Exception("操作超时,请重新进入");
    	}
    }
}
