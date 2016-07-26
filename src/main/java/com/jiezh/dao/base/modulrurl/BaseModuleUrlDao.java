package com.jiezh.dao.base.modulrurl;

import java.util.List;

import com.jiezh.dao.base.modulrurl.ModuleUrlVO;

public interface BaseModuleUrlDao {
    int deleteByPrimaryKey(long moduleId);

    int insert(ModuleUrlVO record);

    int insertSelective(ModuleUrlVO record);

    ModuleUrlVO selectByPrimaryKey(long moduleId);

    int updateByPrimaryKeySelective(ModuleUrlVO record);

	/** 
	 
	* @author 陈继龙   E-mail:  cqcnihao@139.com 
	
	* @Title: findAll 
	
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	
	* @param @param recode
	* @param @return    设定文件 
	
	* @return Page<ModuleUrlVO>    返回类型 
	
	* @throws 
	
	*/ 
    
	List<ModuleUrlVO> findAll(ModuleUrlVO recode);

}