package com.jiezh.dao.base.login;

import java.util.Map;

public interface LoginDao {
    int insert(LoginVO record);

    int updateBySessionId(String sessionId);
    
    int updateByUuid(Map<String,String> map);
    
    int updateLastRequest(String sessionId);
    
    int updateLoginIp(Map<String,String> map);
}