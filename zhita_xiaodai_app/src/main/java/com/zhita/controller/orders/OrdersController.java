


package com.zhita.controller.orders;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.Lifecycle;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.sql.visitor.functions.Length;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;
import com.zhita.dao.manage.OrderdetailsMapper;
import com.zhita.model.manage.AuthenticationInformation;
import com.zhita.model.manage.LiftingAmount;
import com.zhita.service.manage.borrowmoneymessage.IntBorrowmonmesService;
import com.zhita.service.manage.liftingamount.IntLiftingamountServcie;
import com.zhita.service.manage.operational.OperationalService;
import com.zhita.service.manage.order.IntOrderService;
import com.zhita.service.manage.user.IntUserService;

import sun.text.resources.cldr.ar.FormatData_ar_MA;

@Controller
@RequestMapping("/Order")
public class OrdersController {
	@Autowired
	IntOrderService intOrderService;
	
	@Autowired
	IntLiftingamountServcie intLiftingamountServcie;
	
	@Autowired
	IntBorrowmonmesService intBorrowmonmesService;
	
	@Autowired
    OrderdetailsMapper orderdetailsMapper;
	
	@Autowired
	IntUserService intUserService;

	//获取用户借款额度
	   @RequestMapping("/getCanBorrowLines")
	    @ResponseBody
	    @Transactional
	    public Map<String, Object> getCanBorrowLines(int userId,int companyId) {
		   BigDecimal finalLine = new BigDecimal(0);
		   int num =0;
		   Map<String, Object> map = new HashMap<String, Object>();		   
		   int borrowNumber = intOrderService.borrowNumber(userId,companyId); //用户还款次数
		   BigDecimal canBorrowlines = intBorrowmonmesService.getCanBorrowlines(companyId); //初始额度
		   ArrayList<LiftingAmount> list = intLiftingamountServcie.getintLiftingamount(companyId);
		   Integer firstline = intLiftingamountServcie.getFirstline(companyId);
		   int lastuserHowManyConsecutivePayments = intLiftingamountServcie.getlastuserHowManyConsecutivePayments(companyId);//最后一个提额的次数
		   if(borrowNumber>lastuserHowManyConsecutivePayments) {
		    	 int ordersId =  intOrderService.getOrdersId(userId,companyId);//获取最后一个借款订单的id
		    	 BigDecimal lastLine = orderdetailsMapper.getlastLine(ordersId);//获取最后一次还款时的额度
		    	 map.put("finalLine", lastLine);
		    	 return map;
		   }
		  		   
		   if(firstline==null||borrowNumber<firstline) {
			   finalLine = canBorrowlines;
			   map.put("finalLine", finalLine);
		   }else {
			   for (LiftingAmount liftingAmount : list) {
				     int userHowManyConsecutivePayments = liftingAmount.getUserhowmanyconsecutivepayments();//还款多少次之后提额
				     if (borrowNumber<userHowManyConsecutivePayments) {
				    	 int increaseThequota = intLiftingamountServcie.getIncreaseThequota(num,companyId);//提高的额度				    		
				    	 BigDecimal decimalIncreaseThequota = new BigDecimal(Integer.toString(increaseThequota));
				    	 int ordersId =  intOrderService.getOrdersId(userId,companyId);//获取最后一个借款订单的id
				    	 BigDecimal lastLine = orderdetailsMapper.getlastLine(ordersId);//获取最后一次还款时的额度
				    	 finalLine = lastLine.add(decimalIncreaseThequota);
				    	 map.put("finalLine", finalLine);
				    	 return map;
				     }
				     num = userHowManyConsecutivePayments;
				}
		}

			return map;
		   
	   }
	   
	   
	   
	   
	 //展示用户的借款明细，并生成订单
		//获取用户借款额度
	    @RequestMapping("/setorder")
	    @ResponseBody
	    @Transactional
	    public Map<String, Object> setorder(int userId,int companyId,BigDecimal finalLine,String phone,String registeClient, String sourceName) {  //finalLine是上面那个接口得到的额度
	    	
	    	Map<String, Object> map = intBorrowmonmesService.getborrowMoneyMessage(companyId); 
	    	int borrowNumber = intOrderService.borrowNumber(userId,companyId); //用户还款次数
	        int	howManyTimesBorMoney = borrowNumber+1;//第几次借款
	    	BigDecimal ll = new BigDecimal(0);
	    	int lifeOfLoan = ((int) map.get("lifeOfLoan"));//借款期限
	    	ll=BigDecimal.valueOf((int)lifeOfLoan);//借款期限转成decimal类型
	    	int platformfeeRatio =  ((int) map.get("platformfeeRatio"));//平台服务费比率
	        BigDecimal pr = new BigDecimal(0);
	        pr=BigDecimal.valueOf((int)platformfeeRatio);//平台服务费比率
	        BigDecimal bd8 = new BigDecimal("100");
	        pr = pr.divide(bd8);//平台服务费比率除以100之后
	        BigDecimal platformServiceFee = (finalLine.multiply(pr)).setScale(2,BigDecimal.ROUND_HALF_UP);//平台服务费	        
	    	BigDecimal averageDailyInterest = (BigDecimal) map.get("averageDailyInterest");//贷款期限日均利息
	    	BigDecimal averageDailyInterest1 = averageDailyInterest.divide(bd8);//贷款期限日均利息除以100之后
	    	BigDecimal shouldTotalAmount = (((finalLine.multiply(averageDailyInterest1)).multiply(ll)).add(finalLine)).setScale(2,BigDecimal.ROUND_HALF_UP);//期限内应还总金额
	    	BigDecimal totalInterest =((finalLine.multiply(averageDailyInterest1)).multiply(ll)).setScale(2,BigDecimal.ROUND_HALF_UP);//期限内总利息
	    	BigDecimal actualAmountReceived = finalLine.subtract(platformServiceFee); //实际到账金额
	    	
	    	Calendar now = Calendar.getInstance(); 
	    	String year = now.get(Calendar.YEAR)+""; //年
	    	String month = now.get(Calendar.MONTH) + 1 + "";//月
	    	String day = now.get(Calendar.DAY_OF_MONTH)+"";//日
	    	String hour = now.get(Calendar.HOUR_OF_DAY)+"";//时
	    	String minute = now.get(Calendar.MINUTE)+"";//分
	    	String second = now.get(Calendar.SECOND)+"";//秒
	    	String afterFour = phone.substring(phone.length()-4); 
	    	String orderNumber = year+month+day+hour+minute+second+afterFour+"0"+(lifeOfLoan+"")+((borrowNumber+1)+"");//订单编号
	    	String orderCreateTime = String.valueOf(System.currentTimeMillis());//订单生成时间戳
	    	int riskmanagementFraction = intUserService.getRiskControlPoints(userId);//获取风控分数
	    	String shouldReturned = getShouldReturned(lifeOfLoan-1);//应还日时间戳,因为借款当天也算一天，所以要减去一天
	    	int num = intOrderService.setOrder(companyId,userId,orderNumber,orderCreateTime,lifeOfLoan,howManyTimesBorMoney,shouldReturned,riskmanagementFraction);
	    	if(num==1) {
	    		int orderId = intOrderService.getOrderId(orderNumber);
		    	num = orderdetailsMapper.setororderdetails(orderId,finalLine,averageDailyInterest,totalInterest,platformServiceFee,actualAmountReceived,registeClient,sourceName,shouldTotalAmount);
		    	if(num==1) {
		    		map.put("code", 200);
		    		map.put("msg","插入成功");
		    	}else {
					map.put("code",405);
					map.put("msg", "插入失败");
				}
	    	}
	    	
	    	
			return map;
		   
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

	   
//立即还款页面
	   @RequestMapping("/repayment")
	   @ResponseBody
	   @Transactional
	   public Map<String, Object> repayment(int userId,int companyId) {
		   Map<String, Object> map  = new HashMap<String, Object>();		
		   Map<String, Object> map1  = intOrderService.getRepayment(userId,companyId);
		   int orderId = (int) map1.get("id");
		   String orderStatus = (String) map1.get("orderStatus");
		   String shouldReturnTime = (String) map1.get("shouldReturnTime");
		   
		   
		   
   if(orderStatus.equals("0")||orderStatus.equals("2")) {
	   BigDecimal shouldReapyMoney  = orderdetailsMapper.getShouldReapyMoney(orderId);
	   
   }
		return map;
		   
		   
	   }	   
	   
	   
//还款按钮
   @RequestMapping("/reimbursement")
   @ResponseBody
   @Transactional
   public Map<String, Object> reimbursement(int userId,int companyId) {
	   Map<String, Object> map  = intOrderService.getReimbursement(userId,companyId);
	   
	return map;
	   
	   
   }
   
   
   public static void main(String[] args) {
	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");  
	   System.out.println(sdf.format(new Date(Long.valueOf(("1563188604000")))));
	    Date date = new Date();//取时间 
	    Calendar calendar  =   Calendar.getInstance();		 
	    calendar.setTime(date); //需要将date数据转移到Calender对象中操作
	    calendar.add(calendar.DATE, 7);//把日期往后增加n天.正数往后推,负数往前移动   
	    date=calendar.getTime();  //这个时间就是日期往后推一天的结果 
	    System.out.println(sdf.format(date));
}


}

