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

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhita.chanpayutil.BaseConstant;
import com.zhita.chanpayutil.ChanPayUtil;
import com.zhita.controller.chanpayQuickPay.ChanpayQuickCollection;
import com.zhita.controller.chanpayQuickPay.ReturnSend;
import com.zhita.controller.payment.util.HttpClient;
import com.zhita.controller.payment.util.SignUtils;
import com.zhita.controller.payment.zpay.ZpayConfig;
import com.zhita.dao.manage.OrderdetailsMapper;
import com.zhita.model.manage.Bankcard;
import com.zhita.model.manage.Loan_setting;
import com.zhita.model.manage.Orderdetails;
import com.zhita.model.manage.Orders;
import com.zhita.model.manage.Payment_record;
import com.zhita.model.manage.Repayment;
import com.zhita.model.manage.ReturnChanpay;
import com.zhita.model.manage.Thirdparty_interface;
import com.zhita.service.manage.Statistic.Statisticsservice;
import com.zhita.service.manage.borrowmoneymessage.IntBorrowmonmesService;
import com.zhita.service.manage.chanpayQuickPay.Chanpayservice;
import com.zhita.service.manage.newpayment.NewPaymentservice;
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
	
	
	
	@Resource
	private Chanpayservice chanpayservice;
	
	
	
	
	
	
	/**
	 * 修改延期状态
	 * @param billId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("UpdateDefeStatus")
	public Map<String, Object> RtimeDefeStatus(String billId,Integer userId){
		System.out.println("还款状态接口调用：");
		RedisClientUtil redis = new RedisClientUtil();
		final long timeInterval = 1000;  
		Map<String, String> payParams=new HashMap<String, String>();        
        payParams.put("method","zpay.trade.query");
        payParams.put("version","1.0");
        payParams.put("mchId",ZpayConfig.MERCHANT_NO);
        payParams.put("orderId", billId);
        String resultSign= SignUtils.getSign(payParams,ZpayConfig.MD5_KEY);
        payParams.put("sign",resultSign);
		Map<String, Object> map = new HashMap<String, Object>();
		String resp;
		
		try {
			HttpClient httpClient=new HttpClient(ZpayConfig.GATEWAY_URL, 30000, 30000);
			resp = httpClient.send(payParams, "utf-8");
			System.out.println(resp);
	        if(StringUtils.isNotBlank(resp)){
	        	JSONObject jsonObject=JSONObject.parseObject(resp);
	        	String code=jsonObject.getString("code");
	        	String status = jsonObject.getString("status");
	        	String msg = jsonObject.getString("msg");
	        	String orderId = jsonObject.getString("orderId");
	        	String OrderNumber = redis.get("DefeorderId"+orderId);
	    		
	        	if(code.equals("SUCCESS")){//接口访问成功走此内容
	        		Orders ord = new Orders();
	    			ord.setOrderNumber(OrderNumber);
	        		 if(status.equals("SUCCESS")){//还款状态成功
	 	        		ord.setUserId(userId);
	 	        		Integer a = servie.UpdateDefeOrders(ord);
	 	        		if(a != null){
	 	        			map.put("Ncode", 2000);
	 	        			map.put("code", "200");
	 	        			map.put("desc", "插入成功");
	 	        			map.put("msg", "插入成功");
	 	        		}else{
	 	        			map.put("code", "0");
	 	        			map.put("desc", "插入失败");
	 	        			map.put("msg", "插入失败");
	 	        		}
	 	        		
	     				
	        		 }else if(status.equals("PAYERROR")){//还款状态代付失败
	        			map.put("code", 0);
	 	        		map.put("Ncode", 0);
	 	        		map.put("msg", msg);
	 	        		return map;
	        		 }else{//中间状态   比如  待审核   已审核   已提交   待付款
	        			 Runnable runnable = new Runnable() {  
		        	        	
		        	            public void run() { 
		        	            	int i = 1;
		        	                while (true) { 
		        	                    // ------- code for task to run  
		        	                    System.out.println("Hello !!");  
		        	                    // ------- ends here  
		        	                    try {  
		        	                        Thread.sleep(timeInterval);  
		        						        try {
		        									HttpClient httpClient=new HttpClient(ZpayConfig.GATEWAY_URL, 30000, 30000);
		        									String resp = httpClient.send(payParams, "utf-8");
		        									System.out.println(resp);
		        							        if(StringUtils.isNotBlank(resp)){
		        							        	JSONObject jsonObject=JSONObject.parseObject(resp);
		        							        	String code=jsonObject.getString("code");
		        							        	String hkstatus = jsonObject.getString("status");
		        							        	if(code.equals("SUCCESS")){
		        							        		if(SignUtils.checkParam(JSONObject.toJavaObject(jsonObject, Map.class) , ZpayConfig.MD5_KEY)){
		        							        			if(hkstatus.equals("SUCCESS")){
		        							        				servie.UpdateDefeOrders(ord);
		        							        			}
		        							        		}
		        							        	}
		        							        }
		        								} catch (Exception e) {
		        									e.printStackTrace();
		        								}
		        	                        	break;
		        	                    } catch (InterruptedException e) {  
		        	                        e.printStackTrace();  
		        	                    }  
		        	                }  
		        	            }  
		        	        };  
		        	        Thread thread = new Thread(runnable);  
		        	        thread.start(); 
		        	        String Repaymentstatus = chanser.DefeStatus(OrderNumber);
		        	        Integer codesa = 0;
		        	        Integer Ncode = 0;
		        	        if(Repaymentstatus!=null){
		        	        	if(Repaymentstatus.length()!=0){
			        	        	if(Repaymentstatus.equals("成功")){
			        	        		codesa = 200;
			        	        		Repaymentstatus = "延期成功";
			        	        		Ncode = 2000;
				        	        }else{
				        	        	codesa = 0;
				        	        	Repaymentstatus = "延期失败";
			        	        		Ncode = 0;
				        	        }
		        	        	}else{
		        	        		Repaymentstatus = "正在处理,请稍后查询";
		        	        	}
		        	        }else{
		        	        	Repaymentstatus = "正在处理,请稍后查询";
		        	        }
		        	        
		        	        map.put("msg", Repaymentstatus);
		        	        map.put("code", codesa);
		        	        map.put("Ncode", Ncode);
		        	        return map;
	        		 }
	        			
	        	}else{
	        		map.put("code", 0);
	        		map.put("Ncode", 0);
	        		map.put("msg", msg);
	        		return map;
	        	}
	        	}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return map;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 修改还款状态
	 * @param billId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("UpdateRepaymentStatus")
	public Map<String, Object> Rtime(String billId){
		System.out.println("还款状态接口调用：");
		RedisClientUtil redis = new RedisClientUtil();
		final long timeInterval = 1000;  
		Map<String, String> payParams=new HashMap<String, String>();        
        payParams.put("method","zpay.trade.query");
        payParams.put("version","1.0");
        payParams.put("mchId",ZpayConfig.MERCHANT_NO);
        payParams.put("orderId", billId);
        String resultSign= SignUtils.getSign(payParams,ZpayConfig.MD5_KEY);
        payParams.put("sign",resultSign);
		Map<String, Object> map = new HashMap<String, Object>();
		String resp;
		
		try {
			HttpClient httpClient=new HttpClient(ZpayConfig.GATEWAY_URL, 30000, 30000);
			resp = httpClient.send(payParams, "utf-8");
			System.out.println(resp);
	        if(StringUtils.isNotBlank(resp)){
	        	JSONObject jsonObject=JSONObject.parseObject(resp);
	        	String code=jsonObject.getString("code");
	        	String status = jsonObject.getString("status");
	        	String msg = jsonObject.getString("msg");
	        	String tradeNo = jsonObject.getString("tradeNo");
	        	String orderId = jsonObject.getString("orderId");
	        	String OrderNumber = redis.get("orderId"+orderId);
	        	String pipelinenu = "Rsn_"+orderId;
	        	 Repayment repay = new Repayment();
    			 repay.setStatu("成功");
    			 repay.setPipelinenumber(pipelinenu);
    			 repay.setOrderNumber(OrderNumber);
	    		
	        	if(code.equals("SUCCESS")){//接口访问成功走此内容
	        		Orders ord = new Orders();
	    			ord.setOrderNumber(OrderNumber);
	        		 if(status.equals("SUCCESS")){//还款状态成功
	        			 Integer updateId = chanpayservice.UpdateRepayStatusAA(repay);
	     				if(updateId != null){
	     				Integer a = servie.UpdateOrders(ord);
	     					if(a!=null){
	     						map.put("Ncode", 2000);
	     						map.put("code", "200");
	     						map.put("msg", "插入成功");
	     						return map;
	     					}else{
	     						map.put("code", "0");
	     						map.put("Ncode", 0);
	     						map.put("msg", msg);
	     						return map;
	     					}
	     				}else{
	     					map.put("code", "0");
	     					map.put("Ncode", 0);
	     					map.put("msg", "还款状态修改失败,请联系客服");
	     					return map;
	     				}
	     				
	        		 }else if(status.equals("PAYERROR")){//还款状态代付失败
	        			map.put("code", 0);
	 	        		map.put("Ncode", 0);
	 	        		map.put("msg", msg);
	 	        		return map;
	        		 }else{//中间状态   比如  待审核   已审核   已提交   待付款
	        			 Runnable runnable = new Runnable() {  
		        	        	
		        	            public void run() { 
		        	            	int i = 1;
		        	                while (true) { 
		        	                    // ------- code for task to run  
		        	                    System.out.println("Hello !!");  
		        	                    // ------- ends here  
		        	                    try {  
		        	                        Thread.sleep(timeInterval);  
		        						        try {
		        									HttpClient httpClient=new HttpClient(ZpayConfig.GATEWAY_URL, 30000, 30000);
		        									String resp = httpClient.send(payParams, "utf-8");
		        									System.out.println(resp);
		        							        if(StringUtils.isNotBlank(resp)){
		        							        	JSONObject jsonObject=JSONObject.parseObject(resp);
		        							        	String code=jsonObject.getString("code");
		        							        	String hkstatus = jsonObject.getString("status");
		        							        	if(code.equals("SUCCESS")){
		        							        		if(SignUtils.checkParam(JSONObject.toJavaObject(jsonObject, Map.class) , ZpayConfig.MD5_KEY)){
		        							        			if(hkstatus.equals("SUCCESS")){
		        							        				 Integer updateId = chanpayservice.UpdateRepayStatus(pipelinenu,OrderNumber);
		        							 	     				if(updateId != null){
		        							 	     				servie.UpdateOrders(ord);
		        							 	     				break;
		        							 	     				}
		        							        			}
		        							        		}
		        							        	}
		        							        }
		        								} catch (Exception e) {
		        									e.printStackTrace();
		        								}
		        	                        	break;
		        	                    } catch (InterruptedException e) {  
		        	                        e.printStackTrace();  
		        	                    }  
		        	                }  
		        	            }  
		        	        };  
		        	        Thread thread = new Thread(runnable);  
		        	        thread.start(); 
		        	        String Repaymentstatus = chanser.RepaymentStatus(OrderNumber);
		        	        Integer codesa = 0;
		        	        Integer Ncode = 0;
		        	        if(Repaymentstatus!=null){
		        	        	if(Repaymentstatus.length()!=0){
			        	        	if(Repaymentstatus.equals("成功")){
			        	        		codesa = 200;
			        	        		Repaymentstatus = "还款成功";
			        	        		Ncode = 2000;
				        	        }else{
				        	        	codesa = 0;
				        	        	Repaymentstatus = "还款失败";
			        	        		Ncode = 0;
				        	        }
		        	        	}else{
		        	        		Repaymentstatus = "正在处理,请稍后查询";
		        	        	}
		        	        }else{
		        	        	Repaymentstatus = "正在处理,请稍后查询";
		        	        }
		        	        
		        	        map.put("msg", Repaymentstatus);
		        	        map.put("code", codesa);
		        	        map.put("Ncode", Ncode);
		        	        return map;
	        		 }
	        			
	        	}else{
	        		map.put("code", 0);
	        		map.put("Ncode", 0);
	        		map.put("msg", msg);
	        		return map;
	        	}
	        	}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return map;
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * 放款状态
	 * @param orderId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("SelectPayment")
	public Map<String, Object> SelectPaymentStatus(String orderId){
		Map<String, Object> map=new HashMap<String, Object>();    
		final long timeInterval = 1000;  
		Map<String, String> payParams=new HashMap<String, String>();    
	    payParams.put("method","zpay.order.query");
        payParams.put("version","1.0");
        payParams.put("mchId",ZpayConfig.NEW_MERCHANT_NO);
        payParams.put("orderId", orderId);//订单ID
        String resultSign= SignUtils.getSign(payParams,ZpayConfig.NEW_MD5_KEY).toUpperCase();
        payParams.put("sign",resultSign);
        try {
			HttpClient httpClient=new HttpClient(ZpayConfig.GATEWAY_URL, 30000, 30000);
			String resp = httpClient.send(payParams, "utf-8");
			System.out.println("数据:"+resp);
			
	        if(StringUtils.isNotBlank(resp)){
	        	JSONObject jsonObject=JSONObject.parseObject(resp);
	        	String code=jsonObject.getString("code");
	        	String status = jsonObject.getString("status");
	        	String msg = jsonObject.getString("msg");
	        	if(code.equals("SUCCESS")){
	        		String orderIda = jsonObject.getString("orderId");
//	        				 //String tradeNo = jsonObject.getString("tradeNo");
//	    					 String orderIda = jsonObject.getString("orderId");
//	    					 //商户订单号
//	    					 Integer order = newsim.getOrderId(orderIda);
//	    					 
//	    					 Payment_record pays = newsim.getPayment(order);
//	    						 if(pays.getStatus().equals("支付成功")){
//	    							 map.put("code", 200);
//	    							 map.put("Ncode", 2000);
//	    							 map.put("msg", "已放款,请勿重复点击");
//	    							 return map;
//	    						 }else{
//	    							 Integer oid = newsim.getOrderId(orderIda);
//	    							 Payment_record pay = new Payment_record();
//	    					    	 pay.setOrderId(oid);
//	    					    	 if(status.equals("SUCCESS")){
//	    					    		 pay.setStatus("支付成功");
//	    					    	 }else if(status.equals("PAYERROR")){
//	    					    		 pay.setStatus("支付失败");
//	    					    	 }else{
//	    					    		 pay.setStatus("支付成功"); 
//	    					    	 }
//	    					    	 newsim.Updatepaymemt(pay);
//	    						 }
	        			Integer order = newsim.getOrderId(orderIda);
	        		 	Payment_record pays = newsim.getPayment(order);
	        		 	Payment_record pay = new Payment_record();
	        		 	pay.setOrderId(order);
	        			if(status.equals("SUCCESS")){//放款状态成功
	        				pay.setStatus("支付成功"); 
	        				newsim.Updatepaymemt(pay);
	        			}else if(status.equals("PAYERROR")){//放款失败
	        				chanser.DeleteOrderNumber(orderId,msg);
	        				map.put("code", 0);
		        			map.put("Ncode", 0);
		        			map.put("msg", msg);
		        			return map;
	        			}else{
	        				 Runnable runnable = new Runnable() {  
			        	        	
			        	            public void run() { 
			        	                while (true) { 
			        	                    // ------- code for task to run  
			        	                    System.out.println("Hello !!");  
			        	                    // ------- ends here  
			        	                    try {  
			        	                        Thread.sleep(timeInterval);  
			        						        try {
			        									HttpClient httpClient=new HttpClient(ZpayConfig.GATEWAY_URL, 30000, 30000);
			        									String resp = httpClient.send(payParams, "utf-8");
			        									System.out.println(resp);
			        							        if(StringUtils.isNotBlank(resp)){
			        							        	JSONObject jsonObject=JSONObject.parseObject(resp);
			        							        	String code=jsonObject.getString("code");
			        							        	String hkstatus = jsonObject.getString("status");
			        							        	if(code.equals("SUCCESS")){
			        							        		if(SignUtils.checkParam(JSONObject.toJavaObject(jsonObject, Map.class) , ZpayConfig.MD5_KEY)){
			        							        			if(hkstatus.equals("SUCCESS")){
			        							        				 Integer updateId = chanpayservice.UpdatePayStatus(orderIda);
			        							 	     				if(updateId != null){
			        							 	     				break;
			        							 	     				}
			        							        			}
			        							        		}
			        							        	}
			        							        }
			        								} catch (Exception e) {
			        									e.printStackTrace();
			        								}
			        	                        	break;
			        	                    } catch (InterruptedException e) {  
			        	                        e.printStackTrace();  
			        	                    }  
			        	                }  
			        	            }  
			        	        };  
			        	        Thread thread = new Thread(runnable);  
			        	        thread.start(); 
			        	        String paymentStatus = chanser.paymentStatus(orderIda);
			        	        Integer codesa = 0;
			        	        Integer Ncode = 0;
			        	        if(paymentStatus!=null){
			        	        	if(paymentStatus.length()!=0){
				        	        	if(paymentStatus.equals("支付成功")){
				        	        		codesa = 200;
				        	        		paymentStatus = "支付成功";
				        	        		Ncode = 2000;
					        	        }else{
					        	        	codesa = 0;
					        	        	paymentStatus = "支付失败";
				        	        		Ncode = 0;
					        	        }
			        	        	}else{
			        	        		paymentStatus = "正在处理,请稍后查询";
			        	        	}
			        	        }else{
			        	        	paymentStatus = "正在处理,请稍后查询";
			        	        }
			        	        
			        	        map.put("msg", paymentStatus);
			        	        map.put("code", codesa);
			        	        map.put("Ncode", Ncode);
			        	        return map;
		        		 }
	        			}
	        		
	        		}else{
	        			map.put("code", 0);
	        			map.put("Ncode", 0);
	        			map.put("msg", "放款异常");
	        			return map;
	        		}
	        	
		} catch (Exception e) {
			e.printStackTrace();
		}
        return map;
	}
	
	
	
	
	@ResponseBody
	@RequestMapping("UpdatePaymentStatus")
	public Integer UpdateStatus(String orderId){
		Integer a = 0;
		Map<String, String> payParams=new HashMap<String, String>();    
	    payParams.put("method","zpay.order.query");
        payParams.put("version","1.0");
        payParams.put("mchId",ZpayConfig.NEW_MERCHANT_NO);
        payParams.put("orderId", orderId);//订单ID
        String resultSign= SignUtils.getSign(payParams,ZpayConfig.NEW_MD5_KEY).toUpperCase();
        payParams.put("sign",resultSign);
        try {
			HttpClient httpClient=new HttpClient(ZpayConfig.GATEWAY_URL, 30000, 30000);
			String resp = httpClient.send(payParams, "utf-8");
			System.out.println("数据:"+resp);
	        if(StringUtils.isNotBlank(resp)){
	        	JSONObject jsonObject=JSONObject.parseObject(resp);
	        	String code=jsonObject.getString("code");
	        	String status = jsonObject.getString("status");
	        	String msg = jsonObject.getString("msg");
	        	if(code.equals("SUCCESS")){
	        				 //String tradeNo = jsonObject.getString("tradeNo");
	    					 String orderIda = jsonObject.getString("orderId");
	    					 //商户订单号
	    					 Integer order = newsim.getOrderId(orderIda);
	    					 
	    					 if(order!=null){
	    						 Payment_record pays = newsim.getPayment(order);
	    						 if(pays.getStatus().equals("支付成功")){
	    							 a=3;
	    							 return a;
	    						 }else{
	    							 Integer oid = newsim.getOrderId(orderIda);
	    							 Payment_record pay = new Payment_record();
	    					    	 pay.setOrderId(oid);
	    					    	 if(status.equals("SUCCESS")){
	    					    		 pay.setStatus("支付成功");
	    					    	 }else if(status.equals("PAYERROR")){
	    					    		 pay.setStatus("支付失败");
	    					    		 chanser.DeleteOrderNumber(orderId,msg);
	    					    	 }else{
	    					    		 pay.setStatus("支付成功");
	    					    	 }
	    					    	 int c = newsim.Updatepaymemt(pay);
	    					    	 if(c==1){
	    					    		 a = 1;
	    					    	 }
	    						 }
	    					 }
	        		}
	        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
        return a;
	}
	
	
	/**
	 * 放款
	 * @param companyId
	 * @param bankname		开户行名称
	 * @return
	 */
	@ResponseBody
	@RequestMapping("Newpanyment")
	public Map<String, Object> Newpanyment(Integer userId,String TransAmt,Integer companyId,int lifeOfLoan,BigDecimal finalLine,String registeClient,String sourceName,
			BigDecimal shouldTotalAmount,BigDecimal totalInterest,BigDecimal averageDailyInterest){
		Thirdparty_interface paymentname = newsim.SelectPaymentName(companyId);//获取系统设置的 放款名称   和  还款名称
		Loan_setting loana = new Loan_setting();
		loana.setCompanyId(companyId);
		loana.setName(paymentname.getLoanSource());

		SimpleDateFormat sin = new SimpleDateFormat("yyyy-MM-dd");
		String time = sin.format(new Date());
		RedisClientUtil redis = new RedisClientUtil();
		Loan_setting loan = new Loan_setting();
		loan.setCompanyId(companyId);
		loan.setName(paymentname.getLoanSource());
		String a =  chanser.loanSetStatu(loan);//放款状态  1  开启    2 关闭
		Map<String, Object> map1 = new HashMap<String, Object>();
		Map<String, Object> map2 = intBorrowmonmesService.getborrowMoneyMessage(companyId); 
		Integer id = chanser.SelectOrdersId(userId);
		String borrowingScheme = chanser.SelectBorrowing(companyId);
		BigDecimal actualAmountReceived = null;
		BigDecimal acmoney = null;
		Integer j = null;
		int platformfeeRatio =  ((int) map2.get("platformfeeRatio"));//平台服务费比率
		Double pladata = platformfeeRatio*0.01;
		BigDecimal pr = new BigDecimal(0);
		pr=BigDecimal.valueOf((Double)pladata);//平台服务费比率
		BigDecimal platformServiceFee = (finalLine.multiply(pr)).setScale(2,BigDecimal.ROUND_HALF_UP);//平台服务费
		
		
		
		
		if(borrowingScheme.equals("2")){
			BigDecimal sa = new BigDecimal(TransAmt);
			j = shouldTotalAmount.compareTo(sa);
			}
		actualAmountReceived = new BigDecimal(TransAmt); //实际到账金额
		acmoney = new BigDecimal(TransAmt);
		j = actualAmountReceived.compareTo(acmoney);
		System.out.println(j+"金额:"+acmoney+"实际到账:"+actualAmountReceived);
			
			int borrowNumber = intOrderService.borrowNumber(userId,companyId); //用户还款次数
			Bankcard ba = new Bankcard();
			ba.setCompanyId(companyId);
			ba.setUserId(userId);
			Bankcard ban = chanser.SelectBank(ba);
			if(ban!=null){
			System.out.println("数据:"+ban.getTiedCardPhone() + ban.getBankcardName() + ban.getCstmrnm() + ban.getBankcardTypeName());
			Calendar now = Calendar.getInstance(); 
	    	String year = now.get(Calendar.YEAR)+""; //年
	    	String month = now.get(Calendar.MONTH) + 1 + "";//月
	    	if(Integer.parseInt(month)<10) {
	    		month = "0"+Integer.parseInt(month);
	    	}
	    	String day = now.get(Calendar.DAY_OF_MONTH)+"";//日
	    	String hour = now.get(Calendar.HOUR_OF_DAY)+"";//时
	    	String minute = now.get(Calendar.MINUTE)+"";//分
	    	String second = now.get(Calendar.SECOND)+"";//秒
	    	String afterFour = ban.getTiedCardPhone().substring(ban.getTiedCardPhone().length()-4); 
	    	String orderNumber ="DD_"+year+month+day+hour+minute+second+afterFour+"0"+(lifeOfLoan+"")+((borrowNumber+1)+"");//订单编号
	    	
	    	
			
		
		String chanpaysenduserid = redis.get("ChanpaySenduserId"+userId);
		if(chanpaysenduserid != null){
			map1.put("code", "205");
			map1.put("Ncode", 0);
			map1.put("msg", "系统错误,无法借款");
			return map1;
		}else{
		
		if(j==0 || j==1){//j = 0 证明 actualAmountReceived == acmoney j = 1 actualAmountReceived > acmoney
			
		if(id == null){
			
		
		if(a.equals("1")){
		Orderdetails ord = new Orderdetails();
		
		
		ord.setCompanyId(companyId);
		try {
			ord.setStart_time(Timestamps.dateToStamp1(time+" 00:00:00"));
			ord.setEnd_time(Timestamps.dateToStamp1(time+" 23:59:59"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		Integer maxmoney = chanser.loanMaxMoney(companyId);//获取限额
		BigDecimal SumPaymoney = chanser.SumpayMoney(ord);//当天放款额度
		BigDecimal maxMon = new BigDecimal(maxmoney);
		
		if(SumPaymoney==null){
			SumPaymoney = new BigDecimal(0);
		}
		
		
		if(maxmoney != 0){
			Integer i = SumPaymoney.compareTo(maxMon);//i == -1 sumPaymoney 小于 maxMon   0  sumPaymoney 相等 maxMon   1  sumPaymoney 大于 maxMon
			if(i == 1 || i == 0){
				map1.put("msg", "今日放款已达限额,请明日再来");
				map1.put("Ncode", 0);
				map1.put("code", 0);
				return map1;
			}
		}
		
		map1.put("Ncode", 2000);
		if(ban.getTiedCardPhone() != null && ban.getBankcardName() != null && ban.getCstmrnm() != null && ban.getBankcardTypeName() != null
				&& ban.getTiedCardPhone() != "" && ban.getBankcardName() != "" && ban.getCstmrnm() != "" && ban.getBankcardTypeName() != ""){
		
    	
    	
		if(userId != null && TransAmt != null && companyId != null && lifeOfLoan != 0){
		
		
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, +1);
		date = calendar.getTime();
		int borrowNumberR = intOrderService.borrowNumber(userId,companyId); //用户还款次数
	    int	howManyTimesBorMoney = borrowNumberR+1;//第几次借款
	    String orderCreateTime = String.valueOf(System.currentTimeMillis());//订单生成时间戳
    	Integer riskmanagementFraction = 0;
    	Integer asc = intUserService.getRiskControlPoints(userId);//获取风控分数
    	if(asc != null){
    		riskmanagementFraction=asc;
    	}
    	String shouldReturned = getShouldReturned(lifeOfLoan-1);//应还日时间戳,因为借款当天也算一天，所以要减去一天
    	String borrowMoneyWay = "立即贷";//贷款方式
    	
    	int num = 0;
		try {
			num = intOrderService.setOrder(companyId,userId,orderNumber,orderCreateTime,lifeOfLoan,howManyTimesBorMoney,shouldReturned,riskmanagementFraction,borrowMoneyWay,ban.getBankcardName(),ban.getBankcardTypeName());
		} catch (Exception e) {
			redis.set("ChanpaySenduserId"+userId, String.valueOf(userId));
			map1.put("code", "203");
			map1.put("desc", "已放款,未保存");
			map1.put("Ncode", 0);
			return map1;
		}
		actualAmountReceived = new BigDecimal(TransAmt);
		Payment_record pay = new Payment_record();
		pay.setUserId(userId);
		pay.setDeleted("0");
		pay.setPaymentmoney(new BigDecimal(TransAmt));
		pay.setCardnumber(ban.getBankcardName());
    	if(num==1) {
    		int orderId = intOrderService.getOrderId(orderNumber);
	    	BigDecimal surplus_money = finalLine;
    		num = orderdetailsMapper.setororderdetails(orderId,finalLine,averageDailyInterest,totalInterest,platformServiceFee,actualAmountReceived,
	    			registeClient,sourceName,shouldTotalAmount,surplus_money);
	    	if(num==1) {
	    		if(paymentname.getLoanSource().equals("畅捷支付")){
	    			
	    		
				map1.put("code", 200);
				map1.put("Ncode", 2000);
				redis.delkey("ChanpaySenduserId"+userId);//删除字段
	    		map1.put("desc","订单已生成");
	    		map1.put("msg","订单生成成功");
    	
		    	Map<String, String> map = this.requestBaseParameter();
				map.put("TransCode", "T10000"); // 交易码
				map.put("OutTradeNo", orderNumber); // 商户网站唯一订单号
				map.put("CorpAcctNo", "");  //可空
				map.put("BusinessType", "0"); // 业务类型：0对私 1对公
				map.put("BankCommonName", ban.getBankcardTypeName()); // 通用银行名称
				map.put("BankCode", "");//对公必填
				map.put("AccountType", "00"); // 账户类型
				map.put("AcctNo", ChanPayUtil.encrypt(ban.getBankcardName(), BaseConstant.MERCHANT_PUBLIC_KEY, BaseConstant.CHARSET)); // 对手人账号(此处需要用真实的账号信息)
				map.put("AcctName", ChanPayUtil.encrypt(ban.getCstmrnm(), BaseConstant.MERCHANT_PUBLIC_KEY, BaseConstant.CHARSET)); // 对手人账户名称
				map.put("TransAmt", TransAmt);
				map.put("CorpPushUrl", "http://172.20.11.16");		
				map.put("PostScript", "放款");
				String rea = ChanPayUtil.sendPost(map, BaseConstant.CHARSET,
						BaseConstant.MERCHANT_PRIVATE_KEY);
				ReturnSend returnchanpay = JSON.parseObject(rea, ReturnSend.class);
    	
    	
    	
    	
    	/*                           获取银行卡信息         */
    	Bankcard bas = new Bankcard();
    	bas.setCompanyId(companyId);
		bas.setUserId(userId);
		pay.setPipelinenumber(returnchanpay.getPartnerId());
		String statu = returnchanpay.getAcceptStatus();
		if(statu.equals("S")){
			pay.setPaymentbtiao(paymentname.getLoanSource());
			pay.setStatus("支付成功");
			String pipelnen = "lsn_"+returnchanpay.getTradeDate()+returnchanpay.getTradeTime();
			pay.setPipelinenumber(pipelnen);
			pay.setOrderId(orderId);
			chanser.AddPayment_record(pay);
			map1.put("ShortReturn", returnchanpay);
			map1.put("code", 200);
			map1.put("msg", "放款成功");
			map1.put("Ncode", 2000);
			map1.put("desc", "借款成功");
		}else{
			String orderStatus = returnchanpay.getRetMsg();
			pay.setStatus("支付失败");
			chanser.AddPayment_record(pay);
			chanser.DeleteOrderNumber(orderNumber,orderStatus);
			map1.put("ShortReturn", returnchanpay);
			map1.put("code", 0);
			map1.put("msg", orderStatus);
			map1.put("Ncode", 0);
			map1.put("desc", "借款失败");
			map1.put("code", 0);
		}
		
    	}else{
    		
    	redis.delkey("ChanpaySenduserId"+userId);//删除字段
    	pay.setPaymentbtiao(paymentname.getLoanSource());
    	 Map<String, Object> mappam = newsim.Newpayment(new BigDecimal(TransAmt), orderNumber, userId, companyId);
    	 String msg = (String) mappam.get("msg");
    	 if(msg.equals("代付失败")){
    		String orderStatus = (String) mappam.get("msg");
 			chanser.DeleteOrderNumber(orderNumber,orderStatus);
 			mappam.put("code", 0);
 			mappam.put("msg", orderStatus);
 			mappam.put("Ncode", 0);
 			mappam.put("desc", "借款失败");
 			mappam.put("code", 0);
 			return map1;
 			
    		}else{
    			redis.set("payorderId", String.valueOf(orderId));
    			pay.setPaymentbtiao(paymentname.getLoanSource());
    			String pipelnen = "lsn_"+(String)mappam.get("tradeNo");
    			pay.setPipelinenumber(pipelnen);
    			pay.setOrderId(orderId);
    			pay.setStatus("支付失败");
    			int i = chanser.AddPayment_record(pay);
    			if(i == 1){
    				mappam.put("code", 200);
    				mappam.put("msg", "放款成功，可能需要几分钟到账");
    				mappam.put("Ncode", 2000);
    				mappam.put("desc", "借款成功");
    			}
    			return mappam;
    			
    		}
    	}
	    	}else {
	    		redis.delkey("ChanpaySenduserId"+userId);//删除字段
	    		map1.put("Ncode", 0);
				map1.put("code",405); 
				map1.put("desc","订单已生成");
	    		map1.put("msg","订单生成失败");
				
			}
		}
		
		}else{
			map1.put("msg", "userId,TransAmt,companyId,lifeOfLoan不能为空");
			map1.put("code", 406);
			map1.put("Ncode", 0);
		}
		}else{
			map1.put("msg", "数据异常");
			map1.put("code", 402);
			map1.put("Ncode", 0);
		}
		}else{
			map1.put("msg", "渠道关闭,请联系客服");
			map1.put("code", 407);
			map1.put("Ncode", 0);
		}
		}else{
			map1.put("msg", "您有订单未还清");
			map1.put("code", 403);
			map1.put("Ncode", 0);
		}
		}else{
			map1.put("msg", "金额异常");
			map1.put("code", 401);
			map1.put("Ncode", 0);
		}
		}
		
			}else{
				map1.put("code", "0");
				map1.put("msg", "银行卡信息不完整");
				map1.put("Ncode", 0);
			}
		return map1;
	}
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 查询订单
	 * @param billId  订单号
	 * @return
	 */
	@ResponseBody
	@RequestMapping("SelectHkSta")
	public Map<String, String> query(String billId){
		Map<String, String> map=new HashMap<String, String>();   
		RedisClientUtil redis = new RedisClientUtil();
		Map<String, String> payParams=new HashMap<String, String>();        
        payParams.put("method","zpay.trade.query");
        payParams.put("version","1.0");
        payParams.put("mchId",ZpayConfig.MERCHANT_NO);
        payParams.put("orderId", billId);
        String resultSign= SignUtils.getSign(payParams,ZpayConfig.MD5_KEY);
        payParams.put("sign",resultSign);
        
        try {
			HttpClient httpClient=new HttpClient(ZpayConfig.GATEWAY_URL, 30000, 30000);
			String resp = httpClient.send(payParams, "utf-8");
			System.out.println(resp);
			JSONObject jsonObject=JSONObject.parseObject(resp);
			String tradeNo = jsonObject.getString("tradeNo");
			String status = jsonObject.getString("status");
			String msg = jsonObject.getString("msg");
			
			String biaoshiid = redis.get("userId"+tradeNo);
		     String orderIds = redis.get("orderId"+biaoshiid);//获取订单编号
			 Orders order = newsim.getOrders(orderIds);
	        if(StringUtils.isNotBlank(resp)){
	        	String code=jsonObject.getString("code");
	        	if(code.equals("SUCCESS")){
	        		 if(order.getOrderStatus().equals("3")){
					 }else{
						 Repayment repay = new Repayment();
					     Integer oid = newsim.getOrderId(orderIds);
					     repay.setOrderid(oid);
					     repay.setPipelinenumber("Rsn_"+tradeNo);
				    	 if(status.equals("SUCCESS")){
				    		 Integer updateId = newsim.UpdateRepayment(repay);
				    		 if(updateId != null){
				    			 Orders ord = new Orders();
				    	    	 ord.setOrderNumber(orderIds);
				    	    	 servie.UpdateOrders(ord);
				    	    	 map.put("code", "200");
					    		 map.put("Ncode", "2000");
					    		 map.put("msg", msg);
					    		 return map;
				    		 }
				    	 }else if(status.equals("PAYERROR")){
				    		 map.put("code", "0");
				    		 map.put("Ncode", "0");
				    		 map.put("msg", msg);
				    		 return map;
				    	 }
	        		}
	        	}else{
	        		 map.put("code", "0");
		    		 map.put("Ncode", "0");
		    		 map.put("msg", msg);
		    		 return map;
	        	}
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
        return map;
	}	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 
	 * @param MerUserId		//用户ID
	 * @param BkAcctNo		//卡号
	 * @param IDNo			//身份证号
	 * @param CstmrNm		//持卡人姓名
	 * @param MobNo			//银行预留手机号
	 * @return
	 */
	@ResponseBody
	@RequestMapping("Changebank")
	public Map<String, Object> changebank(Integer MerUserId,String BkAcctNo,String IDNo,String CstmrNm,String MobNo,Integer companyId,String bankcardTypeName,String appNumber,String codes){
		
		Thirdparty_interface paymentname = newsim.SelectPaymentName(companyId);//获取系统设置的 放款名称   和  还款名称
		Map<String, Object> map = new HashMap<String, Object>();
		if(MerUserId != null && BkAcctNo != null && MobNo != null){
			
			Integer id = servie.SelectUserId(MerUserId);
			if(id != null){
				map.put("Ncode", 0);
				map.put("code", "0");
				map.put("msg", "已绑卡");
			}else{
				
		Bankcard bank = new Bankcard();
		bank.setUserId(MerUserId);//登陆人ID
		bank.setBankcardName(BkAcctNo);//卡号
		
		bank.setTiedCardPhone(MobNo);//手机号
		Integer SeleId = servie.SelectTrxId(bank);//查询银行卡号
		if(SeleId == null ){
			if(paymentname.getLoanSource().equals("钊力")){
				Map<String, Object> maps = servie.RenzhenId(BkAcctNo, MobNo, IDNo, CstmrNm, bankcardTypeName, MerUserId, companyId,appNumber,codes);
				
				return maps;
			}else{
				
			
			//Integer trxId = chanser.SelectTrxId(bank);
			Map<String, String> origMap = new HashMap<String, String>();
			origMap = setCommonMap(origMap);
			// 2.1 鉴权绑卡 api 业务参数
			String TrxId = ChanPayUtil.generateOutTradeNo();
			origMap.put("Service", "nmg_biz_api_auth_req");// 鉴权绑卡的接口名(商户采集方式)
			origMap.put("TrxId", TrxId);// 订单号
			origMap.put("ExpiredTime", "90m");// 订单有效期
			origMap.put("MerUserId", String.valueOf(MerUserId));// 用户标识（测试时需要替换一个新的meruserid）
			origMap.put("BkAcctTp", "01");// 卡类型（00 – 银行贷记卡;01 – 银行借记卡;）
			origMap.put("BkAcctNo", this.encrypt(BkAcctNo, MERCHANT_PUBLIC_KEY, charset));// 卡号
			origMap.put("IDTp", "01");// 证件类型 （目前只支持身份证 01：身份证）
			origMap.put("IDNo", this.encrypt(IDNo, MERCHANT_PUBLIC_KEY, charset));// 证件号
			origMap.put("CstmrNm", this.encrypt(CstmrNm, MERCHANT_PUBLIC_KEY, charset));// 持卡人姓名
			origMap.put("MobNo", this.encrypt(MobNo, MERCHANT_PUBLIC_KEY, charset));// 银行预留手机号
			
			origMap.put("NotifyUrl", "http://dev.chanpay.com/receive.php");// 异步通知url
			origMap.put("SmsFlag", "1");
			origMap.put("Extension", "");
			String result = "";
			try {
				String urlStr = "https://pay.chanpay.com/mag-unify/gateway/receiveOrder.do?";// 测试环境地址，上生产后需要替换该地址
				result = buildRequest(origMap, "RSA", ChanpayQuickCollection.MERCHANT_PRIVATE_KEY, charset,urlStr);
				ReturnChanpay retu = JSON.parseObject(result,ReturnChanpay.class);
				String code = retu.getAcceptStatus();
				if(code.equals("S")){
					
						map.put("OriAuthTrxId", TrxId);
						map.put("code", "200");
						map.put("Ncode", 2000);
						map.put("desc", "插入成功");
						map.put("ReturnChanpay", retu);
						map.put("msg", retu.getRetMsg());
						
				}else{
					map.put("Ncode", 0);
					map.put("code", 0);
					map.put("ReturnChanpay", retu);
					map.put("msg", retu.getRetMsg());
				}
				
				
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println(result);
			//this.gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
			}
		}else{
			map.put("Ncode", 0);
			map.put("code", "0");
			map.put("ReturnChanpay", "此卡已绑定");
			map.put("msg", "此卡已绑定");
		}
		}
		}else{
			map.put("Ncode", 0);
			map.put("code", "0");
			map.put("ReturnChanpay", "MerUserId,BkAcctNo,IDNo,CstmrNm,MobNo,bankcardTypeId不能未空");
			map.put("msg", "MerUserId,BkAcctNo,IDNo,CstmrNm,MobNo,bankcardTypeId不能未空");
		}
		return map;
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 建立请求，以模拟远程HTTP的POST请求方式构造并获取钱包的处理结果
	 * 如果接口中没有上传文件参数，那么strParaFileName与strFilePath设置为空值 如：buildRequest("",
	 * "",sParaTemp)
	 *
	 * @param strParaFileName
	 *            文件类型的参数名
	 * @param strFilePath
	 *            文件路径
	 * @param sParaTemp
	 *            请求参数数组
	 * @return 钱包处理结果
	 * @throws Exception
	 */
	public static String buildRequest(Map<String, String> sParaTemp, String signType, String key, String inputCharset,
			String gatewayUrl) throws Exception {
		// 待请求参数数组
		Map<String, String> sPara = buildRequestPara(sParaTemp, signType, key, inputCharset);
		HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();
		HttpRequest request = new HttpRequest(HttpResultType.BYTES);
		// 设置编码集
		request.setCharset(inputCharset);
		request.setMethod(HttpRequest.METHOD_POST);
		request.setParameters(generatNameValuePair(createLinkRequestParas(sPara), inputCharset));
		request.setUrl(gatewayUrl);
		System.out.println(gatewayUrl+""+httpProtocolHandler.toString(generatNameValuePair(createLinkRequestParas(sPara), inputCharset)));
		if(sParaTemp.get("Service").equalsIgnoreCase("nmg_quick_onekeypay")||sParaTemp.get("Service").equalsIgnoreCase("nmg_nquick_onekeypay")){
			return null;
		}
		
		// 返回结果处理
		HttpResponse response = httpProtocolHandler.execute(request, null, null);
		if (response == null) {
			return null;
		}
		String strResult = response.getStringResult();
		
		return strResult;
	}
	
	
	
	
	
	/**
	 * 生成要请求参数数组
	 * 
	 * @param sParaTemp
	 *            请求前的参数数组
	 * @param signType
	 *            RSA
	 * @param key
	 *            商户自己生成的商户私钥
	 * @param inputCharset
	 *            UTF-8
	 * @return 要请求的参数数组
	 * @throws Exception
	 */
	public static Map<String, String> buildRequestPara(Map<String, String> sParaTemp, String signType, String key,
			String inputCharset) throws Exception {
		// 除去数组中的空值和签名参数
		Map<String, String> sPara = paraFilter(sParaTemp);
		// 生成签名结果
		String mysign = "";
		if ("MD5".equalsIgnoreCase(signType)) {
			mysign = buildRequestByMD5(sPara, key, inputCharset);
		} else if ("RSA".equalsIgnoreCase(signType)) {
			mysign = buildRequestByRSA(sPara, key, inputCharset);
		}
		// 签名结果与签名方式加入请求提交参数组中
		System.out.println("Sign:" + mysign);
		sPara.put("Sign", mysign);
		sPara.put("SignType", signType);

		return sPara;
	}
	
	
	
	
	/**
	 * 生成MD5签名结果
	 *
	 * @param sPara
	 *            要签名的数组
	 * @return 签名结果字符串
	 */
	public static String buildRequestByMD5(Map<String, String> sPara, String key, String inputCharset)
			throws Exception {
		String prestr = createLinkString(sPara, false); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
		String mysign = "";
		mysign = MD5.sign(prestr, key, inputCharset);
		return mysign;
	}
	
	
	
	
	
	
	/**
	 * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 *
	 * @param params
	 *            需要排序并参与字符拼接的参数组
	 * @param encode
	 *            是否需要urlEncode
	 * @return 拼接后字符串
	 */
	public static String createLinkString(Map<String, String> params, boolean encode) {

		params = paraFilter(params);

		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);

		String prestr = "";

		String charset = params.get("InputCharset");
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);
			if (encode) {
				try {
					value = URLEncoder.encode(value, charset);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}

			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}

		return prestr;
	}
	
	
	
	
	
	/**
	 * 除去数组中的空值和签名参数
	 *
	 * @param sArray
	 *            签名参数组
	 * @return 去掉空值与签名参数后的新签名参数组
	 */
	public static Map<String, String> paraFilter(Map<String, String> sArray) {

		Map<String, String> result = new HashMap<String, String>();

		if (sArray == null || sArray.size() <= 0) {
			return result;
		}

		for (String key : sArray.keySet()) {
			String value = sArray.get(key);
			if (value == null || value.equals("") || key.equalsIgnoreCase("Sign") || key.equalsIgnoreCase("SignType") || key.equalsIgnoreCase("sign_type")) {
				continue;
			}
			result.put(key, value);
		}

		return result;
	}
	
	
	
	
	
	
	
	
	
	/**
	 * 生成RSA签名结果
	 *
	 * @param sPara
	 *            要签名的数组
	 * @return 签名结果字符串
	 */
	public static String buildRequestByRSA(Map<String, String> sPara, String privateKey, String inputCharset)
			throws Exception {
		String prestr = createLinkString(sPara, false); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
		String mysign = "";
		mysign = RSA.sign(prestr, privateKey, inputCharset);
		return mysign;
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 *
	 * @param params
	 *            需要排序并参与字符拼接的参数组
	 * @param encode
	 *            是否需要urlEncode
	 * @return 拼接后字符串
	 */
	public static Map<String, String> createLinkRequestParas(Map<String, String> params) {
		Map<String, String> encodeParamsValueMap = new HashMap<String, String>();
		List<String> keys = new ArrayList<String>(params.keySet());
		String charset = params.get("InputCharset");
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value;
			try {
				value = URLEncoder.encode(params.get(key), charset);
				encodeParamsValueMap.put(key, value);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		return encodeParamsValueMap;
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * MAP类型数组转换成NameValuePair类型
	 *
	 * @param properties
	 *            MAP类型数组
	 * @return NameValuePair类型数组
	 */
	private static NameValuePair[] generatNameValuePair(Map<String, String> properties, String charset)
			throws Exception {
		NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
		int i = 0;
		for (Map.Entry<String, String> entry : properties.entrySet()) {
			// nameValuePair[i++] = new NameValuePair(entry.getKey(),
			// URLEncoder.encode(entry.getValue(),charset));
			nameValuePair[i++] = new NameValuePair(entry.getKey(), entry.getValue());
		}
		return nameValuePair;
	}
	
	
	
	/**
	 * 加密，部分接口，有参数需要加密
	 * 
	 * @param src
	 *            原值
	 * @param publicKey
	 *            畅捷支付发送的平台公钥
	 * @param charset
	 *            UTF-8
	 * @return RSA加密后的密文
	 */
	@ResponseBody
	@RequestMapping("publicKeyPost")
	private String encrypt(String src, String publicKey, String charset) {
		try {
			byte[] bytes = RSA.encryptByPublicKey(src.getBytes(charset), publicKey);
			return Base64.encodeBase64String(bytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	/**
	 * 公共请求参数设置
	 */
	public Map<String, String> setCommonMap(Map<String, String> origMap) {
		// 2.1 基本参数
		origMap.put("Version", "1.0");
		origMap.put("PartnerId", "200005640044");//生产环境测试商户号
		
		origMap.put("InputCharset", charset);// 字符集
		origMap.put("TradeDate", "20170612");// 商户请求时间
		origMap.put("TradeTime", "141200");// 商户请求时间
		origMap.put("Memo", null);
		return origMap;
	}
	
	
	
	public static String getShouldReturned(int day) {
	    Date date = new Date();//取时间 
	    Calendar calendar  =   Calendar.getInstance();		 
	    calendar.setTime(date); //需要将date数据转移到Calender对象中操作
	    calendar.add(Calendar.DATE, day);//把日期往后增加n天.正数往后推,负数往前移动 
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
	    date=calendar.getTime();  //这个时间就是日期往后推一天的结果 
	    System.out.println(sdf.format(date));
	    try {
			date =sdf.parse(sdf.format(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    String shouldReturned= (date.getTime()+86399000)+"";//应还日时间戳
	    return shouldReturned;
   }
	
	
	
	/**
	 * 请求
	* @Title: requestBaseParam 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @return Map<String,String>    返回类型 
	* @throws
	 */
	public Map<String,String> requestBaseParameter(){
		Map<String, String> origMap = new HashMap<String, String>();
		// 2.1 基本参数
		origMap.put(BaseConstant.SERVICE, "cjt_dsf");// 接口名
		origMap.put(BaseConstant.VERSION, "1.0");
		origMap.put(BaseConstant.PARTNER_ID, "200005900369"); //生产环境测试商户号
		origMap.put(BaseConstant.TRADE_DATE, BaseConstant.DATE);
		origMap.put(BaseConstant.TRADE_TIME, BaseConstant.TIME);
		origMap.put(BaseConstant.INPUT_CHARSET, BaseConstant.CHARSET);// 字符集
		origMap.put(BaseConstant.MEMO, "");// 备注
		return origMap;
		
	}
	
	
	
	public static void main(String[] args) {
		
		NewPaymentController newp = new NewPaymentController();
		newp.changebank(131, "6217000360005556842", "142727199807191015", "刘晓云", "18235980719", 3, "建设银行", "米多宝", "eb8f8c9fec27fa660d5351d3925fa803");
	}
}
