package com.jiezh.api.moor.service.imp;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jiezh.api.moor.service.UserMoorService;
import com.jiezh.dao.api.moor.UserMoorVODao;
import com.jiezh.dao.base.user.UserVO;

/**
 * 七陌用户匹配
 * 
 * @author houjiaqiang
 * @since 2016年9月26日 上午10:25:11
 */
@Service
public class UserMoorServiceImp implements UserMoorService {
    
    @Autowired
    UserMoorVODao userMoorVODao;

    @Override
    public UserVO getUser(Map<String, Object> param) {
        
        return userMoorVODao.selectUser(param);
    }

}
