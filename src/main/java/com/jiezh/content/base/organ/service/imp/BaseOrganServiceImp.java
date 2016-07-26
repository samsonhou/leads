package com.jiezh.content.base.organ.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiezh.content.base.organ.service.BaseOrganService;
import com.jiezh.dao.base.cache.CacheDao;
import com.jiezh.dao.base.organ.BaseOrganDao;
import com.jiezh.dao.base.organ.OrganVO;

@Service("base.organ.service.baseOrganService")
public class BaseOrganServiceImp implements BaseOrganService{
	@Autowired
	CacheDao cacheDao;
	@Autowired
	BaseOrganDao baseOrganDao;

	@Override
	public List<OrganVO> getOrganByAll() {
		//获取全部组织信息
		List<OrganVO> listOrgan=cacheDao.getModuleOrganAll();
		return listOrgan;
	}


	@Override
	public List<OrganVO> getOrganByUserOrganCode(String organCode) {
		//获取当前organCode下的组织信息
		List<OrganVO> listOrgan=cacheDao.getModuleOrganByUserOrganCode(organCode);
		return listOrgan;
	}


	@Override
	public PageInfo<OrganVO> search(int currenPage, OrganVO vo) {
		PageHelper.startPage(currenPage, 10);
		Page<OrganVO> page=(Page<OrganVO>)baseOrganDao.selectOrgan(vo);
		
		if(page==null){
			page=new Page<OrganVO>();
		}
		return new PageInfo<OrganVO>(page);
	}


	@Override
	public int addOrgan(OrganVO record) {
	return	baseOrganDao.insert(record);
	}


	@Override
	public int updateOrgan(OrganVO record) {
		return baseOrganDao.updateByPrimaryKeySelective(record);
	}


	@Override
	public OrganVO findOne(Integer id) {
		return baseOrganDao.selectByPrimaryKey(id); 
	}
}
