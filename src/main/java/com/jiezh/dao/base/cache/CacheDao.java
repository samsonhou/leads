package com.jiezh.dao.base.cache;

import java.util.List;
import java.util.Map;

import com.jiezh.content.base.login.model.GroupBean;
import com.jiezh.content.base.login.model.ModelBean;
import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.dao.base.organ.OrganVO;
import com.jiezh.dao.base.user.UserVO;

/**
 * 利用mybatis缓存
 * 
 * @author liangds
 *
 */
public interface CacheDao {
    public UserVO getUserByKey(long userId);

    public AuthorUser getUserByUserName(String userName);

    public List<GroupBean> getModuleGroup(long userId);

    public List<ModelBean> getModuleUrl(long groupId);

    public List<GroupBean> getModuleGroupAll();

    public List<OrganVO> getModuleOrganAll();

    public List<OrganVO> getModuleOrganByUserOrganCode(String organCode);

    // 标准的码表值
    public List<Map<String, String>> getOptions(Map<String, String> map);

    // 自定义的 通过sql去查询
    public String getCustomSql(Map<String, String> map);

    public List<Map<String, Object>> getCustomOptions(Map<String, String> map);

    // 获取机构信息
    public List<Map<String, String>> getOrganInfo(Map<String, String> map);

    public Map<String, String> getOrganByOrganId(Map<String, String> map);

    public List<Map<String, String>> getOrganLevel(Map<String, String> map);

    // 获取用户角色
    public List<String> getUserRole(long userId);

    // 获取机构信息
    public List<Map<String, Object>> getOrgans(Map<String, Object> map);

    // 获取来源信息
    public List<Map<String, Object>> getFromtype(Map<String, Object> map);

    // 获取来源父级
    public List<Map<String, Object>> getFromtypePid(Map<String, Object> map);
}
