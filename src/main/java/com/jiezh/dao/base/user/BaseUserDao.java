package com.jiezh.dao.base.user;

import java.util.List;
import java.util.Map;

import com.jiezh.content.base.login.model.GroupBean;
import com.jiezh.dao.base.user.UserVO;

public interface BaseUserDao {

    int deleteByPrimaryKey(Long userId);

    int insert(UserVO record);

    UserVO selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(UserVO record);

    List<UserVO> selectByrecord(UserVO record);

    List<RoleVO> selectUsreRoles(RoleVO roleVO);

    /**
     * 
     * @author 陈继龙 E-mail: cqcnihao@139.com
     * 
     * @Title: deleteUser2RoleById
     * 
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * 
     * @param @param user2Rolemap 设定文件
     * 
     * @return void 返回类型
     * 
     * @throws
     * 
     */
    int deleteUser2RoleById(Map<String, String> user2Rolemap);

    int insertUser2Role(Map<String, String> user2Rolemap);

    /**
     * 
     * @author 陈继龙 E-mail: cqcnihao@139.com
     * 
     * @Title: selectUsreRolesByUserId
     * 
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * 
     * @param @param userId
     * @param @return 设定文件
     * 
     * @return List<RoleVO> 返回类型
     * 
     * @throws
     * 
     */
    List<RoleVO> selectUsreRolesByUserId(long userId);

    /**
     * 
     * @author 陈继龙 E-mail: cqcnihao@139.com
     * 
     * @Title: deleteUserGroupByUserid
     * 
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * 
     * @param @param userId
     * @param @return 设定文件
     * 
     * @return int 返回类型
     * 
     * @throws
     * 
     */
    int deleteUserGroupByUserid(long userId);

    /**
     * 
     * @author 陈继龙 E-mail: cqcnihao@139.com
     * 
     * @Title: insertUserGroup
     * 
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * 
     * @param @param groupBean
     * @param @return 设定文件
     * 
     * @return int 返回类型
     * 
     * @throws
     * 
     */
    int insertUserGroup(GroupBean groupBean);

    List<UserVO> selectByrecordOfAdBook(UserVO record);

    /**
     * 
     * 查询锁定用户
     * 
     * @param
     * @return
     *         Exception
     */
    List<UserVO> selectLockUser(UserVO userVo);

    /**
     * 
     * 用户解锁
     * 
     * @param usercode 用户编码
     * @return
     *         Exception
     */
    void updateUserLock(Map<String, Object> map);

    void updateBatchUserLock(Map<String, Object> map);

    Map<String, Object> selectSmsInfo(Map<String, Object> map);

    /**
     * 查询用户及机构信息
     * 
     * @param
     * @return
     *         Exception
     */
    List<Map<String, Object>> selectUserAndOrg(Map<String, Object> map);
}
