package com.jiezh.dao.base.organ;

import com.github.pagehelper.Page;
import com.jiezh.dao.base.organcompany.OrganCompanyVO;

public interface BaseOrganDao {
    int deleteByPrimaryKey(Integer id);

    int insert(OrganVO record);

    int insertSelective(OrganVO record);

    OrganVO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrganVO record);

	/** 
	 
	* @author 陈继龙   E-mail:  cqcnihao@139.com 
	
	* @Title: selectOrganCompany 
	
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	
	* @param @param vo
	* @param @return    设定文件 
	
	* @return Page<OrganBean>    返回类型 
	
	* @throws 
	
	*/ 
	Page<OrganVO> selectOrgan(OrganVO vo);

    
}