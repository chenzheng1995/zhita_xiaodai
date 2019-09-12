package com.zhita.controller.chanpayquickpay;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.NameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zhita.chanpayutil.ChanPayUtil;
import com.zhita.dao.manage.PaymentRecordMapper;
import com.zhita.dao.manage.StatisticsDao;
import com.zhita.model.manage.Orders;
import com.zhita.model.manage.Repayment;
import com.zhita.model.manage.ReturnChanpay;
import com.zhita.util.HttpProtocolHandler;
import com.zhita.util.HttpRequest;
import com.zhita.util.HttpResponse;
import com.zhita.util.HttpResultType;
import com.zhita.util.MD5;
import com.zhita.util.RSA;
import com.zhita.util.Timestamps;


@Service
public class LoopBankMoneyserviceimp {

	
	@Autowired
	private StatisticsDao sdao;
	
	
	
	@Autowired
	private PaymentRecordMapper padao;
	
	
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
	 * 20190805201545678123132
	 * 2.4 支付请求接口 api nmg_biz_api_quick_payment6217000360005556842
	 */
	@ResponseBody
	@RequestMapping("nmg_biz_api_quick_payment")
	public Map<String, Object> nmg_biz_api_quick_payment(String TrxId,String ordrName,String MerUserId,String CardBegin,String CardEnd,String TrxAmt) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(TrxId != null && ordrName != null && MerUserId != null && CardBegin != null && CardEnd != null && TrxAmt != null){
			Integer orderId = sdao.SelectRepaymentOrderId(TrxId);
			if(orderId == null){
			Map<String, String> origMap = new HashMap<String, String>();
			// 2.1 基本参数 
			origMap = setCommonMap(origMap);
			origMap.put("Service", "nmg_biz_api_quick_payment");// 支付的接口名
			Repayment repay = new Repayment();//还账记录表
			repay.setUserId(Integer.valueOf(MerUserId));
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
					
					SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					repay.setCardnumber(sdao.Cardnumber(repay.getUserId()));
					try {
						repay.setRepaymentDate(Timestamps.dateToStamp1(sim.format(new Date())));
					} catch (Exception e) {
						// TODO: handle exception
					}
					repay.setPaymentbtiao("畅捷支付");
					Orders o = sdao.SelectOrderId(repay.getOrderNumber());
					repay.setOrderid(o.getId());
					System.out.println(repay.getOrderid());
					
					Integer a = sdao.AddRepay(repay);
					if(a!=null){
						map.put("Ncode", 2000);
						map.put("ReturnChanpay", retu);
						map.put("TrxId", TrxId);
						map.put("code", 200);
						map.put("msg", "插入成功");
					}else{
						repay.setStatu("失败");
						map.put("ReturnChanpay", retu);
						map.put("TrxId", TrxId);
						map.put("Ncode", 0);
						map.put("code", 0);
						map.put("msg", "插入失败");
						
					}
					
				
				
				}else{
					map.put("Ncode", 0);
					map.put("ReturnChanpay", retu);
					map.put("msg", "卡号异常,请您换一张卡");
				}
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println(result);
			}else{
				map.put("msg", "该订单已还");
				map.put("Ncode", 2000);
				map.put("code", 200);
				return map;
			}
			}else{
				map.put("ReturnChanpay", "TrxId,OrdrName,MerUserId,CardBegin,CardEnd,TrxAmt不能位null");
				map.put("code", 0);
				map.put("Ncode", 0);
				map.put("msg", "TrxId,OrdrName,MerUserId,CardBegin,CardEnd,TrxAmt不能位null");
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
				String pipelinenu = "Rsn_"+OriPayTrxId;
//				Integer updateId = chanpayservice.UpdateRepayStatus(pipelinenu,OrderNumber);
//				if(updateId != null){
//				Integer a = servie.UpdateOrders(ord);
//					if(a!=null){
//						map.put("Ncode", 2000);
//						map.put("code", "200");
//						map.put("ReturnChanpay", retu);
//						map.put("msg", "插入成功");
//					}else{
//						map.put("code", "0");
//						map.put("Ncode", 0);
//						map.put("ReturnChanpay", retu);
//						map.put("msg", "插入失败");
//					}
//				}else{
//					map.put("code", "0");
//					map.put("Ncode", 0);
//					map.put("ReturnChanpay", retu);
//					map.put("msg", "还款状态修改失败,请联系客服");
//				}
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
			return map;
	}
	
	
}
