package com.jiezh.api.goodluck;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jiezh.content.base.pub.web.WebAction;
import com.jiezh.dao.leads.client.ClientDao;
import com.jiezh.dao.leads.clientimp.ClientImportVO;
import com.jiezh.dao.leads.clientimp.ClientImportVODao;

import net.sf.json.JSONObject;

/**
 * 描述类的作用
 * 
 * @author houjiaqiang
 * @since 2016年8月23日 上午11:05:22
 */
@Controller
@RequestMapping("/exchange/api/")
public class HttpService extends WebAction {
    Logger logger = Logger.getLogger(this.getClass());
    
    @Autowired
    ClientDao clientDao;

    @Autowired
    ClientImportVODao clientImportVODao;

    @RequestMapping("goodluck")
    public void saveClient() {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String name = request.getParameter("name");
            String tel = request.getParameter("tel");
            String city = request.getParameter("city");
            String stationNo = request.getParameter("stationNo");
            String token = request.getParameter("token");
            String sign = getMd5("name=" + name + "&tel=" + tel + "&secret=jiezhong");
            logger.info("goodluck invoke token >>>>>>>>>>>>>>>>>" + token);
            if (token.equalsIgnoreCase(sign)) {
                ClientImportVO clientImportVO = new ClientImportVO();
                clientImportVO.setClientName(name);
                clientImportVO.setTel(tel);
                clientImportVO.setCity(city);
                clientImportVO.setCreatedTime(new Date());
                clientImportVO.setStationNo(stationNo);
                clientImportVO.setStatus("0");// 未分配
                clientImportVO.setFromType("1000002002");// 咕啦彩票
//                clientImportVO.setFromTypeBig("1");// 互联网

                int flag1 = clientDao.selectTel(tel);
                int flag2 = clientImportVODao.selectByTel(tel);
                if (flag1 + flag2 > 0) {
                    map.put("msg", "电话已经存在！！！");
                    logger.info("========================goodluck tel 电话已经存在==============================");
                } else {
                    clientImportVODao.insert(clientImportVO);
                    map.put("msg", "ok");
                }

            } else {
                map.put("msg", "请小心，数据已被篡改！！！");
                logger.info("========================Attention================咕啦推送数据被篡改！！！！！");
            }
            map.put("respTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            OutputStream out = response.getOutputStream();
            JSONObject json = JSONObject.fromObject(map);
            out.write(json.toString().getBytes());
        } catch (Exception e) {
            try {
                map.put("msg", "exception !!!");
                JSONObject json = JSONObject.fromObject(map);
                response.getWriter().write(json.toString());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            logger.error("===================goodluck invoke exception ====================");
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // String url = "http://124.65.242.70:9003/exchange/api/goodluck";
        String url = "http://localhost:8088/leads/exchange/api/goodluck";

        HttpPost httppost = new HttpPost(url);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        Map<String, String> map = new HashMap<>();
        map.put("name", "tom");
        map.put("tel", "13834942929");
        map.put("city", "北京");
        map.put("stationNo", "0123");
        map.put("token", getMd5("name=tom&tel=13834942929&secret=jiezhong"));
        Set<String> set = map.keySet();
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            String name = it.next();
            params.add(new BasicNameValuePair(name, map.get(name)));
        }

        httppost.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));

        HttpResponse response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();
        String msg = EntityUtils.toString(entity, StandardCharsets.UTF_8);
        System.out.println(msg);
    }

    public static String getMd5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());

            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) i += 256;
                if (i < 16) buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            // 32位加密
            return buf.toString().toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

    }
}
