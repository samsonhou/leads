package com.jiezh.content.leads.search.web;

import java.io.File;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.jiezh.content.base.pub.Env;
import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.content.base.pub.token.Token;
import com.jiezh.content.base.pub.util.DateUtil;
import com.jiezh.content.base.pub.web.WebAction;
import com.jiezh.content.leads.service.ClientService;
import com.jiezh.dao.leads.client.ClientVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * 客户线索查询
 * @author wp
 *
 */

@Controller
@Scope("prototype")
@RequestMapping("/leads/search/")
public class SearchController extends WebAction{
	@Autowired
	ClientService clientService;	
	@Autowired
	SessionRegistry sessionRegistry;
	
	//add by cj begin
	@RequestMapping("recycled")
	public ModelAndView recycled() throws Exception {
		ModelAndView mv=new ModelAndView("leads/search/clientlistOfDel");
		Page<ClientVO> page=new Page<ClientVO>();
		mv.addObject("page", new PageInfo<ClientVO>(page));
		mv.addObject("clientVO", new ClientVO());
		return mv ;
	}

	//add by cj 回收站线索查询
	@RequestMapping("queryListOfCol")
	public ModelAndView queryListOfCol() throws Exception{
		ModelAndView mv=new ModelAndView("leads/search/clientlistOfDel");
		
		ClientVO clientVO=(ClientVO) getBean(ClientVO.class);
		int currenPage=1;
		if(request.getParameter("currenPage")!=null || !"".equals(request.getParameter("currenPage"))){
			currenPage=Integer.parseInt(request.getParameter("currenPage"));
		}
		AuthorUser user=getUser();
		clientVO.setRid(user.getUserId().intValue());
		clientVO.setSysOrganCode(user.getOrganCode());
		clientVO.setCompanyid(user.getOrganId());
		
		String flag = ""; //add by cj 判断是否是管理员 0是 1否
		
		String dealPerson = request.getParameter("sidName");
		String stime = request.getParameter("stnextdate");
		String etime = request.getParameter("nextdate");
		//查询条件临时存进去vo
		clientVO.setEmail(stime);
		clientVO.setTitle(etime);
		
		mv.addObject("page", clientService.getSearchList1(currenPage,clientVO));
		mv.addObject("clientVO", clientVO);
		mv.addObject("dealPerson",dealPerson);
		mv.addObject("stnextdate", stime);
		mv.addObject("nextdate", etime);
		
		
		if(request.getParameter("currenPage")!=null || !"".equals(request.getParameter("currenPage"))){
			currenPage=Integer.parseInt(request.getParameter("currenPage"));
		}
		//是否管理员 管理员查询它所在机构下所有的 
		if("1".endsWith(checkRole(Env.ROLE_MANAGE))){
			//add by cj 
			flag = "0";
		}else{
			//add by cj 
			flag = "1";
		}
		mv.addObject("flag", flag);	
		return mv ;
	}
	
	//add by cj 
	@RequestMapping("renewData")
	public ModelAndView renewData() throws Exception {
		String[] ids = request.getParameter("ids").split(",");
		clientService.renewData(Arrays.asList(ids));
		return queryListOfCol() ;
	}
	//add by cj end

	@RequestMapping("index")
	public ModelAndView index() throws Exception {
		ModelAndView mv=new ModelAndView("leads/search/clientlist");
		Page<ClientVO> page=new Page<ClientVO>();
		
		AuthorUser user=getUser();
		String flag="";
		//是否管理员 管理员查询它所在机构下所有的 
		if("1".endsWith(checkRole(Env.ROLE_MANAGE))&&user.getOrganId().equals("00")){
			//add by cj 
		    flag = "0";
		}else{
			//add by cj 
			flag = "1";
		}
		mv.addObject("flag", flag);	
		
		mv.addObject("page", new PageInfo<ClientVO>(page));
		mv.addObject("clientVO", new ClientVO());
		mv.addObject("count", clientService.getClientCount());
		//add by cj
		mv.addObject("ogId", user.getOrganId());	
		return mv ;
	}

