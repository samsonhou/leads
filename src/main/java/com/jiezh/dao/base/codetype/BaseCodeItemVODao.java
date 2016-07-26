package com.jiezh.dao.base.codetype;

import java.util.List;

public interface BaseCodeItemVODao {
    
	    int deleteByPrimaryKey(long codeItemId);

	    int insert(CodeItemVO record);

	    int insertSelective(CodeItemVO record);

	    CodeItemVO selectByPrimaryKey(long codeItemId);

	    int updateByPrimaryKeySelective(CodeItemVO record);

	    int updateByPrimaryKey(CodeItemVO record);
	    
	    List<CodeItemVO> selectByType(CodeTypeVO record);
	    
	    List<CodeItemVO> selectByType1(CodeItemVO recode);
    
}