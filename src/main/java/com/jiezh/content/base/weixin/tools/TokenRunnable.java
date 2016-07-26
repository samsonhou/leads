package com.jiezh.content.base.weixin.tools;

public class TokenRunnable implements Runnable{
	@Override
	public void run() {
		WeixinConfig.init(Parameters.initId);
	}
}
