/**  
* @Title: BaseGroupService.java 
* @Package com.jiezh.content.base.group.service 
* @author A18ccms A18ccms_gmail_com  
* @date 2015年12月8日 下午5:32:46 
* @version V1.0  
*/ 
package com.jiezh.content.base.group.service;

import java.util.List;

import com.jiezh.content.base.login.model.GroupBean;
import com.jiezh.dao.base.modulgroup.GroupModuleVO;
import com.jiezh.dao.base.modulgroup.ModuleGroupVO;

/** 
* @ClassName: BaseGroupService 
* @author 陈继龙  E-mail:  cqcnihao@139.com 
* @date 2015年12月8日 下午5:32:46 
*/
public interface BaseGroupService {
	/**
	 * 
	* @Title: getGroupByAll 
	* @Description: 取得全部 组信息
	* @return List<GroupBean>
	* @author 陈继龙   E-mail:  cqcnihao@139.com 
	* @date 2016年1月18日 上午10:31:31
	 */
	public List<GroupBean> getGroupByAll();
	/***
	 * 
	* @Title: getGroupByUserId 
	* @Description: 按照用户ID 查找到用户的组信息集合 
	* @param  userId
	* @return List<GroupBean>
	* @author 陈继龙  
	* @date 2016年1月18日 上午10:31:31
	 */
	public List<GroupBean> getGroupByUserId(Long userId);

	/** 
	* @author 陈继龙   E-mail:  cqcnihao@139.com 
	* @Title: getSelectGroupAll 
	* @param @return    设定文件 
	* @return Object    返回类型 
	* @throws 
	*/ 
	public List<ModuleGroupVO> getSelectGroupAll(ModuleGroupVO record);
	/** 
	* @author 陈继龙   E-mail:  cqcnihao@139.com 
	* @Title: save 
	* @param @param moduleGroupform    设定文件 
	* @return void    返回类型 
	* @throws 
	*/ 
	public int save(ModuleGroupVO moduleGroupform);

	/***
	* @Title: getModuleUrByGroupId 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param vo
	* @return List<GroupModuleVO>
	* @author 陈继龙  
	* @date 2016年1月18日 上午10:31:31
	 */
	public List<GroupModuleVO> getModuleUrByGroupId(GroupModuleVO vo);

	 /**
	  * 
	 * @Title: saveModurleGroup 
	 * @Description: 保持菜单分配到组的信息
	 * @param modurlegroupformlist
	 * @param isGroupModule
	 * @param id
	 * @return int
	 * @author 陈继龙  
	 * @date 2016年1月18日 上午10:31:31
	  */
	public int saveModurleGroup(List<?> modurlegroupformlist,boolean isGroupModule,long id);

	/**
	 * 
	 * @Title: findOneGroup 
	 * @Description: 按照 groupId 查找 一个菜单信息  vo 信息
	 * @param groupId
	 * @return ModuleGroupVO
	 * @author 陈继龙   E-mail:  cqcnihao@139.com 
	 * @date 2016年1月18日 上午10:31:31
	 */
	public ModuleGroupVO findOneGroup(long groupId);

	/** 
	* @author 陈继龙   E-mail:  cqcnihao@139.com 
	* @Title: updateGroup 
	* @param moduleGroupform    设定文件 
	* @return int    返回类型 
	* @throws 
	*/ 
	public int updateGroup(ModuleGroupVO moduleGroupform);
	/**
	* @author 陈继龙   E-mail:  cqcnihao@139.com 
	* @Title: getSelectGroupAllByUserId 
	* @param  userId
	* @return List<ModuleGroupVO>    返回类型 
	* @throws 
	*/ 
	public List<ModuleGroupVO> getSelectGroupAllByUserId(long userId);
}
