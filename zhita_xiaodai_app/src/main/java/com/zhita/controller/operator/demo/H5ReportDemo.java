package com.zhita.controller.operator.demo;



import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.zhita.controller.operator.RiskApiClient;

/**
 * H5互联网运营商认证方式-运营商抓取
 *  文档地址: http://zxreport.f3322.net:4999/web/#/22?page_id=92
 */
@Controller
@RequestMapping(value="/H5Report")
public class H5ReportDemo {

	@ResponseBody
	@RequestMapping("/getH5Report")
	@Transactional
	public String getH5Report(){


        RiskApiClient riskClient = new RiskApiClient();
        //公共参数
        riskClient.setAppId("AfFGnpOE"); //(TODO)替换值，写入机构ID
        riskClient.setAppSecret("Ku4aEJu9GaOHOyqPZE");//   //(TODO)替换值，写入机构密钥
        riskClient.setOnline(false);  //(TODO)替换值，默认为线上环境（true），需要使用方替换，也可不替换
        //业务参数
        riskClient.setReqId(UUID.randomUUID().toString());  //调用方生成的会话唯一标识id，建议使用流水号生成（调用方定义，长度最大为64位）
        riskClient.setName("李会琴");                       //(TODO)替换值，写入客户姓名
        riskClient.setUserId("c12312312312312");         //(TODO)替换值，写入客户在商户平台的用户ID（调用方定义，长度最大为64位）
        riskClient.setPhone("18871552652");              //(TODO)替换值，写入客户机号(运营商报告请与传给抓取服务的手机号一致)
        riskClient.setIdNumber("420683199902210924");    //(TODO)替换值，写入客户身份证号码
        riskClient.setNotifyUrl("http://39.98.83.65:8080/zhita_daichao_app/notifyUrl/callback"); //(TODO)替换值，写入抓取成功后商户服务器异步通知页面路径 （长度最大为128位）
        riskClient.setReturnUrl("https://www.taobao.com"); //(TODO)替换值，页面跳转同步通知页面路径 （长度最大为512位）returnUrl不传，会出现认证链接失效问题
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
