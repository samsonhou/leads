package com.jiezh.content.leads.jieyue.service.imp;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiezh.content.base.pub.Env;
import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.content.leads.jieyue.service.ClientForJyService;
import com.jiezh.dao.leads.client.ClientDao;
import com.jiezh.dao.leads.clientimp.ClientImportVO;
import com.jiezh.dao.leads.clientimp.ClientImportVODao;

/**
 * 捷越录入service类
 * 
 * @author houjiaqiang
 * @since 2016年9月13日 上午10:12:54
 */
@Service
public class ClientForJyServiceImp implements ClientForJyService {

    @Autowired
    ClientImportVODao clientImportVODao;

    @Autowired
    ClientDao clientDao;

    @Override
    public int saveClient(ClientImportVO vo, AuthorUser user) {
        vo.setCreatedTime(new Date());
        vo.setCreatedUserId(user.getUserId());
        vo.setFromTypeBig("3");// 直销
        vo.setStatus("0");
        vo.setFromType(Long.valueOf(1030));// 捷越
        return clientImportVODao.insert(vo);
    }

    @Override
    public PageInfo<ClientImportVO> getPageInfo(int curPage, ClientImportVO vo) {
        PageHelper.startPage(curPage, Env.PAGE_SIZE);
        vo.setFromTypeBig("3");
        vo.setFromType(Long.valueOf(1030));
        Page<ClientImportVO> page = (Page<ClientImportVO>) clientImportVODao.selectByVo(vo);
        if (page == null) {
            page = new Page<>();
        }
        return new PageInfo<ClientImportVO>(page);
    }

    @Override
    public Map<String, Object> queryByTel(String tel) {
        Map<String, Object> map = new HashMap<>();
        int flag1 = clientImportVODao.selectByTel(tel);
        int flag2 = clientDao.selectTel(tel);
        if (flag1 + flag2 > 0) {
            map.put("info", "电话号码已经存在！！");
            map.put("status", "n");
        } else {
            map.put("status", "y");
        }
        return map;
    }

}
