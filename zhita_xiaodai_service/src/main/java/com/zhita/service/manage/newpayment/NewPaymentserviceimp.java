package com.zhita.service.manage.newpayment;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import com.zhita.controller.payment.util.HttpClient;
import com.zhita.controller.payment.util.SignUtils;
import com.zhita.dao.manage.NewMapper;
import com.zhita.dao.manage.StatisticsDao;
import com.zhita.model.manage.Bankcard;
import com.zhita.model.manage.Loan_setting;
import com.zhita.model.manage.Orders;
import com.zhita.model.manage.Payment_record;
import com.zhita.model.manage.Repayment;
import com.zhita.model.manage.Thirdparty_interface;
import com.zhita.service.manage.newpayment.util.MapUtil;
import com.zhita.util.RedisClientUtil;


@Service
public class NewPaymentserviceimp implements NewPaymentservice{
	
	
	@Autowired
	private NewMapper newdao;
	
	
	@Autowired
	private StatisticsDao stdao;
	
	

	@Override
	public Thirdparty_interface SelectPaymentName(Integer companyId) {
		return newdao.NewloanRepayment(companyId);
	}



	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> Newpayment(BigDecimal amount,String billId,Integer userId,Integer companyId) {
		Map<String, String> payParams=new HashMap<String, String>(); 
		Map<String, Object> map = new HashMap<String, Object>();
		MapUtil maputil = new MapUtil();
		
		Bankcard ba = new Bankcard();
		ba.setCompanyId(companyId);
		ba.setUserId(userId);
		Bankcard ban = stdao.SelectBanl(ba);//查询银行卡
		
		
		Loan_setting loan = new Loan_setting();
		loan.setCompanyId(companyId);
		loan.setName("必付");
		String a =  stdao.SelectLoanStatus(loan);//放款状态  1  开启    2 关闭
		
		RedisClientUtil redis = new RedisClientUtil();
		redis.set("NewChanpaymentId", "NewChanpaymentId "+userId);
		if(a.equals("1")){
			if(ban.getTiedCardPhone() != null && ban.getBankcardName() != null && ban.getCstmrnm() != null && ban.getBankcardTypeName() != null
					&& ban.getTiedCardPhone() != "" && ban.getBankcardName() != "" && ban.getCstmrnm() != "" && ban.getBankcardTypeName() != ""){
				
				payParams.put("method","zpay.order.trade");
		        payParams.put("version","1.0");
		        payParams.put("mchId",ZpayConfig.NEW_MERCHANT_NO);
		        payParams.put("amount",amount.setScale(2).toString());
		        payParams.put("appType","autobank");
		        payParams.put("notifyUrl",ZpayConfig.RECHARGE_NOTIFY_NEW);//异步通知地址
		        payParams.put("orderId", billId);//订单ID
		        payParams.put("orderUid", "MDBS");//客户ID
		        payParams.put("orderName", "米多宝");//客户名称
		        payParams.put("skName", ban.getCstmrnm());//收款人姓名东新雨
		        payParams.put("skUnpayAccount", ban.getBankcardName());//收款人账号"6214835901884138"
		        payParams.put("skBankCode", maputil.getBankMap(ban.getBankcardTypeName()));//收款人银行编码
		        payParams.put("skCardCode", ban.getBankcardName());//收款银行卡号
		        payParams.put("skMobile", ban.getTiedCardPhone());//银行预留手机号
		        payParams.put("skIdCode", ban.getIDcardnumber());//收款人身份证号"420621199905157170"
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
			        	String tradeNo = jsonObject.getString("tradeNo");
			        	if(code.equals("SUCCESS")){
			        		if(SignUtils.checkParam(JSONObject.toJavaObject(jsonObject, Map.class) , ZpayConfig.NEW_MD5_KEY)){
				        		map.put("msg", maputil.getMap(status));
				        		map.put("tradeNo", tradeNo);
				        		map.put("orderId", billId);
				        		System.out.println("数据:"+jsonObject);
			        			return map;
			        		}
			        	}else{
				        		map.put("msg", "代付失败");
				        		map.put("tradeNo", tradeNo);
				        		map.put("orderId", billId);
			        			return map;
			        	}
			        }
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}else{
				map.put("code", 403);
				map.put("Ncode", 403);
				map.put("msg", "放款信息不完整");
			}
		}else{
			map.put("code", 0);
			map.put("Ncode", 0);
			map.put("msg", "放款渠道已关闭");
		}
	    
