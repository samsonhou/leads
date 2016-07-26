package com.jiezh.content.base.modulrurl.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.jiezh.dao.base.modulgroup.ModuleGroupVO;
import com.jiezh.dao.base.modulrurl.ModuleUrlVO;

public interface BaseModurleUrlService {
	/***
	 * @author 陈继龙 E-mail: cqcnihao@139.com @Title:
	 * search @Description:(这里用一句话描述这个方法的作用)
	 * @param
	 * @param currenPage 
	 * @param 
	 * @param recode 
	 * @param 
	 * @return 设定文件 
	 * @return PageInfo<ModuleUrlVO> 
	 * 返回类型 @throws
	 */
	PageInfo<ModuleUrlVO> search(int currenPage, ModuleUrlVO recode);

	/**
	 * @author 陈继龙 E-mail: cqcnihao@139.com
	 * @Title: save
	 * @param @param moduleUrlform 设定文件
	 * @return void 返回类型
	 * @throws
	 * 
	 */
	void save(ModuleUrlVO moduleUrlform);

	/**
	 * @author 陈继龙 E-mail: cqcnihao@139.com
	 * @Title: findOne
	 * @param @param id @param @return 设定文件
	 * @return ModuleUrlVO 返回类型
	 * @throws
	 */
	ModuleUrlVO findOneModuleUr(long moduleId);

	/**
	 * @author 陈继龙 E-mail: cqcnihao@139.com
	 * @Title: updateModurle
	 * @param @param moduleUrlform 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	int updateModurle(ModuleUrlVO moduleUrlform);

}
