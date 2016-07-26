package com.jiezh.content.leads.urge.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.jiezh.dao.leads.client.urge.LmurgeVO;

/** 
* @ClassName: LmurgeService 
* @Description: 线索催促
* @author 陈继龙  
* @date 2016年1月25日 下午5:40:41 
*  
*/
public interface LmurgeService {
    
    int insert (LmurgeVO record);
    
    int update (LmurgeVO record);
    
    LmurgeVO selectByPrimaryKey(long id);
    
    PageInfo<LmurgeVO> getClientLmurge(int currenPage, LmurgeVO record); 
 
    //add by cj
    List<Map<String, Object>> selectByLmurgeList(Map<String,Object> paras); 
    
}
