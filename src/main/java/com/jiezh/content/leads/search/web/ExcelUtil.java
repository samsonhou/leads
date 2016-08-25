package com.jiezh.content.leads.search.web;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
 * @author cj 报表导出工具
 *
 */
public class ExcelUtil {

    private static final Map<Integer, String[]> confMap = new LinkedHashMap<Integer, String[]>();

    public static Map<Integer, String[]> getConfmap() {
        if (confMap.isEmpty()) {
            setConfmap();
        }
        return confMap;
    }

    private static void setConfmap() {
        // 1=线索查询表头
        String[] title1 = new String[] {"是否已结算", "滴滴订单号", "客户姓名", "业务类别", "手机", "客服专员", "门店", "客户经理 ", "来源", "租赁产品", "小定金支付情况", "风控审核状态", "是否可退小定金",
            "客服首次邀约详情", "客服末次邀约时间", "门店一次邀约时间", "门店一次邀约详情", "门店二次邀约时间", "门店二次邀约详情", "门店三次邀约时间", "门店三次邀约详情", "门店末次邀约时间", "门店末次邀约详情", "预判等级", "预判C级原因",
            "预判D级原因", "到店时间", "是否到店", "大定金支付情况", "是否邀约", "进件时间", "是否进件", "签约时间", "是否签约", "提车时间", "是否提车", "花生合同", "是否可回收"};
        confMap.put(1, title1);

        String[] title2 = new String[] {"序号", "大区", "城市", "客户经理", "线索量", "首次邀约量", "首次跟进率", "战败客户", "战败率", "到店客户量", "首次邀约到店量", "通过风控   客户量", "风控通过率",
            "成交客户量", "成交率", "战败成交量", "进件量", "进件率", "签约量", "签约率"};
        confMap.put(2, title2);

        String[] title3 = new String[] {"分公司", "销售", "时间", "总量", "回复量", "未回复量", "追踪率"};
        confMap.put(3, title3);
    }

}
