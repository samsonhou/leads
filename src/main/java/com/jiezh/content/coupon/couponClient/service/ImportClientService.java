package com.jiezh.content.coupon.couponClient.service;

import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.dao.coupon.client.CouponClientVO;

/**
 * 客户导入Service类
 * 
 * @author houjiaqiang
 * @since 2016年7月8日 上午10:46:41
 */
public interface ImportClientService {
    public String processImportClient(MultipartFile file, AuthorUser user) throws Exception;

    public PageInfo<CouponClientVO> getcouponClient(CouponClientVO record, int curPage);

    public String processMsg(String ids, AuthorUser user);
}
