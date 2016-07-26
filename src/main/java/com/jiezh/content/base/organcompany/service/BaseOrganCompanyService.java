package com.jiezh.content.base.organcompany.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.jiezh.dao.base.organcompany.OrganCompanyVO;

public interface BaseOrganCompanyService {

    PageInfo<OrganCompanyVO> search(int currenPage, OrganCompanyVO vo);

    int addOrganCompany(OrganCompanyVO organCompanyForm);

    int updateOrganCompany(OrganCompanyVO organCompanyForm);

    OrganCompanyVO findOne(String organId, String organCode);

    public String findOrgans(Map<String, Object> params);
}
