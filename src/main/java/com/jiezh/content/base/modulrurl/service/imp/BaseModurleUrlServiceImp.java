package com.jiezh.content.base.modulrurl.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiezh.content.base.modulrurl.service.BaseModurleUrlService;
import com.jiezh.dao.base.modulrurl.BaseModuleUrlDao;
import com.jiezh.dao.base.modulrurl.ModuleUrlVO;

@Service("BaseModurleUrlService")
public class BaseModurleUrlServiceImp implements BaseModurleUrlService{
	@Autowired
	BaseModuleUrlDao baseModuleUrlDao;

	/***
	
	* @author 陈继龙  E-mail:  cqcnihao@139.com 
	
	* @date 2015年12月10日 下午6:46:30 
	
	* <p>Title: search</p> 
	
	* <p>Description: </p> 
	
	* @param currenPage
	* @param recode
	* @return 
	
	* @see com.jiezh.content.base.modulrurl.service.BaseModurleUrlService#search(int, com.jiezh.dao.base.modulrurl.ModuleUrlVO) 
	
	*/ 
	@Override
	public PageInfo<ModuleUrlVO> search(int currenPage, ModuleUrlVO recode) {
		PageHelper.startPage(currenPage, 999999999);
		Page<ModuleUrlVO> page= (Page<ModuleUrlVO>) baseModuleUrlDao.findAll(recode);
		if(page==null){
			page =new Page<ModuleUrlVO>();
		}
		return new PageInfo<ModuleUrlVO> (page);
	}

	/***
	
	* @author 陈继龙  E-mail:  cqcnihao@139.com 
	
	* @date 2015年12月11日 上午10:28:11 
	
	* <p>Title: save</p> 
	
	* <p>Description: </p> 
	
	* @param moduleUrlform 
	
	* @see com.jiezh.content.base.modulrurl.service.BaseModurleUrlService#save(com.jiezh.dao.base.modulrurl.ModuleUrlVO) 
	
	*/ 
	@Override
	public void save(ModuleUrlVO record) {
		baseModuleUrlDao.insert(record);
	}
	@Override
	public ModuleUrlVO findOneModuleUr(long moduleId){
		return baseModuleUrlDao.selectByPrimaryKey(moduleId);
	}

	/***
	
	* @author 陈继龙  E-mail:  cqcnihao@139.com 
	
	* @date 2015年12月22日 上午11:45:09 
	
	* <p>Title: updateModurle</p> 
	
	* <p>Description: </p> 
	
	* @param moduleUrlform
	* @return 
	
	* @see com.jiezh.content.base.modulrurl.service.BaseModurleUrlService#updateModurle(com.jiezh.dao.base.modulrurl.ModuleUrlVO) 
	
	*/ 
	@Override
	public int updateModurle(ModuleUrlVO record) {
			return baseModuleUrlDao.updateByPrimaryKeySelective(record);
	}
	
	
}
