package com.jiezh.content.base.codetype.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.jiezh.dao.base.codetype.CodeItemVO;
import com.jiezh.dao.base.codetype.CodeTypeVO;

public interface BaseCodeTypeService{
	public int addCodeType(CodeTypeVO recode);
	public List<CodeTypeVO> getCodeTypeList(CodeTypeVO recode);
	/** 
	* @author 陈继龙   E-mail:  cqcnihao@139.com 
	* @Title: search 
	* @param @param currenPage
	* @param @return    设定文件 
	* @return Object    返回类型 
	* @throws 
	*/ 
	public PageInfo<CodeTypeVO> search(int currenPage,CodeTypeVO recode);
	/** 
	* @author 陈继龙   E-mail:  cqcnihao@139.com 
	* @Title: findOne 
	* @param @param codeType
	* @param @return    设定文件 
	* @return CodeTypeVO    返回类型 
	* @throws 
	*/ 
	public CodeTypeVO findOne(String codeType);
	/** 
	* @author 陈继龙   E-mail:  cqcnihao@139.com 
	* @Title: updateCodeType 
	* @param @param codeTypeForm    设定文件 
	* @return void    返回类型 
	* @throws 
	*/ 
	public int updateCodeType(CodeTypeVO codeTypeForm);
	/***
	* @author 陈继龙  E-mail:  cqcnihao@139.com 
	* @date 2015年12月11日 下午4:24:13 
	* <p>Title: findMax</p> 
	* <p>Description: </p> 
	* @return 最大加 +1  
	*/ 
	public CodeTypeVO findMaxCodeType();
	/** 
	* @author 陈继龙   E-mail:  cqcnihao@139.com 
	* @Title: findPeiZhiSearch 
	* @param @param currenPage
	* @param @param vo
	* @param @return    设定文件 
	* @return Object    返回类型 
	* @throws 
	*/ 
	public PageInfo<CodeItemVO> findPeiZhiSearch(int currenPage, CodeTypeVO vo);
	public PageInfo<CodeItemVO> selectPeiZhiSearch(int currenPage, CodeItemVO vo);
	/** 
	* @author 陈继龙   E-mail:  cqcnihao@139.com 
	* @Title: addCodeItem 
	* @param @param codeItemForm    设定文件 
	* @return void    返回类型 
	* @throws 
	*/ 
	public int addCodeItem(CodeItemVO codeItemForm);
	/** 
	* @author 陈继龙   E-mail:  cqcnihao@139.com 
	* @Title: updateCodeItem 
	* @param @param codeItemForm    设定文件 
	* @return void    返回类型 
	* @throws 
	*/ 
	public int updateCodeItem(CodeItemVO codeItemForm);
	/** 
	* @author 陈继龙   E-mail:  cqcnihao@139.com 
	* @Title: delCodeItem 
	* @Description: 删除
	* @param @param codeItemId    设定文件 
	* @return void    返回类型 
	* @throws 
	*/ 
	public int delCodeItem(long codeItemId);
	/** 
	* @author 陈继龙   E-mail:  cqcnihao@139.com 
	* @Title: findOneCodeType 
	* @param @param codeItemId
	* @param @return    设定文件 
	* @return CodeItemVO    返回类型 
	* @throws 
	*/ 
	public CodeItemVO findOneCodeType(long codeItemId);
}
