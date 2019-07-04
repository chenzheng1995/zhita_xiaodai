package com.zhita.controller.operator.http;


import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;

import com.zhita.controller.operator.exception.HttpPostException;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

/**
 * HTTP协议远程调用使用
 *
 * @author Lawrence
 * @time 2018-06-23
 */
public final class HttpPost {


    /**
     * 连接超时时间
     * 注：一次http请求，必定会有三个阶段，一：建立连接；二：数据传送；三，断开连接
     */
    static final int DEFAULT_CONNECT_TIMEOUT = 1000;

    /**
     * 数据传输超时时间
     * 注：一次HTTP请求，必定会有三个阶段，一：建立连接；二：数据传输；三，断开连接
     */
    static final int DEFAULT_SOCKET_TIMEOUT = 5000;

    /**
     * 默认字符集 UTF_8
     */
    static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;



    public static String form(final String url, Map<String, String> formArgs) {
        return form(url, formArgs, DEFAULT_CHARSET);
    }


    /**
     * POST 请求方式，内容为json
     *
     * @param url      请求地址
     * @param formArgs 请求参数体，键值对
     * @param charset  字符集
     * @return
     */
    public static String form(final String url, Map<String, String> formArgs, final Charset charset) {
        return form(url, formArgs, charset, null);
    }

    /**
     * POST 请求方式，内容为json
     *
     * @param url      请求地址
     * @param formArgs 请求参数体，键值对
     * @param headers  自定义头信息
     * @return
     */
    public static String form(final String url, Map<String, String> formArgs, final Header... headers) {
        return form(url, formArgs, DEFAULT_CHARSET, headers);
    }

    /**
     * POST 请求方式，内容为json
     *
     * @param url      请求地址
     * @param formArgs 请求参数体，键值对
     * @param headers  自定义头信息
     * @return
     */
    public static String form(final String url, Map<String, String> formArgs, final Charset charset,
                              final Header... headers) {
        Form form = Form.form();
        if (Objects.nonNull(formArgs)) {
            formArgs.forEach((k, v) -> form.add(k, v));
        }
        return form(url, form, charset, headers);
    }




    public static String form(final String url, Form form, final Header... headers) {
        return form(url, form, DEFAULT_CHARSET, headers);
    }

    /**
     * POST 请求方式,采用from表单提交方式
     *
     * @param url     请求地址
     * @param form    请求参数对象，
     * @param charset 请求字符集
     * @param headers 自定义头信息
     * @return
     */
    public static String form(final String url, Form form, final Charset charset,
                              final Header... headers) {
        try {
            return Request.Post(url)
                    .setHeaders(headers)
                    .bodyForm(form.build(), charset)
                    .version(HttpVersion.HTTP_1_1)
                    .connectTimeout(DEFAULT_CONNECT_TIMEOUT)
                    .socketTimeout(DEFAULT_SOCKET_TIMEOUT)
                    .execute()
                    .handleResponse(new StringResponseHandler());
        } catch (Exception ioe) {
            System.err.println(String.format("请求失败; message[%s],url[%s],form[%s]", ioe.getMessage(), url, form.build()));
            throw new HttpPostException(ioe.getMessage(), ioe);
        }
    }



    /**
     * oauth认证使用
     * @param url
     * @param headers
     * @return
     */
    public static String oauth(final String url,final Header... headers) throws IOException {
            return Request.Post(url)
                    .setHeaders(headers)
                    .version(HttpVersion.HTTP_1_1)
                    .connectTimeout(DEFAULT_CONNECT_TIMEOUT)
                    .socketTimeout(DEFAULT_SOCKET_TIMEOUT)
                    .execute()
                    .handleResponse(new StringResponseHandler());

    }

    static class StringResponseHandler implements ResponseHandler<String> {
        @Override
        public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
            StatusLine statusLine = response.getStatusLine();
            HttpEntity entity = response.getEntity();
            ContentType contentType = ContentType.getOrDefault(entity);
            if (statusLine.getStatusCode() >= 300) {
                String message = (!"".equals(statusLine.getReasonPhrase()))
                        ?statusLine.getReasonPhrase(): EntityUtils.toString(entity);
                throw new HttpResponseException(
                        statusLine.getStatusCode(),message);
            }
            return EntityUtils.toString(entity, contentType.getCharset());
        }
    }



}
