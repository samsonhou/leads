package com.jiezh.content.base.pub;

/**
 * 系统变量
 * 
 * @author liangds
 *
 */
public class Env {
    public static final String WEB_ROOT = "/leads";
    public static final int PAGE_SIZE = 10;// 默认分页数

    public static final String ROLE_MANAGE = "1"; // 管理员
    public static final String ROLE_SALE = "2"; // 销售
    public static final String ROLE_CLIENT = "3"; // 客服

    public static final String ROW_START_KEY = "srownum"; // Excel参数绑定
    public static final String ROW_END_KEY = "erownum";
    public static final String COL_START_KEY = "scolnum";
    public static final String COL_END_KEY = "ecolnum";

    public static String getWebRoot() {
        return WEB_ROOT;
    }
}
