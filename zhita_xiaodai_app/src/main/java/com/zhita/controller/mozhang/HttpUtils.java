package com.zhita.controller.mozhang;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author wanghongzhi
 * @create 2017-10-17 下午7:44
 **/
public class HttpUtils {
    public final static String DEFAULT_CHARSET = "UTF-8";

    public static String get(String url, Map<String,String> data){
        String result = null;
        try{
            url = getWholeGetURL(url,data);
            InputStream inputStream = getInputStream(url);
            System.out.println("requestUrl:"+url);
            if(inputStream != null){
                byte[] inData = inputStream2ByteArray(inputStream);
                return  new String(inData);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static String getWholeGetURL(String baseUrl,Map<String,String> data){
        if(data!=null && !data.isEmpty()){
            String params = mapToKeyPairString(data);
            if(baseUrl.indexOf("?")>0){
                baseUrl = baseUrl + "&"+ params;
            }else{
                baseUrl = baseUrl + "?"+ params;
            }
        }
        return baseUrl;
    }

    public static String getEncodeParam(String value){
        try {
            return URLEncoder.encode(value,DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return value;
        }
    }

    public static byte[] inputStream2ByteArray(InputStream inputStream){
        try{
            if(inputStream!=null){
                ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
                byte[] cache = new byte[1024];
                int length = 0;
                while((length=inputStream.read(cache))>0){
                    swapStream.write(cache,0,length);
                }
                return swapStream.toByteArray();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static String mapToKeyPairString(Map<String,String> data){
        if(data!=null && !data.isEmpty()){
            StringBuffer dataStr = new StringBuffer();
            for(String key:data.keySet()){
                String value = data.get(key);
                dataStr.append(key).append("=").append(getEncodeParam(value)).append("&");
            }
            return dataStr.substring(0,dataStr.length()-1);
        }
        return "";
    }

    /**
     * 从服务器获得一个输入流(本例是指从服务器获得一个image输入流)
     */
    public static InputStream getInputStream(String baseUrl) {
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;

        try {
            URL url = new URL(baseUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            // 设置网络连接超时时间
            httpURLConnection.setConnectTimeout(3000);
            // 设置应用程序要从网络连接读取数据
            httpURLConnection.setDoInput(true);

            httpURLConnection.setRequestMethod("GET");
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == 200) {
                // 从服务器返回一个输入流
                inputStream = httpURLConnection.getInputStream();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return inputStream;

    }
}
