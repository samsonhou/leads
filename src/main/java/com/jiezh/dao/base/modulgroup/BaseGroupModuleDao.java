package com.jiezh.dao.base.modulgroup;

import java.util.List;

import com.jiezh.dao.base.modulgroup.GroupModuleVO;

public interface BaseGroupModuleDao{
    int insert(GroupModuleVO record);

    int insertSelective(GroupModuleVO record);
    
	List<GroupModuleVO> selectGroupModule(GroupModuleVO vo);
	
	int deleteGroup(long groupId);
	
	int deleteModule(long moduleId);

	
}