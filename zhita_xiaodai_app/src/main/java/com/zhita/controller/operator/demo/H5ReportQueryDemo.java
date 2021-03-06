package com.zhita.controller.operator.demo;



import java.util.UUID;

import com.zhita.controller.operator.RiskApiClient;

/**
 * H5互联网运营商认证方式-运营商抓取
 *  文档地址: http://zxreport.f3322.net:4999/web/#/22?page_id=92
 */
public class H5ReportQueryDemo {

	public String getH5ReportQuery(int userId,String phone, String name, String idNumber,String reqId, String search_id){

//        RiskApiClient riskClient = new RiskApiClient();
//        //公共参数
//        riskClient.setAppId("AfFGnpOE"); //(TODO)替换值，写入机构ID
//        riskClient.setAppSecret("Ku4aEJu9GaOHOyqPZE");//   //(TODO)替换值，写入机构密钥
//        riskClient.setOnline(false);  //(TODO)替换值，默认为线上环境（true），需要使用方替换，也可不替换
//        //业务参数
//        riskClient.setReqId(UUID.randomUUID().toString());  //调用方生成的会话唯一标识id，建议使用流水号生成（调用方定义，长度最大为64位）
//        riskClient.setUserId("c12312312312312");    //(TODO)替换值，写入客户在商户平台的用户ID（调用方定义，长度最大为64位）
//        riskClient.setIdName("张三");    //(TODO)替换值，写入客户在商户平台的用户ID（调用方定义，长度最大为64位）
//        riskClient.setIdNo("350************0014");    //(TODO)替换值，写入客户在商户平台的用户ID（调用方定义，长度最大为64位）
//        riskClient.setPhone("15******104");    //(TODO)替换值，写入客户在商户平台的用户ID（调用方定义，长度最大为64位）
//        String ruleResult = riskClient.execute("/api/entry/risk/open/v1/h5report/getdata");
//        System.out.println("运营商链接："+ruleResult);
        
        RiskApiClient riskClient = new RiskApiClient();
        //公共参数
//        riskClient.setAppId("AfFGnpOE"); //(TODO)替换值，写入机构ID
//        riskClient.setAppSecret("Ku4aEJu9GaOHOyqPZE");//   //(TODO)替换值，写入机构密钥
        riskClient.setAppId("app190812164735176026");                    //(TODO)替换值，写入机构ID
        riskClient.setAppSecret("QKP43WQG8U261HHRLYX9IINB7VUUV5LT");      //(TODO)替换值，写入机构密钥
        riskClient.setOnline(true);  //(TODO)替换值，默认为线上环境（true），需要使用方替换，也可不替换
        //业务参数
        riskClient.setReqId(reqId);  //调用方生成的会话唯一标识id，建议使用流水号生成（调用方定义，长度最大为64位）
        riskClient.setUserId(search_id);    //(TODO)替换值，写入客户在商户平台的用户ID（调用方定义，长度最大为64位）
        riskClient.setIdName(name);    //(TODO)替换值，写入客户在商户平台的用户ID（调用方定义，长度最大为64位）
        riskClient.setIdNo(idNumber);    //(TODO)替换值，写入客户在商户平台的用户ID（调用方定义，长度最大为64位）
        riskClient.setPhone(phone);    //(TODO)替换值，写入客户在商户平台的用户ID（调用方定义，长度最大为64位）
        String ruleResult = riskClient.execute("/api/entry/risk/open/v1/h5report/getdata");
        System.out.println("运营商链接："+ruleResult);
		return ruleResult;

    }
}
