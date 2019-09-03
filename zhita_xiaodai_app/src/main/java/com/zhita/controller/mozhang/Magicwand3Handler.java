package com.zhita.controller.mozhang;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class Magicwand3Handler {

    private final static String API_URL = "https://api.51datakey.com/risk-gateway/api/gateway";

    private String appId;

    private String privateKey;

    private String version;

    private Magicwand3Method method;

    private Map<String, Object> bizParams;

    public static MagicScoreHandlerBuilder builder() {
        return new MagicScoreHandlerBuilder();
    }

    private void setAppId(String appId) {
        this.appId = appId;
    }

    private void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    private void setMethod(Magicwand3Method method) {
        this.method = method;
    }

    private void setBizParams(Map<String, Object> bizParams) {
        this.bizParams = bizParams;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * get请求
     *
     * @throws Exception
     */
    public String executeGetMethod() throws Exception {
        /** 请求参数 */
        Map<String, String> reqParams = new HashMap<String, String>();
        reqParams.put(ReqCommonParams.METHOD, method.getMethod());
        reqParams.put(ReqCommonParams.APP_ID, appId);
        reqParams.put(ReqCommonParams.VERSION, ReqCommonParamsValue.VERSION);
        reqParams.put(ReqCommonParams.FORMAT, ReqCommonParamsValue.FORMAT);
        reqParams.put(ReqCommonParams.SIGN_TYPE, ReqCommonParamsValue.SIGN_TYPE);
        reqParams.put(ReqCommonParams.TIMESTAMP, String.valueOf(System.currentTimeMillis()));

        /** 业务参数 */
        String bizContent = new ObjectMapper().writeValueAsString(bizParams);

        reqParams.put(ReqCommonParams.BIZ_CONTENT, bizContent);
        //签名
        String sign = Magicwand3MoxieSignUtils.signSHA1WithRSA(reqParams, privateKey);
//        System.out.println("sign:" + sign);
        reqParams.put(ReqCommonParams.SIGN, sign);

        String resContent = Magicwand3HttpUtils.get(API_URL, reqParams);

        System.out.println("resContent:" + resContent);
		return resContent;
    }

    /**
     * post请求
     *
     * @throws Exception
     */
    public void executePostMethod() throws Exception {
        /** 请求参数 */
        Map<String, String> reqParams = new HashMap<String, String>();
        reqParams.put(ReqCommonParams.METHOD, method.getMethod());
        reqParams.put(ReqCommonParams.APP_ID, appId);
        reqParams.put(ReqCommonParams.VERSION, ReqCommonParamsValue.VERSION);
        reqParams.put(ReqCommonParams.FORMAT, ReqCommonParamsValue.FORMAT);
        reqParams.put(ReqCommonParams.SIGN_TYPE, ReqCommonParamsValue.SIGN_TYPE);
        reqParams.put(ReqCommonParams.TIMESTAMP, String.valueOf(System.currentTimeMillis()));

        /** 业务参数 */
        String bizContent = new ObjectMapper().writeValueAsString(bizParams);

        reqParams.put(ReqCommonParams.BIZ_CONTENT, bizContent);
        //签名
        String sign = Magicwand3MoxieSignUtils.signSHA1WithRSA(reqParams, privateKey);
        reqParams.put(ReqCommonParams.SIGN, sign);

        String request = "";

        for (Map.Entry<String, String> entry : reqParams.entrySet()) {
            request = String.format("%s&%s", request, String.format("%s=%s", entry.getKey(), entry.getValue()));
        }

        String resContent = Magicwand3HttpUtils.post(API_URL, request.substring(1));

        System.out.println("resContent:" + resContent);
    }

    public interface ReqCommonParams {
        String METHOD = "method";
        String APP_ID = "app_id";
        String VERSION = "version";
        String FORMAT = "format";
        String SIGN_TYPE = "sign_type";
        String TIMESTAMP = "timestamp";
        String BIZ_CONTENT = "biz_content";
        String SIGN = "sign";
    }

    public interface ReqCommonParamsValue {
        String VERSION = "1.0";
        String FORMAT = "JSON";
        String SIGN_TYPE = "RSA";
    }

    public static class MagicScoreHandlerBuilder {
        private String appId;
        private String privateKey;
        private Magicwand3Method method;
        private String version;
        private Map<String, Object> bizParams;

        MagicScoreHandlerBuilder() {

        }

        public MagicScoreHandlerBuilder withAppId(String appId) {
            this.appId = appId;
            return this;
        }

        public MagicScoreHandlerBuilder withPrivateKey(String privateKey) {
            this.privateKey = privateKey;
            return this;
        }

        public MagicScoreHandlerBuilder withMethod(Magicwand3Method method) {
            this.method = method;
            return this;
        }

        public MagicScoreHandlerBuilder withVersion(String version) {
            this.version = version;
            return this;
        }

        public MagicScoreHandlerBuilder withBizParams(Map<String, Object> bizParams) {
            this.bizParams = bizParams;
            return this;
        }

        public Magicwand3Handler build() {


            Magicwand3Handler handler = new Magicwand3Handler();
            handler.setAppId(appId);
            handler.setPrivateKey(privateKey);
            handler.setMethod(method);
            handler.setBizParams(bizParams);
            handler.setVersion(version);
            return handler;
        }

    }
}
