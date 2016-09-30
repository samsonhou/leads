package com.jiezh.api.moor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jiezh.api.moor.service.UserMoorService;
import com.jiezh.dao.base.user.UserVO;

/**
 * 七陌接口服务
 * 
 * @author houjiaqiang
 * @since 2016年9月26日 上午8:32:12
 */
@Controller
@RequestMapping("/exchange/api/moor")
public class HttpMoorController {
    private static Logger logger = Logger.getLogger(HttpMoorController.class);
    @Autowired
    UserMoorService userMoorService;

    @RequestMapping("login")
    public void moor(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String agentId = req.getParameter("Agent")==null?req.getParameter("loginExten"):req.getParameter("Agent");
        Map<String, Object> param = new HashMap<>();
        param.put("agentId", agentId);
        UserVO user = userMoorService.getUser(param);
        if (user != null) {
            param.put("j_username", user.getUserCode());
            param.put("j_password", user.getPassword()+"@7moor");
            logger.info("7moor >>>>>>>>>>>>>>>>>login");

        }
        doPostReq(resp, req.getContextPath() + "/j_spring_security_check", param);

    }

    public void doPostReq(HttpServletResponse response, String postUrl, Map<String, Object> paramMap) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.println("<form name='postSubmit' method='post' action='" + postUrl + "' >");
        for (String key : paramMap.keySet()) {
            out.println("<input type='hidden' name='" + key + "' value='" + paramMap.get(key) + "'>");
        }
        out.println("</form>");
        out.println("<script>");
        out.println("  document.postSubmit.submit()");
        out.println("</script>");
    }
}
