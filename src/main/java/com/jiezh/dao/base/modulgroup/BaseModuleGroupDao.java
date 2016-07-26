package com.jiezh.dao.base.modulgroup;

import java.util.List;

import com.jiezh.dao.base.modulgroup.ModuleGroupVO;

public interface BaseModuleGroupDao {
    int deleteByPrimaryKey(long groupId);

    int insert(ModuleGroupVO record);

    int insertSelective(ModuleGroupVO record);

    ModuleGroupVO selectByPrimaryKey(long groupId);

    int updateByPrimaryKeySelective(ModuleGroupVO record);
    
	List<ModuleGroupVO> selectGroup(ModuleGroupVO record);

	List<ModuleGroupVO> selectGroupAllByUserId(long userId);

}