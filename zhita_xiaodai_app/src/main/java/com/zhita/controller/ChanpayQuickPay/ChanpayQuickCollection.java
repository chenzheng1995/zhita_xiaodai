package com.zhita.controller.chanpayQuickPay;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.NameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.zhita.chanpayutil.ChanPayUtil;
import com.zhita.model.manage.Bankcard;
import com.zhita.model.manage.Deferred;
import com.zhita.model.manage.Orders;
import com.zhita.model.manage.Repayment;
import com.zhita.model.manage.ReturnChanpay;
import com.zhita.service.manage.Statistic.Statisticsservice;
import com.zhita.util.HttpProtocolHandler;
import com.zhita.util.HttpRequest;
import com.zhita.util.HttpResponse;
import com.zhita.util.HttpResultType;
import com.zhita.util.MD5;
import com.zhita.util.RSA;


@Controller
@RequestMapping("/Chanpay")
public class ChanpayQuickCollection {
	
	
	@Autowired
	private Statisticsservice servie;
	
	
	
	/**
	 * 畅捷支付平台公钥
	 */
	private static String MERCHANT_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDPq3oXX5aFeBQGf3Ag/86zNu0VICXmkof85r+DDL46w3vHcTnkEWVbp9DaDurcF7DMctzJngO0u9OG1cb4mn+Pn/uNC1fp7S4JH4xtwST6jFgHtXcTG9uewWFYWKw/8b3zf4fXyRuI/2ekeLSstftqnMQdenVP7XCxMuEnnmM1RwIDAQAB";//生产环境公钥

	/**
	 * 生产环境 测试商户号私钥
	 */
	private static String MERCHANT_PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALmnE/9rQUoiSGubWg1B7nomyBMJ1xvLqAbiSa91dYAERUig2dG9xsSSjdvcPuN0ghoOCfPRp++NsjQ9YTzrp3Mla7DwNcWsr9Q3J5De9LKR2BaOs8GH6tlGMrNTZeciKASEYftH3B1a7lCmNUNzsQwB5stmZBQZUOs4oHPo4pTjAgMBAAECgYA5iW1qMHxolF1rdBchmrhlBRXmyJrxE1n84C7EARwsU6aBTtCS70Tl8SGj7QtUhOw5VPaCqQxp8rky9X7oWYRoZQHuub6jvvgmCBQXoBDeUaaO4JQ+7KDYP1OpR+e0s0D0fTzL2yujChkF5LyngF2tdwi2272foAaIQgqVLFo9MQJBAOPwNqane7bXiWCuyKgcEeIA8iKGNSXZqTiF+Yf1cW/CaudXdVyV1kFdnlEqAws9fwOEbtSmr2qhdgEUjMxHVQkCQQDQgix8oqAmxmUGqwAMSfIKCUY8xc+ghd1UxmrsnPtpzVjNG/QKaa4D0pC1Li/YmX2u9tAd+oFpTF/LqH+iN2GLAkA20D1UNlJ51bsjlOSMCLQENVYFF2EQiRc4kH9BQrTUu1wZ5d5DNYUgkvLPcdrpiRBSODauzKbCbbGC8P4q4byBAkEApFrnzDq4eLgL7EygVg93nVegKMlYF1VNmMRqhiZbuxNlexActpSE2XiCHn7QsjCTNHZSqD7NAX51SGiCaxCybwJBALZSXAWzQxinkGJXYvTyvktanGqAkHzn1pSf+XbGYtokryVANhuf+YTMm/lYedq8w9um8byZhGl53ph33xn+U0Y=";

