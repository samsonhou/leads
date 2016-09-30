package com.jiezh.content.base.fromtype.service.imp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiezh.content.base.fromtype.service.FromTypeService;
import com.jiezh.content.base.pub.Env;
import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.dao.base.fromtype.BaseSourceVO;
import com.jiezh.dao.base.fromtype.BaseSourceVODao;

/**
 * 来源service实现类
 * 
 * @author houjiaqiang
 * @since 2016年9月19日 下午1:54:46
 */
@Service
public class FromTypeServiceImp implements FromTypeService {

    @Autowired
    BaseSourceVODao baseSourceVODao;

    @Override
    public Map<String, Object> getFromtype(Map<String, Object> map) {
        Map<String, Object> resutl = new HashMap<>();
        List<Map<String, Object>> list = baseSourceVODao.selectVoByMap(map);
        StringBuffer sb = new StringBuffer();
        if (list.size() > 0) {
            String level = list.get(0).get("CODELEVEL").toString();
            sb.append("<select level='" + level + "'  class='form-control' datatype='*' onchange='fromtype.change(this)'>");
            sb.append("<option value='" + map.get("pid") + "'>请选择</option>");
            for (Map<String, Object> obj : list) {
                sb.append("<option value='" + obj.get("CODE") + "'>");
                sb.append(obj.get("NAME").toString() + "</option>");
            }
            sb.append("</select>");
        }
        resutl.put("select", sb.toString());
        return resutl;
    }

    @Override
    public int addFromtype(BaseSourceVO vo, AuthorUser user) {
        vo.setPid(vo.getCode());
        vo.setCode(null);
        vo.setCreatedTime(new Date());
        vo.setCreatedUserId(user.getUserId());
        baseSourceVODao.insert(vo);
        return 0;
    }

    @Override
    public PageInfo<BaseSourceVO> getPageInfo(BaseSourceVO vo, int curPage) {
        PageHelper.startPage(curPage, Env.PAGE_SIZE);
        Page<BaseSourceVO> page = (Page<BaseSourceVO>) baseSourceVODao.selectListByVo(vo);
        if (page == null) {
            page = new Page<>();
        }
        return new PageInfo<BaseSourceVO>(page);
    }

}
