package com.zhita.chanpayutil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * <p>
 * 定义请求的参数名称
 * </p>
 * 
 * @author yanghta@chenjet.com
 * @version $Id: BaseConstant.java, v 0.1 2017-05-03 下午5:25:44
 */
public class BaseConstant {

	// 基础参数
	public static final String SERVICE = "Service";
	public static final String VERSION = "Version";
	public static final String PARTNER_ID = "PartnerId";
	// 日期
	public static final String TRADE_DATE = "TradeDate";
	public static final String TRADE_TIME = "TradeTime";
	public static final String INPUT_CHARSET = "InputCharset";
	public static final String SIGN = "Sign";
	public static final String SIGN_TYPE = "SignType";
	public static final String MEMO = "Memo";

	public static final String MD5 = "MD5";
	public static final String RSA = "RSA";
	
	
	/**
	 * 畅捷支付平台公钥
	 */
	//生产环境
	//public static final String MERCHANT_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDPq3oXX5aFeBQGf3Ag/86zNu0VICXmkof85r+DDL46w3vHcTnkEWVbp9DaDurcF7DMctzJngO0u9OG1cb4mn+Pn/uNC1fp7S4JH4xtwST6jFgHtXcTG9uewWFYWKw/8b3zf4fXyRuI/2ekeLSstftqnMQdenVP7XCxMuEnnmM1RwIDAQAB";//生产环境公钥
		                                                
		
	//生产环境 测试商户号私钥
	//public static final String MERCHANT_PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALmnE/9rQUoiSGubWg1B7nomyBMJ1xvLqAbiSa91dYAERUig2dG9xsSSjdvcPuN0ghoOCfPRp++NsjQ9YTzrp3Mla7DwNcWsr9Q3J5De9LKR2BaOs8GH6tlGMrNTZeciKASEYftH3B1a7lCmNUNzsQwB5stmZBQZUOs4oHPo4pTjAgMBAAECgYA5iW1qMHxolF1rdBchmrhlBRXmyJrxE1n84C7EARwsU6aBTtCS70Tl8SGj7QtUhOw5VPaCqQxp8rky9X7oWYRoZQHuub6jvvgmCBQXoBDeUaaO4JQ+7KDYP1OpR+e0s0D0fTzL2yujChkF5LyngF2tdwi2272foAaIQgqVLFo9MQJBAOPwNqane7bXiWCuyKgcEeIA8iKGNSXZqTiF+Yf1cW/CaudXdVyV1kFdnlEqAws9fwOEbtSmr2qhdgEUjMxHVQkCQQDQgix8oqAmxmUGqwAMSfIKCUY8xc+ghd1UxmrsnPtpzVjNG/QKaa4D0pC1Li/YmX2u9tAd+oFpTF/LqH+iN2GLAkA20D1UNlJ51bsjlOSMCLQENVYFF2EQiRc4kH9BQrTUu1wZ5d5DNYUgkvLPcdrpiRBSODauzKbCbbGC8P4q4byBAkEApFrnzDq4eLgL7EygVg93nVegKMlYF1VNmMRqhiZbuxNlexActpSE2XiCHn7QsjCTNHZSqD7NAX51SGiCaxCybwJBALZSXAWzQxinkGJXYvTyvktanGqAkHzn1pSf+XbGYtokryVANhuf+YTMm/lYedq8w9um8byZhGl53ph33xn+U0Y=";
		
	
	
	/**
	 * 畅捷支付平台公钥
	 */
	public static final String MERCHANT_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDPq3oXX5aFeBQGf3Ag/86zNu0VICXmkof85r+DDL46w3vHcTnkEWVbp9DaDurcF7DMctzJngO0u9OG1cb4mn+Pn/uNC1fp7S4JH4xtwST6jFgHtXcTG9uewWFYWKw/8b3zf4fXyRuI/2ekeLSstftqnMQdenVP7XCxMuEnnmM1RwIDAQAB";//生产环境公钥

	/**
	 * 生产环境 测试商户号私钥
	 */
	public static final String MERCHANT_PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOn6RSGNCtd+eo76lWte/CAjVyN0IKV9iTkf8voWmQoUShH61JKUro06RshaJwHYmqJiQUDp0fZR2GogwycqzwB2gTkodoa7vEC9bFb2Vih/2m3854beg+O94Nn8N3qc/1biTWMQEi1zRKey3pJ8a5VkP3pgjgc9R9lsMU1DrBSNAgMBAAECgYBtLsJPs7dZc8XRLpHHNt3ESXmCK9CIKRp9s7D4js7/axRda4UCmasScB1TpO4K6H7QQ7Vbi0DVBsBMVdl7T3ikRDt8VMlisOY/xhU3MHmKNha+v9Ry3H5/mr4uca7RL/RJ62QDRtdNzHevE8AyH7dFCBGDWSZiqqtgX2qvXzsiQQJBAPcxgx9EV+wxFlSku/yGbZO0vdRBdrIwqbdO7ONknrNITQraANNoHoy8nmV9BAS9V5LKljuewmBhqsgc6jx1pTkCQQDyUDmxFf5w6SHVrWTI89p3naL6ZckLt6B5vTo6WNqM5wWO8TXHt6n7fx3h/PBbpKAZOXZhBgGI9Hzee7G/yZ31AkA1AGTA+4JunkfPdlLoXlp4ZTSeojq1EkIRj9azGwv8HPCH9x5eNeD3BCpiUF/gNEjmrJFxzySWTdq0dZ6zv/XhAkEAv4EeB1G9lNj7b5epJpRq3cxM6R6WhNcsUep2YXSlzWQr5WvHOD90bRpqsJcqExOJoEXkxAtuHSI0qdUShuO+HQJBAJ3lqyDuDTEn4trLxE+CUpndq5qYjMciY+xjO3saezFozyiFZoCshh1Yq9puhoqcmCNHFWd0+1Qimb8SNuS9cOY=";

	
	
	
	
	
	
	
	/**
	 * 编码类型
	 */
	public static final String CHARSET = "UTF-8";
	public final static String GATEWAY_URL = "https://pay.chanpay.com/mag-unify/gateway/receiveOrder.do";
	public final static String BATCH_FILE_GATEWAY_URL = "https:/pay.chanpay.com/mag-unify/gateway/batchOrder.do";
	public static String DATE = new SimpleDateFormat("yyyyMMdd").format(new Date());
	public static String TIME = new SimpleDateFormat("HHmmss").format(new Date());

}
