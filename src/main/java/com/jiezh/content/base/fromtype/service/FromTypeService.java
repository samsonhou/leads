package com.jiezh.content.base.fromtype.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.dao.base.fromtype.BaseSourceVO;

/**
 * 来源service接口
 * 
 * @author houjiaqiang
 * @since 2016年9月19日 上午11:09:26
 */
public interface FromTypeService {
    public Map<String, Object> getFromtype(Map<String, Object> map);

    public int addFromtype(BaseSourceVO vo, AuthorUser user);

    public PageInfo<BaseSourceVO> getPageInfo(BaseSourceVO vo, int curPage);
}
