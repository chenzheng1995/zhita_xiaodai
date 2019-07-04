package com.zhita.controller.operator;

import com.alibaba.fastjson.JSON;
import com.zhita.controller.operator.http.Environment;
import com.zhita.controller.operator.http.HttpPost;
import org.apache.http.message.BasicHeader;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 *  客户端（demo使用）
 * @author Lawrence
 */
public class RiskApiClient {

    // 应用ID
    private String appId;
    // 应用密钥
    private String appSecret;
    // 环境 true 正式   false  沙箱/测试
    private boolean online;
    // 请求号(商户生成)
    private String reqId;
    // 姓名
    private String name;
    // 用户id
    private String userId;
    //用户手机号
    private String phone;
    //身份证号码
    private String idNumber;


    /** 服务器异步通知页面路径(H5抓取) */
    private String notifyUrl;
    private String returnUrl;
    private String backUrl;



    /** 服务器绑定数据**/
    private String idNo;
    private String idName;



    public String execute(String URI){
        Assert.hasLength(appId,"can not be null");
        Assert.hasLength(appSecret,"can not be null");
        Assert.notNull(online,"can not be null");
        TokenClient tokenClient = new TokenClient();
        tokenClient.setAppId(appId);
        tokenClient.setAppSecret(appSecret);
        tokenClient.setOnline(online);
        BasicHeader token = tokenClient.tokenBasicHeader();
        Map<String,String> params = new HashMap<>();
        params.put("reqId", UUID.randomUUID().toString());
        params.put("appId", appId);
        params.put("name", name);
        params.put("phone", phone);
        params.put("idNumber", idNumber);
        params.put("userId", userId);
        if(!StringUtils.isEmpty(notifyUrl)){
            params.put("notifyUrl", notifyUrl);
        }
        if(!StringUtils.isEmpty(backUrl)){
            params.put("backUrl", backUrl);
        }
        if(!StringUtils.isEmpty(returnUrl)){
            params.put("returnUrl", returnUrl);
        }
        if(!StringUtils.isEmpty(idNo)){
            params.put("idNo", idNo);
        }
        if(!StringUtils.isEmpty(idName)){
            params.put("idName", idName);
        }
        return HttpPost.form(Environment.url(online,URI),params,token);
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getBackUrl() {
        return backUrl;
    }

    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl;
    }
    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }
    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }
}
