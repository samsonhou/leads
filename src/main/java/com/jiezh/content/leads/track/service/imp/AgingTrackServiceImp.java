/**
 * 
 */
package com.jiezh.content.leads.track.service.imp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiezh.content.base.pub.Env;
import com.jiezh.content.leads.track.service.AgingTrackService;
import com.jiezh.dao.leads.client.ClientDao;
import com.jiezh.dao.leads.client.ClientVO;

/**
 * 时效追踪service实现类
 * @author houjiaqiang
 * @since 2016年3月14日 上午11:12:43
 */
@Service("leads.track.AgingTrackService")
public class AgingTrackServiceImp implements AgingTrackService {
	
	@Resource
	ClientDao clientDao;

	/**
	 * 根据时效追踪类型查询线索
	 * @param currentPage 当前页，clientVo 传入对象参数
	 * @author houjiaqiang
	 */
	@Override
	public PageInfo<ClientVO> queryClientByType(int currentPage, ClientVO clientVo) {
		PageHelper.startPage(currentPage, Env.PAGE_SIZE);
		Page<ClientVO> page = (Page<ClientVO>) clientDao.selectClentByType(clientVo);
		if (page == null) {
			page = new Page<ClientVO>();
		}
		return new PageInfo<ClientVO>(page);
	}

}
