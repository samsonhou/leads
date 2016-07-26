package com.jiezh.content.base.organcompany.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiezh.content.base.organcompany.service.BaseOrganCompanyService;
import com.jiezh.content.base.pub.util.Node;
import com.jiezh.content.base.pub.util.Tools;
import com.jiezh.dao.base.cache.CacheDao;
import com.jiezh.dao.base.organcompany.BaseOrganCompanyDao;
import com.jiezh.dao.base.organcompany.OrganCompanyVO;

@Service("base.organcompany.service.baseOrganCompanyService")
public class BaseOrganCompanyServiceImp implements BaseOrganCompanyService {
    @Autowired
    CacheDao cacheDao;
    @Autowired
    BaseOrganCompanyDao baseOrganCompany;

    @Override
    public PageInfo<OrganCompanyVO> search(int currenPage, OrganCompanyVO vo) {
        PageHelper.startPage(currenPage, 10);
        Page<OrganCompanyVO> page = (Page<OrganCompanyVO>) baseOrganCompany.selectOrganCompany(vo);
        if (page == null) {
            page = new Page<OrganCompanyVO>();
        }
        return new PageInfo<OrganCompanyVO>(page);
    }

    @Override
    public int addOrganCompany(OrganCompanyVO record) {
        //默认添加机构为3级机构
        record.setOrganLevel("3");
        return baseOrganCompany.insert(record);
    }

    @Override
    public int updateOrganCompany(OrganCompanyVO record) {
        OrganCompanyVO updateOrganCompany = findOne(record.getOrganId(), record.getOrganCode());
        updateOrganCompany.setName(record.getName());
        updateOrganCompany.setAbbrName(record.getAbbrName());
        updateOrganCompany.setAddress(record.getAddress());
        updateOrganCompany.setStatus(record.getStatus());
        updateOrganCompany.setParentId(record.getParentId());
        return baseOrganCompany.updateByPrimaryKey(updateOrganCompany);
    }

    @Override
    public OrganCompanyVO findOne(String organId, String organCode) {
        Map<String, Object> findmap = new HashMap<String, Object>();
        findmap.put("organId", organId);
        findmap.put("organCode", organCode);
        return baseOrganCompany.selectByPrimaryKey(findmap);
    }

    public String findOrgans(Map<String, Object> params) {
        List<Map<String, Object>> list = cacheDao.getOrgans(params);
        List<Node> nodes = new ArrayList<>();
        for (Map<String, Object> obj : list) {
            Node node = new Node();
            node.setId(obj.get("ID").toString());
            node.setName(obj.get("NAME").toString());
            node.setpId(obj.get("PID").toString());
            node.setOpen(false);
            node.setChecked(false);
            node.setNocheck(false);
            node.setIsParent(false);
            if (obj.get("ISPARENT").equals("true")) {
                node.setIsParent(true);
            }

            nodes.add(node);
        }

        return Tools.listToJson(nodes);
    }
}
