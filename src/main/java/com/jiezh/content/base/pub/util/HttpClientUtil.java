package com.jiezh.content.base.pub.util;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.druid.support.logging.LogFactory;

/**
 * http 调用接口工具类
 * 
 * @author houjiaqiang
 * @since 2016年9月26日 上午11:28:41
 */
public class HttpClientUtil {
    /**
     * 
     * post请求
     * 
     * @param url 请求地址
     *            map 请求参数
     * @return
     *         Exception
     */
    public static String httpPost(String url, Map<String, Object> map) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        Set<String> set = map.keySet();
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            String name = it.next();
            params.add(new BasicNameValuePair(name, map.get(name).toString()));
        }

        httppost.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));

        HttpResponse response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();

        return EntityUtils.toString(entity, StandardCharsets.UTF_8);
    }

    /**
     * 
     * 将json参数直接放到输出流
     * 
     * @param
     * @return
     *         Exception
     */
    public static String httpPostByJson(String url, String jsonStr) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(new InputStreamEntity(new ByteArrayInputStream(jsonStr.getBytes())));
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();
        return EntityUtils.toString(entity, StandardCharsets.UTF_8);
    }

    public static String httpGet(String targetURI, Map<String, String> paraMap) throws Exception {
        Set<Entry<String, String>> entrySet = paraMap.entrySet();
        StringBuilder paramStr = new StringBuilder("?");
        for (Entry<String, String> entry : entrySet) {
            if (paramStr.indexOf("=") > 0) {
                paramStr.append("&");
            }
            paramStr.append(entry.getKey() + "=" + entry.getValue());
        }
        targetURI += paramStr.toString();
        return httpGet(targetURI);
    }

    public static String httpGet(String targetURI) throws Exception {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet httpget = new HttpGet(targetURI);
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity, StandardCharsets.UTF_8);
        } catch (Exception e) {
            LogFactory.getLog(HttpClientUtil.class).error("使用GET请求调用远程系统接口异常！", e);
            throw new Exception("调用远程系统接口异常！");
        }
    }

}
