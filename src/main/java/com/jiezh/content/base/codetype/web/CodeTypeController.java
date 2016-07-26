package com.jiezh.content.base.codetype.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.jiezh.content.base.codetype.service.BaseCodeTypeService;
import com.jiezh.content.base.pub.web.WebAction;
import com.jiezh.dao.base.codetype.CodeItemVO;
import com.jiezh.dao.base.codetype.CodeTypeVO;

/****
 * 
 * @ClassName: CodeTypeController
 * 
 * @author 陈继龙 E-mail: cqcnihao@139.com *
 * 
 * @date 2015年12月11日 下午4:59:51
 */
@Controller
@Scope("prototype")
@RequestMapping("/base/codetype/")
public class CodeTypeController extends WebAction {

	@Autowired
	BaseCodeTypeService baseCodeTypeService;

	@RequestMapping("index")
	public ModelAndView index() throws Exception {
		ModelAndView mv = new ModelAndView("base/codetype/list");
		Page<CodeTypeVO> page = new Page<CodeTypeVO>();
		mv.addObject("page", new PageInfo<CodeTypeVO>(page));
		return mv;
	}

	@RequestMapping("queryList")
	public ModelAndView queryList() throws Exception {
		ModelAndView mv = new ModelAndView("base/codetype/list");
		int currenPage = 1;
		if (request.getParameter("currenPage") != null && !"".equals(request.getParameter("currenPage"))) {
			currenPage = Integer
					.parseInt(request.getParameter("currenPage") == null ? "1" : request.getParameter("currenPage"));
		}
		CodeTypeVO recode = new CodeTypeVO();
		recode = (CodeTypeVO) this.getBean(recode.getClass());
		mv.addObject("findObj", recode);
		mv.addObject("page", baseCodeTypeService.search(currenPage, recode));
		return mv;
	}

	@RequestMapping("save")
	public ModelAndView save() throws Exception {
		CodeTypeVO codeTypeForm = new CodeTypeVO();
		codeTypeForm = (CodeTypeVO) this.getBean(codeTypeForm.getClass());
		if ("1".equals(codeTypeForm.getIsnew())) {
			baseCodeTypeService.addCodeType(codeTypeForm);
		} else
			baseCodeTypeService.updateCodeType(codeTypeForm);
		return queryList();
	}

	@RequestMapping("editor")
	@ResponseBody
	public CodeTypeVO editor() throws Exception {
		CodeTypeVO codeTypeEditor = new CodeTypeVO();
		codeTypeEditor = (CodeTypeVO) this.getBean(codeTypeEditor.getClass());
		codeTypeEditor = baseCodeTypeService.findOne(codeTypeEditor.getCodeType());
		return codeTypeEditor;
	}

	@RequestMapping("increased")
	@ResponseBody
	public CodeTypeVO increased() throws Exception {
		CodeTypeVO vo = baseCodeTypeService.findMaxCodeType();
		return vo;
	}

	@RequestMapping("pezhifind")
	public ModelAndView pezhifind(
			@RequestParam(value = "peizhipage", required = false, defaultValue = "0") String peizhipage)
					throws Exception {
		ModelAndView mv = new ModelAndView("base/codetype/pezhifind");
		mv.addObject("peizhipage", "peizhi");
		PageInfo<CodeItemVO> PageInfo = new PageInfo<CodeItemVO>();
		CodeTypeVO vo = new CodeTypeVO();
		vo = (CodeTypeVO) this.getBean(vo.getClass());
		int currenPage = 1;
		if (request.getParameter("currenPage") != null || !"".equals(request.getParameter("currenPage"))) {
			currenPage = Integer
					.parseInt(request.getParameter("currenPage") == null ? "1" : request.getParameter("currenPage"));
		}
		if ("peizhi".equals(peizhipage)) {
			CodeItemVO codeVo = new CodeItemVO();
			codeVo = (CodeItemVO) this.getBean(codeVo.getClass());
			PageInfo = baseCodeTypeService.selectPeiZhiSearch(currenPage, codeVo);
			mv.addObject("findObj", codeVo); // 控制查询回显示
			CodeTypeVO findvo = baseCodeTypeService.findOne(vo.getCodeType());
			mv.addObject("page", PageInfo);
			mv.addObject("codeTypeName", findvo.getName());
			mv.addObject("codeType", findvo.getCodeType());
		} else {
			PageInfo = baseCodeTypeService.findPeiZhiSearch(currenPage, vo);
			vo = baseCodeTypeService.findOne(vo.getCodeType());
			mv.addObject("page", PageInfo);
			mv.addObject("codeTypeName", vo.getName());
			mv.addObject("codeType", vo.getCodeType());
		}
		return mv;
	}

	@RequestMapping("pezhisave")
	public ModelAndView pezhisave() throws Exception {
		CodeItemVO codeItemForm = new CodeItemVO();
		codeItemForm = (CodeItemVO) this.getBean(codeItemForm.getClass());
		if ("1".equals(codeItemForm.getIsnew())) {
			baseCodeTypeService.addCodeItem(codeItemForm);
		} else
			baseCodeTypeService.updateCodeItem(codeItemForm);
		return pezhifind("");
	}

	@RequestMapping("delpezhi")
	public ModelAndView delpezhi(@RequestParam("codeItemId") long codeItemId) throws Exception {
		baseCodeTypeService.delCodeItem(codeItemId);
		return pezhifind("");
	}

	@RequestMapping("editorpeizhi")
	@ResponseBody
	public CodeItemVO editorpeizhi(@RequestParam("codeItemId") long codeItemId) throws Exception {
		CodeItemVO codeTypeEditor = new CodeItemVO();
		codeTypeEditor = baseCodeTypeService.findOneCodeType(codeItemId);
		return codeTypeEditor;
	}

}