	/**
	 * 编码类型
	 */
	private static String charset = "UTF-8";
	
	
//	@Resource
//	private Chanpayservice chanpayservice;
//	
	
	
	

	
	
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
	public static Object buildRequests(Map<String, String> sParaTemp,
			String signType, String key, String inputCharset, String gatewayUrl)
			throws Exception {
		// 待请求参数数组
		Map<String, String> sPara = buildRequestPara(sParaTemp, signType, key,
				inputCharset);
		HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler
				.getInstance();
		HttpRequest request = new HttpRequest(HttpResultType.BYTES);
		// 设置编码集
		request.setCharset(inputCharset);
		request.setMethod(HttpRequest.METHOD_POST);
		request.setParameters(generatNameValuePair(
				createLinkRequestParas(sPara), inputCharset));
		request.setUrl(gatewayUrl);
		HttpResponse response = httpProtocolHandler
				.execute(request, null, null);
		if (response == null) {
			return null;
		}

		byte[] byteResult = response.getByteResult();
		if (byteResult.length > 1024) {
			return byteResult;
		} else {
			return response.getStringResult();
		}

	}
	
	
	
	
	
	
	


	
	
	
	/**
	 * 向测试服务器发送post请求
	 * 
	 * @param origMap
	 *            参数map
	 * @param charset
	 *            编码字符集
	 * @param MERCHANT_PRIVATE_KEY
	 *            私钥
	 */
	public Object gatewayPosts(Map<String, String> origMap, String charset,
			String MERCHANT_PRIVATE_KEY) {
		try {
			String urlStr = "https://pay.chanpay.com/mag-unify/gateway/receiveOrder.do?";
			// String urlStr =
			// "https://cpay.chanpay.com/mag-unify/gateway/receiveOrder.do?";
			Map<String, String> sPara = buildRequestPara(origMap, "RSA",
					MERCHANT_PRIVATE_KEY, charset);
			System.out.println(urlStr + createLinkString(sPara, true));
			Object obj = buildRequests(origMap, "RSA", MERCHANT_PRIVATE_KEY,
					charset, urlStr);
			System.out.println(obj);
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	
	
	
	/**
	 * 向测试服务器发送post请求
	 * 
	 * @param origMap
	 *            参数map
	 * @param charset
	 *            编码字符集
	 * @param MERCHANT_PRIVATE_KEY
	 *            私钥
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("SendPost")
	public void gatewayPost(Map<String, String> origMap, String charset, String MERCHANT_PRIVATE_KEY)  {
		String result="";
		try {
			String urlStr = "https://pay.chanpay.com/mag-unify/gateway/receiveOrder.do?";// 测试环境地址，上生产后需要替换该地址
			Map<String, String> sPara = buildRequestPara(origMap, "RSA", MERCHANT_PRIVATE_KEY, charset);
				result = buildRequest(origMap, "RSA", ChanpayQuickCollection.MERCHANT_PRIVATE_KEY, charset,
						urlStr);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(result);
		
		
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
	
	
	
	
	/**
	 * 2.1 鉴权绑卡  nmg_api_auth_req；商户采集方式，
	 */
	@ResponseBody
	@RequestMapping("nmg_api_auth_req")
	@Transactional
	public Map<String, Object> nmg_biz_api_auth_req(Integer MerUserId,String BkAcctNo,String IDNo,String CstmrNm,String MobNo,Integer bankcardTypeId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(MerUserId != null && BkAcctNo != null && IDNo != null && CstmrNm != null && MobNo != null && bankcardTypeId != null){
			System.out.println(MerUserId);
			System.out.println("银行卡:"+bankcardTypeId);
			Integer id = servie.SelectUserId(MerUserId);
			if(id != null){
				map.put("code", "0");
				map.put("desc", "已绑卡");
			}else{
				
			
		Bankcard bank = new Bankcard();
		bank.setAttestationStatus("0");
		bank.setUserId(MerUserId);//登陆人ID
		bank.setBankcardTypeId(bankcardTypeId);//银行卡类型
		bank.setBankcardName(BkAcctNo);//卡号
		bank.setTiedCardPhone(MobNo);//手机号
		bank.setDeleted("0");
		bank.setIDcardnumber(IDNo);//身份证号
		bank.setCstmrnm(CstmrNm);//持卡人姓名
		Integer SeleId = servie.SelectTrxId(bank);//查询银行卡号
		if(SeleId == null ){
		
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
					map.put("Ncode", 2000);
					Integer a = servie.AddBankcard(bank);
					if(a!=null){
						map.put("OriAuthTrxId", TrxId);
						map.put("code", "200");
						map.put("desc", "插入成功");
						map.put("ReturnChanpay", retu);
					}else{
						map.put("OriAuthTrxId", TrxId);
						map.put("code", "0");
						map.put("desc", "插入失败");
						map.put("ReturnChanpay", retu);
					}
				}else{
					map.put("Ncode", 2000);
					map.put("ReturnChanpay", retu);
				}
				
				
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println(result);
			//this.gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
		
		}else{
			map.put("Ncode", 0);
			map.put("code", "0");
			map.put("ReturnChanpay", "此卡已绑定");
		}
		}
		}else{
			map.put("Ncode", 0);
			map.put("code", "0");
			map.put("ReturnChanpay", "MerUserId,BkAcctNo,IDNo,CstmrNm,MobNo,bankcardTypeId不能未空");
		}
		return map;
	}

	/**
	 * 2.2 鉴权绑卡 nmg_page_api_auth_req；前台模式
	 */
	@ResponseBody
	@RequestMapping("nmg_page_api_auth_req")
	public Map<String, Object> nmg_page_api_auth_req() {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> origMap = new HashMap<String, String>();
		origMap = setCommonMap(origMap);
		// 2.1 鉴权绑卡 api 业务参数
		origMap.put("Service", "nmg_page_api_auth_req");// 鉴权绑卡的接口名(前台模式)；
		//String trxId = "201756796880882";
		String trxId = Long.toString(System.currentTimeMillis());
		
		origMap.put("TrxId", trxId);// 订单号
		origMap.put("ExpiredTime", "90m");// 订单有效期
		origMap.put("MerUserId", "zyr003");// 用户标识（测试时需要替换一个新的meruserid）
		origMap.put("NotifyUrl", "http://dev.chanpay.com/receive.php");// 异步通知url
		origMap.put("ReturnUrl", "http://www.baidu.com");// 回跳地址，可空
		String result = "";
		try {
			String urlStr = "https://pay.chanpay.com/mag-unify/gateway/receiveOrder.do?";// 测试环境地址，上生产后需要替换该地址
			result = buildRequest(origMap, "RSA", ChanpayQuickCollection.MERCHANT_PRIVATE_KEY, charset,urlStr);
			ReturnChanpay retu = JSON.parseObject(result,ReturnChanpay.class);
			map.put("ReturnChanpay", retu);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(result);
		//this.gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
		return map;
	}

	/**
	 * 2.3 鉴权绑卡确认 api nmg_api_auth_sms
	 */
	@ResponseBody
	@RequestMapping("nmg_api_auth_sms")
	public Map<String, Object> nmg_api_auth_sms(String oriAuthTrxId,String SmsCode,Integer userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> origMap = new HashMap<String, String>();
		System.out.println(oriAuthTrxId+"AAAAAAA"+SmsCode);
		if(oriAuthTrxId != null && SmsCode != null && oriAuthTrxId != ""){
			System.out.println("数据:"+oriAuthTrxId);
			// 2.1 基本参数
			origMap = setCommonMap(origMap);
			origMap.put("Service", "nmg_api_auth_sms");// 鉴权绑卡确认的接口名
			// 2.1 鉴权绑卡  业务参数
			String trxId = Long.toString(System.currentTimeMillis());	
			origMap.put("TrxId", trxId);// 订单号
			origMap.put("OriAuthTrxId", oriAuthTrxId);// 原鉴权绑卡订单号
			origMap.put("SmsCode", SmsCode);// 鉴权短信验证码
			origMap.put("NotifyUrl", "http://dev.chanpay.com/receive.php");// 异步通知地址
			String result = "";
			try {                                                                                                                                                                                             
				String urlStr = "https://pay.chanpay.com/mag-unify/gateway/receiveOrder.do?";// 测试环境地址，上生产后需要替换该地址
				result = buildRequest(origMap, "RSA", ChanpayQuickCollection.MERCHANT_PRIVATE_KEY, charset,
							urlStr);
				ReturnChanpay retu = JSON.parseObject(result,ReturnChanpay.class);
				String ssa = retu.getAcceptStatus();
				if(ssa.equals("S")){
					map.put("Ncode", 2000);
				
				Integer a = servie.UpdateChanpay(userId);
				if(a!=null){
					
					map.put("code", "200");
					map.put("ReturnChanpay", retu);
					map.put("desc", "认证成功");
					map.put("desc", "插入成功");
					
				}else{
					
					servie.deleteBank(userId);
					map.put("code", "0");
					map.put("ReturnChanpay", retu);
					map.put("desc", "认证失败");
				}
				
				}else{
					
				}
				
				} catch (Exception e) {
					e.printStackTrace();
				}
		}else{
			map.put("code", 0);
			map.put("Ncode", 0);
			map.put("desc", "OriAuthTrxId，SmsCode不能为空");
		}
		
		return map;
	}
	
	
	
	
	
	/**
	 * 20190805201545678123132
	 * 2.4 支付请求接口 api nmg_biz_api_quick_payment6217000360005556842
	 */
	@ResponseBody
	@RequestMapping("nmg_biz_api_quick_payment")
	public Map<String, Object> nmg_biz_api_quick_payment(String TrxId,String ordrName,String MerUserId,String CardBegin,String CardEnd,String TrxAmt) {
		Map<String, Object> map = new HashMap<String, Object>();	
		if(TrxId != null && ordrName != null && MerUserId != null && CardBegin != null && CardEnd != null && TrxAmt != null){
		Map<String, String> origMap = new HashMap<String, String>();
		// 2.1 基本参数 
		origMap = setCommonMap(origMap);
		origMap.put("Service", "nmg_biz_api_quick_payment");// 支付的接口名
		Repayment repay = new Repayment();//还账记录表
		repay.setThirdparty_id(1);
		repay.setOrderNumber(TrxId);
		repay.setPipelinenumber(TrxId);
		 BigDecimal bd=new BigDecimal(TrxAmt);   
		repay.setRepaymentMoney(bd);
		origMap.put("TrxId", ChanPayUtil.generateOutTradeNo());// 订单号
		origMap.put("OrdrName", ordrName);// 商品名称
		origMap.put("MerUserId", MerUserId);// 用户标识（测试时需要替换一个新的meruserid）
		origMap.put("SellerId", "200005640044");// 子账户号
		origMap.put("SubMerchantNo", "200005640044");// 子商户号
		origMap.put("ExpiredTime", "40m");// 订单有效期
		origMap.put("CardBegin", CardBegin);// 卡号前6位
		origMap.put("CardEnd", CardEnd);// 卡号后4位
		origMap.put("TrxAmt", TrxAmt);// 交易金额
		origMap.put("TradeType", "11");// 交易类型
		origMap.put("SmsFlag", "1");
		String result = "";
		try {
			String urlStr = "https://pay.chanpay.com/mag-unify/gateway/receiveOrder.do?";// 测试环境地址，上生产后需要替换该地址
				result = buildRequest(origMap, "RSA", ChanpayQuickCollection.MERCHANT_PRIVATE_KEY, charset,
						urlStr);
			ZhifuAcceptStatus retu = JSON.parseObject(result,ZhifuAcceptStatus.class);
			System.out.println("数据:"+retu.getTrxId());
			String pipelinenu = "Rsn_"+retu.getTrxId();
			repay.setPipelinenumber(pipelinenu);
			String sa = retu.getAcceptStatus();
			if(sa.equals("S")){
				map.put("Ncode", 2000);
				repay.setStatu("成功");
				Integer a = servie.AddRepayment(repay);
				if(a!=null){
					map.put("ReturnChanpay", retu);
					map.put("TrxId", TrxId);
					map.put("code", 200);
					map.put("desc", "插入成功");
				}else{
					repay.setStatu("失败");
					map.put("ReturnChanpay", retu);
					map.put("TrxId", TrxId);
					map.put("code", 0);
					map.put("desc", "插入失败");
					
				}
				
			
			
			}else{
				map.put("Ncode", 2000);
				map.put("ReturnChanpay", retu);
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(result);
		}else{
			map.put("ReturnChanpay", "TrxId,OrdrName,MerUserId,CardBegin,CardEnd,TrxAmt不能位null");
			map.put("code", 0);
			map.put("Ncode", 2000);
		}
		return map;
	}

	
	/**
	 * 2.5 支付确认接口： api nmg_api_quick_payment_smsconfirm
	 */
	@ResponseBody
	@RequestMapping("nmg_api_quick_payment_smsconfirm")
	public Map<String, Object> nmg_api_quick_payment_smsconfirm(String OriPayTrxId,String SmsCode,String OrderNumber) {
		Map<String, Object> map = new HashMap<String, Object>();	
		Map<String, String> origMap = new HashMap<String, String>();
		// 2.1 基本参数
		origMap = setCommonMap(origMap);
		origMap.put("Service", "nmg_api_quick_payment_smsconfirm");// 请求的接口名称
		// 2.2 业务参数
		String trxId = ChanPayUtil.generateOutTradeNo();
		origMap.put("TrxId", trxId);//订单号
		origMap.put("OriPayTrxId", OriPayTrxId);// 原有支付请求订单号
		origMap.put("SmsCode", SmsCode);// 短信验证码
		String result = "";
		try {
			String urlStr = "https://pay.chanpay.com/mag-unify/gateway/receiveOrder.do?";// 测试环境地址，上生产后需要替换该地址
			result = buildRequest(origMap, "RSA", ChanpayQuickCollection.MERCHANT_PRIVATE_KEY, charset,urlStr);
			ReturnChanpay retu = JSON.parseObject(result,ReturnChanpay.class);
			String as = retu.getAcceptStatus();
			Orders ord = new Orders();
			ord.setOrderNumber(OrderNumber);
			if(as.equals("S")){
				map.put("Ncode", 2000);
				Integer a = servie.UpdateOrders(ord);
				if(a!=null){
					System.out.println(ord.getOrderNumber());
					System.out.println(a);
					map.put("code", "200");
					map.put("ReturnChanpay", retu);
					map.put("desc", "插入成功");
				}else{
					map.put("code", "0");
					map.put("ReturnChanpay", retu);
					map.put("desc", "插入失败");
				}
			}else{
				map.put("Ncode", 0);
				map.put("ReturnChanpay", retu);
			}
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(result);
			return map;
	}

//	/**
//	 * 
//	 * 2.6 支付请求接口(直付通) api nmg_zft_api_quick_payment
//	 */
//	@ResponseBody
//	@RequestMapping("nmg_zft_api_quick_payment")
//	private Map<String, Object> nmg_zft_api_quick_payment() {
//		Map<String, Object> map = new HashMap<String, Object>();	
//		Map<String, String> origMap = new HashMap<String, String>();
//		// 2.1 基本参数
//		origMap = setCommonMap(origMap);
//		origMap.put("Service", "nmg_zft_api_quick_payment");// 支付接口名称
//		
//		origMap.put("TrxId", ChanPayUtil.generateOutTradeNo());// 订单号
//		origMap.put("OrdrName", "畅捷支付");// 商品名称
//		origMap.put("MerUserId", "a001");// 用户标识（测试时需要替换一个新的meruserid）
//		origMap.put("SellerId", "200005640044");// 子账户号
//		origMap.put("SubMerchantNo", "200005640044");// 子商户号
//		origMap.put("ExpiredTime", "40m");// 订单有效期
//		
//		origMap.put("BkAcctTp", "01");// 卡类型（00 – 银行贷记卡;01 – 银行借记卡;）
//		origMap.put("BkAcctNo", this.encrypt("6214835901884138", MERCHANT_PUBLIC_KEY, charset));// 卡号
//		origMap.put("IDTp", "01");// 证件类型 （目前只支持身份证 01：身份证）
//		origMap.put("IDNo", this.encrypt("420621199905157170", MERCHANT_PUBLIC_KEY, charset));// 证件号
//		origMap.put("CstmrNm", this.encrypt("东新雨", MERCHANT_PUBLIC_KEY, charset));// 持卡人姓名
//		origMap.put("MobNo", this.encrypt("13487139655", MERCHANT_PUBLIC_KEY, charset));// 银行预留手机号
////		origMap.put("EnsureAmount", "1");//担保金额
//		origMap.put("TrxAmt", "0.1");// 交易金额
//		origMap.put("TradeType", "11");// 交易类型
//		origMap.put("SmsFlag", "0");//短信发送标识
//		this.gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
//		String result = "";
//		try {
//			String urlStr = "https://pay.chanpay.com/mag-unify/gateway/receiveOrder.do?";// 测试环境地址，上生产后需要替换该地址
//			Map<String, String> sPara = buildRequestPara(origMap, "RSA", MERCHANT_PRIVATE_KEY, charset);
//				result = buildRequest(origMap, "RSA", ChanpayQuickCollection.MERCHANT_PRIVATE_KEY, charset,
//						urlStr);
//			ReturnChanpay retu = JSON.parseObject(result,ReturnChanpay.class);
//			map.put("ReturnChanpay", retu);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			System.out.println(result);
//		//this.gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
//		return map;
//	}
	
//	/**
//	 * 
//	 * 2.2.7. 直接支付请求接口(畅捷前台)  nmg_quick_onekeypay
//	 */
//	@ResponseBody
//	@RequestMapping("nmg_quick_onekeypay")
//	private Map<String, Object> nmg_quick_onekeypay(){
//		Map<String, Object> map = new HashMap<String, Object>();
//		Map<String, String> origMap = new HashMap<String, String>();
//		// 2.1 基本参数
//		origMap = setCommonMap(origMap);
//		origMap.put("Service", "nmg_quick_onekeypay");// 直接支付
//		// 2.2 业务参数
//		//origMap.put("TrxId", "2017031310052312");// 商户网站唯一订单号
//		String trxId = Long.toString(System.currentTimeMillis());		
//		origMap.put("TrxId", trxId);// 订单号
//
//		origMap.put("OrdrName", "测试商品"); // 商品名称
//		origMap.put("OrdrDesc", "[{'商品型号':'D007','商品性能':'Test'}]");// 商品描述
//		origMap.put("MerUserId", "1700000001");// 用户标识（测试时需要替换一个新的meruserid）
//		origMap.put("SellerId", "200001160097");// 生产环境
//		origMap.put("SubMerchantNo", "200005640044");// 子商户号
//		origMap.put("ExpiredTime", "40m");// 订单有效期
////		origMap.put("BkAcctTp", "01");// 卡类型（00 – 银行贷记卡;01 – 银行借记卡;）
////		origMap.put("BkAcctNo", this.encrypt("430000000000000000", MERCHANT_PUBLIC_KEY, charset));// 卡号
////		origMap.put("IDTp", "01");// 证件类型 （目前只支持身份证 01：身份证）
////		origMap.put("IDNo", this.encrypt("1300000000000000", MERCHANT_PUBLIC_KEY, charset));// 证件号
////		origMap.put("CstmrNm", this.encrypt("XXX", MERCHANT_PUBLIC_KEY, charset));// 持卡人姓名
////		origMap.put("MobNo", this.encrypt("1380000000", MERCHANT_PUBLIC_KEY, charset));// 银行预留手机号
//		
////		origMap.put("CardCvn2", "");//cvv2
////		origMap.put("CardExprDt", "07/21");//有效期
//
//		//origMap.put("EnsureAmount", "1");//担保金额
//		origMap.put("TrxAmt", "0.01");// 交易金额
//		origMap.put("TradeType", "11");// 交易类型
//		origMap.put("AccessChannel", "web");//用户终端类型  web  wap
//		origMap.put("ReturnUrl", "http://192.168.0.168");//同步回调地址
//		origMap.put("NotifyUrl", "");//异步回调地址
//		origMap.put("Extension", "");//扩展字段
//		String result = "";
//		try {
//			String urlStr = "https://pay.chanpay.com/mag-unify/gateway/receiveOrder.do?";// 测试环境地址，上生产后需要替换该地址
//			Map<String, String> sPara = buildRequestPara(origMap, "RSA", MERCHANT_PRIVATE_KEY, charset);
//				result = buildRequest(origMap, "RSA", ChanpayQuickCollection.MERCHANT_PRIVATE_KEY, charset,
//						urlStr);
//			ReturnChanpay retu = JSON.parseObject(result,ReturnChanpay.class);
//			map.put("ReturnChanpay", retu);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			System.out.println(result);
//		//this.gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
//		return map;
//	
//	}
	
	
	
	
	
	
	/**
	 * 
	 * 2.2.8  支付请求接口(畅捷前台)  nmg_nquick_onekeypay
	 */
	@ResponseBody
	@RequestMapping("nmg_nquick_onekeypay")
	public Map<String, Object> nmg_nquick_onekeypay(String SubMerchantNo,String MerUserId,String CardBegin,String CardEnd,String TradeType,String TrxAmt){
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> origMap = new HashMap<String, String>();
		// 2.1 基本参数
		origMap = setCommonMap(origMap);
		origMap.put("Service", "nmg_nquick_onekeypay");// 支付请求接口
		// 2.2 业务参数
		//origMap.put("TrxId", "2017031310052312");// 商户网站唯一订单号
		String trxId = Long.toString(System.currentTimeMillis());		
		origMap.put("TrxId", trxId);// 订单号

		origMap.put("OrdrName", "支付"); // 商品名称
		origMap.put("OrdrDesc", "");// 商品描述
		origMap.put("MerUserId", MerUserId);// 用户标识（测试时需要替换一个新的meruserid）
		origMap.put("SellerId", "200001160097");// 生产环境
		origMap.put("SubMerchantNo", SubMerchantNo);// 子商户号
		
		origMap.put("BkAcctTp", "01");// 卡类型（00 – 银行贷记卡;01 – 银行借记卡;）	实际上应该不需要这个字段	
		origMap.put("CardBegin", CardBegin);// 卡号前6位
		origMap.put("CardEnd", CardEnd);// 卡号后4位
		origMap.put("ExpiredTime", "40m");// 订单有效期
		//origMap.put("EnsureAmount", "1");//担保金额
		origMap.put("TradeType", TradeType);// 交易类型
		origMap.put("TrxAmt", TrxAmt);// 交易金额
	
		origMap.put("AccessChannel", "web");//用户终端类型  web  wap
		origMap.put("ReturnUrl", "http://www.baidu.com");//同步回调地址
		origMap.put("NotifyUrl", "");//异步回调地址
		origMap.put("Extension", "");//扩展字段
		String result = "";
		try {
			String urlStr = "https://pay.chanpay.com/mag-unify/gateway/receiveOrder.do?";// 测试环境地址，上生产后需要替换该地址
			Map<String, String> sPara = buildRequestPara(origMap, "RSA", MERCHANT_PRIVATE_KEY, charset);
				result = buildRequest(origMap, "RSA", ChanpayQuickCollection.MERCHANT_PRIVATE_KEY, charset,
						urlStr);
				System.out.println(sPara);
			ReturnChanpay retu = JSON.parseObject(result,ReturnChanpay.class);
			map.put("ReturnChanpay", retu);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(result);
		//this.gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
		return map;
	}
	
	
	/**
	 * 
	 * 2.9 用户鉴权绑卡信息查询 api nmg_api_auth_info_qry
	 */
	@ResponseBody
	@RequestMapping("nmg_api_auth_info_qry")
	private Map<String, Object> nmg_api_auth_info_qry() {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> origMap = new HashMap<String, String>();
		// 2.1 基本参数
		origMap = setCommonMap(origMap);
		origMap.put("Service", "nmg_api_auth_info_qry");// 用户鉴权绑卡信息查询接口名
		// 2.2 业务参数
		origMap.put("TrxId", "10086192541");// 商户网站唯一订单号
		origMap.put("MerUserId", "35"); // 用户标识（测试时需要替换一个新的meruserid）
		origMap.put("CardBegin", "621483");// 卡号前6位
		origMap.put("CardEnd", "4138");// 卡号后4位
		origMap.put("BkAcctTp", "01");// 卡类型（00 – 银行贷记卡;01 – 银行借记卡）
		String result = "";
		try {
			String urlStr = "https://pay.chanpay.com/mag-unify/gateway/receiveOrder.do?";// 测试环境地址，上生产后需要替换该地址
			Map<String, String> sPara = buildRequestPara(origMap, "RSA", MERCHANT_PRIVATE_KEY, charset);
				result = buildRequest(origMap, "RSA", ChanpayQuickCollection.MERCHANT_PRIVATE_KEY, charset,
						urlStr);
			ReturnChanpay retu = JSON.parseObject(result,ReturnChanpay.class);
			map.put("ReturnChanpay", retu);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(result);
		//this.gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
		return map;
	}
	
	/**
	 * 
	 * 用户鉴权解绑 nmg_api_auth_unbind  普通方式
	 */
	@ResponseBody
	@RequestMapping("nmg_api_auth_unbind")
	public Map<String, Object> nmg_api_auth_unbind(String CardBegin,String CardEnd,String MerUserId) {
		Map<String, String> origMap = new HashMap<String, String>();
		Map<String, Object> map = new HashMap<String, Object>();
		// 2.1 基本参数
		origMap = setCommonMap(origMap);
		origMap.put("Service", "nmg_api_auth_unbind");// 用户鉴权解绑接口名
		// 2.2 业务参数
		String trxId = Long.toString(System.currentTimeMillis());		
		origMap.put("TrxId", trxId);// 商户网站唯一订单号
		origMap.put("MerchantNo", "200005640044");// 子商户号
		origMap.put("MerUserId", MerUserId); // 用户标识（测试时需要替换一个新的meruserid）
		origMap.put("UnbindType", "1"); // 解绑模式。0为物理解绑，1为逻辑解绑
//		origMap.put("CardId", "");// 卡号标识
		origMap.put("CardBegin", CardBegin);// 卡号前6位
		origMap.put("CardEnd", CardEnd);// 卡号后4位
		origMap.put("Extension", "");// 扩展字段
		String result = null;
		try {
			String urlStr = "https://pay.chanpay.com/mag-unify/gateway/receiveOrder.do?";// 测试环境地址，上生产后需要替换该地址
			result = buildRequest(origMap, "RSA", ChanpayQuickCollection.MERCHANT_PRIVATE_KEY, charset,
						urlStr);
			ReturnChanpay retu = JSON.parseObject(result,ReturnChanpay.class);
			//chanser.DeleteChan(Integer.valueOf(MerUserId));
			map.put("ReturnChanpay", retu);
			} catch (Exception e) {
				e.printStackTrace();
		}
		return map;
	}

	/**
	 * 
	 * 2.10 用户退款请求接口： nmg_api_refund
	 */
	@ResponseBody
	@RequestMapping("nmg_api_refund")
	private Map<String, Object> nmg_api_refund(String TrxId,String OriTrxId,String TrxAmt) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> origMap = new HashMap<String, String>();
		// 2.1 基本参数
		origMap = setCommonMap(origMap);
		origMap.put("Service", "nmg_api_refund");// 支付的接口名
		// 2.2 业务参数
		origMap.put("TrxId", TrxId);// 订单号
		origMap.put("OriTrxId", OriTrxId);// 原有支付请求订单号
		origMap.put("TrxAmt", TrxAmt);// 退款金额
		origMap.put("RefundEnsureAmount", null);// 退担保金额
//		origMap.put("RoyaltyParameters",
//				"[{'userId':'13890009900','PID':'2','account_type':'101','amount':'100.00'},{'userId':'13890009900','PID':'2','account_type':'101','amount':'100.00'}]");// 退款分润账号集
		origMap.put("NotifyUrl", "www.baidu.com");// 异步通知地址
		String result = "";
		try {
			String urlStr = "https://pay.chanpay.com/mag-unify/gateway/receiveOrder.do?";// 测试环境地址，上生产后需要替换该地址
			Map<String, String> sPara = buildRequestPara(origMap, "RSA", MERCHANT_PRIVATE_KEY, charset);
				result = buildRequest(origMap, "RSA", ChanpayQuickCollection.MERCHANT_PRIVATE_KEY, charset,
						urlStr);
			ReturnChanpay retu = JSON.parseObject(result,ReturnChanpay.class);
			map.put("ReturnChanpay", retu);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(result);
		//this.gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
		return map;
	}

	/**
	 * 
	 * 2.11 商户短信验证码重发  ：nmg_api_quick_payment_resend
	 */

	private void nmg_sms_resend() {

		Map<String, String> origMap = new HashMap<String, String>();
		// 2.1 基本参数
		origMap = setCommonMap(origMap);
		// 2.2 业务参数
		origMap.put("Service", "nmg_api_quick_payment_resend");
		origMap.put("TrxId", "2017030915102022");// 订单号
		origMap.put("OriTrxId", "20170309131120");// 原业务请求订单号
		origMap.put("TradeType", "auth_order");// 原业务订单类型
		this.gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
	}

	

	/**
	 * 2.12 商户日退款对账单文件接口：nmg_api_refund_trade_file
	 */
	public void nmg_api_refund_trade_file() {

		Map<String, String> origMap = new HashMap<String, String>();
		// 2.1 基本参数
		origMap = setCommonMap(origMap);
		origMap.put("Service", "nmg_api_refund_trade_file");// 请求的接口名称
		// 2.9 日退款对账单文件
		origMap.put("TransDate", "20160728");// 交易日期
		this.gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
	}
	/**
	 * *
	 * 
	 * 
	 * 2.13：确认收货接口：nmg_api_quick_payment_receiptconfirm
	 * 
	 */

	public void nmg_api_quick_payment_receiptconfirm() {
		Map<String, String> origMap = new HashMap<String, String>();
		// 2.1 基本参数
		origMap = setCommonMap(origMap);
		origMap.put("TrxId", "2334554654232342");//订单号
		origMap.put("OrderTrxId", "2334554654232342");//原支付请求订单号
		origMap.put("Service", "nmg_api_quick_payment_receiptconfirm");
		this.gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);

	}
	
	/**
	 * 
	 * 2.14 商户鉴权/支付/退款订单状态查询接口 api tzt_order_query
	 */

	private void nmg_api_query_trade() {

		Map<String, String> origMap = new HashMap<String, String>();
		// 2.1 基本参数
		origMap = setCommonMap(origMap);
		origMap.put("Service", "nmg_api_query_trade");// 请求的接口名
		// 2.2 业务参数
		origMap.put("TrxId", "2017030915202027");// 订单号
		origMap.put("OrderTrxId", "2017030915302088");// 原业务请求订单号
		origMap.put("TradeType", "refund_order");// 原业务订单类型
		this.gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
	}

	/**
	 * 2.15 商户日交易对账单文件:nmg_api_everyday_trade_file
	 */
	public void nmg_api_everyday_trade_file() {

		Map<String, String> origMap = new HashMap<String, String>();
		// 2.1 基本参数
		origMap = setCommonMap(origMap);
		origMap.put("Service", "nmg_api_everyday_trade_file");// 请求的接口名称
		// 2.11 日支付对账文件
		origMap.put("TransDate", "20190805");// 交易日期
//		this.gatewayPost(origMap, charset, MERCHANT_PRIVATE_KEY);
		
		Object obj = this.gatewayPosts(origMap, charset, MERCHANT_PRIVATE_KEY);
		if (obj instanceof String) {
			System.out.println((String) obj);
		} else {
			this.downloadFile((byte[]) obj);
		}

	}
	
	
	
	/**
	 * 
	 * 2.4 支付请求接口 api nmg_biz_api_quick_payment
	 */
	@ResponseBody
	@RequestMapping("Defenmg_biz_api_quick_payment")
	public Map<String, Object> Defenmg_biz_api_quick_payment(String TrxId,String OrdrName,String MerUserId,String CardBegin,String CardEnd,String TrxAmt,
			String deferBeforeReturntime,Integer postponeDate,String deferAfterReturntime) {
		Map<String, Object> map = new HashMap<String, Object>();	
		if(TrxId != null && OrdrName != null && MerUserId != null && CardBegin != null && CardEnd != null && TrxAmt != null){
		Map<String, String> origMap = new HashMap<String, String>();
		// 2.1 基本参数 
		System.out.println("走接口");
		origMap = setCommonMap(origMap);
		origMap.put("Service", "nmg_biz_api_quick_payment");// 支付的接口名
		Deferred defe = new Deferred();
		
		
		defe.setOrderNumber(TrxId);
		BigDecimal onarrears = new BigDecimal(TrxAmt);
		defe.setInterestOnArrears(onarrears);
		defe.setDeferBeforeReturntime(deferBeforeReturntime);
		defe.setPostponeDate(postponeDate);
		defe.setDeferAfterReturntime(deferAfterReturntime);
		
		origMap.put("TrxId", ChanPayUtil.generateOutTradeNo());// 订单号
		origMap.put("OrdrName", OrdrName);// 商品名称
		origMap.put("MerUserId", MerUserId);// 用户标识（测试时需要替换一个新的meruserid）
		origMap.put("SellerId", "200005640044");// 子账户号
		origMap.put("SubMerchantNo", "200005640044");// 子商户号
		origMap.put("ExpiredTime", "40m");// 订单有效期
		origMap.put("CardBegin", CardBegin);// 卡号前6位
		origMap.put("CardEnd", CardEnd);// 卡号后4位
		origMap.put("TrxAmt", TrxAmt);// 交易金额
		origMap.put("TradeType", "11");// 交易类型
		origMap.put("SmsFlag", "1");
		String result = "";
		try {
			String urlStr = "https://pay.chanpay.com/mag-unify/gateway/receiveOrder.do?";// 测试环境地址，上生产后需要替换该地址
			result = buildRequest(origMap, "RSA", ChanpayQuickCollection.MERCHANT_PRIVATE_KEY, charset,urlStr);
			ZhifuAcceptStatus retu = JSON.parseObject(result,ZhifuAcceptStatus.class);
			String sa = retu.getAcceptStatus();
			if(sa.equals("S")){
				map.put("Ncode", 2000);
				Integer a = servie.AddDeferred(defe);
			if(a != null){
				map.put("ReturnChanpay", retu);
				map.put("TrxId", TrxId);
				map.put("desc", "插入成功");
				map.put("code", 200);
			}else{
				map.put("ReturnChanpay", retu);
				map.put("TrxId", TrxId);
				map.put("desc", "插入失败");
				map.put("code", 0);
			}
			
			}else{
				map.put("Ncode", 2000);
				map.put("ReturnChanpay", retu);
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(result);
		}else{
			map.put("ReturnChanpay", "TrxId,OrdrName,MerUserId,CardBegin,CardEnd,TrxAmt不能位null");
			map.put("Ncode", 2000);
			map.put("code", 0);
		}
		return map;
	}

	
	/**
	 * 
	 * 2.5 延期还款支付确认接口： api nmg_api_quick_payment_smsconfirm
	 */
	@ResponseBody
	@RequestMapping("Defenmg_api_quick_payment_smsconfirm")
	public Map<String, Object> Defenmg_api_quick_payment_smsconfirm(String OriPayTrxId,String SmsCode,Integer userId,String orderNumber) {
		Map<String, Object> map = new HashMap<String, Object>();	
		Map<String, String> origMap = new HashMap<String, String>();
		// 2.1 基本参数
		origMap = setCommonMap(origMap);
		origMap.put("Service", "nmg_api_quick_payment_smsconfirm");// 请求的接口名称
		// 2.2 业务参数
		origMap.put("TrxId", ChanPayUtil.generateOutTradeNo());// 订单号
		origMap.put("OriPayTrxId", OriPayTrxId);// 原有支付请求订单号
		origMap.put("SmsCode", SmsCode);// 短信验证码
		String result = "";
		try {
			String urlStr = "https://pay.chanpay.com/mag-unify/gateway/receiveOrder.do?";// 测试环境地址，上生产后需要替换该地址
			result = buildRequest(origMap, "RSA", ChanpayQuickCollection.MERCHANT_PRIVATE_KEY, charset,urlStr);
			ReturnChanpay retu = JSON.parseObject(result,ReturnChanpay.class);
			Orders ord = new Orders();
			ord.setOrderNumber(orderNumber);
			System.out.println("用户ID:"+userId);
			ord.setUserId(userId);
			String sa = retu.getAcceptStatus();
			if(sa.equals("S")){
				map.put("Ncode", 2000);
			
			Integer a = servie.UpdateDefeOrders(ord);
			if(a != null){
				
				map.put("code", "200");
				map.put("desc", "插入成功");
				map.put("ReturnChanpay", retu);
			}else{
				map.put("code", "0");
				map.put("desc", "插入失败");
				map.put("ReturnChanpay", retu);
			}
			
			}else{
				
			}
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(result);
		return map;
	}
	
	
	
	
	

	
	public void downloadFile(byte[] bt) {
		// 确定写出文件的位置
		File file = new File("C:\\TestQuick.xls");
		// 建立输出字节流
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);
			// 用FileOutputStream 的write方法写入字节数组
			fos.write(bt);
			System.out.println("文件下载成功");
			// 为了节省IO流的开销，需要关闭
			if (fos != null) {
				fos.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void uuID(){
		  String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		  System.out.println(uuid);
	}
	

	
	
	
	public static void main(String[] args) {
		ChanpayQuickCollection test = new ChanpayQuickCollection();
		
//		test.nmg_biz_api_auth_req(); // 2.1 鉴权请求---API
//		test.nmg_page_api_auth_req(); //2.2 鉴权请求 ---畅捷前端
//		test.nmg_api_auth_sms(); // 2.3 鉴权请求确认---API
//		test.nmg_api_quick_payment_smsconfirm(); //2.5 支付确认---API
//		test.nmg_zft_api_quick_payment(); //2.6 支付请求（直付通）
//		test.nmg_quick_onekeypay();  //2.7 直接请求---畅捷前端
//		test.nmg_nquick_onekeypay();  //2.8 支付请求---畅捷前端
//		test.nmg_api_auth_info_qry(); // 2.9 鉴权绑卡查询
//		test.nmg_api_auth_unbind(); // 鉴权解绑（普通）
//		test.nmg_api_refund();//商户退款请求
		test.SelectCompany("");
	//	test.nmg_api_auth_unbind("621700", "6842", "17");
//		test.nmg_sms_resend(); //2.11 短信重发
//		test.nmg_api_query_trade(); //2.14 订单状态查询
//		test.nmg_api_refund_trade_file(); //2.12 退款对账单
//		test.nmg_api_everyday_trade_file(); //2.15 交易对账单
//		test.nmg_api_quick_payment_receiptconfirm();// 2.13 确认收货接口
//		test.notifyVerify(); // 测试异步通知验签
	}
	
	
	
	@ResponseBody
	@RequestMapping("SelectCompanyId")
	public void SelectCompany(String orderNumber){
		servie.SelectId(orderNumber);
	}
	
	
	
}
