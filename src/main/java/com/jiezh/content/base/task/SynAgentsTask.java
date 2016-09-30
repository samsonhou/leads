package com.jiezh.content.base.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jiezh.content.base.pub.util.HttpClientUtil;
import com.jiezh.dao.api.moor.UserMoorVO;
import com.jiezh.dao.api.moor.UserMoorVODao;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 定时同步客服坐席
 * 
 * @className SynAgentsTask
 * @author JXY
 */
@Component
public class SynAgentsTask {

    @Autowired
    UserMoorVODao userMoorDao;

    @Scheduled(cron = "0 0 3 * * ?")
    public void execute() throws Exception {
        Map<String, String> logMap = new HashMap<String, String>();
        logMap.put("name", "定时同步坐席");
        try {
            StringBuilder errMsg = new StringBuilder();
            List<UserMoorVO> userMoorList = new ArrayList<UserMoorVO>();
            // 查询七陌系统中所有坐席
            Map<String, Object> paraMap = new HashMap<String, Object>();
            paraMap.put("action", "getCCAgentsByAcc");
            paraMap.put("account", "N00000009345");
            paraMap.put("md5", "81f0e1f0-32df-11e3-a2e6-1d21429e5f46");
            String agents = HttpClientUtil.httpPost("http://115.29.14.183:3000/openService", paraMap);
            JSONObject json = JSONObject.fromObject(agents);
            boolean succeed = json.getBoolean("success");
            if (!succeed) {
                logMap.put("flag", "0");
                logMap.put("msg", "同步七陌系统坐席失败，错误信息：" + json.getString("message"));
                userMoorDao.recordLog2DB(logMap);
                return;
            }
            JSONArray jsonArray = JSONArray.fromObject(json.get("data"));
            // 转换为线索系统坐席用户
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObj = (JSONObject) jsonArray.get(i);
                String agentId = jsonObj.optString("exten"); // 坐席号
                String userName = jsonObj.optString("displayName"); // 坐席姓名
                Long userId = userMoorDao.selectUserIdByUserName(userName);
                if (userId == null) {
                    errMsg.append(userName + "[" + agentId + "];");
                } else {
                    UserMoorVO userMoor = new UserMoorVO();
                    userMoor.setId(Long.valueOf(i));
                    userMoor.setUserId(userId);
                    userMoor.setAgentId(agentId);
                    userMoorList.add(userMoor);
                }
            }
            if (errMsg.length() > 0) {
                logMap.put("flag", "0");
                logMap.put("msg", "坐席用户未找到！未成功的用户：" + errMsg.toString());
                userMoorDao.recordLog2DB(logMap);
            }
            // 同步七陌系统最新坐席用户到线索系统
            if (userMoorList.size() > 0) {
                userMoorDao.deleteAllUserMoor();
                userMoorDao.insertUserMoorList(userMoorList);
                logMap.put("flag", "1");
                logMap.put("msg", "坐席用户同步成功" + userMoorList.size() + "条。");
                userMoorDao.recordLog2DB(logMap);
            } else {
                logMap.put("flag", "0");
                logMap.put("msg", "坐席为空，没有同步！");
                userMoorDao.recordLog2DB(logMap);
            }
        } catch (Exception e) {
            logMap.put("flag", "0");
            logMap.put("msg", "坐席同步异常！" + e.getMessage());
            userMoorDao.recordLog2DB(logMap);
            throw e;
        }

    }

}
