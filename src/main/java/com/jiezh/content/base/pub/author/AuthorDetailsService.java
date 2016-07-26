package com.jiezh.content.base.pub.author;

import java.util.List;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.jiezh.content.base.pub.Log;
import com.jiezh.content.base.pub.util.DaoUtil;
import com.jiezh.dao.base.cache.CacheDao;
/**
 * 用户受权
 *
 */
public class AuthorDetailsService implements UserDetailsService{
	Log log=new Log(AuthorDetailsService.class);
	
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		CacheDao cacheDao=DaoUtil.instance().cacheDao();
		AuthorUser user=cacheDao.getUserByUserName(userName);
		if(user==null){
			throw new UsernameNotFoundException("用户名或密码错误");
		}else if("0".equals(user.getEnable())){
			throw new DisabledException("用户已失效");
		}
		//用户角色
		if(user!=null && user.getUserId()>0){
			List<String> userRole=cacheDao.getUserRole(user.getUserId());
			if(userRole!=null && userRole.size()>0){
				user.setUserRole(userRole);
			}
		}
		return user;
	}
}
