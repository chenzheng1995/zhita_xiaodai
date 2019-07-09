package com.zhita.controller.operator.demo;



import java.util.UUID;

import com.zhita.controller.operator.RiskApiClient;

/**
 * 运营商小额模型
 * http://zxreport.f3322.net:4999/web/#/22?page_id=94
 */
public class ScoreDemo {

	public String getScore(int userId,String phone, String name, String idNumber,String reqId){

        RiskApiClient riskClient = new RiskApiClient();
        //公共参数
        riskClient.setAppId("app190628133950478882"); //(TODO)替换值，写入机构ID
        riskClient.setAppSecret("VASH4I83CDHA7JWE5DDJ8XRQEZ56IVIM");//   //(TODO)替换值，写入机构密钥
        riskClient.setOnline(false);  //(TODO)替换值，默认为线上环境（true），需要使用方替换，也可不替换
        //业务参数
        riskClient.setReqId(reqId);
        riskClient.setName(name);                       //(TODO)替换值，写入客户姓名
        riskClient.setPhone(phone);              //(TODO)替换值，写入客户机号(运营商报告请与传给抓取服务的手机号一致)
        riskClient.setIdNumber(idNumber);    //(TODO)替换值，写入客户身份证号码
        /**
         * (TODO)注意：为用户ID与黑名单不同，需要传入抓取运营商回调接口抓取成功时的search_id
         * (TODO) 具体见接入文档 3.2 节notifyUrl 报告回调 http://192.168.1.46:4999/web/#/22?page_id=92
         */
        riskClient.setUserId(userId+"");

        String ruleResult = riskClient.execute("/api/entry/risk/open/v1/score/pdscorev5");
        System.out.println("规则集合返回："+ruleResult);
        return ruleResult;

    }
}