	@RequestMapping("queryList")
	public ModelAndView queryList() throws Exception{
		ModelAndView mv=new ModelAndView("leads/search/clientlist");
		
		ClientVO clientVO=(ClientVO) getBean(ClientVO.class);
		int currenPage=1;
		if(request.getParameter("currenPage")!=null || !"".equals(request.getParameter("currenPage"))){
			currenPage=Integer.parseInt(request.getParameter("currenPage"));
		}
		AuthorUser user=getUser();
		clientVO.setRid(user.getUserId().intValue());
		clientVO.setSysOrganCode(user.getOrganCode());
		clientVO.setCompanyid(user.getOrganId());
		
		
		String dealPerson = request.getParameter("sidName");
		String stime = request.getParameter("stnextdate");
		String etime = request.getParameter("nextdate");
		String companyid = request.getParameter("organId");
		//查询条件临时存进去vo
		clientVO.setEmail(stime);
		clientVO.setTitle(etime);
		
		String flag = "";
		if("1".endsWith(checkRole(Env.ROLE_MANAGE))){
			clientVO.setCadTmp(companyid);
			flag = "0";
		}else{ 
			clientVO.setCadTmp(user.getOrganId());
			flag = "1";
		}
		mv.addObject("flag", flag);	
		
		mv.addObject("page", clientService.getSearchList(currenPage,clientVO));
		System.out.println(clientVO.getClientName());
		mv.addObject("clientVO", clientVO);
		mv.addObject("dealPerson",dealPerson);
		mv.addObject("stnextdate", stime);
		mv.addObject("nextdate", etime);
		mv.addObject("ogId", user.getOrganId());
		
		mv.addObject("organId",clientVO.getCadTmp());
		mv.addObject("count", clientService.getClientCount());
		return mv ;
	}
	
	/**
	 * @throws Exception
	 * 查询机构和人的数
	 */
	@RequestMapping(value = "queryOrgPerson")
	@ResponseBody
	public String queryOrgPerson()	throws Exception {
		AuthorUser user=getUser();
		String str0 = clientService.queryOrgPerson("",user.getOrganId(),user.getUserId(),user.getOrganCode());
		
		response.getWriter().print(str0);
		// response.getWriter().print(str0 );

		return null;
	}
	

