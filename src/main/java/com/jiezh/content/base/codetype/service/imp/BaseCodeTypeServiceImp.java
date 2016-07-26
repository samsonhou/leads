package com.jiezh.content.base.codetype.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiezh.content.base.codetype.service.BaseCodeTypeService;
import com.jiezh.content.base.pub.Env;
import com.jiezh.dao.base.cache.CacheDao;
import com.jiezh.dao.base.codetype.BaseCodeItemVODao;
import com.jiezh.dao.base.codetype.BaseCodeTypeDao;
import com.jiezh.dao.base.codetype.CodeItemVO;
import com.jiezh.dao.base.codetype.CodeTypeVO;


@Service("BaseCodeTypeService")
public class BaseCodeTypeServiceImp implements BaseCodeTypeService{
	@Autowired
	BaseCodeTypeDao baseCodeTypeDao;
	
	@Autowired
	BaseCodeItemVODao baseCodeItemVODao;
	
    @Autowired
	CacheDao cacheDao;
	
	@Override
	public int addCodeType(CodeTypeVO record) {
		return baseCodeTypeDao.insert(record);
	}
	
	@Override
	public List<CodeTypeVO> getCodeTypeList(CodeTypeVO recode) {
		List<CodeTypeVO>  codeTypeList=null;
		
		  codeTypeList= baseCodeTypeDao.selectByCodeType(recode);
		
		return codeTypeList;
	}
	
	@Override
	public PageInfo<CodeTypeVO> search(int currenPage,CodeTypeVO recode) {
		PageHelper.startPage(currenPage, Env.PAGE_SIZE);
		Page<CodeTypeVO> page= (Page<CodeTypeVO>) baseCodeTypeDao.selectByCodeType(recode);
		if(page==null){
			page =new Page<CodeTypeVO>();
		}
		return new PageInfo<CodeTypeVO>(page);
	}
	
	@Override
	public CodeTypeVO findOne(String codeType) {
		
		return baseCodeTypeDao.selectByPrimaryKey(codeType);
	}
	
	@Override
	public int updateCodeType(CodeTypeVO record) {
		return baseCodeTypeDao.updateByPrimaryKeySelective(record);
	}
	
	@Override
	public CodeTypeVO findMaxCodeType() {
		CodeTypeVO vo=baseCodeTypeDao.findByMax();
		long code=Long.parseLong(vo.getCodeType())+1;
		vo.setCodeType(String.valueOf(code));
		return vo;
	}
	
	@Override
	public PageInfo<CodeItemVO> findPeiZhiSearch(int currenPage,  CodeTypeVO recode) {
		PageHelper.startPage(currenPage, Env.PAGE_SIZE);
		Page<CodeItemVO> page= (Page<CodeItemVO>) baseCodeItemVODao.selectByType(recode);
		if(page==null){
			page =new Page<CodeItemVO>();
		}
		return new PageInfo<CodeItemVO>(page);
	}
	
	@Override
	public PageInfo<CodeItemVO> selectPeiZhiSearch(int currenPage, CodeItemVO recode) {
		PageHelper.startPage(currenPage, Env.PAGE_SIZE);
		Page<CodeItemVO> page= (Page<CodeItemVO>) baseCodeItemVODao.selectByType1(recode);
		if(page==null){
			page =new Page<CodeItemVO>();
		}
		return new PageInfo<CodeItemVO>(page);
	}
	
	@Override
	public int addCodeItem(CodeItemVO record) {
		return baseCodeItemVODao.insert(record);
	}
	
	@Override
	public int updateCodeItem(CodeItemVO record) {
		return baseCodeItemVODao.updateByPrimaryKey(record);
	}
	
	@Override
	public int delCodeItem(long codeItemId) {
		return baseCodeItemVODao.deleteByPrimaryKey(codeItemId);
	}
	
	@Override
	public CodeItemVO findOneCodeType(long codeItemId) {
		return baseCodeItemVODao.selectByPrimaryKey(codeItemId);
	}
	
}
