package com.zhita.controller.ChanpayQuickPay;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zhita.chanpayutil.BaseConstant;
import com.zhita.chanpayutil.BaseParameter;
import com.zhita.chanpayutil.ChanPayUtil;
import com.zhita.dao.manage.OrderdetailsMapper;
import com.zhita.model.manage.Bankcard;
import com.zhita.model.manage.Orders;
import com.zhita.model.manage.Payment_record;
import com.zhita.model.manage.ReturnChanpay;
import com.zhita.model.manage.ShortReturn;
import com.zhita.service.manage.chanpayQuickPay.Chanpayservice;
import com.zhita.service.manage.order.IntOrderService;
import com.zhita.service.manage.user.IntUserService;




@Controller
@RequestMapping("sendPara")
public class ChanpaySend extends BaseParameter{
		
		@Autowired
		private Chanpayservice chanser;
		
		
		@Autowired
		IntOrderService intOrderService;
		
		
		@Autowired
		IntUserService intUserService;
		
		
		
		@Autowired
	    OrderdetailsMapper orderdetailsMapper;
		
	
	
	/**
	 * 放款
	 * @param orderNumber
	 * @param AcctNo
	 * @param AcctName
	 * @param TransAmt
	 * @return
	 * 
	 */
	@ResponseBody
	@RequestMapping("send")
	public Map<String, Object> SendMoney(Integer userId,String TransAmt,Integer companyId,int lifeOfLoan){
		Map<String, Object> map1 = new HashMap<String, Object>();
		Bankcard ba = new Bankcard();
		ba.setCompanyId(companyId);
		ba.setUserId(userId);
		Bankcard ban = chanser.SelectBank(ba);
		
		int borrowNumber = intOrderService.borrowNumber(userId,companyId); //用户还款次数
        int	howManyTimesBorMoney = borrowNumber+1;//第几次借款
		Calendar now = Calendar.getInstance(); 
		String year = now.get(Calendar.YEAR)+""; //年
    	String month = now.get(Calendar.MONTH) + 1 + "";//月
    	String day = now.get(Calendar.DAY_OF_MONTH)+"";//日
    	String hour = now.get(Calendar.HOUR_OF_DAY)+"";//时
    	String minute = now.get(Calendar.MINUTE)+"";//分
    	String second = now.get(Calendar.SECOND)+"";//秒
    	String afterFour = ban.getTiedCardPhone().substring(ban.getTiedCardPhone().length()-4); 
    	String orderNumber = year+month+day+hour+minute+second+afterFour+"0"+(lifeOfLoan+"")+((borrowNumber+1)+"");//订单编号
    	String orderCreateTime = String.valueOf(System.currentTimeMillis());//订单生成时间戳
    	int riskmanagementFraction = intUserService.getRiskControlPoints(userId);//获取风控分数
    	String shouldReturned = getShouldReturned(lifeOfLoan-1);//应还日时间戳,因为借款当天也算一天，所以要减去一天
    	String borrowMoneyWay = "立即贷";//贷款方式
    	
    	Orders orders = new Orders();
    	orders.setCompanyId(companyId);
    	orders.setUserId(userId);
    	orders.setOrderNumber(orderNumber);
    	orders.setOrderCreateTime(orderCreateTime);
    	orders.setHowManyTimesBorMoney(String.valueOf(howManyTimesBorMoney));
    	orders.setRiskmanagementFraction(String.valueOf(riskmanagementFraction));
    	orders.setBorrowMoneyWay(borrowMoneyWay);
    	
		if(orderNumber != null && ban.getBankcardname() != null && ban.getCstmrnm() != null && TransAmt != null){
		
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
		ReturnChanpay returnchanpay = JSON.parseObject(rea, ReturnChanpay.class);
		map1.put("ReturnChanpay", returnchanpay);
		map1.put("Orders", orders);
		map1.put("lifeOfLoan", lifeOfLoan);
		map1.put("shouldReturned", shouldReturned);
		map1.put("code", 200);
		}else{
			map1.put("ReturnChanpay", "orderNumber,AcctNo,AcctName,TransAmt 不能为空");
			map1.put("code", 0);
		}
		
		return map1;
		
	}
	
	
	
	
	/**
	 * String OriOutTradeNo
	 * String BeginDate	开始时间  例子 20190726
	 * String EndDate	结束时间  例子 20190727
	 * @param OriOutTradeNo
	 * @param BeginDate
	 * @param EndDate
	 * @return
	 */
	@ResponseBody
	@RequestMapping("Mingxi")
	public Map<String, Object> SendMing(String OriOutTradeNo,String BeginDate,String EndDate,Orders orders,int lifeOfLoan,String shouldReturned,String sourceName,String registeClient,
			BigDecimal finalLine,BigDecimal averageDailyInterest,BigDecimal totalInterest,BigDecimal platformServiceFee,BigDecimal actualAmountReceived,BigDecimal shouldTotalAmount) {
		
		Map<String, String> map = this.requestBaseParameter();
		Map<String, Object> map1 = new HashMap<String, Object>();
		Payment_record pay = new Payment_record();
		
		map.put("TransCode", "C10001");
		map.put("OutTradeNo", ChanPayUtil.generateOutTradeNo());//官网唯一订单号
		map.put("OriOutTradeNo", OriOutTradeNo);//原交易订单号
		map.put("BeginIdx", "0");//查询起始位置，从0开始
		map.put("QueryNum", "1");//查询记录条数
		map.put("Status", "");//交易状态   1-成功   2-失败   3-处理中
		map.put("TransFlag", "2");//交易类型 1-代收；2-代付
		map.put("BeginDate", BeginDate);//起始时间
		map.put("EndDate", EndDate);//结束时间
		String staring = ChanPayUtil.sendPost(map, BaseConstant.CHARSET,
				BaseConstant.MERCHANT_PRIVATE_KEY);
		ShortReturn sreturn = JSON.parseObject(staring,ShortReturn.class);
		pay.setOrderNumber(OriOutTradeNo);
		BigDecimal money = new BigDecimal("TransAmt");
		pay.setPaymentmoney(money);
		if(sreturn.getJsonarraydetaillist().getRetCode() =="000000" || sreturn.getJsonarraydetaillist().getStatus() == "1"){
			pay.setStatus("支付成功");
			Integer addId = chanser.AddPayment_record(pay);
			if(addId != null){
				int num = intOrderService.setOrder(orders.getCompanyId(),orders.getUserId(),orders.getOrderNumber(),orders.getOrderCreateTime(),lifeOfLoan,
						Integer.valueOf(orders.getHowManyTimesBorMoney()),shouldReturned,Integer.valueOf(orders.getRiskmanagementFraction()),orders.getBorrowMoneyWay());
		    	if(num==1) {
		    		int orderId = intOrderService.getOrderId(orders.getOrderNumber());
			    	num = orderdetailsMapper.setororderdetails(orderId,finalLine,averageDailyInterest,totalInterest,platformServiceFee,actualAmountReceived,
			    			registeClient,sourceName,shouldTotalAmount);
			    	if(num==1) {
			    		map1.put("code", 200);
			    		map1.put("msg","插入成功");
			    	}else {
						map1.put("code",405);
						map1.put("msg", "插入失败");
					}
		    	}
			}
		
		}else{
			pay.setStatus("支付失败");
			chanser.AddPayment_record(pay);
			map1.put("ShortReturn", sreturn);
			map1.put("code", 0);
		}
		return map1;
	}
	
	
	
	
	
	public static String getShouldReturned(int day) {
	    Date date = new Date();//取时间 
	    Calendar calendar  =   Calendar.getInstance();		 
	    calendar.setTime(date); //需要将date数据转移到Calender对象中操作
	    calendar.add(calendar.DATE, day);//把日期往后增加n天.正数往后推,负数往前移动 
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
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		ChanpaySend send = new ChanpaySend();
		//send.SendMoney();
	//	send.SendMing("1563758797000", "20190726", "20190727");
	}
	
	
	
	
}
