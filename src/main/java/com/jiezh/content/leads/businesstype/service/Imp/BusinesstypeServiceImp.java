package com.jiezh.content.leads.businesstype.service.Imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiezh.content.base.pub.Env;
import com.jiezh.content.leads.businesstype.service.BusinesstypeService;
import com.jiezh.dao.leads.client.LmcategoryDao;
import com.jiezh.dao.leads.client.LmcategoryVO;
@Service("leads.businesstype.service.ExportService")
public class BusinesstypeServiceImp implements BusinesstypeService {
	@Autowired
	LmcategoryDao  lmcategoryDao;

	@Override
	public PageInfo<LmcategoryVO> getSearchList(int currenPage, LmcategoryVO lmcategoryVO) {
		PageHelper.startPage(currenPage, Env.PAGE_SIZE);
		Page<LmcategoryVO> page = (Page<LmcategoryVO>)lmcategoryDao.selectByLmcategory(lmcategoryVO);
		if (page == null) {
			page = new Page<LmcategoryVO>();
		}
		//return new PageInfo<LmcategoryVO>(page);
		return new PageInfo<LmcategoryVO>(getLmcategoryList(page));
	}

	private Page<LmcategoryVO> getLmcategoryList(Page<LmcategoryVO> pages){
		
	    List<LmcategoryVO> lmcategorylist=pages.getResult();
	    for (LmcategoryVO vo : lmcategorylist) {
	    	if(vo.getChildrenCount()>0){
	    	LmcategoryVO lmcategoryVO =new  LmcategoryVO();
	    	lmcategoryVO.setPid(vo.getId());
	    	vo.setChildrenList(lmcategoryDao.selectByLmcategory(lmcategoryVO));
	    	}
	    }
	   
		
		return	pages;
	}
	
	@Override
	public int addLmcategory(LmcategoryVO lmcategoryVO) {
		return lmcategoryDao.insert(lmcategoryVO);
	}

	@Override
	public int updateLmcategory(LmcategoryVO lmcategoryVO) {
		return lmcategoryDao.updateByPrimaryKey(lmcategoryVO);
	}

	@Override
	public LmcategoryVO findOneLmcategory(long id) {
		return lmcategoryDao.selectByPrimaryKey(id);
	}
	
}
