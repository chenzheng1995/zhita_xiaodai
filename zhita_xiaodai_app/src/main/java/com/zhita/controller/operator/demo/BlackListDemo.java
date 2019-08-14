package com.zhita.controller.operator.demo;



import java.util.UUID;

import com.zhita.controller.operator.RiskApiClient;

/**
 * 危险名单-黑名单 调用示例
 * 文档地址: http://zxreport.f3322.net:4999/web/#/22?page_id=91
 */
public class BlackListDemo {

    public static void main(String[] args) {

        RiskApiClient riskClient = new RiskApiClient();
        //公共参数
//        riskClient.setAppId("AfFGnpOE");                    //(TODO)替换值，写入机构ID
//        riskClient.setAppSecret("Ku4aEJu9GaOHOyqPZE");      //(TODO)替换值，写入机构密钥
        riskClient.setAppId("app190812164735176026");                    //(TODO)替换值，写入机构ID
        riskClient.setAppSecret("QKP43WQG8U261HHRLYX9IINB7VUUV5LT");      //(TODO)替换值，写入机构密钥
        riskClient.setOnline(true);                        //(TODO)替换值，默认为线上环境（true），需要使用方替换，也可不替换
        //业务参数
        riskClient.setReqId(UUID.randomUUID().toString());
        riskClient.setName("张三");                         //(TODO)替换值，写入客户姓名
        riskClient.setUserId("c12312312312312");           //(TODO)替换值，写入客户在商户平台的用户ID（调用方定义，长度最大为64位）
        riskClient.setPhone("157****4733");                //(TODO)替换值，写入客户机号(运营商报告请与传给抓取服务的手机号一致)
        riskClient.setIdNumber("350***********0014");      //(TODO)替换值，写入客户身份证号码
        String result = riskClient.execute("/api/entry/risk/open/v1/list/black");
        System.out.println("黑名单返回："+result);


    }
}