		return map;
	}



	@Override
	public Map<String, Object> Payment(BigDecimal amount,String returnUrl,Integer companyId,Integer userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Bankcard ba = new Bankcard();
		ba.setCompanyId(companyId);
		ba.setUserId(userId);
		Bankcard ban = stdao.SelectBanl(ba);//查询银行卡
		MapUtil maputil = new MapUtil();
		Loan_setting loan = new Loan_setting();
		loan.setCompanyId(companyId);
		loan.setName("必付");
		String a =  stdao.SelectLoanStatus(loan);//放款状态  1  开启    2 关闭
		SimpleDateFormat sim = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String billId = sim.format(new Date());
		
		if(a.equals("1")){
		
		if(ban.getTiedCardPhone() != null && ban.getBankcardName() != null && ban.getCstmrnm() != null && ban.getBankcardTypeName() != null
					&& ban.getTiedCardPhone() != "" && ban.getBankcardName() != "" && ban.getCstmrnm() != "" && ban.getBankcardTypeName() != ""){
		Map<String, String> payParams=new HashMap<String, String>();    
	    payParams.put("method","zpay.trade.znew");
        payParams.put("version","1.0");
        payParams.put("mchId",ZpayConfig.NEW_MERCHANT_NO);
        payParams.put("amount",amount.setScale(2).toString());
        payParams.put("appType","xunpay");
        payParams.put("type", "3");
        payParams.put("payType", "1");
        payParams.put("notifyUrl",ZpayConfig.RECHARGE_NOTIFY_NEWPAY);//异步通知地址
        payParams.put("returnUrl", returnUrl);
        payParams.put("orderId", billId);//订单ID
        payParams.put("orderUid", "MDBS");//客户ID
        payParams.put("orderName", "米多宝");//客户名称
        payParams.put("khName", ban.getCstmrnm());//收款人姓名
        payParams.put("khBankCode", maputil.getBankMap(ban.getBankcardTypeName()));//收款人银行编码
        payParams.put("khCardCode", ban.getBankcardName());//收款银行卡号
        payParams.put("khMobile", ban.getTiedCardPhone());//银行预留手机号
        payParams.put("khIdCode", ban.getIDcardnumber());//收款人身份证号
        String resultSign= SignUtils.getSign(payParams,ZpayConfig.NEW_MD5_KEY).toUpperCase();
        payParams.put("sign",resultSign);
        try {
			HttpClient httpClient=new HttpClient(ZpayConfig.GATEWAY_URL, 30000, 30000);
			String resp = httpClient.send(payParams, "utf-8");
			System.out.println("数据:"+resp);
	        if(StringUtils.isNotBlank(resp)){
	        	JSONObject jsonObject=JSONObject.parseObject(resp);
	        	String code=jsonObject.getString("code");
	        	String url = jsonObject.getString("qrUrl");
	        	String orderNumber = jsonObject.getString("orderId");
	        	String msg = jsonObject.getString("msg");
	        	if(code.equals("SUCCESS")){
	        		if(SignUtils.checkParam(JSONObject.toJavaObject(jsonObject, Map.class) , ZpayConfig.NEW_MD5_KEY)){
	        			map.put("url", url);
	        			map.put("billId", orderNumber);
	        			map.put("msg", msg);
	        			map.put("status", 2);
	        			map.put("loaName", "必付");
	        			return map;
	        		}
	        	}
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
			}else{
				map.put("code", 403);
				map.put("Ncode", 403);
				map.put("msg", "放款信息不完整");
			}
		}else{
				map.put("code", 0);
				map.put("Ncode", 0);
				map.put("msg", "通道已关闭");
		}
			
		return map;
	}
	
	
	
	
	
	
	@Override
	public Map<String, Object> DefePayment(BigDecimal amount,String returnUrl,Integer companyId,Integer userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Bankcard ba = new Bankcard();
		ba.setCompanyId(companyId);
		ba.setUserId(userId);
		Bankcard ban = stdao.SelectBanl(ba);//查询银行卡
		MapUtil maputil = new MapUtil();
		Loan_setting loan = new Loan_setting();
		loan.setCompanyId(companyId);
		loan.setName("必付");
		String a =  stdao.SelectLoanStatus(loan);//放款状态  1  开启    2 关闭
		SimpleDateFormat sim = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String billId = sim.format(new Date());//stdao.SelectOrderNumber(userId);//订单编号
		String orderId = stdao.SelectOrderNumber(userId);//订单编号
		if(a.equals("1")){
			
			if(ban.getTiedCardPhone() != null && ban.getBankcardName() != null && ban.getCstmrnm() != null && ban.getBankcardTypeName() != null
					&& ban.getTiedCardPhone() != "" && ban.getBankcardName() != "" && ban.getCstmrnm() != "" && ban.getBankcardTypeName() != ""){
		Map<String, String> payParams=new HashMap<String, String>();    
	    payParams.put("method","zpay.trade.znew");
        payParams.put("version","1.0");
        payParams.put("mchId",ZpayConfig.NEW_MERCHANT_NO);
        payParams.put("amount",amount.setScale(2).toString());
        payParams.put("appType","xunpay");
        payParams.put("type", "3");
        payParams.put("payType", "1");
        payParams.put("notifyUrl",ZpayConfig.RECHARGE_NOTIFY_NEWDEFE);//异步通知地址
        payParams.put("returnUrl", returnUrl);
        payParams.put("orderId", billId);//订单ID
        payParams.put("orderUid", "MDBS");//客户ID
        payParams.put("orderName", "米多宝");//客户名称
        payParams.put("khName", ban.getCstmrnm());//收款人姓名
        payParams.put("khBankCode", maputil.getBankMap(ban.getBankcardTypeName()));//收款人银行编码
        payParams.put("khCardCode", ban.getBankcardName());//收款银行卡号
        payParams.put("khMobile", ban.getTiedCardPhone());//银行预留手机号
        payParams.put("khIdCode", ban.getIDcardnumber());//收款人身份证号
        String resultSign= SignUtils.getSign(payParams,ZpayConfig.NEW_MD5_KEY).toUpperCase();
        payParams.put("sign",resultSign);
        try {
			HttpClient httpClient=new HttpClient(ZpayConfig.GATEWAY_URL, 30000, 30000);
			String resp = httpClient.send(payParams, "utf-8");
			System.out.println("数据:"+resp);
	        if(StringUtils.isNotBlank(resp)){
	        	JSONObject jsonObject=JSONObject.parseObject(resp);
	        	String code=jsonObject.getString("code");
	        	String url = jsonObject.getString("qrUrl");
	        	String orderNumber = jsonObject.getString("orderId");
	        	String number = jsonObject.getString("tradeNo");//还款流水号
	        	String msg = jsonObject.getString("msg");
	        	if(code.equals("SUCCESS")){
	        		if(SignUtils.checkParam(JSONObject.toJavaObject(jsonObject, Map.class) , ZpayConfig.NEW_MD5_KEY)){
	        			map.put("url", url);
	        			map.put("DefebillId", orderNumber);
	        			map.put("billId", billId);
	        			map.put("msg", msg);
	        			map.put("status", 2);
	        			map.put("loaName", "必付");
	        			return map;
	        		}
	        	}
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
			}else{
				map.put("code", 403);
				map.put("Ncode", 403);
				map.put("msg", "放款信息不完整");
			}
		}else{
				map.put("code", 0);
				map.put("Ncode", 0);
				map.put("msg", "通道已关闭");
		}
			
		return map;
	}



	@Override
	public Integer UpdateRepayment(Repayment orderIds) {
		return newdao.Repaymentsa(orderIds);
	}



	@Override
	public Integer getOrderId(String orderIds) {
		return newdao.getOrderId(orderIds);
	}

	
	
	
	


	@Override
	public Integer Updatepaymemt(Payment_record pay) {
		Integer a = null;
		Integer id = newdao.UpdateOrders(pay.getOrderId());
		if(id != null){
			newdao.Updatepaymemt(pay);
			a=1;
		}
		return a;
	}



	@Override
	public Orders getOrders(String orderNumber) {
		return newdao.getOrders(orderNumber);
	}



	@Override
	public Payment_record getPayment(Integer orderId) {
		// TODO Auto-generated method stub
		return newdao.getPayment(orderId);
	}
	
	
	
	
	
	
	

}
