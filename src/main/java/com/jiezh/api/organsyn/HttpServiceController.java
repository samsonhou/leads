package com.jiezh.api.organsyn;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jiezh.api.organsyn.service.OrganSynService;
import com.jiezh.content.base.pub.web.WebAction;
import com.jiezh.dao.api.orgsyn.OrganSynVO;

import net.sf.json.JSONObject;

/**
 * 部门数据同步
 * 
 * @author houjiaqiang
 * @since 2016年8月23日 上午11:05:22
 */
@Controller
@RequestMapping("/exchange/api/")
public class HttpServiceController extends WebAction {
    Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    OrganSynService organSynService;

    @RequestMapping("organsyn")
    public void organSyn() {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String json = request.getParameter("json");
            JSONObject jsonObj = JSONObject.fromObject(json);

            String storeId = jsonObj.optString("store_id"); // 门店ID
            String cityId = jsonObj.optString("city_id"); // 目标城市ID
            String storeName = jsonObj.optString("name"); // 门店名称
            String orgCode = jsonObj.optString("org_code"); // 组织机构编码
            String storeAdd = jsonObj.optString("address"); // 门店详细地址
            String status = jsonObj.optString("status"); // 同步状态标识

            OrganSynVO orgsynVO = new OrganSynVO();
            if (StringUtils.isBlank(storeId)) {
                map.put("msg", "参数[store_id]的值为空！");
                returnStatus(map);
                return;
            } else {
                orgsynVO.setStoreId(Long.valueOf(storeId));
            }

            if (StringUtils.isBlank(cityId)) {
                map.put("msg", "参数[city_id]的值为空！");
                returnStatus(map);
                return;
            } else {
                orgsynVO.setCityId(Long.valueOf(cityId));
            }

            if (StringUtils.isBlank(storeName)) {
                map.put("msg", "参数[name]的值为空！");
                returnStatus(map);
                return;
            } else {
                orgsynVO.setStoreName(storeName);
            }

            if (StringUtils.isBlank(orgCode)) {
                map.put("msg", "参数[org_code]的值为空！");
                returnStatus(map);
                return;
            } else {
                orgsynVO.setOrgCode(orgCode);
            }

            if (StringUtils.isBlank(storeAdd)) {
                map.put("msg", "参数[address]的值为空！");
                returnStatus(map);
                return;
            } else {
                orgsynVO.setStoreAdd(storeAdd);
            }

            if (StringUtils.isBlank(status)) {
                map.put("msg", "参数[status]的值为空！");
                returnStatus(map);
                return;
            } else {
                orgsynVO.setStatus(status);
            }

            if (StringUtils.equals("3", status)) {
                // 根据storeId删除对应数据
                organSynService.deleteOrganSynVO(Long.valueOf(storeId));
            } else if (StringUtils.equals("2", status)) {
                // 根据storeId修改对应数据
                organSynService.updateOrganSynVO(orgsynVO);
            } else {
                // 新增且不存在时直接插入
                boolean isExist = organSynService.queryOrganCountByStoreId(Long.valueOf(storeId));
                if (isExist) {
                    organSynService.updateOrganSynVO(orgsynVO);
                } else {
                    organSynService.insertOrganSynVO(orgsynVO);
                }
            }
            map.put("msg", "ok");
            returnStatus(map);
        } catch (Exception e) {
            try {
                logger.error("同步数据时发生错误！", e);
                map.put("msg", "同步数据时发生错误！");
                returnStatus(map);
            } catch (Exception e1) {
                logger.error("同步数据时发生错误！", e1);
            }
        }
    }

    public void returnStatus(Map<String, Object> map) throws Exception {
        map.put("respTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        OutputStream out = response.getOutputStream();
        JSONObject json = JSONObject.fromObject(map);
        out.write(json.toString().getBytes());
    }
}
