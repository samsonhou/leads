package com.jiezh.dao.message;

import java.util.List;

public interface MessageTitleVODao {
    int deleteByPrimaryKey(Long id);

    int insert(MessageTitleVO record);

    int insertSelective(MessageTitleVO record);

    MessageTitleVO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MessageTitleVO record);

    int updateByPrimaryKey(MessageTitleVO record);

    List<MessageTitleVO> selectListByVo(MessageTitleVO vo);
}
