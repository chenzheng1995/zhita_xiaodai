package com.zhita.controller.operator.demo;



import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.zhita.controller.operator.RiskApiClient;
import com.zhita.service.manage.userattestation.UserAttestationService;

/**
 * H5互联网运营商认证方式-运营商抓取
 *  文档地址: http://zxreport.f3322.net:4999/web/#/22?page_id=92
 */

public class H5ReportDemo {

	public String getH5Report(int userId,String phone, String name, String idNumber,String reqId){

        RiskApiClient riskClient = new RiskApiClient();
        //公共参数
//        riskClient.setAppId("AfFGnpOE"); //(TODO)替换值，写入机构ID
//        riskClient.setAppSecret("Ku4aEJu9GaOHOyqPZE");//   //(TODO)替换值，写入机构密钥
        riskClient.setAppId("app190812164735176026");                    //(TODO)替换值，写入机构ID
        riskClient.setAppSecret("QKP43WQG8U261HHRLYX9IINB7VUUV5LT");      //(TODO)替换值，写入机构密钥
        riskClient.setOnline(true);  //(TODO)替换值，默认为线上环境（true），需要使用方替换，也可不替换
        //业务参数
        riskClient.setReqId(reqId);  //调用方生成的会话唯一标识id，建议使用流水号生成（调用方定义，长度最大为64位）
        riskClient.setName(name);                       //(TODO)替换值，写入客户姓名
        riskClient.setUserId(userId+"");         //(TODO)替换值，写入客户在商户平台的用户ID（调用方定义，长度最大为64位）
        riskClient.setPhone(phone);              //(TODO)替换值，写入客户机号(运营商报告请与传给抓取服务的手机号一致)
        riskClient.setIdNumber(idNumber);    //(TODO)替换值，写入客户身份证号码


        riskClient.setNotifyUrl("http://47.56.173.1:8081/zhita_xiaodai_app/notifyUrl/callback"); //(TODO)替换值，写入抓取成功后商户服务器异步通知页面路径 （长度最大为128位）
        riskClient.setReturnUrl("https://mdbko.tcc1688.com/renzhengchenggong/index3.html"); //(TODO)替换值，页面跳转同步通知页面路径 （长度最大为512位）returnUrl不传，会出现认证链接失效问题

//        riskClient.setReturnUrl("http://xcx.rong51dai.com/idcard/renzhengchenggong/index3.html"); //(TODO)替换值，页面跳转同步通知页面路径 （长度最大为512位）returnUrl不传，会出现认证链接失效问题
        riskClient.setBackUrl("https://www.taobao.com"); //(TODO)替换值，认证页面的首页左上角按钮对应的地址
        String ruleResult = riskClient.execute("/api/entry/risk/open/v1/h5report/collectuser");
        System.out.println("运营商链接："+ruleResult);
        JSONObject jsonObject = JSONObject.parseObject(ruleResult);
        String tianji_api_tianjireport_collectuser_response = jsonObject.get("tianji_api_tianjireport_collectuser_response").toString();
        jsonObject = JSONObject.parseObject(tianji_api_tianjireport_collectuser_response);
        String redirectUrl = jsonObject.get("redirectUrl").toString();
		return redirectUrl;
    }
}
