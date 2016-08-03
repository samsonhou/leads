package com.jiezh.dao.leads.client;

import java.util.List;

public interface QaDetailVODao {
    int deleteByPrimaryKey(Long id);

    int insert(QaDetailVO record);

    int insertSelective(QaDetailVO record);

    QaDetailVO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(QaDetailVO record);

    int updateByPrimaryKey(QaDetailVO record);

    List<QaDetailVO> selectDetails(QaDetailVO condition);
}
