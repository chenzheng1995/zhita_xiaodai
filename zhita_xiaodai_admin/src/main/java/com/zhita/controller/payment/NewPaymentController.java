package com.zhita.controller.payment;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.zhita.dao.manage.OrderdetailsMapper;
import com.zhita.model.manage.Orders;
import com.zhita.model.manage.Payment_record;
import com.zhita.model.manage.Repayment;
import com.zhita.service.manage.Statistic.Statisticsservice;
import com.zhita.service.manage.borrowmoneymessage.IntBorrowmonmesService;
import com.zhita.service.manage.chanpayQuickPay.Chanpayservice;
import com.zhita.service.manage.newpayment.NewPaymentservice;
import com.zhita.service.manage.order.IntOrderService;
import com.zhita.service.manage.user.IntUserService;
import com.zhita.util.RedisClientUtil;




@Controller
@RequestMapping("newpay")
public class NewPaymentController {
	
	
	
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
	
	
	//还款
	@RequestMapping(value = "/callback", consumes = "multipart/form-data", method = RequestMethod.POST)
    public String callback(MultipartHttpServletRequest request) {
	 RedisClientUtil redis = new RedisClientUtil();
	
	 String code = request.getParameter("code");
     String msg = request.getParameter("msg");
     String orderId = request.getParameter("orderId");
  /*   String mchId = request.getParameter("mchId");
     String orderUid = request.getParameter("orderUid");*/
     String tradeNo = request.getParameter("tradeNo");
     String status = request.getParameter("status");
    // String amount = request.getParameter("amount");
/*     String sign = request.getParameter("sign");*/
     String biaoshiid = redis.get("userId"+tradeNo);
     String orderIds = redis.get("orderId"+biaoshiid);//获取订单编号
     System.out.println("收款回调接收到的参数:"+code+"返回放款状态:"+status+"msg:"+msg+"orderId:"+orderId);
     
     Map<String, Object> map = new HashMap<String, Object>();
     Repayment repay = new Repayment();
     Integer oid = newsim.getOrderId(orderIds);
     repay.setOrderid(oid);
     repay.setPipelinenumber("Rsn_"+tradeNo);
     Integer updateId = newsim.UpdateRepayment(repay);
     if(updateId != null){
    	 Orders ord = new Orders();
    	 ord.setOrderNumber(orderIds);
    	 servie.UpdateOrders(ord);
    	 map.put("msg", "数据插入成功");
         map.put("Code", "200");
     } else {
         map.put("msg", "数据插入失败");
         map.put("Code", "405");
     }
     return "SUCCESS";
}
	
	
	
	
	
	@RequestMapping(value = "/callbackdefe", consumes = "multipart/form-data", method = RequestMethod.POST)
    public String callbackDefe(MultipartHttpServletRequest request) {
	
		 RedisClientUtil redis = new RedisClientUtil();
			
		 String code = request.getParameter("code");
	     String msg = request.getParameter("msg");
	     String orderId = request.getParameter("orderId");
	  /*   String mchId = request.getParameter("mchId");
	     String orderUid = request.getParameter("orderUid");*/
	     String tradeNo = request.getParameter("tradeNo");
	     String status = request.getParameter("status");
	    // String amount = request.getParameter("amount");
	/*     String sign = request.getParameter("sign");*/
	     String biaoshiid = redis.get("DefeuserId"+tradeNo);
	     String orderIds = redis.get("DefeorderId"+biaoshiid);//获取订单编号
	     System.out.println("延期回调接收到的参数:"+code+"返回放款状态:"+status+"msg:"+msg+"orderId:"+orderId);
	     Map<String, Object> map = new HashMap<String, Object>();
	     Integer userId = Integer.valueOf(redis.get("DefeUserId"));
	     Orders ord = new Orders();
		 ord.setOrderNumber(orderIds);
		 ord.setUserId(userId);
	     Integer a = servie.UpdateDefeOrders(ord);
	     if(a != null){
				map.put("Ncode", 2000);
				map.put("msg", "插入成功");
				map.put("code", 200);
			}else{
				map.put("Ncode", 0);
				map.put("msg", "插入失败");
				map.put("code", 0);
			}
	     return "SUCCESS";
}
	
	
	
	
	
	
	
	

	@RequestMapping(value = "/callbackpay", consumes = "multipart/form-data", method = RequestMethod.POST)
    public String callbackpay(MultipartHttpServletRequest request) {
	
		// RedisClientUtil redis = new RedisClientUtil();
			
		 String code = request.getParameter("code");
	     String msg = request.getParameter("msg");
	     String orderId = request.getParameter("orderId");
	     String status = request.getParameter("status");
	     String orderStatus = msg;
		 
	     System.out.println("代付回调接收到的参数:"+code+"返回放款状态:"+status+"msg:"+msg+"orderId:"+orderId);
	     Map<String, Object> map = new HashMap<String, Object>();
	     Integer oid = newsim.getOrderId(orderId);
	     if(status.equals("SUCCESS")){
	    	 Payment_record pay = new Payment_record();
	    	 pay.setOrderId(oid);
	    	 pay.setStatus("放款成功");
	    	 newsim.Updatepaymemt(pay);
	    	 map.put("code", 200);
	    	 map.put("Ncode", 2000);
	     }else{
	    	 Payment_record pay = new Payment_record();
	    	 pay.setOrderId(oid);
	    	 pay.setStatus("放款失败");
	    	 newsim.Updatepaymemt(pay);
	    	 chanser.DeleteOrderNumber(orderId,orderStatus);
	    	 map.put("code", 0);
	    	 map.put("Ncode", 0);
	     }
	     
	     return "SUCCESS";
}
	
	
	
	
	
}
