package com.jiezh.content.coupon.couponClient.service;

import com.jiezh.content.base.pub.author.AuthorUser;

/**
 * 赠券核销service接口
 * 
 * @author houjiaqiang
 * @since 2016年7月12日 下午5:10:56
 */
public interface DesCouClientService {
    public String processDestory(String couponCode, AuthorUser user);
}
