package com.zhita.controller.payment;

import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.zhita.controller.payment.util.SignUtils;
import com.zhita.controller.payment.zpay.ZpayConfig;
import com.zhita.dao.manage.OrderdetailsMapper;
import com.zhita.model.manage.Deferred;
import com.zhita.model.manage.Orders;
import com.zhita.model.manage.Payment_record;
import com.zhita.model.manage.Repayment;
import com.zhita.model.manage.SmsSendRequest;
import com.zhita.service.manage.SmsReport.Smservice;
import com.zhita.service.manage.Statistic.Statisticsservice;
import com.zhita.service.manage.borrowmoneymessage.IntBorrowmonmesService;
import com.zhita.service.manage.chanpayQuickPay.Chanpayservice;
import com.zhita.service.manage.newpayment.NewPaymentservice;
import com.zhita.service.manage.order.IntOrderService;
import com.zhita.service.manage.user.IntUserService;
import com.zhita.util.CommonUtils;
import com.zhita.util.PhoneDeal;
import com.zhita.util.RedisClientUtil;




@Controller
@RequestMapping("newpay")
public class NewPaymentController {
	
	
	
	@Autowired
	private Smservice serv;
	
	
	@Autowired
	private Statisticsservice servie;
	
	
	
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
	
	
	
	
	
	@ResponseBody
	@RequestMapping(value = "/callbackdefe")
    public Object callbackDefe(HttpServletRequest request,HttpServletResponse response) {
		CommonUtils com = new CommonUtils();
		PhoneDeal p = new PhoneDeal();
		System.out.println("调用延期回调,111111111");
		Map<String, String> requestmap = com.getParameterMap(request);
		String resultSign= SignUtils.getSign(requestmap,ZpayConfig.NEW_MD5_KEY).toUpperCase();
		requestmap.put("sign",resultSign);
		 try {
			 String responseStr = "ERROR";
			 String code = request.getParameter("code");
			 if(code.equals("SUCCESS")){
				 
				 if(SignUtils.checkParam(requestmap, ZpayConfig.MD5_KEY)){
					 String orderId = requestmap.get("orderId");
					 //商户订单号
				     Deferred defe = chanpayservice.getDefeDele(orderId);
					 if(defe != null){
						 System.out.println("aaa:"+defe.getDeleted());
						 if(defe.getDeleted().equals("0")){
							 responseStr = "SUCCESS"; 
						 }else{
							 System.out.println("aaa:"+defe.getDeleted());
						     Orders ord = new Orders();
							 ord.setOrderNumber(chanpayservice.getOrderNumberDefe(defe.getOrderid()));
							 ord.setUserId(chanpayservice.getUserId(defe.getOrderid()));
					    	 Integer i = servie.UpdateDefeOrders(ord);
					    	 SmsSendRequest sm = new SmsSendRequest();
					    	 if(i != null){
					    		 responseStr = "SUCCESS"; 
					    	 }else{
					    		 responseStr = "ERROR"; 
					    	 }
						 }
					 }
				 }
			 }
			 
			 PrintWriter writer = response.getWriter();
			 writer.print(responseStr);
			 writer.close();
		} catch (Exception e) {
			System.out.println("aaaa延期");
		}
		 return "SUCCESS";
	}
	
	
	
	
	//还款
	@ResponseBody
	@RequestMapping(value = "/callback")
    public Object callback(HttpServletRequest request,HttpServletResponse response) {
		CommonUtils com = new CommonUtils();
		PhoneDeal p = new PhoneDeal();
		System.out.println("还钱回调,111111111");
		Map<String, String> requestmap = com.getParameterMap(request);
		String resultSign= SignUtils.getSign(requestmap,ZpayConfig.NEW_MD5_KEY).toUpperCase();
		requestmap.put("sign",resultSign);
		 try {
			 String responseStr = "ERROR";
			 String code = request.getParameter("code");
			 System.out.println("code="+code);
			 if(code.equals("SUCCESS")){
				 
				 if(SignUtils.checkParam(requestmap, ZpayConfig.MD5_KEY)){
					 String orderId = requestmap.get("orderId");
					 Repayment repayment = chanpayservice.getRepayment(orderId);//获取还款数据
					 if(repayment != null){
						if(repayment.getReoaybtai().equals("0")){
							responseStr = "SUCCESS"; 
						}else {
							Integer updateId = chanpayservice.UpdateRepayStatusAA(repayment.getPipelinenumber(),repayment.getOrderid());//修改还款状态
							Orders ord = chanpayservice.getOrders(repayment.getOrderid());
							if(updateId != null){
								servie.UpdateOrders(ord);
								responseStr = "SUCCESS";
							}else{
								responseStr = "ERROR";
							}
						}
					 }
				 }
			 }
			 
			 PrintWriter writer = response.getWriter();
			 writer.print(responseStr);
			 writer.close();
		} catch (Exception e) {
			System.out.println("aaaa");
		}
		 return "SUCCESS";
}
	
	
	
	
	
