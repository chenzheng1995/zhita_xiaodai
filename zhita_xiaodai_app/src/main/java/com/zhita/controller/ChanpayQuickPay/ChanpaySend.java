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
		int borrowNumber = intOrderService.borrowNumber(userId,companyId); //用户还款次数
		Bankcard ba = new Bankcard();
		ba.setCompanyId(companyId);
		ba.setUserId(userId);
		Bankcard ban = chanser.SelectBank(ba);
		System.out.println("数据:"+ban.getTiedCardPhone() + ban.getBankcardName() + ban.getCstmrnm() + ban.getBankcardTypeName());
		
		if(ban.getTiedCardPhone() != null && ban.getBankcardName() != null && ban.getCstmrnm() != null && ban.getBankcardTypeName() != null
				&& ban.getTiedCardPhone() != "" && ban.getBankcardName() != "" && ban.getCstmrnm() != "" && ban.getBankcardTypeName() != ""){
		
    	Calendar now = Calendar.getInstance(); 
    	String year = now.get(Calendar.YEAR)+""; //年
    	String month = now.get(Calendar.MONTH) + 1 + "";//月
    	String day = now.get(Calendar.DAY_OF_MONTH)+"";//日
    	String hour = now.get(Calendar.HOUR_OF_DAY)+"";//时
    	String minute = now.get(Calendar.MINUTE)+"";//分
    	String second = now.get(Calendar.SECOND)+"";//秒
    	String afterFour = ban.getTiedCardPhone().substring(ban.getTiedCardPhone().length()-4); 
    	String orderNumber = year+month+day+hour+minute+second+afterFour+"0"+(lifeOfLoan+"")+((borrowNumber+1)+"");//订单编号
    	
    	
    	
		if(userId != null && TransAmt != null && companyId != null && lifeOfLoan != 0){
		
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
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, +1);
		date = calendar.getTime();
		SimpleDateFormat sim = new SimpleDateFormat("yyyyMMdd");
		map1.put("End_time", sim.format(date));
		map1.put("ReturnChanpay", returnchanpay);
		map1.put("lifeOfLoan", lifeOfLoan);
		map1.put("OrderNumber", orderNumber);
		map1.put("code", 200);
		}else{
			map1.put("msg", "userId,TransAmt,companyId,lifeOfLoan不能为空");
			map1.put("code", 0);
		}
		}else{
			map1.put("msg", "数据异常");
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
	public Map<String, Object> SendMing(String orderNumber,String BeginDate,String EndDate,int lifeOfLoan,String sourceName,String registeClient,Integer HowManyTimesBorMoney,
			Integer userId,Integer companyId,BigDecimal finalLine,BigDecimal averageDailyInterest,BigDecimal totalInterest,BigDecimal platformServiceFee,BigDecimal actualAmountReceived,BigDecimal shouldTotalAmount) {
		int borrowNumber = intOrderService.borrowNumber(userId,companyId); //用户还款次数
	    int	howManyTimesBorMoney = borrowNumber+1;//第几次借款
	    String orderCreateTime = String.valueOf(System.currentTimeMillis());//订单生成时间戳
    	int riskmanagementFraction = intUserService.getRiskControlPoints(userId);//获取风控分数
    	String shouldReturned = getShouldReturned(lifeOfLoan-1);//应还日时间戳,因为借款当天也算一天，所以要减去一天
    	String borrowMoneyWay = "立即贷";//贷款方式
	    
	    
		Map<String, String> map = this.requestBaseParameter();
		Map<String, Object> map1 = new HashMap<String, Object>();
		Payment_record pay = new Payment_record();
		
		
		map.put("TransCode", "C10001");
		map.put("OutTradeNo", ChanPayUtil.generateOutTradeNo());//官网唯一订单号
		map.put("OriOutTradeNo", orderNumber);//原交易订单号
		map.put("BeginIdx", "0");//查询起始位置，从0开始
		map.put("QueryNum", "1");//查询记录条数
		map.put("Status", "");//交易状态   1-成功   2-失败   3-处理中
		map.put("TransFlag", "2");//交易类型 1-代收；2-代付
		map.put("BeginDate", BeginDate);//起始时间
		map.put("EndDate", EndDate);//结束时间
		String staring = ChanPayUtil.sendPost(map, BaseConstant.CHARSET,
				BaseConstant.MERCHANT_PRIVATE_KEY);
		System.out.println("返回:"+staring);
		ShortReturn sreturn = JSON.parseObject(staring,ShortReturn.class);
		pay.setPipelinenumber(orderNumber);
		pay.setDeleted("0");
		pay.setPaymentmoney(finalLine);
		Integer code = Integer.valueOf(sreturn.getJsonarraydetaillist().get(0).getStatus());
		if(code == 1){
			pay.setStatus("支付成功");
			pay.setPipelinenumber(orderNumber);
			Integer addId = chanser.AddPayment_record(pay);
			if(addId != null){
				System.out.println("kaishu:"+companyId+","+userId+","+orderNumber+","+orderCreateTime+","+lifeOfLoan+","+HowManyTimesBorMoney+","+shouldReturned+","+riskmanagementFraction+","+borrowMoneyWay+"");
				int num = intOrderService.setOrder(companyId,userId,orderNumber,orderCreateTime,lifeOfLoan,howManyTimesBorMoney,shouldReturned,riskmanagementFraction,borrowMoneyWay);
		    	if(num==1) {
		    		int orderId = intOrderService.getOrderId(orderNumber);
			    	num = orderdetailsMapper.setororderdetails(orderId,finalLine,averageDailyInterest,totalInterest,platformServiceFee,actualAmountReceived,
			    			registeClient,sourceName,shouldTotalAmount);
			    	pay.setOrderId(orderId);
			    	chanser.UpdatePayment(pay);
			    	if(num==1) {
			    		map.put("ShortReturn", staring);
			    		map1.put("code", 200);
			    		map1.put("msg","插入成功");
			    	}else {
						map1.put("code",405);
						map1.put("msg", "插入失败");
					}
		    	}
			}
			
		}else{
			System.out.println("数据:"+sreturn.getJsonarraydetaillist().get(0).getStatus()+"AAAA"+sreturn.getJsonarraydetaillist().get(0).getRetCode());
			pay.setStatus("支付失败");
			map1.put("查看状态", sreturn.getJsonarraydetaillist().get(0).getStatus());
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