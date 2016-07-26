/**
 * 
 */
package com.jiezh.content.base.user.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.jiezh.dao.base.user.UserVO;

/**
 * 用户锁定service类接口
 * 
 * @author houjiaqiang
 * @since 2016年2月22日 下午5:09:53
 */
public interface LockUserService {
	/**
	 * 
	 * 分页查询当前锁定用户
	 * 
	 * @param currentPage
	 *            当前页
	 * @return Exception
	 */
	public PageInfo<UserVO> queryLockUser(int currentPage, UserVO userVo);

	/**
	 * 
	 * 用户解锁
	 * 
	 * @param map
	 *            传入参数
	 * @return Exception
	 */
	public void modifyUserLock(Map<String, Object> map);

	/**
	 * 
	 * 用户解锁
	 * 
	 * @param map
	 *            传入参数
	 * @return Exception
	 */
	public void ModifyBatchUserLock(Map<String, Object> map);

}
