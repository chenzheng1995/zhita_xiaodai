package com.zhita.controller.payment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.NameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zhita.chanpayutil.ChanPayUtil;
import com.zhita.controller.chanpayQuickPay.ChanpayQuickCollection;
import com.zhita.model.manage.Bankcard;
import com.zhita.model.manage.Loan_setting;
import com.zhita.model.manage.ReturnChanpay;
import com.zhita.model.manage.Thirdparty_interface;
import com.zhita.service.manage.Statistic.Statisticsservice;
import com.zhita.service.manage.chanpayQuickPay.Chanpayservice;
import com.zhita.service.manage.newpayment.NewPaymentservice;
import com.zhita.util.HttpProtocolHandler;
import com.zhita.util.HttpRequest;
import com.zhita.util.HttpResponse;
import com.zhita.util.HttpResultType;
import com.zhita.util.MD5;
import com.zhita.util.RSA;
import com.zhita.util.RedisClientUtil;




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
	
	
	
	@Resource
	private Chanpayservice chanpayservice;
	
	
	
	
	@Autowired
	private NewPaymentservice newsim;
	
	
	/**
	 * 放款
	 * @param companyId
	 * @param bankname		开户行名称
	 * @return
	 */
	@ResponseBody
	@RequestMapping("Newpanyment")
	public Map<String, Object> Newpanyment(Integer companyId,String bankname){
		Thirdparty_interface paymentname = newsim.SelectPaymentName(companyId);//获取系统设置的 放款名称   和  还款名称
		Map<String, Object> map = new HashMap<String, Object>();
		if(paymentname.equals("畅捷支付")){
			
		}else{
			
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
	public Map<String, Object> changebank(Integer MerUserId,String BkAcctNo,String IDNo,String CstmrNm,String MobNo,Integer companyId){
		Loan_setting loan = new Loan_setting();
		loan.setCompanyId(companyId);
		loan.setName("米多宝");
		String a =  chanpayservice.loanSetStatu(loan);//放款状态  1  开启    2 关闭
		
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		RedisClientUtil redis = new RedisClientUtil();
		String aca = redis.get("nmg_api_auth_req"+MerUserId);
		if(aca != null){
			map.put("Ncode", 0);
			map.put("code", "0");
			map.put("msg", "请勿重复点击!!");
			redis.delkey("nmg_api_auth_req"+MerUserId);
			return map;
		}
		if(MerUserId != null && BkAcctNo != null && MobNo != null){
			
			Integer id = servie.SelectUserId(MerUserId);
			if(id != null){
				map.put("Ncode", 0);
				map.put("code", "0");
				map.put("msg", "已绑卡");
			}else{
				
		redis.set("nmg_api_auth_req"+MerUserId, String.valueOf(MerUserId));
		Bankcard bank = new Bankcard();
		bank.setUserId(MerUserId);//登陆人ID
		bank.setBankcardName(BkAcctNo);//卡号
		bank.setTiedCardPhone(MobNo);//手机号
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
					
						map.put("OriAuthTrxId", TrxId);
						map.put("code", "200");
						map.put("Ncode", 2000);
						map.put("desc", "插入成功");
						redis.delkey("nmg_api_auth_req"+MerUserId);
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
}
