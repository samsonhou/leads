
package com.jiezh.content.base.organ.service;

import java.util.List;
import com.github.pagehelper.PageInfo;
import com.jiezh.dao.base.organ.OrganVO;


public interface BaseOrganService {
	public List<OrganVO> getOrganByAll();

	public List<OrganVO> getOrganByUserOrganCode(String organCode);

	public PageInfo<OrganVO> search(int currenPage, OrganVO vo);

	public int addOrgan(OrganVO organForm);

	public int updateOrgan(OrganVO organForm);
	public OrganVO findOne(Integer id);
}
