package com.jiezh.dao.message;

import java.util.List;

public interface MessageAnswerVODao {
    int deleteByPrimaryKey(Long id);

    int insert(MessageAnswerVO record);

    int insertSelective(MessageAnswerVO record);

    MessageAnswerVO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MessageAnswerVO record);

    int updateByPrimaryKey(MessageAnswerVO record);
    
    List<MessageAnswerVO> selectAnswerByVo(MessageAnswerVO vo);
}