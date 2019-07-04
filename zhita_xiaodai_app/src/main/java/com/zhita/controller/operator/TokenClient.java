package com.zhita.controller.operator;

import com.alibaba.fastjson.JSON;
import com.zhita.controller.operator.exception.Oauth2TokenException;
import com.zhita.controller.operator.http.Environment;
import com.zhita.controller.operator.http.HttpPost;
import com.zhita.controller.operator.http.HttpResponseBody;

import org.apache.http.message.BasicHeader;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Base64;


/**
 * Token 客户端（demo使用）
 * @author Lawrence
 */
public class TokenClient{

    private static final String  AUTHORIZATION = "Authorization";
    private static final String  BEARER  = "Bearer ";
    private static final String  BASIC  = "Basic ";
    private static final String  COLON  = ":";
    private static final String  TOKEN_URL = "/oauth/token?grant_type=client_credentials";

    // 应用ID
    private String appId;
    // 应用密钥
    private String appSecret;
    // 环境 true 正式   false  沙箱/测试
    private boolean online;

    /**
     * 获取 BasicHeader 类型 token
     * @return BasicHeader
     */
    public BasicHeader tokenBasicHeader(){
        return new BasicHeader(AUTHORIZATION,BEARER + token());
    }

    /**
     * 获取带 token值（Bearer ）
     *
     *
     * @return 例如 Bearer ef62cb38-c8a8-4f8f-b41e-a0100c02b2b4
     */
    public String tokenBearer(){
        return BEARER + token();
    }
    /**
     * 获取token值
     * @return 例如 ef62cb38-c8a8-4f8f-b41e-a0100c02b2b4
     */
    public String token(){

        StringBuilder tokenUrl = new StringBuilder(Environment.url(online,TOKEN_URL));

        String secretKey = Base64.getEncoder().encodeToString(new StringBuilder(appId)
                .append(COLON).append(appSecret)
                .toString()
                .getBytes());

        String result = null;
        try {
            result = HttpPost.oauth(tokenUrl.toString(),new BasicHeader(AUTHORIZATION,BASIC + secretKey));
        } catch (IOException e) {
            // TODO 非DEMO版本请处理异常
            e.printStackTrace();
        }

        if(StringUtils.isEmpty(result)){
            throw new Oauth2TokenException(String.format("Get token failure,secretkey is [%s],result is null",secretKey));
        }

        HttpResponseBody body = JSON.parseObject(result,HttpResponseBody.class);
        if(StringUtils.isEmpty(body) || StringUtils.isEmpty(body.getAccess_token())){
            throw new Oauth2TokenException(String.format("Get token failure,secretkey is [%s],result is [%s]",secretKey,result));
        }

        return body.getAccess_token();
    }


    public void setAppId(String appId) {
        this.appId = appId;
    }



    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
