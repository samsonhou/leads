package com.jiezh.dao.base.fromtype;

import java.util.List;
import java.util.Map;

public interface BaseSourceVODao {
    int deleteByPrimaryKey(Long id);

    int insert(BaseSourceVO record);

    int insertSelective(BaseSourceVO record);

    BaseSourceVO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaseSourceVO record);

    int updateByPrimaryKey(BaseSourceVO record);

    List<Map<String, Object>> selectVoByMap(Map<String, Object> map);

    List<BaseSourceVO> selectListByVo(BaseSourceVO vo);
}
