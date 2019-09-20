package com.zhita.controller.payment.util;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.*;
import java.util.Map;

/**
 * @author chenyun
 * @create 2018-09-19 16:54
 */
public class HttpClient {

    private URL url;
    private int connectionTimeout;
    private int readTimeOut;
    private String result;

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public HttpClient(String url, int connectionTimeout, int readTimeOut) {
    	System.out.println(url);
        try {
            this.url = new URL(url);
            this.connectionTimeout = connectionTimeout;
            this.readTimeOut = readTimeOut;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public String send(Map<String, String> data, String encoding) throws Exception {
        try {
            HttpURLConnection httpURLConnection = createConnection(encoding);
            System.out.println(httpURLConnection);
            if (null == httpURLConnection) {
            	System.out.println("创建失败");
                throw new Exception("创建联接失败");
            }
            requestServer(httpURLConnection,getRequestParamString(data, encoding), encoding);
           System.out.println("SEND:"+httpURLConnection+":"+encoding);
            this.result = response(httpURLConnection, encoding);
            return result;
        } catch (Exception e) {
            throw e;
        }
    }

    private void requestServer(URLConnection connection, String message, String encoder) throws Exception {
        PrintStream out = null;
        try {
        	System.out.println(connection+":ASC:"+message);
            connection.connect();
            out = new PrintStream(connection.getOutputStream(), false, encoder);
            out.print(message);
            out.flush();
        } catch (Exception e) {
            throw e;
        } finally {
            if (null != out){
                try{out.close();}catch(Exception e){}
            }
        }
    }

    private String response(HttpURLConnection connection, String encoding)
            throws URISyntaxException, IOException, Exception {
        InputStream in = null;
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        try {
            if (200 == connection.getResponseCode()) {
                in = connection.getInputStream();
                sb.append(new String(read(in), encoding));
            } else {
                in = connection.getErrorStream();
                sb.append(new String(read(in), encoding));
            }
            String str = sb.toString();
            return str;
        } catch (Exception e) {
            throw e;
        } finally {
            if (null != br) {
                br.close();
            }
            if (null != in) {
                in.close();
            }
            if (null != connection)
                connection.disconnect();
        }
    }

    public static byte[] read(InputStream in) throws IOException {
        int byteLen=in.available();
        byte[] buf = new byte[byteLen+1];
        int length = 0;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        while ((length = in.read(buf, 0, buf.length)) > 0) {
            bout.write(buf, 0, length);
        }
        bout.flush();
        return bout.toByteArray();
    }

    private HttpURLConnection createConnection(String encoding)
            throws ProtocolException {
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection) this.url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        httpURLConnection.setConnectTimeout(this.connectionTimeout);
        httpURLConnection.setReadTimeout(this.readTimeOut);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setRequestProperty("Content-type",new StringBuilder().append("application/x-www-form-urlencoded;charset=").append(encoding).toString());
        httpURLConnection.setRequestMethod("POST");
        if ("https".equalsIgnoreCase(this.url.getProtocol())) {
            HttpsURLConnection husn = (HttpsURLConnection) httpURLConnection;
            husn.setSSLSocketFactory(new BaseHttpSSLSocketFactory());
            husn.setHostnameVerifier(new BaseHttpSSLSocketFactory.TrustAnyHostnameVerifier());
            return husn;
        }
        return httpURLConnection;
    }

    private String getRequestParamString(Map<String, String> requestParam,
                                         String coder) {
        if ((null == coder) || ("".equals(coder))) {
            coder = "UTF-8";
        }
        StringBuffer sf = new StringBuffer("");
        String reqstr = "";
        if ((null != requestParam) && (0 != requestParam.size())) {
            for (Map.Entry en : requestParam.entrySet()) {
                try {
                    sf.append(new StringBuilder()
                            .append((String) en.getKey())
                            .append("=")
                            .append((null == en.getValue())
                                    || ("".equals(en.getValue())) ? ""
                                    : URLEncoder.encode((String) en.getValue(),
                                    coder)).append("&").toString());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return "";
                }
            }
            reqstr = sf.substring(0, sf.length() - 1);
        }
        return reqstr;
    }
}