	@RequestMapping(value = "/callbackdefeCC")
    public void callbackDefeCC(HttpServletRequest request,HttpServletResponse response) {
		RedisClientUtil redis = new RedisClientUtil();
		System.out.println("调用言言言,111111111");
		CommonUtils com = new CommonUtils();
		Map<String, String> requestmap = com.getParameterMap(request);
		 String resultSign= SignUtils.getSign(requestmap,ZpayConfig.NEW_MD5_KEY).toUpperCase();
		 requestmap.put("sign",resultSign);
		 try {
			 String responseStr = "ERROR";
				 if(SignUtils.checkParam(requestmap, ZpayConfig.MD5_KEY)){
					 String tradeNo = requestmap.get("tradeNo");
					 String orderId = requestmap.get("orderId");
					 String status = requestmap.get("status");
					 //商户订单号
				     Deferred defe = chanpayservice.getDefeDele(orderId);
					 if(defe != null){
						 if(defe.getDeleted().equals("0")){
							 responseStr = "SUCCESS"; 
						 }else{
							 Integer userId = Integer.valueOf(redis.get("DefeUserId"+orderId));
						     Orders ord = new Orders();
							 ord.setOrderNumber(chanpayservice.getOrderNumberDefe(defe.getOrderid()));
							 ord.setUserId(userId);
					    	 if(status.equals("SUCCESS")){
					    		 servie.UpdateDefeOrders(ord);
					    	 }else if(status.equals("PAYERROR")){
					    		 responseStr = "ERROR";
					    	 }
							 
					    	 
						 }
					 }
				 }
			 
			 PrintWriter writer = response.getWriter();
			 writer.print(responseStr);
			 writer.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
			
//		 String code = request.getParameter("code");
//	     String msg = request.getParameter("msg");
//	     String orderId = request.getParameter("orderId");
//	  /*   String mchId = request.getParameter("mchId");
//	     String orderUid = request.getParameter("orderUid");*/
//	     String tradeNo = request.getParameter("tradeNo");
//	     String status = request.getParameter("status");
//	    // String amount = request.getParameter("amount");
//	/*     String sign = request.getParameter("sign");*/
//	     String biaoshiid = redis.get("DefeuserId"+tradeNo);
//	     String orderIds = redis.get("DefeorderId"+biaoshiid);//获取订单编号
//	     System.out.println("延期回调接收到的参数:"+code+"返回放款状态:"+status+"msg:"+msg+"orderId:"+orderId);
//	     Map<String, Object> map = new HashMap<String, Object>();
//	     Integer userId = Integer.valueOf(redis.get("DefeUserId"));
//	     Orders ord = new Orders();
//		 ord.setOrderNumber(orderIds);
//		 ord.setUserId(userId);
//	     Integer a = servie.UpdateDefeOrders(ord);
//	     if(a != null){
//				map.put("Ncode", 2000);
//				map.put("msg", "插入成功");
//				map.put("code", 200);
//			}else{
//				map.put("Ncode", 0);
//				map.put("msg", "插入失败");
//				map.put("code", 0);
//			}
//	     return "SUCCESS";
}
	
	
	
	
	
	
	
	
	@ResponseBody
	@RequestMapping(value = "/callbackpay")
    public Object callbackpay(HttpServletRequest request,HttpServletResponse response) {
		CommonUtils com = new CommonUtils();
		PhoneDeal p = new PhoneDeal();
		System.out.println("调用支付回调,111111111");
		Map<String, String> requestmap = com.getParameterMap(request);
		String resultSign= SignUtils.getSign(requestmap,ZpayConfig.NEW_MD5_KEY).toUpperCase();
		requestmap.put("sign",resultSign);
		 try {
			 String responseStr = "ERROR";
			 String code = request.getParameter("code");
			 if(code.equals("SUCCESS")){
				 
				 if(SignUtils.checkParam(requestmap, ZpayConfig.MD5_KEY)){
					 String orderId = requestmap.get("orderId");
					 String status = requestmap.get("status");
					 String msg = requestmap.get("msg");
					 //商户订单号
					 Integer order = newsim.getOrderId(orderId);
					 String nophone = chanpayservice.getPhone(orderId);
			    	 String phone = p.decryption(nophone);
					 if(order!=null){
						 Payment_record pays = newsim.getPayment(order);
						 if(pays.getStatus().equals("支付成功")){
							 responseStr = "SUCCESS"; 
						 }else{
							 Integer oid = newsim.getOrderId(orderId);
							 Payment_record pay = new Payment_record();
					    	 pay.setOrderId(oid);
					    	 SmsSendRequest sm = new SmsSendRequest();
					    	 if(status.equals("SUCCESS")){
					    		 pay.setStatus("支付成功");
				 	     		 sm.setMsg("【到款提醒】恭喜您的申请已经到账，请查收。");
				 	     		 sm.setPhone(phone);
					    		 newsim.Updatepaymemt(pay);
					    	 }else if(status.equals("PAYERROR")){
					    		 chanser.DeleteOrderNumber(orderId,msg);
					    		 sm.setMsg("【未到款提醒】很抱歉，您的申请未到帐，请登录APP重试或询人工客服。");
				 	     		 sm.setPhone(phone);
					    		 pay.setStatus("支付失败");
					    		 newsim.Updatepaymemt(pay);
					    	 }
					    	 serv.SendSmOne(sm);
					    	 responseStr = "SUCCESS";
						 }
					 }
				 }
			 }
			 
			 PrintWriter writer = response.getWriter();
			 writer.print(responseStr);
			 writer.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
//		 String code = request.getParameter("code");
//	     String msg = request.getParameter("msg");
//	     String orderId = request.getParameter("orderId");
//	     String status = request.getParameter("status");
//	     String orderStatus = msg;
//		 
//	     System.out.println("代付回调接收到的参数:"+code+"返回放款状态:"+status+"msg:"+msg+"orderId:"+orderId);
//	     
//	     Integer oid = newsim.getOrderId(orderId);
//	     if(status.equals("SUCCESS")){
//	    	 Payment_record pay = new Payment_record();
//	    	 pay.setOrderId(oid);
//	    	 pay.setStatus("支付成功");
//	    	 newsim.Updatepaymemt(pay);
//	    	 map.put("code", 200);
//	    	 map.put("Ncode", 2000);
//	     }else{
//	    	 Payment_record pay = new Payment_record();
//	    	 pay.setOrderId(oid);
//	    	 pay.setStatus("支付失败");
//	    	 newsim.Updatepaymemt(pay);
//	    	 chanser.DeleteOrderNumber(orderId,orderStatus);
//	    	 map.put("code", 0);
//	    	 map.put("Ncode", 0);
//	     }
//	     
//	     return "SUCCESS";
		 return "SUCCESS";
}
	
	
	
	
	
}
