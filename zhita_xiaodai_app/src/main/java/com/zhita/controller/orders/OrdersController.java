
package com.zhita.controller.orders;

import java.math.BigDecimal;
import java.text.DateFormat;
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
import com.zhita.dao.manage.BankcardMapper;
import com.zhita.dao.manage.BankcardTypeMapper;
import com.zhita.dao.manage.OrderdetailsMapper;
import com.zhita.model.manage.AuthenticationInformation;
import com.zhita.model.manage.LiftingAmount;
import com.zhita.service.manage.bankcard.BankcardService;
import com.zhita.service.manage.borrowmoneymessage.IntBorrowmonmesService;
import com.zhita.service.manage.deferredsettings.IntDeferredsetService;
import com.zhita.service.manage.liftingamount.IntLiftingamountServcie;
import com.zhita.service.manage.operational.OperationalService;
import com.zhita.service.manage.order.IntOrderService;
import com.zhita.service.manage.user.IntUserService;


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
	
	@Autowired
	IntDeferredsetService intDeferredsetService;
	
	@Autowired
	BankcardMapper bankcardMapper;
	
	@Autowired
	BankcardTypeMapper bankcardTypeMapper;
	
	

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
	   
	   
	   
	   
	 //立即提现页面
	    @RequestMapping("/borrowinginformation")
	    @ResponseBody
	    @Transactional
	    public Map<String, Object> borrowinginformation(int userId,int companyId,BigDecimal finalLine,String phone,String registeClient, String sourceName) {  //finalLine是上面那个接口得到的额度
	    	 Map<String, Object> map1 = new HashMap<String, Object>();		   
	    	Map<String, Object> map = intBorrowmonmesService.getborrowMoneyMessage(companyId); 
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
	    	map1.put("code", 200);
	    	map1.put("lifeOfLoan", lifeOfLoan);
	    	map1.put("averageDailyInterest1", averageDailyInterest1);
	    	map1.put("finalLine", finalLine);
	    	map1.put("platformServiceFee", platformServiceFee);
	    	map1.put("shouldTotalAmount", shouldTotalAmount);
	    	map1.put("actualAmountReceived", actualAmountReceived);
	    	map1.put("totalInterest", totalInterest);
	    	map1.put("averageDailyInterest", averageDailyInterest);
			return map1;
	    	
	    	
	    	
	    	
	    }
	    //立即提现按钮
	    @RequestMapping("/setorder")
	    @ResponseBody
	    @Transactional
	    public Map<String, Object> setorder(int userId,int companyId,BigDecimal finalLine,String phone,String registeClient, String sourceName,int lifeOfLoan,BigDecimal averageDailyInterest,BigDecimal totalInterest,BigDecimal platformServiceFee,BigDecimal actualAmountReceived,BigDecimal shouldTotalAmount) {  //finalLine是上面那个接口得到的额度
	    	 Map<String, Object> map = new HashMap<String, Object>();		 
		    int borrowNumber = intOrderService.borrowNumber(userId,companyId); //用户还款次数
		    int	howManyTimesBorMoney = borrowNumber+1;//第几次借款
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
	    	String borrowMoneyWay = "立即贷";//贷款方式
	    	int num = intOrderService.setOrder(companyId,userId,orderNumber,orderCreateTime,lifeOfLoan,howManyTimesBorMoney,shouldReturned,riskmanagementFraction,borrowMoneyWay);
	    	if(num==1) {
	    		int orderId = intOrderService.getOrderId(orderNumber);
		    	num = orderdetailsMapper.setororderdetails(orderId,finalLine,averageDailyInterest,totalInterest,platformServiceFee,actualAmountReceived,registeClient,sourceName,shouldTotalAmount);
		    	if(num==1) {
		    		map.put("code", 200);
		    		map.put("msg","插入成功");
		    		map.put("orderNumber",orderNumber);
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
		   String shouldReturnTime = (String) map1.get("shouldReturnTime");//应还时间
		   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		   shouldReturnTime = sdf.format(new Date(Long.parseLong(shouldReturnTime))); // 时间戳转换日期
		   BigDecimal shouldReapyMoney  = orderdetailsMapper.getShouldReapyMoney(orderId);//共需还钱
		   
		   
		   
   if(orderStatus.equals("0")||orderStatus.equals("2")) {//逾期前
	   map.put("shouldReturnTime",shouldReturnTime);
	   map.put("shouldReapyMoney",shouldReapyMoney);
	   map.put("msg","未逾期");
	   map.put("code",1);
	   
	   
   }
   if(orderStatus.equals("1")) {//逾期后
	   BigDecimal interestInAll = orderdetailsMapper.getinterestInAll(orderId);//总利息
	   String overdueNumberOfDays = orderdetailsMapper.getoverdueNumberOfDays(orderId);//逾期天数
	   BigDecimal overdueMoney = shouldReapyMoney.add(interestInAll);//逾期应还钱
	   map.put("overdueMoney", overdueMoney);
	   map.put("overdueNumberOfDays", overdueNumberOfDays);
	   map.put("msg","已逾期");
	   map.put("code",0);
	   
   }
   
		return map;
		   
		   
	   }	   
	   
	   @RequestMapping("/getrepayment")
	   @ResponseBody
	   @Transactional
	   public Map<String, Object> getrepayment(int userId,int companyId) {
		   Map<String, Object> map  = new HashMap<String, Object>();		
		   Map<String, Object> map1  = intOrderService.getRepayment(userId,companyId);
		   String TrxId = (String) map1.get("orderNumber");//订单号
           Map<String, Object> map2 = bankcardMapper.getbankcard(userId);
           int bankcardTypeId = (int) map2.get("bankcardTypeId");
           String bankcardTypeName = bankcardTypeMapper.getbankcardTypeName(bankcardTypeId);//银行卡类型
           String AcctName = (String) map2.get("cstmrnm");//持卡人姓名
		   String AcctNo = (String) map2.get("bankcardName");//卡号
		   String CardBegin = AcctNo.substring(0,6);//卡号前6位
		   String CardEnd = AcctNo.substring(AcctNo.length()-4,AcctNo.length());//卡号前4位
		   map.put("TrxId", TrxId);
		   map.put("CardBegin", CardBegin);
		   map.put("CardEnd", CardEnd);
		   map.put("bankcardTypeName",bankcardTypeName);
		   map.put("AcctName",AcctName);
		   map.put("AcctNo",AcctNo);
		return map;
		   
	   }
	   

//还款按钮
   @RequestMapping("/reimbursement")
   @ResponseBody
   @Transactional
   public Map<String, Object> reimbursement(int userId,int companyId,String overdueState) {//shouldReapyMoney 应还总金额，从上个接口获取,overdueState 逾期状态
	   Map<String, Object> map1  = new HashMap<String, Object>();	
	   BigDecimal shouldReapyMoney = null;
	   if(overdueState.equals("0")) {//未逾期
		   Map<String, Object> map  = intOrderService.getRepayment(userId,companyId);
		   int id = (int) map.get("id");
		   shouldReapyMoney = orderdetailsMapper.getShouldReapyMoney(id);
	   }
	   if(overdueState.equals("1")) {//已逾期
		   Map<String, Object> map  = intOrderService.getRepayment(userId,companyId);
		   int id = (int) map.get("id");
		   shouldReapyMoney = orderdetailsMapper.getShouldReapyMoney(id);
		   BigDecimal interestPenaltySum = orderdetailsMapper.interestPenaltySum(id);
		   shouldReapyMoney = shouldReapyMoney.add(interestPenaltySum);
	   }
//	   int borrowTimeLimit  = intOrderService.getBorrowTimeLimit(userId,companyId);	//借款期限   
//	   Map<String, Object> map = intBorrowmonmesService.getborrowMoneyMessage(companyId); 
//	   BigDecimal pr = new BigDecimal(0);
//       BigDecimal bd8 = new BigDecimal("100");
//       pr = pr.divide(bd8);//平台服务费比率除以100之后
//   	BigDecimal averageDailyInterest = (BigDecimal) map.get("averageDailyInterest");//贷款期限日均利息
//   	BigDecimal averageDailyInterest1 = averageDailyInterest.divide(bd8);//贷款期限日均利息除以100之后
   	Map<String, Object> map2  = bankcardMapper.getbankcard(userId);
   	String bankcardName = (String) map2.get("bankcardName");
   	int bankcardTypeId = (int) map2.get("bankcardTypeId");
   	String bankcardTypeName = bankcardTypeMapper.getbankcardTypeName(bankcardTypeId);
   	String payment = bankcardTypeName+bankcardName;//支付方式

   	map1.put("shouldReapyMoney", shouldReapyMoney);
//   	map1.put("averageDailyInterest1", averageDailyInterest1);
//   	map1.put("borrowTimeLimit", borrowTimeLimit);
   	map1.put("payment", payment);
	return map1;
	   
	   
   }
   
   
   //立即还款按钮
   @RequestMapping("/repaymentbutton")
   @ResponseBody
   @Transactional
   public Map<String, Object> repaymentbutton (int userId,int companyId) {
	   
	return null;
	   
   }  
   
   
   //判断该用户是否已借钱
   @RequestMapping("/whetherborrow")
   @ResponseBody
   @Transactional
   public Map<String, Object> whetherborrow (int userId,int companyId) {
	   Map<String, Object> map  = new HashMap<String, Object>();
	   int num = intOrderService.getorderStatus(userId,companyId);
	   if(num==0) {
		   map.put("code", "1");
		   map.put("msg", "没有正在执行的订单");
	   }else {
		   map.put("code", "2");
		   map.put("msg", "有正在执行的订单");
	}
	   
	return map;
         
   }
   
   
   //判断用户是否可以延期
   @RequestMapping("/whetherdelay")
   @ResponseBody
   @Transactional
   public Map<String, Object> whetherdelay (int userId,int companyId) throws ParseException {
	   Map<String, Object> map  = new HashMap<String, Object>();
       String orderStatus = intOrderService.getorderStatus1(userId, companyId);
       if(orderStatus.equals("2")) {
    	   map.put("msg","不能延期");
    	   map.put("code",0);
       }else {
    	   
    	   Map<String, Object> map1 = intDeferredsetService.getDeferredset(companyId); 
    	   int overdueHowdayCanDeferred = (int) map1.get("overdueHowdayCanDeferred");//逾期多少天前可延期
    	   int maximumCanDeferredTime = (int) map1.get("maximumCanDeferredTime");//最多可延期次数
    	   int currentDelays = intUserService.getdelayTimes(userId);//当前延期次数
		   Map<String, Object> map2  = intOrderService.getRepayment(userId,companyId);
		   String shouldReturnTime = (String) map2.get("shouldReturnTime");//应还时间
		   SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		   df.format(new Date());// new Date()为获取当前系统时间
           SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
           String sd = sdf.format(new Date(Long.parseLong(shouldReturnTime))); // 时间戳转换日期
           Date date = new SimpleDateFormat("yyyy-MM-dd").parse(df.format(new Date()));//取时间 
           Calendar calendar  =   Calendar.getInstance();		 
		    calendar.setTime(date); //需要将date数据转移到Calender对象中操作
		    calendar.add(calendar.DATE, overdueHowdayCanDeferred);//把日期往后增加n天.正数往后推,负数往前移动 
		    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");  
		    date=calendar.getTime();  //这个时间就是日期往后推一天的结果 
		    sdf1.format(date);
		    int num = compare_date(sdf1.format(date),sd);
		    if(num==1) {
		    	map.put("msg","不能延期");
		    	map.put("code",0);
		    }else {
				if(currentDelays<=maximumCanDeferredTime) {
					map.put("msg","可以延期");
					map.put("code",1);
				}else {
					map.put("msg","不能延期");
					map.put("code",0);
				}
			}
	}
	   
	return map;
         
   }
   
   
 //延期页面
   @RequestMapping("/deferredpage")
   @ResponseBody
   @Transactional
   public Map<String, Object> deferredpage (int userId,int companyId,BigDecimal finalLine) throws ParseException{
  	Map<String, Object> map = intBorrowmonmesService.getborrowMoneyMessage(companyId); 
    Map<String, Object> map1  = new HashMap<String, Object>();
  	BigDecimal ll = new BigDecimal(0);
  	int lifeOfLoan = ((int) map.get("lifeOfLoan"));//延期天数
  	ll=BigDecimal.valueOf((int)lifeOfLoan);//借款期限转成decimal类型
  	int platformfeeRatio =  ((int) map.get("platformfeeRatio"));//平台服务费比率
      BigDecimal pr = new BigDecimal(0);
      pr=BigDecimal.valueOf((int)platformfeeRatio);//平台服务费比率
      BigDecimal bd8 = new BigDecimal("100");
      pr = pr.divide(bd8);//平台服务费比率除以100之后
      BigDecimal deferredexpenses = (finalLine.multiply(pr)).setScale(2,BigDecimal.ROUND_HALF_UP);//延期费用	
	  String beforeTime = intOrderService.getshouldReturnTime(userId,companyId);//延期前应还时间
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	beforeTime = sdf.format(new Date(Long.parseLong(beforeTime))); // 时间戳转换日期  
      Date date = new SimpleDateFormat("yyyy/MM/dd").parse(beforeTime);//取时间 
      Calendar calendar  =   Calendar.getInstance();		 
	    calendar.setTime(date); //需要将date数据转移到Calender对象中操作
	    calendar.add(calendar.DATE, lifeOfLoan);//把日期往后增加n天.正数往后推,负数往前移动 
	    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");  
	    date=calendar.getTime();  //这个时间就是日期往后推一天的结果 
	    String afterTime = sdf1.format(date);//延期后应还时间
	   	Map<String, Object> map2  = bankcardMapper.getbankcard(userId);
	   	String bankcardName = (String) map2.get("bankcardName");
	   	int bankcardTypeId = (int) map2.get("bankcardTypeId");
	   	String bankcardTypeName = bankcardTypeMapper.getbankcardTypeName(bankcardTypeId);
	   	String payment = bankcardTypeName+bankcardName;//支付方式
      map1.put("deferredexpenses", deferredexpenses);
      map1.put("beforeTime", beforeTime);
      map1.put("lifeOfLoan", lifeOfLoan);
      map1.put("afterTime", afterTime);
      map1.put("payment", payment);
	  
	return map1;
	   
   }
   
   
   //立即延期按钮
   @RequestMapping("/delayButton")
   @ResponseBody
   @Transactional
   public Map<String, Object> delayButton (int userId,int companyId,String orderNumber,BigDecimal finalLine) throws ParseException{
	   
	return null;
	   
   }
   
   
   
   
   public static int compare_date(String DATE1, String DATE2) {//比较时间大小
 DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
 try {
     Date dt1 = df.parse(DATE1);
     Date dt2 = df.parse(DATE2);
     if (dt1.getTime() > dt2.getTime()) {
         return 1;
     } else if (dt1.getTime() < dt2.getTime()) {
         return -1;
     } else {
         return 0;
     }
 } catch (Exception exception) {
     exception.printStackTrace();
 }
 return 0;
      }
   
   
   
   
   
   
   
   public static void main(String[] args) {
//	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");  
//	   System.out.println(sdf.format(new Date(Long.valueOf(("1563188604000")))));
//	    Date date = new Date();//取时间 
//	    Calendar calendar  =   Calendar.getInstance();		 
//	    calendar.setTime(date); //需要将date数据转移到Calender对象中操作
//	    calendar.add(calendar.DATE, 7);//把日期往后增加n天.正数往后推,负数往前移动   
//	    date=calendar.getTime();  //这个时间就是日期往后推一天的结果 
//	    System.out.println(sdf.format(date));
//	   SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
//	   System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
//	   System.out.println(System.currentTimeMillis());
//	  System.out.println(compare_date("2019-11-2","2019-11-2"));

    } 
	   
}




