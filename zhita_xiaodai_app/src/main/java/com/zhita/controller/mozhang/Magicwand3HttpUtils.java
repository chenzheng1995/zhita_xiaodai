package com.zhita.controller.mozhang;

import java.io.*;
import java.net.*;
import java.util.Map;


public class Magicwand3HttpUtils {
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
        if (null == value || value.length() == 0){
            return value;
        }
        try {
            return URLEncoder.encode(value,DEFAULT_CHARSET);
        } catch (Exception e) {
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
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(10000);
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

    public static String post(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result +=  line;
            }
        } catch (Exception e) {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
}
