package com.zhita.controller.payment.zpay;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.zhita.controller.payment.util.HttpClient;
import com.zhita.controller.payment.util.SignUtils;


@Controller
@RequestMapping("zpayhelper")
public class ZpayHelper {

	/**
	 * 企业支付宝 APP
	 * @param amount  订单金额
	 * @param billId	订单号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static JSONObject yuanApp(BigDecimal amount,String billId){
		Map<String, String> payParams=new HashMap<String, String>();    
	    payParams.put("method","zpay.trade.yuan");
        payParams.put("version","1.0");
        payParams.put("mchId",ZpayConfig.NEW_MERCHANT_NO);
        payParams.put("amount",amount.setScale(2).toString());
        payParams.put("type","30");
        payParams.put("notifyUrl",ZpayConfig.RECHARGE_NOTIFY_NEW);
        payParams.put("orderId", billId);
        String resultSign= SignUtils.getSign(payParams,ZpayConfig.NEW_MD5_KEY).toUpperCase();
        payParams.put("sign",resultSign);
        try {
			HttpClient httpClient=new HttpClient(ZpayConfig.GATEWAY_URL, 30000, 30000);
			String resp = httpClient.send(payParams, "utf-8");
			System.out.println(resp);
	        if(StringUtils.isNotBlank(resp)){
	        	JSONObject jsonObject=JSONObject.parseObject(resp);
	        	String code=jsonObject.getString("code");
	        	if(code.equals("SUCCESS")){
	        		if(SignUtils.checkParam(JSONObject.toJavaObject(jsonObject, Map.class) , ZpayConfig.NEW_MD5_KEY)){
	        			return jsonObject;
	        		}
	        	}
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
        return null;
	}

	/**
	 * 企业支付宝 H5 返回跳转支付宝支付页面
	 * @param amount  订单金额
	 * @param billId	订单号
	 * @return
	 */
	public static String yuanH5(BigDecimal amount,String billId,String returnUrl){
		Map<String, String> payParams=new HashMap<String, String>();
		payParams.put("method","zpay.trade.yuan");
		payParams.put("version","1.0");
		payParams.put("mchId",ZpayConfig.NEW_MERCHANT_NO);
		payParams.put("amount",amount.setScale(2).toString());
		payParams.put("type","31");
		payParams.put("returnUrl",returnUrl);
		payParams.put("notifyUrl",ZpayConfig.RECHARGE_NOTIFY_NEW);
		payParams.put("orderId", billId);
		String resultSign= SignUtils.getSign(payParams,ZpayConfig.NEW_MD5_KEY).toUpperCase();
		payParams.put("sign",resultSign);
		try {
			HttpClient httpClient=new HttpClient(ZpayConfig.GATEWAY_URL, 30000, 30000);
			String resp = httpClient.send(payParams, "utf-8");
			System.out.println(resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 企业支付宝 pc 返回跳转支付宝支付页面
	 * @param amount  订单金额
	 * @param billId	订单号
	 * @return
	 */
	public static String yuanPC(BigDecimal amount,String billId,String returnUrl){
		Map<String, String> payParams=new HashMap<String, String>();
		payParams.put("method","zpay.trade.yuan");
		payParams.put("version","1.0");
		payParams.put("mchId",ZpayConfig.NEW_MERCHANT_NO);
		payParams.put("amount",amount.setScale(2).toString());
		payParams.put("type","32");
		payParams.put("returnUrl",returnUrl);
		payParams.put("notifyUrl",ZpayConfig.RECHARGE_NOTIFY_NEW);
		payParams.put("orderId", billId);
		String resultSign= SignUtils.getSign(payParams,ZpayConfig.NEW_MD5_KEY).toUpperCase();
		payParams.put("sign",resultSign);
		try {
			HttpClient httpClient=new HttpClient(ZpayConfig.GATEWAY_URL, 30000, 30000);
			String resp = httpClient.send(payParams, "utf-8");
			System.out.println(resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 点对点支付
	 * @param billId  订单id
	 * @param money	订单金额
	 * @param createTime	创建时间
	 * @param returnUrl	支付完跳转url
	 * @return
	 */
	public static JSONObject qpayNew(String billId,BigDecimal money,Date createTime,String returnUrl){
		
        Map<String, String> payParams=new HashMap<String, String>();        
        payParams.put("method","zpay.trade.znew");
        payParams.put("version","1.0");
        payParams.put("mchId",ZpayConfig.MERCHANT_NO);
        payParams.put("amount",money.setScale(2).toString());
        payParams.put("type","2");
		payParams.put("appType","xunpay");
        payParams.put("payType","1");
        payParams.put("notifyUrl",ZpayConfig.RECHARGE_NOTIFY);
        payParams.put("returnUrl",returnUrl);
        payParams.put("orderId", billId);
        String resultSign= SignUtils.getSign(payParams,ZpayConfig.MD5_KEY).toUpperCase();
        payParams.put("sign",resultSign);
		try {
			HttpClient httpClient=new HttpClient(ZpayConfig.GATEWAY_URL, 30000, 30000);
			String resp = httpClient.send(payParams, "utf-8");
			System.out.println(resp);
	        if(StringUtils.isNotBlank(resp)){
	        	JSONObject jsonObject=JSONObject.parseObject(resp);
	        	String code=jsonObject.getString("code");
	        	if(code.equals("SUCCESS")){
	        		if(SignUtils.checkParam(JSONObject.toJavaObject(jsonObject, Map.class) , ZpayConfig.MD5_KEY)){
	        			return jsonObject;
	        		}
	        	}
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
        return null;
    }

	/**
	 * 查询订单
	 * @param billId  订单号
	 * @return
	 */
	public static JSONObject query(String billId){
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
	        if(StringUtils.isNotBlank(resp)){
	        	JSONObject jsonObject=JSONObject.parseObject(resp);
	        	String code=jsonObject.getString("code");
	        	if(code.equals("SUCCESS")){
	        		if(SignUtils.checkParam(JSONObject.toJavaObject(jsonObject, Map.class) , ZpayConfig.MD5_KEY)){
	        			return jsonObject;
	        		}
	        	}
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
        return null;
	}
	
	
	
	/**
	 * 点对点代付
	 * @param amount  订单金额
	 * @param billId	订单号
	 * @return
	 */
	@ResponseBody
	@RequestMapping("CC")
	public static Map<String, Object> YuXia(){
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> payParams=new HashMap<String, String>();  
		SimpleDateFormat sim = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String orderId = sim.format(new Date());
	    payParams.put("method","zpay.order.trade");
        payParams.put("version","1.0");
        payParams.put("mchId",ZpayConfig.NEW_MERCHANT_NO);
        payParams.put("amount","25");
        payParams.put("appType","autobank");
        payParams.put("notifyUrl",ZpayConfig.RECHARGE_NOTIFY_NEW);//异步通知地址
        payParams.put("orderId", "DD_"+orderId);//订单ID
        payParams.put("orderUid", "MDBS");//客户ID
        payParams.put("orderName", "米多宝");//客户名称
        payParams.put("skName", "东新雨");//收款人姓名
        payParams.put("skUnpayAccount", "6214835901884138");//收款人账号
        payParams.put("skBankCode", "CMB");//收款人银行编码
        payParams.put("skCardCode", "6214835901884138");//收款银行卡号
        payParams.put("skMobile", "13487139655");//银行预留手机号
        payParams.put("skIdCode", "420621199905157170");//收款人身份证号
        String resultSign= SignUtils.getSign(payParams,ZpayConfig.NEW_MD5_KEY).toUpperCase();
        payParams.put("sign",resultSign);
        try {
			HttpClient httpClient=new HttpClient(ZpayConfig.GATEWAY_URL, 30000, 30000);
			String resp = httpClient.send(payParams, "utf-8");
			System.out.println("数据:"+resp);
	        if(StringUtils.isNotBlank(resp)){
	        	JSONObject jsonObject=JSONObject.parseObject(resp);
	        	String code=jsonObject.getString("code");
	        	if(code.equals("SUCCESS")){
	        		if(SignUtils.checkParam(JSONObject.toJavaObject(jsonObject, Map.class) , ZpayConfig.NEW_MD5_KEY)){
	        			map.put("code", 200);
	        			map.put("Ncode", 2000);
	        			map.put("msg", "成功");
	        			return map;
	        		}
	        	}
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
        return map;
	}
	
	
	
	
	
	/**
	 * 点对点收款
	 * @param amount  订单金额
	 * @param billId	订单号
	 * @return
	 */
	public static JSONObject Receivables(BigDecimal amount){
		Map<String, String> payParams=new HashMap<String, String>();    
		SimpleDateFormat sim = new SimpleDateFormat("yyyyMMddHHmmssSSSSSSS");
		String billId = sim.format(new Date());
	    payParams.put("method","zpay.trade.znew");
        payParams.put("version","1.0");
        payParams.put("mchId",ZpayConfig.NEW_MERCHANT_NO);
        payParams.put("amount",amount.setScale(2).toString());
        payParams.put("appType","xunpay");
        payParams.put("type", "3");
        payParams.put("payType", "1");
        payParams.put("notifyUrl",ZpayConfig.RECHARGE_NOTIFY_NEW);//异步通知地址
        payParams.put("returnUrl", "http://new");
        payParams.put("orderId", "DD_"+billId);//订单ID
        payParams.put("orderUid", "MDBS");//客户ID
        payParams.put("orderName", "米多宝");//客户名称
        payParams.put("khName", "郑凯");//收款人姓名
        payParams.put("khBankCode", "CMB");//收款人银行编码
        payParams.put("khCardCode", "6217582000013555867");//收款银行卡号
        payParams.put("khMobile", "13145972433");//银行预留手机号
        payParams.put("khIdCode", "440229199710242917");//收款人身份证号
        String resultSign= SignUtils.getSign(payParams,ZpayConfig.NEW_MD5_KEY).toUpperCase();
        payParams.put("sign",resultSign);
        try {
			HttpClient httpClient=new HttpClient(ZpayConfig.GATEWAY_URL, 30000, 30000);
			String resp = httpClient.send(payParams, "utf-8");
			System.out.println("数据:"+resp);
	        if(StringUtils.isNotBlank(resp)){
	        	JSONObject jsonObject=JSONObject.parseObject(resp);
	        	String code=jsonObject.getString("code");
	        	if(code.equals("SUCCESS")){
	        		if(SignUtils.checkParam(JSONObject.toJavaObject(jsonObject, Map.class) , ZpayConfig.NEW_MD5_KEY)){
	        			System.out.println(billId);
	        			return jsonObject;
	        		}
	        	}
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
        return null;
	}
	
	
	
	
	/**
	 * 点对点代付
	 * @param amount  订单金额
	 * @param billId	订单号
	 * @return
	 */
	
	public static JSONObject Select(){
		Map<String, String> payParams=new HashMap<String, String>();    
	    payParams.put("method","zpay.order.query");
        payParams.put("version","1.0");
        payParams.put("mchId",ZpayConfig.NEW_MERCHANT_NO);
        payParams.put("orderId", "test_20190901716225944");//订单ID
        String resultSign= SignUtils.getSign(payParams,ZpayConfig.NEW_MD5_KEY).toUpperCase();
        payParams.put("sign",resultSign);
        try {
			HttpClient httpClient=new HttpClient(ZpayConfig.GATEWAY_URL, 30000, 30000);
			String resp = httpClient.send(payParams, "utf-8");
			System.out.println("数据:"+resp);
	        if(StringUtils.isNotBlank(resp)){
	        	JSONObject jsonObject=JSONObject.parseObject(resp);
	        	String code=jsonObject.getString("code");
	        	if(code.equals("SUCCESS")){
	        		if(SignUtils.checkParam(JSONObject.toJavaObject(jsonObject, Map.class) , ZpayConfig.NEW_MD5_KEY)){
	        			return jsonObject;
	        		}
	        	}
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
        return null;
	}
	
	
	
	
	
	

	/**
	 * 点对点代付
	 * @param amount  订单金额
	 * @param billId	订单号
	 * @return
	 */
	public static JSONObject SelectFF(){
		Map<String, String> payParams=new HashMap<String, String>();    
	    payParams.put("method","zpay.order.query");
        payParams.put("version","1.0");
        payParams.put("mchId",ZpayConfig.NEW_MERCHANT_NO);
        payParams.put("orderId", "DD_20190922171832a433016");//订单ID
        String resultSign= SignUtils.getSign(payParams,ZpayConfig.NEW_MD5_KEY).toUpperCase();
        payParams.put("sign",resultSign);
        try {
			HttpClient httpClient=new HttpClient(ZpayConfig.GATEWAY_URL, 30000, 30000);
			String resp = httpClient.send(payParams, "utf-8");
			System.out.println("数据:"+resp);
	        if(StringUtils.isNotBlank(resp)){
	        	JSONObject jsonObject=JSONObject.parseObject(resp);
	        	String code=jsonObject.getString("code");
	        	if(code.equals("SUCCESS")){
	        		if(SignUtils.checkParam(JSONObject.toJavaObject(jsonObject, Map.class) , ZpayConfig.NEW_MD5_KEY)){
	        			return jsonObject;
	        		}
	        	}
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
        return null;
	}
	
	
	
	
	public static void main(String[] args) {
		//点对点
		//qpayNew("test_"+System.currentTimeMillis(), new BigDecimal("110.01"), new Date(), ZpayConfig.RECHARGE_NOTIFY);
		//企业支付宝 返回json
		//yuanApp(new BigDecimal("2.0"), "test_" + System.currentTimeMillis());
		//企业支付宝 APP
		//yuanH5(new BigDecimal("2.0"), "test_" + System.currentTimeMillis(),"http://test");
		//SelectFF();
	//	Select();
//		SimpleDateFormat sim = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//		String billId = sim.format(new Date());
//		billId = "test_201909017175056";
		//Receivables(new BigDecimal("1.0"), "test_201909201745652145233652"); 
		//Receivables(new , "test_201909201745652145233652");
		Receivables(new BigDecimal("50"));
//		SelectFF();
		//	SimpleDateFormat sim = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		//    String biaoshi = sim.format(new Date());
		//	System.out.println(biaoshi);
		//20190923124515394
		//YuXia();
		//query("20190923151144230");
	}
}
