/**
 * 
 */
package com.jiezh.api.moor.service;

import java.util.Map;

import com.jiezh.dao.base.user.UserVO;

/**
 * 描述类的作用
 * @author houjiaqiang
 * @since 2016年9月26日 上午9:45:47
 */
public interface UserMoorService {
    public UserVO getUser(Map<String, Object> param);
}
