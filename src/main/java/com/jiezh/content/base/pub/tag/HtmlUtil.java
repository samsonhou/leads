package com.jiezh.content.base.pub.tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;

import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.content.base.pub.util.DaoUtil;
import com.jiezh.content.base.pub.web.WebTools;
import com.jiezh.dao.base.cache.CacheDao;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;
/**
 * 自定义标签
 * @author liangds
 *
 */
@SuppressWarnings("deprecation")
public class HtmlUtil implements TemplateMethodModel {

	@SuppressWarnings("rawtypes")
	@Override
	public Object exec(List list) throws TemplateModelException {
		StringBuffer sb = new StringBuffer();
		try {
			// 参数要按顺序来传递 格式如下
			// list[0] 类型 list[1]..之后为参数 严格来传参
			if (list != null && list.size() > 0) {
				String tag = (String) list.get(0);
				CacheDao dao= DaoUtil.instance().cacheDao();
				/**
				 * 下拉框
				 * 0 标准      select type codeType  defValue where haveHead headName
				 * 1 自定义  select type codeType  defValue paramName paramValue haveHead headName
				 */
				// select 标准型
				if ("select".equalsIgnoreCase(tag)) {
					String type=(String) list.get(1);
					String codeType=(String) list.get(2);
					String defValue=(String) list.get(3);
					if (defValue == null || "".equals(defValue)) {
						defValue = "";
					}
					//标准
					if("0".equals(type)){
						Map<String, String> map = new HashMap<String, String>();
						map.put("codeType", codeType);// codeType
						map.put("whereCase", (String) list.get(4));// where
						if("true".equals((String) list.get(5))){
							sb.append("<option value=''>"+((String) list.get(6))+"</option>");
						}
						List<Map<String, String>> options =dao.getOptions(map);
						if (options != null && options.size() > 0) {
							for (Map<String, String> option : options) {
								sb.append("<option " + (defValue.equals(option.get("VALUE")) ? "selected" : "") + " value='"+ option.get("VALUE") + "'>" + option.get("NAME") + "</option>");
							}
						}
					}
					//自定义
					else if("1".equals(type)){
						Map<String, String> map = new HashMap<String, String>();
						map.put("codeType", codeType);// codeType
						if("true".equals((String) list.get(6))){
							sb.append("<option value=''>"+((String) list.get(7))+"</option>");
						}
						//1.找出自定义sql
						String sql=dao.getCustomSql(map);
						if(sql==null || "".equals(sql)){
							return "";
						}
						Map<String, String> param = new HashMap<String, String>();
						param.put("sql", sql);
						splitStr((String) list.get(4),(String) list.get(5),param);
						List<Map<String, Object>> options =dao.getCustomOptions(param);
						if (options != null && options.size() > 0) {
							for (Map<String, Object> option : options) {
								sb.append("<option " + (defValue.equals(option.get("VALUE").toString()) ? "selected" : "") + " value='"+ option.get("VALUE").toString() + "'>" + option.get("NAME") + "</option>");
							}
						}
					}
				}
				//机构选择
				else if("organ".equalsIgnoreCase(tag)){
					String showLevel=(String) list.get(1) ;
					String defValue=(String) list.get(2);
					AuthorUser user=WebTools.getLoginUser(SecurityContextHolder.getContext().getAuthentication());
					String currOrganId=user.getOrganId();
					boolean flag=false;
					List<Map<String,String>> organList=null;
					//超级用户 从根查起
					if("ALL".equals(user.getOrganCode())){
						Map<String,String> param=new HashMap<String,String>();
						param.put("parentId", "0");
						param.put("status", "0");
						param.put("organCode", "ALL");
						organList=dao.getOrganInfo(param);
					}else{
						//1.从哪个开始显示 2.否则根据当前用户所在机构来显示
						if(showLevel!=null && showLevel.length()>0){
							currOrganId=showLevel;
							flag=true;
						}
						Map<String,String> param=new HashMap<String,String>();
						param.put("parentId", currOrganId);
						param.put("status", "1");
						param.put("organCode", user.getOrganCode());
						organList=dao.getOrganInfo(param);
					}
					//拼出select 两层 第1层显示当前机构 第二层
					sb.append("<span class='select_org' id='"+((String) list.get(5))+"'>");
					sb.append("<input type='hidden' data-seq='"+WebTools.getOrganLevel(user.getOrganCode(),defValue,currOrganId)+"' value='"+defValue+"' id='"+((String)list.get(3))+"' name='"+(String)list.get(4)+"'>");
					if("ALL".equals(user.getOrganCode())){
						//不显示当前机构
					}else{
						Map<String,String> map1=new HashMap<String,String>();
						map1.put("organId", flag?currOrganId:user.getOrganId());
						map1.put("organCode", user.getOrganCode());
						sb.append("<select "+((String) list.get(6))+" data-parent='1' data-first='1' onchange='organ.change(this);' >")
						  .append("<option value=''>请选择</option>")
						  .append("<option selected value='"+(flag?currOrganId:user.getOrganId())+"'>"+dao.getOrganByOrganId(map1).get("ABBR_NAME")+"</option>")
						  .append("</select>");
					}
					if(organList!=null && organList.size()>0){
						sb.append("<select "+((String) list.get(6))+" data-parent='1' "+("ALL".equals(user.getOrganCode())?" data-first='1' ":"")+" onchange='organ.change(this);'>");
						sb.append("<option value=''>请选择</option>");
						for (Map<String, String> option : organList) {
							sb.append("<option value='"+option.get("ORGAN_ID")+"'>").append(option.get("NAME")).append("</option>");
						}
						sb.append("</select>");
					}
					sb.append("</span>");
				}
			}
		} catch (Exception ex) {
			System.out.println("============TemplateModelException=======begin======");
			ex.printStackTrace();
			System.out.println("============TemplateModelException=======end======");
		}
		return sb.toString();
	}

	/**
	 * 分割参数 以逗号为分割符
	 * @param paramNames
	 * @param paramValues
	 * @param map
	 */
	private void splitStr(String paramNames,String paramValues,Map<String,String> map){
		String[] names=paramNames.split(",");
		String[] values=paramValues.split(",");
		if(names!=null && values!=null && names.length==values.length){
			for(int i=0;i<names.length;i++){
				map.put(names[i], values[i]);
			}
		}
	}
	
	
}