	//add by cj 
	@RequestMapping("Inputstatistics")
	public ModelAndView Inputstatistics() throws Exception {
		ModelAndView mv=new ModelAndView("leads/search/list");
		Page<ClientVO> page=new Page<ClientVO>();
		mv.addObject("page", new PageInfo<ClientVO>(page));
		mv.addObject("clientVO", new ClientVO());
		return mv ;
	}
	
	
	//add by cj 
		@RequestMapping("queryListByUser")
		public ModelAndView queryListByUser() throws Exception {
			ModelAndView mv=new ModelAndView("leads/search/list");
		
			String userId = request.getParameter("userId");
			String stime = request.getParameter("stnextdate");
			String etime = request.getParameter("nextdate");

			Map<String,Object> paras = new HashMap<String,Object>();
			paras.put("userid", userId);
			paras.put("stime", stime);
			paras.put("etime", etime);		
			
			mv.addObject("userId",userId);
			mv.addObject("stnextdate", stime);
			mv.addObject("nextdate", etime);
	
			mv.addObject("data", clientService.getTotal(paras,"".equals(userId)||userId==null));
			return mv ;
		}
		//add by cj 回复率
		@Token(generate=true)
		@RequestMapping("replyList")
		public ModelAndView replyList() throws Exception{
			ModelAndView mv=new ModelAndView("leads/search/replyList");
			
			String userId = request.getParameter("sid");
			String stime = request.getParameter("stnextdate");
			String etime = request.getParameter("nextdate");
			String sidName = request.getParameter("sidName");
			
		
			AuthorUser user=getUser();
			String organId = request.getParameter("organId")==""||request.getParameter("organId")==null?user.getOrganId():request.getParameter("organId");
			if(stime == null){
				//mv.addObject("organId",user.getOrganId());
				mv.addObject("organCode",user.getOrganCode());
				mv.addObject("stnextdate", new SimpleDateFormat("yyyy-MM-dd").format(new Date())+" 00:00");
				mv.addObject("nextdate", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
				mv.addObject("ogId", user.getOrganId());
				return mv;
			}
	
			Map<String,Object> paras = new HashMap<String,Object>();
			paras.put("userid", userId);
			paras.put("stime", stime);
			paras.put("etime", etime);		
			paras.put("orgcode", user.getOrganCode());		
			paras.put("organId", organId);		
			
			mv.addObject("sid",userId);
			mv.addObject("stnextdate", stime);
			mv.addObject("nextdate", etime);
			mv.addObject("organId", organId);
			
			//通过存过统计
			mv.addObject("data", clientService.analyzeRepleat(organId, stime, etime));
			
			//mv.addObject("data", clientService.getHfCount(paras));
			
			mv.addObject("time", stime +" ~ " + etime);
			//mv.addObject("organId",user.getOrganId());
			//mv.addObject("organCode",user.getOrganCode());
			mv.addObject("dealPerson",sidName);
			mv.addObject("ogId", user.getOrganId());
			
			return mv ;
		}	
		
		@RequestMapping("exportExcel")
		public void exportExcel() throws Exception{
			ModelAndView mv=new ModelAndView("leads/search/clientlist");
			ClientVO clientVO=(ClientVO) getBean(ClientVO.class);
			AuthorUser user=getUser();
			clientVO.setRid(user.getUserId().intValue());
			clientVO.setSysOrganCode(user.getOrganCode());
			clientVO.setCompanyid(user.getOrganId());
			
			String dealPerson = request.getParameter("sidName");
			String stime = request.getParameter("stnextdate");
			String etime = request.getParameter("nextdate");
			String companyid = request.getParameter("organId");
			//查询条件临时存进去vo
			clientVO.setEmail(stime);
			clientVO.setTitle(etime);
			clientVO.setCadTmp(companyid);
			
			mv.addObject("clientVO", clientVO);
			mv.addObject("dealPerson",dealPerson);
			mv.addObject("stnextdate", stime);
			mv.addObject("nextdate", etime);
			mv.addObject("count", clientService.getClientCount());
			mv.addObject("organId", companyid);

			response.reset();
	        response.setContentType("application/vnd.ms-excel;charset=utf-8");
	        response.setHeader("Content-Disposition", "attachment;filename=" + new String(("客户线索查询"+DateUtil.date2String(new Date(), "yyyy-MM-dd HH:mm:ss") +".xls").getBytes(), "iso-8859-1"));
	        OutputStream outputStream=response.getOutputStream();
	        outputStream.write(clientService.getSearchListOfExcel(clientVO));
	        outputStream.close();
		}
		//add by cj 
		@RequestMapping("queryPlan")
		public ModelAndView queryPlan() throws Exception {
			ModelAndView mv=new ModelAndView("leads/search/planlist");
			int currenPage=1;
			if(request.getParameter("currenPage")!=null && !"".equals(request.getParameter("currenPage"))){
				currenPage=Integer.parseInt(request.getParameter("currenPage"));
			}
			Map<String,Object> paras = new HashMap<String,Object>();
			String month = request.getParameter("month");
			String organId = request.getParameter("organId");
			paras.put("month", month);
			paras.put("organId", organId);
			mv.addObject("page", clientService.getPlanList(currenPage,paras));
			
			mv.addObject("organId",organId);
			mv.addObject("month",month);
			return mv ;
		}
		//add by cj 
		@RequestMapping(value = "modifyPlan")
		@ResponseBody
		public String modifyPlan(String id,String num,String month)	throws Exception {
			String date = new SimpleDateFormat("yyyy-MM").format(new Date());
			
			Map<String,Object> paras = new HashMap<String,Object>();
			paras.put("user_id", id);
			paras.put("month", month==null||"".equals(month)?date:month);
			paras.put("plan_num", num);
			Map<String,Object> result= clientService.getPlan(paras);
			int count = clientService.updatePlan(result,paras);
			response.getWriter().print("保存成功!");
			return null;
		}
		
		//add by cj test
		@RequestMapping("test")
		public ModelAndView test() throws Exception {
			//ModelAndView mv=new ModelAndView("leads/search/listtest");	
			ModelAndView mv=new ModelAndView("leads/search/listtest3");     
			return mv ;
		}
		
		//add by cj 
		@RequestMapping(value = "getData")
		@ResponseBody
		public String getData(String id,String num)	throws Exception {		
			String date = new SimpleDateFormat("yyyy-MM").format(new Date());					
			Map<String,Object> paras = new HashMap<String,Object>();
			response.getWriter().print(date);
			return null;
		}
		
		//add by cj 
		@RequestMapping("queryPlanFist")
		public ModelAndView queryPlanFist() throws Exception {
			ModelAndView mv=new ModelAndView("leads/search/planlist");				
			return mv ;
		}
		
		//add by cj 
		@RequestMapping(value = "getExists")
		@ResponseBody
		public String getExists(String connum) throws Exception {		
			String result = clientService.getIsExistsOfClient(connum);
			response.getWriter().print(result);
			return null;
		}
		
		
		//add by cj test 
		@RequestMapping(value = "getParent")
		@ResponseBody
		public String getParent(String id) throws Exception {
			JSONArray jsArray = new JSONArray();
		    JSONObject jsObj1 = new JSONObject();
		    JSONObject jsObj2 = new JSONObject();
		    jsObj1.element("id", "1");
		    jsObj1.element("name", "name1");
		    jsObj1.element("isParent", "true");
		    
		    jsObj2.element("id", "2");
		    jsObj2.element("name", "name2");
		    jsObj2.element("isParent", "true");
			
		    jsArray.add(jsObj1);
		    jsArray.add(jsObj2);
			response.getWriter().print(jsArray.toString());
			return null;
		}
		
		//add by cj test 
		@RequestMapping(value = "getChild")
		@ResponseBody
		public String getChild(String id) throws Exception {
			
			JSONArray jsArray = new JSONArray();	    
		    for(int i=0;i<300;i++){
		    	 JSONObject jsObj1 = new JSONObject();
		    	 jsObj1.element("id", "id"+i);
				 jsObj1.element("name", "childName"+i);
				 jsObj1.element("isParent", "false");
				 jsArray.add(jsObj1);
		    }  
			response.getWriter().print(jsArray.toString());
			return null;
		}
		
		//add by cj test 
		@RequestMapping("impFile")
		public ModelAndView impFile(@RequestParam(value = "file") MultipartFile file) {	
			String path = this.request.getSession().getServletContext().getRealPath("/") + "/res/pub/imgT";
			ModelAndView mv=new ModelAndView("leads/search/listtest3");			
			File f = new File(path+File.separator+file.getOriginalFilename());		
			try { 
				if(!f.exists()){
					f.createNewFile();
				}
				file.transferTo(f);		
			} catch (Exception e) {				
				e.printStackTrace();
			}
			mv.addObject("name", file.getOriginalFilename());
			return mv ;
		}
		
		@Token(validate=true)
		@RequestMapping("exportReport")
		public ModelAndView exportReport() throws Exception{
			AuthorUser user = this.getUser();
			String stime = request.getParameter("stnextdate");
			String etime = request.getParameter("nextdate");
			String organId = request.getParameter("organId")==""||request.getParameter("organId")==null?user.getOrganId():request.getParameter("organId");
			response.reset();
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(("追踪率报表.xls").getBytes(), "iso-8859-1"));
			OutputStream os = response.getOutputStream();
			os.write(this.clientService.exportReport( stime, etime, organId));
			os.close();
			return null;
		}
}
