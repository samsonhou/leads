package com.jiezh.dao.api.moor;

import java.util.List;
import java.util.Map;

import com.jiezh.dao.base.user.UserVO;

public interface UserMoorVODao {
    int deleteByPrimaryKey(Long id);

    int insert(UserMoorVO record);

    int insertSelective(UserMoorVO record);

    UserMoorVO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserMoorVO record);

    int updateByPrimaryKey(UserMoorVO record);

    UserVO selectUser(Map<String, Object> param);

    String selectUserAgentIdByUserId(Long userId);

    Long selectUserIdByUserName(String userName);

    void deleteAllUserMoor();

    int insertUserMoorList(List<UserMoorVO> userMoorList);

    void recordLog2DB(Map<String, String> logMap);

}
