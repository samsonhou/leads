package com.jiezh.dao.base.codetype;

import java.util.List;

public interface BaseCodeTypeDao {
    int deleteByPrimaryKey(String codeType);

    int insert(CodeTypeVO record);

    int insertSelective(CodeTypeVO record);

    CodeTypeVO selectByPrimaryKey(String codeType);

    int updateByPrimaryKeySelective(CodeTypeVO record);
    
    List<CodeTypeVO> selectByAll(String codeType);
    
    List<CodeTypeVO> selectByCodeType(CodeTypeVO record);

    CodeTypeVO findByMax(); 
    
}