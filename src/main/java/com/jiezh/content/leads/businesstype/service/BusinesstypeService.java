package com.jiezh.content.leads.businesstype.service;

import com.github.pagehelper.PageInfo;
import com.jiezh.dao.leads.client.LmcategoryVO;

public interface BusinesstypeService {
	 int addLmcategory(LmcategoryVO lmcategoryVO);
	 int updateLmcategory(LmcategoryVO lmcategoryVO);
	 LmcategoryVO findOneLmcategory(long id );
	 PageInfo<LmcategoryVO> getSearchList(int currenPage, LmcategoryVO lmcategoryVO);
}
