/**
 * 
 */
package com.jiezh.content.leads.track.service;

import com.github.pagehelper.PageInfo;
import com.jiezh.dao.leads.client.ClientVO;

/**
 * 时效追踪service类
 * @author houjiaqiang
 * @since 2016年3月14日 上午11:05:06
 */
public interface AgingTrackService {
	public PageInfo<ClientVO> queryClientByType(int currentPage,ClientVO clientVo);
}
