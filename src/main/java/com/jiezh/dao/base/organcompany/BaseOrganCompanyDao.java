package com.jiezh.dao.base.organcompany;

import java.util.Map;

import com.github.pagehelper.Page;
import com.jiezh.dao.base.organcompany.OrganCompanyVO;

public interface BaseOrganCompanyDao {
    int deleteByPrimaryKey(String organId, String organCode);

    int insert(OrganCompanyVO record);

    int insertSelective(OrganCompanyVO record);


    //int updateByPrimaryKeySelective( java.util.Map record);

    int updateByPrimaryKey(OrganCompanyVO record);

	/** 
	 
	* @author 陈继龙   E-mail:  cqcnihao@139.com 
	
	* @Title: selectOrganCompany 
	
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	
	* @param @param vo
	* @param @return    设定文件 
	
	* @return Page<OrganCompanyVO>    返回类型 
	
	* @throws 
	
	*/ 
	Page<OrganCompanyVO> selectOrganCompany(OrganCompanyVO vo);

	/** 
	 
	* @author 陈继龙   E-mail:  cqcnihao@139.com 
	
	* @Title: selectByPrimaryKey 
	
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	
	* @param @param findmap
	* @param @return    设定文件 
	
	* @return OrganCompanyVO    返回类型 
	
	* @throws 
	
	*/ 
	OrganCompanyVO selectByPrimaryKey(Map<String, Object>  findmap);
}