/**  

* @Title: BaseGroupServiceImp.java 

* @Package com.jiezh.content.base.group.service.imp 

* @author A18ccms A18ccms_gmail_com  

* @date 2015年12月8日 下午5:33:40 

* @version V1.0  

*/ 
package com.jiezh.content.base.group.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jiezh.content.base.group.service.BaseGroupService;
import com.jiezh.content.base.login.model.GroupBean;
import com.jiezh.dao.base.cache.CacheDao;
import com.jiezh.dao.base.modulgroup.BaseGroupModuleDao;
import com.jiezh.dao.base.modulgroup.BaseModuleGroupDao;
import com.jiezh.dao.base.modulgroup.GroupModuleVO;
import com.jiezh.dao.base.modulgroup.ModuleGroupVO;

/** 
* @ClassName: BaseGroupServiceImp 
* @author 陈继龙  E-mail:  cqcnihao@139.com *
* @date 2015年12月8日 下午5:33:40 
*/
@Service("base.group.baseGroupService")
public class BaseGroupServiceImp implements BaseGroupService {
    @Autowired
	CacheDao cacheDao;
    @Autowired
    BaseModuleGroupDao baseModuleGroupDao;
    @Autowired
    BaseGroupModuleDao baseGroupModuleDao;
	
    
	/***
	* @author 陈继龙  E-mail:  cqcnihao@139.com 
	* @date 2015年12月8日 下午5:36:26 
	* <p>Title: getGroupByAll</p> 
	* <p>Description: 全部获取组 </p> 
	* @return 
	* @see com.jiezh.content.base.group.service.BaseGroupService#getGroupByAll() 
	*/ 
	@Override
	public List<GroupBean> getGroupByAll() {
		//全部获取组
		List<GroupBean> listGroup=cacheDao.getModuleGroupAll();
		return listGroup;
	}

	/***
	* @author 陈继龙  E-mail:  cqcnihao@139.com 
	* @date 2015年12月9日 上午11:14:53 
	* <p>Title: getGroupByUserId</p> 
	* <p>Description: 获当取前用户组</p> 
	* @param userId
	* @return 
	* @see com.jiezh.content.base.group.service.BaseGroupService#getGroupByUserId(java.lang.Long) 
	*/ 
	@Override
	public List<GroupBean> getGroupByUserId(Long userId) {
		//获取组
		List<GroupBean> listGroup=cacheDao.getModuleGroup(userId);
		return listGroup;
	}

	/***
	
	* @author 陈继龙  E-mail:  cqcnihao@139.com 
	
	* @date 2015年12月18日 下午1:59:58 
	
	* <p>Title: getSelectGroupAll</p> 
	
	* <p>Description: </p> 
	
	* @return 
	
	* @see com.jiezh.content.base.group.service.BaseGroupService#getSelectGroupAll() 
	
	*/ 
	@Override
	public List<ModuleGroupVO> getSelectGroupAll(ModuleGroupVO record) {
		return baseModuleGroupDao.selectGroup(record);
	}

	/***
	
	* @author 陈继龙  E-mail:  cqcnihao@139.com 
	
	* @date 2015年12月18日 下午3:06:50 
	
	* <p>Title: save</p> 
	
	* <p>Description: </p> 
	
	* @param moduleGroupform
	* @return 
	
	* @see com.jiezh.content.base.group.service.BaseGroupService#save(com.jiezh.dao.base.modulgroup.ModuleGroupVO) 
	
	*/ 
	@Override
	public int save(ModuleGroupVO record) {
		return baseModuleGroupDao.insert(record);
	}

	@Override
	public ModuleGroupVO findOneGroup(long groupId) {
		return baseModuleGroupDao.selectByPrimaryKey(groupId);
		
	}
	/***
	
	* @author 陈继龙  E-mail:  cqcnihao@139.com 
	
	* @date 2015年12月18日 下午5:01:58 
	
	* <p>Title: getModuleUrByGroupId</p> 
	
	* <p>Description: </p> 
	
	* @param vo
	* @return 
	
	* @see com.jiezh.content.base.group.service.BaseGroupService#getModuleUrByGroupId(com.jiezh.dao.base.modulgroup.GroupModuleVO) 
	
	*/ 
	@Override
	public List<GroupModuleVO> getModuleUrByGroupId(GroupModuleVO vo) {
		return baseGroupModuleDao.selectGroupModule(vo);
	}

	/***
	
	* @author 陈继龙  E-mail:  cqcnihao@139.com 
	
	* @date 2015年12月21日 下午6:24:49 
	
	* <p>Title: saveModurleGroup</p> 
	
	* <p>Description: </p> 
	
	* @param modurlegroupform
	* @return 
	
	* @see com.jiezh.content.base.group.service.BaseGroupService#saveModurleGroup(com.jiezh.dao.base.modulgroup.GroupModuleVO) 
	
	*/ 
	@Override
	public int saveModurleGroup(List<?> modurlegroupformlist,boolean isGroupModule,long id) {
		
		if(isGroupModule){
			baseGroupModuleDao.deleteGroup(id);
		}else{
		    baseGroupModuleDao.deleteModule(id);
		}
		for(int i=0;i<modurlegroupformlist.size();i++){
		GroupModuleVO modurlegroup=(GroupModuleVO)modurlegroupformlist.get(i); 
		baseGroupModuleDao.insert(modurlegroup);
		}
		return 1;
	}

	/***
	
	* @author 陈继龙  E-mail:  cqcnihao@139.com 
	
	* @date 2015年12月22日 上午11:46:17 
	
	* <p>Title: updateGroup</p> 
	
	* <p>Description: </p> 
	
	* @param moduleGroupform 
	
	* @see com.jiezh.content.base.group.service.BaseGroupService#updateGroup(com.jiezh.dao.base.modulgroup.ModuleGroupVO) 
	
	*/ 
	@Override
	public int updateGroup(ModuleGroupVO record) {
		return baseModuleGroupDao.updateByPrimaryKeySelective(record);
	}

	
	@Override
	public List<ModuleGroupVO> getSelectGroupAllByUserId(long userId) {
		return baseModuleGroupDao.selectGroupAllByUserId(userId);
	}

	

	
	
	
}
