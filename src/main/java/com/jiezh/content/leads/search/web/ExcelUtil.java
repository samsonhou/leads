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
        String[] title1 = new String[] {"联系人", "业务类别", "手机", "填写人", "销售人员分公司", "销售人员 ", "来源", "等级", "放弃原因", "电话具体原因", "填写时间", "进件时间", "成交时间", "租赁产品",
            "购车意向", "是否提交审核材料", "是否提车", "提车时间", "是否邀约", "是否到店", "风控审核状态","定金支付情况","合同号","最新跟进时间","最新跟进详情"};
        confMap.put(1, title1);

        String[] title2 = new String[] {"序号", "大区", "城市", "客户经理", "线索量", "首次邀约量", "首次跟进率", "战败客户", "战败率", "到店客户量", "首次邀约到店量", "通过风控   客户量", "风控通过率",
            "成交客户量", "成交率", "战败成交量", "进件量", "进件率", "签约量", "签约率"};
        confMap.put(2, title2);

        String[] title3 = new String[] {"分公司", "销售", "时间", "总量", "回复量", "未回复量", "追踪率"};
        confMap.put(3, title3);
    }

}
