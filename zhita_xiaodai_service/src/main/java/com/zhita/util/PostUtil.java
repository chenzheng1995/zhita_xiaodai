package com.zhita.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

public class PostUtil {
	   /**
     * 定义所需的变量
     */
    private static HttpClient httpClient = new DefaultHttpClient();
    private static HttpPost httppost;
    private static HttpResponse response;
    private HttpEntity entity;
    private String postResult = null;

    public String post(String url,String param){
    	String strResult ="";
        String loginURL = "我们要测试的接口地址";
        // 创建一个httppost请求
        httppost = new HttpPost(url);
        
        JSONObject jsonParam = JSONObject.parseObject(param);
//        JSONObject jsonParam = new JSONObject();
//        jsonParam.put("mobile", "15627898765");
//        jsonParam.put("password","e10adc3949ba59abbe56e057f20f883e");

        try {

            StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");// 解决中文乱码问题
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httppost.setEntity(entity);
            response = httpClient.execute(httppost); 
            strResult = EntityUtils.toString(response.getEntity());
            System.out.println("查看返回的结果：" + strResult);


        } catch (Exception e) {
            e.printStackTrace();
        }

        httppost.releaseConnection();
        return strResult;
    }
}
