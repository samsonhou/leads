/**
 * 
 */
package com.jiezh.content.base.user.service.imp;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiezh.content.base.user.service.LockUserService;
import com.jiezh.dao.base.user.BaseUserDao;
import com.jiezh.dao.base.user.UserVO;

/**
 * 锁定用户service实现类
 * 
 * @author houjiaqiang
 * @since 2016年2月22日 下午5:13:39
 */
@Service("base.user.lockUserService")
public class LockUserServiceImp implements LockUserService {

	@Autowired
	BaseUserDao baseUserDao;

	/**
	 * 
	 * 分页查询当前锁定用户
	 * 
	 * @param currentPage
	 *            当前页
	 * @return Exception
	 */
	@Override
	public PageInfo<UserVO> queryLockUser(int currentPage,UserVO userVo) {
		PageHelper.startPage(currentPage, 10);
		Page<UserVO> page = (Page<UserVO>) baseUserDao.selectLockUser(userVo);
		if (page == null) {
			page = new Page<UserVO>();
		}
		return new PageInfo<UserVO>(page);
	}

	/**
	 * 
	 * 用户解锁
	 * 
	 * @param map
	 *            传入参数 
	 * @return Exception
	 */
	@Override
	public void modifyUserLock(Map<String, Object> map) {
		baseUserDao.updateUserLock(map);
	}

	/**
	 * 
	 * 批量用户解锁
	 * 
	 * @param map
	 *            传入参数 
	 * @return Exception
	 */
	@Override
	public void ModifyBatchUserLock(Map<String, Object> map) {
		baseUserDao.updateBatchUserLock(map);
		
	}

}
