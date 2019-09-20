package com.zhita.controller.payment;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;














import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.NameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.zhita.chanpayutil.BaseConstant;
import com.zhita.chanpayutil.ChanPayUtil;
import com.zhita.dao.manage.OrderdetailsMapper;
import com.zhita.model.manage.Bankcard;
import com.zhita.model.manage.Loan_setting;
import com.zhita.model.manage.Orderdetails;
import com.zhita.model.manage.Payment_record;
import com.zhita.model.manage.ReturnChanpay;
import com.zhita.model.manage.Thirdparty_interface;
import com.zhita.service.manage.Statistic.Statisticsservice;
import com.zhita.service.manage.borrowmoneymessage.IntBorrowmonmesService;
import com.zhita.service.manage.chanpayQuickPay.Chanpayservice;
import com.zhita.service.manage.newpayment.NewPaymentservice;
import com.zhita.service.manage.newpayment.NewPaymentserviceimp;
import com.zhita.service.manage.order.IntOrderService;
import com.zhita.service.manage.user.IntUserService;
import com.zhita.util.HttpProtocolHandler;
import com.zhita.util.HttpRequest;
import com.zhita.util.HttpResponse;
import com.zhita.util.HttpResultType;
import com.zhita.util.MD5;
import com.zhita.util.RSA;
import com.zhita.util.RedisClientUtil;
import com.zhita.util.Timestamps;




@Controller
@RequestMapping("newpay")
public class NewPaymentController {
	
	
	
	@Autowired
	private Statisticsservice servie;
	
	
	
	
	
	/**
	 * 畅捷支付平台公钥
	 */
	public static final String MERCHANT_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDPq3oXX5aFeBQGf3Ag/86zNu0VICXmkof85r+DDL46w3vHcTnkEWVbp9DaDurcF7DMctzJngO0u9OG1cb4mn+Pn/uNC1fp7S4JH4xtwST6jFgHtXcTG9uewWFYWKw/8b3zf4fXyRuI/2ekeLSstftqnMQdenVP7XCxMuEnnmM1RwIDAQAB";
		                                                
		
	/**
	 * 商户私钥
	 */
	public static final String MERCHANT_PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMWGZZTekot7jUeVTD11ThwLWL/Ax5bt0k73bCVRZQx0VyGFGL6368u5kJLY9Xh83bquh0qgwx9A3PA3gp/6Fz/xkdgZRq8KKgrSgrOTp2cNYCmMd3YfGdYXlBFahCEM1uuNRxbCqK8Eb7QsE2ZiF/ik5xvzzs1FDsx2XLN9jXAFAgMBAAECgYBz9WRpMwkBDbVxErrBUb5bRGYDnF8Pweo3JZW9ir0xxJLqQMK4JC0vWm7/ZpMk+tkIoTEXpK0oCeIqu8vZsu42OXzAPItGLCrGuwRgx0tYEE/89mHwKKeoDPvDOsxZ8ASzX30FE93VQZ7fzOZazxKTKiHzcDiPiyZxuuAKFrENOQJBAOvkCuQkMtbQAx05LRGQ3iOtClCbr0Y929s8h54OB7ipfZkxx8+jzqsOgHPKIkltUTM7zwpXfCrQqTjsJyWpiwcCQQDWXRQ42c6ea0VZyhsM/iw2vNCYojsPJ+gNm0k65oR0CCU5xeJkgD6VotN67GTB2fKxvP082iDB8kBZM3Wiss2TAkBDsWpqs/Se7oymOz0yuEb3J/Y40aSH3MKV9JXahp4yoPj5GG8FqDVrozq7f7s9JRDTSguNJTPtuXmGa0aEqVXLAkEAvhckS5W6B/mQMiNrAYaTpqahQ/j47mOw///oXHb2lf5zJFw6emzPEtqlNqhSYSTodnzlBAVabyJntbJQasqsSQJAIAqIBcKbero2nMZ/3gbinZIdOKQKAqBDv8zkQeIkR5r86msndCZc4Uo1PAhYPSU3CfUnRzyp5gdNqcE7DpS/JQ==";
	
	
	
	/**
	 * 编码类型
	 */
	private static String charset = "UTF-8";
	
	
	
	@Autowired
	private Chanpayservice chanser;
	
	
	
	
	@Autowired
	private NewPaymentservice newsim;
	
	
	
	@Autowired
	IntOrderService intOrderService;
	
	
	@Autowired
	IntUserService intUserService;
	
	
	
	@Autowired
    OrderdetailsMapper orderdetailsMapper;
	
	
	
	@Autowired
	IntBorrowmonmesService intBorrowmonmesService;
	
	
	
	@RequestMapping(value = "/callback", consumes = "multipart/form-data", method = RequestMethod.POST)
    public String callback(MultipartHttpServletRequest request) {
  String mchId = request.getParameter("mchId");
     String gateway = request.getParameter("gateway");
     String sign = request.getParameter("sign");
     System.out.println("接收到的参数：\nsearch_为："
             + mchId + "\noutUniqueId为：" + gateway
             + "\nuserId为：" + sign + "\nstate为：");
     
    Map<String, Object> map = new HashMap<String, Object>();
 // int number = operatorService.updateSearch_id(search_id,userId);
//     if (number == 1) {                   
//         map.put("msg", "数据插入成功");
//         map.put("Code", "200");
//     } else {
//         map.put("msg", "数据插入失败");
//         map.put("Code", "405");
//     }
     
     return "SUCCESS";
}
	
	
	
	
	
	@RequestMapping(value = "/callback", consumes = "multipart/form-data", method = RequestMethod.POST)
    public String callbackhk(MultipartHttpServletRequest request) {
  String mchId = request.getParameter("mchId");
     String gateway = request.getParameter("gateway");
     String sign = request.getParameter("sign");
     System.out.println("接收到的参数：\nsearch_为："
             + mchId + "\noutUniqueId为：" + gateway
             + "\nuserId为：" + sign + "\nstate为：");
     
    Map<String, Object> map = new HashMap<String, Object>();
// int number = operatorService.updateSearch_id(search_id,userId);
//     if (number == 1) {                   
//         map.put("msg", "数据插入成功");
//         map.put("Code", "200");
//     } else {
//         map.put("msg", "数据插入失败");
//         map.put("Code", "405");
//     }
     
     return "SUCCESS";
}
	
	
	
	
	
	
}
