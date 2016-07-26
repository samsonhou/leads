package com.jiezh.content.leads.urge.service.imp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiezh.content.base.pub.Env;
import com.jiezh.content.leads.urge.service.LmurgeService;
import com.jiezh.dao.leads.client.urge.LmurgeVO;
import com.jiezh.dao.leads.client.urge.LmurgeVODao;

/** 
* @ClassName: LmurgeServiceImp 
* @Description: 催促信息的接口实现类 
* @author 陈继龙  
* @date 2016年1月25日 下午5:43:38 
* 
*/
@Service("leads.urge.service.LmurgeService")
public class LmurgeServiceImp implements LmurgeService {
    @Autowired
    LmurgeVODao lmurgeVODao;
    /*
    * <p>Title: insert</p> 
    * <p>Description:新增催促信息</p> 
    * @param record
    * @return int
    * @see com.jiezh.content.leads.urge.service.LmurgeService#insert(com.jiezh.dao.leads.client.urge.LmurgeVO) 
    */
    @Override
    public int insert(LmurgeVO record) {
	return lmurgeVODao.insert(record);
    }

    /*
    * <p>Title: update</p> 
    * <p>Description:修改催促信息  </p> 
    * @param record
    * @return 
    * @see com.jiezh.content.leads.urge.service.LmurgeService#update(com.jiezh.dao.leads.client.urge.LmurgeVO) 
    */
    @Override
    public int update(LmurgeVO record) {
	return lmurgeVODao.updateByPrimaryKey(record);
    }

    /*
    * <p>Title: getByLmurge</p> 
    * <p>Description:按照条件查询返回催促信息集合 </p> 
    * @param  record
    * @return List<LmurgeVO>
    */
    private List<LmurgeVO> getByLmurge(LmurgeVO record) {
	return lmurgeVODao.selectByLmurge(record);
    }
   /*
   * <p>Title: getClientLmurge</p> 
   * <p>Description: 分页查询 </p> 
   * @param currenPage
   * @param record
   * @return PageInfo<Map<String, Object>>(page)
   * @see com.jiezh.content.leads.urge.service.LmurgeService#getClientLmurge(int, com.jiezh.dao.leads.client.urge.LmurgeVO)
    */
    @Override
    public PageInfo<LmurgeVO> getClientLmurge(int currenPage, LmurgeVO record) {
	PageHelper.startPage(currenPage, Env.PAGE_SIZE);
	Page<LmurgeVO> page = (Page<LmurgeVO>)getByLmurge(record);
	if (page == null) {
	    page = new Page<LmurgeVO>();
	}	
	return new PageInfo<LmurgeVO>(page);
    }

    /*
    * <p>Title: selectByPrimaryKey</p> 
    * <p>Description: </p> 
    * @param id
    * @return 
    * @see com.jiezh.content.leads.urge.service.LmurgeService#selectByPrimaryKey(long) 
    */
    @Override
    public LmurgeVO selectByPrimaryKey(long id) {
	return lmurgeVODao.selectByPrimaryKey(id);
    }

	@Override
	public List<Map<String, Object>> selectByLmurgeList(Map<String,Object> paras) {
		return lmurgeVODao.selectByLmurgeList(paras);
	}

}
