package com.jiezh.content.base.user.service;


import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.jiezh.content.base.login.model.GroupBean;
import com.jiezh.dao.base.user.RoleVO;
import com.jiezh.dao.base.user.UserVO;

public interface BaseUserService{
	public int addUser(UserVO recode);
	public PageInfo<UserVO> search(int currenPage,UserVO vo);
	public String getOrganInfo(String organId,String organCode,String defVal);
	/** 
	* @author 陈继龙   E-mail:  cqcnihao@139.com 
	* @Title: findOne 
	* @param userId 
	* @return UserVO
	* @throws 
	*/ 
	public UserVO findOne(long userId);

	public int save(String[] roles, UserVO uservoform)throws Exception;

	public int updateModurle(String[] roles,UserVO uservoform) throws Exception;
	/** 
	* @author 陈继龙   E-mail:  cqcnihao@139.com 
	* @Title: selectUsreRoles 
	* @param roleVO
	* @return List<RoleVO>    返回类型 
	* @throws 
	*/ 
	public List<RoleVO> selectUsreRoles(RoleVO roleVO);
	/** 
	* @author 陈继龙   E-mail:  cqcnihao@139.com 
	* @Title: selectUsreRolesByUserId 	
	* @param userId
	* @return List<RoleVO>    返回类型 
	* @throws 
	*/ 
	public List<RoleVO> selectUsreRolesByUserId(long userId);
	/***
	 * 
	* @Title: saveUserGroup 
        * @param newBeans
	* @param userId.
	* @return void
	* @author 陈继龙  
	* @date 2016年1月18日
	 */
	public void saveUserGroup(List<?> newBeans, long userId);
	
	public PageInfo<UserVO> searchOfAdBook(int currenPage,UserVO vo);
	/** 
	* @Title: checkUserOnly 
	* @Description: 检查用户登录名是否唯一
	* @param  @param userCode
	* @param  @return
	* @return String
	* @author 陈继龙  
	* @date 2016年1月22日
	*/
	public String checkUserOnly(String userCode,long userId);
	
	public Map<String, Object> getSmsInfo(Map<String, Object> params);
}
