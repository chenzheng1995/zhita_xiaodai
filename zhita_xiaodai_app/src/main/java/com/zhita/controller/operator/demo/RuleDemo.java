package com.zhita.controller.operator.demo;



import java.util.UUID;

import com.zhita.controller.operator.RiskApiClient;
/**
 * 规则集合
 * 文档地址: http://zxreport.f3322.net:4999/web/#/22?page_id=93
 */
public class RuleDemo {

	public void getRule(int userId,String phone, String name, String idNumber,String reqId){
//
//        RiskApiClient riskClient = new RiskApiClient();
//        //公共参数
//        riskClient.setAppId("AfFGnpOE");                   //(TODO)替换值，写入机构ID
//        riskClient.setAppSecret("Ku4aEJu9GaOHOyqPZE");    //(TODO)替换值，写入机构密钥
//        riskClient.setOnline(false);                      //(TODO)替换值，默认为线上环境（true），需要使用方替换，也可不替换
//        //业务参数
//        riskClient.setReqId(UUID.randomUUID().toString());
//        riskClient.setName("陈峥");                       //(TODO)替换值，写入客户姓名
//        riskClient.setUserId("15617913838669348733");         //(TODO)替换值，写入客户在商户平台的用户ID（调用方定义，长度最大为64位）
//        riskClient.setPhone("13486070402");              //(TODO)替换值，写入客户机号(运营商报告请与传给抓取服务的手机号一致)
//        riskClient.setIdNumber("330225199507155112");    //(TODO)替换值，写入客户身份证号码
//
//        //请求
//        String resultJson = riskClient.execute("/api/entry/risk/open/v1/rulemodel/features");
//        System.out.println("规则集合返回："+resultJson);
//
//    }
    
    RiskApiClient riskClient = new RiskApiClient();
    //公共参数
    riskClient.setAppId("app190628133950478882");                   //(TODO)替换值，写入机构ID
    riskClient.setAppSecret("VASH4I83CDHA7JWE5DDJ8XRQEZ56IVIM");    //(TODO)替换值，写入机构密钥
    riskClient.setOnline(false);                      //(TODO)替换值，默认为线上环境（true），需要使用方替换，也可不替换
    //业务参数
    riskClient.setReqId(reqId);
    riskClient.setName(name);                       //(TODO)替换值，写入客户姓名
    riskClient.setUserId(userId+"");         //(TODO)替换值，写入客户在商户平台的用户ID（调用方定义，长度最大为64位）
    riskClient.setPhone(phone);              //(TODO)替换值，写入客户机号(运营商报告请与传给抓取服务的手机号一致)
    riskClient.setIdNumber(idNumber);    //(TODO)替换值，写入客户身份证号码

    //请求
    String resultJson = riskClient.execute("/api/entry/risk/open/v1/rulemodel/features");
//    System.out.println("规则集合返回："+resultJson);
//	return resultJson;

}
}
