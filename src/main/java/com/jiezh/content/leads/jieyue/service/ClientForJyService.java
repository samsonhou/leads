package com.jiezh.content.leads.jieyue.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.dao.leads.clientimp.ClientImportVO;

/**
 * 捷越录入service接口
 * 
 * @author houjiaqiang
 * @since 2016年9月13日 上午10:10:23
 */
public interface ClientForJyService {

    public int saveClient(ClientImportVO vo, AuthorUser user);

    public PageInfo<ClientImportVO> getPageInfo(int curPage, ClientImportVO vo);

    public Map<String, Object> queryByTel(String tel);

}
