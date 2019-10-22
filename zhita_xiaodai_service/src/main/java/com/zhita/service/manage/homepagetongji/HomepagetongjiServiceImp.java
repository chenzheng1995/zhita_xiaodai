package com.zhita.service.manage.homepagetongji;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhita.dao.manage.HomepageTongjiMapper;
import com.zhita.model.manage.HomepageTongji;
import com.zhita.model.manage.Orderdetails;
import com.zhita.model.manage.Orders;
import com.zhita.util.DateListUtil;
import com.zhita.util.ListPageUtil;
import com.zhita.util.PageUtil2;
import com.zhita.util.Timestamps;

@Service
public class HomepagetongjiServiceImp implements IntHomepagetongjiService{
	
	@Autowired
	private HomepageTongjiMapper homepageTongjiMapper;
	
	//首页统计
	public Map<String, Object> queryAll(Integer companyId){
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d=new Date();
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		String date=sf.format(d);//date为当天时间(格式为年月日)
		
		String startTimestamps = null;
		String endTimestamps = null;
		try {
			String startTime = date;
			startTimestamps = Timestamps.dateToStamp(startTime);
			String endTime = date;
			endTimestamps = (Long.parseLong(Timestamps.dateToStamp(endTime))+86400000)+"";
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/**
		 * 今日数据
		 */
		int todayregiste = homepageTongjiMapper.queryToDayRegiste(companyId, startTimestamps, endTimestamps);//今日注册人数
		int todayapply = homepageTongjiMapper.queryToDayApply(companyId, startTimestamps, endTimestamps);//今日申请人数
		int todayloan = homepageTongjiMapper.queryToDayLoan(companyId, startTimestamps, endTimestamps);//今日放款人数
		int todayloannew = homepageTongjiMapper.queryToDayLoannew(companyId, startTimestamps, endTimestamps);//今日放款新客
		int todayloanold = homepageTongjiMapper.queryToDayLoanold(companyId, startTimestamps, endTimestamps);//今日放款复贷
		int todaydeferred=0;//今日延期笔数
		int defer= homepageTongjiMapper.queryToDayDeferred(companyId, startTimestamps, endTimestamps);//今日延期笔数---线上延期
		int deferlay=homepageTongjiMapper.queryToDayDeferredlay(companyId, startTimestamps, endTimestamps);//今日延期笔数---人工延期
		todaydeferred=defer+deferlay;
		int todayrepayment = 0;//今日回款笔数
		int todayrepaymentreal = homepageTongjiMapper.queryToDayRepayment(companyId,startTimestamps, endTimestamps);//今日回款笔数（实际回款）
		//int todayrepaymentacc = homepageTongjiMapper.queryToDayRepaymentacc(companyId, startTimestamps, endTimestamps);//今日回款笔数（线上回款）
		int todayrepaymentoff = homepageTongjiMapper.queryToDayRepaymentoff(companyId,startTimestamps, endTimestamps);//今日回款笔数（线下回款）
		int todayrepaymentbank = homepageTongjiMapper.queryToDayRepaymentbank(companyId, startTimestamps, endTimestamps);//今日回款笔数（银行卡回款）
		todayrepayment=todayrepaymentreal+todayrepaymentoff+todayrepaymentbank;
		
		int todayoverdue=0;//今日逾后已还笔数
		List<Orders> listreal = homepageTongjiMapper.queryToDayOverdue(companyId, startTimestamps, endTimestamps);//今日逾后已还笔数    ---还款表
		List<Orders> listoff = homepageTongjiMapper.queryToDayOverdueoff(companyId, startTimestamps, endTimestamps);//今日逾后已还笔数    ---线下还款表
		List<Orders> listbank = homepageTongjiMapper.queryToDayOverduebank(companyId, startTimestamps, endTimestamps);//今日逾后已还笔数    ---银行卡扣款 表
		listreal.addAll(listoff);//合并两个集合
		listreal.addAll(listbank);//合并后的集合再次合并第三个集合
		for (int j = 0; j < listreal.size(); j++) {
			try {
					if(!listreal.isEmpty()&&listreal.size()!=0){
						String realtime=Timestamps.stampToDate(listreal.get(j).getRealtime());//实还时间
						String shouletime=Timestamps.stampToDate(listreal.get(j).getShouldReturnTime());//应还时间
					
						if(sdf.parse(realtime).getTime()>sdf.parse(shouletime).getTime()){//转成long类型比较
							System.out.println("实际还款时间大于应还时间");
							todayoverdue++;
						}/*else if(sdf.parse(realtime).getTime()<=sdf.parse(shouletime).getTime()){
							System.out.println("还款时间小于应还时间");
							overduerepay++;
						}*/
					}
					
				} catch (ParseException e) {
					e.printStackTrace();
			}
		}
		
		BigDecimal todayloantotalmoney = homepageTongjiMapper.queryToDayLoanTotalmoney(companyId, startTimestamps, endTimestamps);//今日放款总金额
		
		BigDecimal todayreturtoalmoney = new BigDecimal("0.00");//今日回款总金额
		BigDecimal todayreturtoalmoneyreal = homepageTongjiMapper.queryToDayReturTotalmoney(companyId, startTimestamps, endTimestamps);//今日回款总金额（实还金额）
		if(todayreturtoalmoneyreal==null){
			todayreturtoalmoneyreal=new BigDecimal("0.00");
		}
		BigDecimal toDayDeffer =new BigDecimal("0.00");//今日回款总金额（延期费）
		BigDecimal deffer= homepageTongjiMapper.queryToDayDeffer(companyId, startTimestamps, endTimestamps);//今日回款总金额（线上延期费）
		if(deffer==null){
			deffer=new BigDecimal("0.00");
		}
		BigDecimal defferlay= homepageTongjiMapper.queryToDayDefferlay(companyId, startTimestamps, endTimestamps);//今日回款总金额（线下延期费）
		if(defferlay==null){
			defferlay=new BigDecimal("0.00");
		}
		toDayDeffer=deffer.add(defferlay);
		/*BigDecimal toDayDefferacc = homepageTongjiMapper.queryToDayDefferacc(companyId,startTimestamps, endTimestamps);//今日回款总金额（减免后已还总金额）（线上）
		if(toDayDefferacc==null){
			toDayDefferacc=new BigDecimal("0.00");
		}*/
		BigDecimal toDayDefferoff = homepageTongjiMapper.queryToDayDefferoff(companyId, startTimestamps, endTimestamps);//今日回款总金额（减免后已还总金额）（线下）
		if(toDayDefferoff==null){
			toDayDefferoff=new BigDecimal("0.00");
		}
		BigDecimal toDayBank = homepageTongjiMapper.queryToDayBank(companyId, startTimestamps, endTimestamps);//今日回款总金额（银行扣款总金额）
		if(toDayBank==null){
			toDayBank=new BigDecimal("0.00");
		}
		todayreturtoalmoney=todayreturtoalmoneyreal.add(toDayDeffer).add(toDayDefferoff).add(toDayBank);
		
		BigDecimal todayoveruetotalmoney=new BigDecimal("0.00");//今日逾期已还金额
		BigDecimal todayoveruetotalmoneyreal = homepageTongjiMapper.queryToDayOverueTotalmoney(companyId, startTimestamps, endTimestamps);//今日逾期已还金额（用户实还金额）
		if(todayoveruetotalmoneyreal==null){
			todayoveruetotalmoneyreal=new BigDecimal("0.00");
		}
		BigDecimal todayoveruetotalmoneyoff = homepageTongjiMapper.queryToDayOverueTotalmoneyoff(companyId, startTimestamps, endTimestamps);//今日逾期已还金额（线下减免已还）
		if(todayoveruetotalmoneyoff==null){
			todayoveruetotalmoneyoff=new BigDecimal("0.00");
		}
		BigDecimal todayoveruetotalmoneybank = homepageTongjiMapper.queryToDayOverueTotalmoneybank(companyId, startTimestamps, endTimestamps);//今日逾期已还金额（银行卡扣款已还）
		if(todayoveruetotalmoneybank==null){
			todayoveruetotalmoneybank=new BigDecimal("0.00");
		}
		todayoveruetotalmoney=todayoveruetotalmoneyreal.add(todayoveruetotalmoneyoff).add(todayoveruetotalmoneybank);
		
		String orderrepaytodaycvr=null;//当日订单回款率
		/*if(todayloan==0){
			orderrepaytodaycvr="0.00%";
		}else{
			orderrepaytodaycvr = (new DecimalFormat("#0.00").format(todayrepayment*1.0/todayloan*100))+"%";//当日订单回款率
		}*/
		int todayshouldorder=homepageTongjiMapper.shouldorder(companyId, startTimestamps, endTimestamps);//今日应还订单
		List<Orders> listorders=homepageTongjiMapper.overduerepay(companyId, startTimestamps, endTimestamps);//已还款订单  还款表
		List<Orders> listordersoff=homepageTongjiMapper.overduerepayoff(companyId, startTimestamps, endTimestamps);//已还款订单    线下还款表
		List<Orders> listordersbank=homepageTongjiMapper.overduerepaybank(companyId, startTimestamps, endTimestamps);//已还款订单    银行卡扣款表
		listorders.addAll(listordersoff);//合并两个集合
		listorders.addAll(listordersbank);//合并后的集合再次合并第三个集合
		int todayshouorderalready=listorders.size();//今日应还订单已还的数量
		if(todayshouldorder==0){
			orderrepaytodaycvr="0.00%";
		}else{
			orderrepaytodaycvr = (new DecimalFormat("#0.00").format(todayshouorderalready*1.0/todayshouldorder*100))+"%";//当日订单回款率
		}
		
		/**
		 * 累计数据
		 */
		int sumregiste = homepageTongjiMapper.querySumRegiste(companyId);//累计注册用户
		int sumapply = homepageTongjiMapper.querySumApply(companyId);//累计申请用户总数
		int sumloan = homepageTongjiMapper.querySumLoan(companyId);//累计放款总笔数
		
		int sumrepayment = 0;//累计回款总笔数
		int sumrepaymentreal = homepageTongjiMapper.querySumRepayment(companyId);//累计回款总笔数（实还笔数）
		//int sumrepaymentacc = homepageTongjiMapper.querySumRepaymentacc(companyId);//累计回款总笔数（线上减免已还清笔数）
		int sumrepaymentoff = homepageTongjiMapper.querySumRepaymentoff(companyId);//累计回款总笔数（线下减免已还清笔数）
		int sumrepaymentbank = homepageTongjiMapper.querySumRepaymentbank(companyId);//累计回款总笔数（银行卡扣款已结清笔数）
		sumrepayment=sumrepaymentreal+sumrepaymentoff+sumrepaymentbank;
		
		String paymentpasscvr = (new DecimalFormat("#0.00").format(sumloan*1.0/sumregiste*100))+"%";//放款通过率
		
		String orderrepaycvr=null;//订单回款率
		/*if(sumloan==0){
			orderrepaycvr="0.00%";
		}else{
			orderrepaycvr = (new DecimalFormat("#0.00").format(sumrepayment*1.0/sumloan*100))+"%";//订单回款率
		}*/
		int cutofftodayshouldrepay=homepageTongjiMapper.cutofftodayshouldrepay(companyId, endTimestamps);//截止今天应还的订单数量
		if(cutofftodayshouldrepay==0){
			orderrepaycvr="0.00%";
		}else{
			orderrepaycvr = (new DecimalFormat("#0.00").format(sumrepayment*1.0/cutofftodayshouldrepay*100))+"%";//订单回款率
		}
		BigDecimal payrecmoney = homepageTongjiMapper.querypayrecMoney(companyId);//累计放款总金额
		if(payrecmoney==null){
			payrecmoney=new BigDecimal("0.00");
		}
		
		BigDecimal repaymoney = new BigDecimal("0.00");//累计回款总金额
		BigDecimal repaymoneyreal = homepageTongjiMapper.queryrepayMoney(companyId);//累计回款总金额(实还金额)
		if(repaymoneyreal==null){
			repaymoneyreal=new BigDecimal("0.00");
		}
		BigDecimal deffermoney=new BigDecimal("0.00"); //累计回款总金额（延期费）
		BigDecimal defmoney = homepageTongjiMapper.querydeffermoney(companyId);//累计回款总金额（线上延期费）
		if(defmoney==null){
			defmoney=new BigDecimal("0.00");
		}
		
		BigDecimal defmoneylay = homepageTongjiMapper.querydeffermoneylay(companyId);//累计回款总金额（人工延期费）
		if(defmoneylay==null){
			defmoneylay=new BigDecimal("0.00");
		}
		deffermoney=defmoney.add(defmoneylay);
		/*BigDecimal deffermoneyacc = homepageTongjiMapper.querydeffermoneyacc(companyId);//累计回款总金额（线上减免）
		if(deffermoneyacc==null){
			deffermoneyacc=new BigDecimal("0.00");
		}*/
		BigDecimal deffermoneyoff = homepageTongjiMapper.querydeffermoneyoff(companyId);//累计回款总金额（线下减免）
		if(deffermoneyoff==null){
			deffermoneyoff=new BigDecimal("0.00");
		}
		BigDecimal deffermoneybank = homepageTongjiMapper.querydeffermoneybank(companyId);//累计回款总金额（银行扣款）
		if(deffermoneybank==null){
			deffermoneybank=new BigDecimal("0.00");
		}
		repaymoney=repaymoneyreal.add(deffermoney).add(deffermoneyoff).add(deffermoneybank);
		
		BigDecimal annulmoney=new BigDecimal("0.00");//累计减免金额（包含线上减免的money+线下减免掉的money）
		BigDecimal annulmoneyacc = homepageTongjiMapper.annulmoneyacc(companyId);//累计减免总金额（线上减免的money）
		if(annulmoneyacc==null){
			annulmoneyacc=new BigDecimal("0.00");
		}
		BigDecimal annulmoneyoff = homepageTongjiMapper.annulmoneyoff(companyId);//累计减免总金额（线下减免的money）
		if(annulmoneyoff==null){
			annulmoneyoff=new BigDecimal("0.00");
		}
		annulmoney=annulmoneyacc.add(annulmoneyoff);
		
		BigDecimal shouldMoney = new BigDecimal("0.00");//累计原始应收总金额
		List<Orderdetails> listdetail = homepageTongjiMapper.queryshouldMoney(companyId);
		for (int i = 0; i < listdetail.size(); i++) {
			BigDecimal queryshouldMoneyfor=new BigDecimal("0.00");
			
			if(listdetail.get(i).getInterestPenaltySum()==null){
				listdetail.get(i).setInterestPenaltySum(new BigDecimal("0.00"));
			}
			if(listdetail.get(i).getRealityBorrowMoney().compareTo(listdetail.get(i).getMakeLoans())==0){
				queryshouldMoneyfor=listdetail.get(i).getRealityBorrowMoney().add(listdetail.get(i).getInterestSum()).
						add(listdetail.get(i).getInterestPenaltySum().add(listdetail.get(i).getTechnicalServiceMoney()));
			}else{
				queryshouldMoneyfor=listdetail.get(i).getRealityBorrowMoney().add(listdetail.get(i).getInterestSum()).
						add(listdetail.get(i).getInterestPenaltySum());
			}
			shouldMoney=shouldMoney.add(queryshouldMoneyfor);
		}
		BigDecimal shoumoneydef=homepageTongjiMapper.queryshouldMoneydef(companyId);//延期表的延期费
		if(shoumoneydef==null){
			shoumoneydef=new BigDecimal("0.00");
		}
		BigDecimal shoumoneylay=homepageTongjiMapper.queryshouldMoneylay(companyId);//人工延期表的费用
		if(shoumoneylay==null){
			shoumoneylay=new BigDecimal("0.00");
		}
		shouldMoney=shouldMoney.add(shoumoneydef).add(shoumoneylay);
		BigDecimal realymoney = repaymoney.subtract(payrecmoney);//实际收益
		
		/**
		 * 期限内数据
		 */
		int overdue = homepageTongjiMapper.overdue(companyId);//逾前未还笔数
		
		BigDecimal overduemoney = homepageTongjiMapper.overdueshouldMoney(companyId);//逾前未还总金额
		if(overduemoney==null){
			overduemoney=new BigDecimal("0.00");
		}
		
		/**
		 * 已逾期数据
		 */
		int overdue1 = homepageTongjiMapper.overdue1(companyId);//逾后未还笔数
		//int baddebt = homepageTongjiMapper.baddebt1(companyId);//已坏账笔数
		int shouorder = homepageTongjiMapper.shouorder(companyId);//应还订单
		String overduecvr=null;
		if(shouorder==0){
			overduecvr="0.00%";
		}else{
			overduecvr = (new DecimalFormat("#0.00").format((overdue1)*1.0/shouorder*100))+"%";//逾期率
		}
		
		BigDecimal overshouldMoney = new BigDecimal("0.00");//逾期未还总金额
		List<Orderdetails> listtail = homepageTongjiMapper.overshouldMoney(companyId);
		for (int i = 0; i < listtail.size(); i++) {
			if(listtail.get(i).getInterestPenaltySum()==null){
				listtail.get(i).setInterestPenaltySum(new BigDecimal("0.00"));
			}
			BigDecimal queryshouldMoneyfor=listtail.get(i).getShouldReapyMoney().add(listtail.get(i).getInterestPenaltySum());
			overshouldMoney=overshouldMoney.add(queryshouldMoneyfor);
		}
		
		Map<String, Object> map=new HashMap<>();
		map.put("todayregiste", todayregiste);//今日注册人数
		map.put("todayapply", todayapply);//今日申请人数
		map.put("todayloan", todayloan);//今日放款人数
		map.put("todayloannew", todayloannew);//今日放款新客
		map.put("todayloanold", todayloanold);//今日放款复贷
		map.put("todaydeferred", todaydeferred);//今日延期笔数
		map.put("todayrepayment", todayrepayment);//今日回款笔数
		map.put("todayoverdue", todayoverdue);//今日逾后已还笔数
		map.put("todayloantotalmoney",todayloantotalmoney);//今日放款总金额
		map.put("todayreturtoalmoney",todayreturtoalmoney );//今日回款总金额
		map.put("todayoveruetotalmoney",todayoveruetotalmoney);//今日逾期已还金额
		map.put("orderrepaytodaycvr", orderrepaytodaycvr);//当日订单回款率
		
		map.put("sumregiste",sumregiste);//累计注册用户
		map.put("sumapply",sumapply);//累计申请用户总数
		map.put("sumloan",sumloan);//累计放款总笔数
		map.put("sumrepayment",sumrepayment);//累计回款总笔数
		map.put("paymentpasscvr",paymentpasscvr);//放款通过率
		map.put("orderrepaycvr",orderrepaycvr);//订单回款率
		map.put("payrecmoney",payrecmoney);//累计放款总金额
		map.put("repaymoney",repaymoney);//累计回款总金额
		map.put("shouldMoney",shouldMoney);//累计原始应收总金额
		map.put("annulmoney",annulmoney);//累计减免总金额
		map.put("realymoney", realymoney);//实际收益
		
		map.put("overdue",overdue);//逾前未还笔数
		//map.put("overduerealMoney", overduerealMoney);//逾期实还金额
		map.put("overduemoney", overduemoney);//逾前未还总金额
		
		map.put("overdue1", overdue1);//逾后未还笔数
		map.put("overduecvr", overduecvr);//逾期率
		map.put("overshouldMoney", overshouldMoney);//逾期未还总金额
		return map;
	}
	
	//回收率报表
	public Map<String, Object> recoveryStatement(Integer companyId,Integer page,String shouldrepayStartTime,String shouldrepayEndTime){
		int lifeofloan = homepageTongjiMapper.querylifeOfLoan(companyId);//查询借款期限
		List<HomepageTongji> listtongji=new ArrayList<HomepageTongji>();
		List<HomepageTongji> listtongjito=new ArrayList<HomepageTongji>();
		PageUtil2 pageUtil=null;
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=new Date();
		Calendar calendar = Calendar.getInstance(); //创建Calendar 的实例
		calendar.set(Calendar.DAY_OF_MONTH,-1); //当前时间减去一天，即一天前的时间
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = DateUtils.addDays(date, -lifeofloan);
		Date endDate = DateUtils.addDays(date, lifeofloan);
		//String date=sf.format(d);//date为当天时间(格式为年月日)
		
		String startTime = null;//开始时间（年月日格式）
		String endTime = null;//结束时间（年月日格式）
		if((shouldrepayStartTime!=null&&!"".equals(shouldrepayStartTime))&&(shouldrepayEndTime!=null&&!"".equals(shouldrepayEndTime))){
			startTime = shouldrepayStartTime;
			endTime = shouldrepayEndTime;
		}else{
			startTime = sf.format(startDate);
			endTime = sf.format(endDate);
		}
		
		List<String> list=DateListUtil.getDays(startTime, endTime);
		//List<String> liststr=homepageTongjiMapper.queryAllShouldTime(companyId);
		//List<String> liststrStamp = new ArrayList<>();// 用来存时间戳转换后的时间（年月日格式的时间）（订单表所有的应还时间）
		/*for (int k = 0; k < liststr.size(); k++) {
			liststrStamp.add(Timestamps.stampToDate1(liststr.get(k)));
		}*/
		//HashSet h1 = new HashSet(liststrStamp);
		//liststrStamp.clear();
		//liststrStamp.addAll(h1);
		
		//list.retainAll(liststrStamp);//两个集合的交集
		
		for (int i = 0; i < list.size(); i++) {
			String startTimefor = list.get(i);
			String endTimefor = list.get(i);
			
			String startTimestampsfor = null;//开始时间（时间戳格式）
			String endTimestampsfor = null;//结束时间（时间戳格式）
			try {
				startTimestampsfor = Timestamps.dateToStamp(startTimefor);
				endTimestampsfor = (Long.parseLong(Timestamps.dateToStamp(endTimefor))+86400000)+"";
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
			HomepageTongji homepageTongji=new HomepageTongji();//循环一次new出一个HomepageTongji实体类
			int shouldorder=homepageTongjiMapper.shouldorder(companyId, startTimestampsfor, endTimestampsfor);//（应还订单 ）
			int overduenotrepay=homepageTongjiMapper.overduenotrepay(companyId, startTimestampsfor, endTimestampsfor);//（逾前未还）
			int overduerepay=0;//（逾前已还）
			int overdueafterrepay=0;//（逾后已还）
			List<Orders> listorders=homepageTongjiMapper.overduerepay(companyId, startTimestampsfor, endTimestampsfor);//已还款订单  还款表
			List<Orders> listordersoff=homepageTongjiMapper.overduerepayoff(companyId, startTimestampsfor, endTimestampsfor);//已还款订单    线下还款表
			List<Orders> listordersbank=homepageTongjiMapper.overduerepaybank(companyId, startTimestampsfor, endTimestampsfor);//已还款订单    银行卡扣款表
			listorders.addAll(listordersoff);//合并两个集合
			listorders.addAll(listordersbank);//合并后的集合再次合并第三个集合
			
			for (int j = 0; j < listorders.size(); j++) {
				try {
					if(!listorders.isEmpty()&&listorders.size()!=0){
						String realtime=Timestamps.stampToDate(listorders.get(j).getRealtime());//实还时间
						String shouletime=Timestamps.stampToDate(listorders.get(j).getShouldReturnTime());//应还时间
					
						if(sdf.parse(realtime).getTime()>sdf.parse(shouletime).getTime()){//转成long类型比较
							System.out.println("实际还款时间大于应还时间");
							overdueafterrepay++;
						}else if(sdf.parse(realtime).getTime()<=sdf.parse(shouletime).getTime()){
							System.out.println("还款时间小于应还时间");
							overduerepay++;
						}
					}
					} catch (ParseException e) {
						e.printStackTrace();
				}
			}
			int overdueafternotrepay=homepageTongjiMapper.overdueafternotrepay(companyId,startTimestampsfor, endTimestampsfor);//（逾后未还）
			int baddebt=homepageTongjiMapper.baddebt(companyId,startTimestampsfor, endTimestampsfor);//（已坏账）
			
			
			BigDecimal shouldmoney = new BigDecimal("0.00");//（实际应还金额）
			List<Orderdetails> orderdetails=homepageTongjiMapper.shouldmoney(companyId,startTimestampsfor, endTimestampsfor);
			for (int k = 0; k < orderdetails.size(); k++) {
				if(orderdetails.get(k).getInterestPenaltySum()==null){
					orderdetails.get(k).setInterestPenaltySum(new BigDecimal("0.00") );
				}
				BigDecimal shouldmoneyfor=orderdetails.get(k).getShouldReapyMoney().add(orderdetails.get(k).getInterestPenaltySum());
				shouldmoney=shouldmoney.add(shouldmoneyfor);
			}
			BigDecimal repaymentmoney=homepageTongjiMapper.realymoney(companyId,startTimestampsfor, endTimestampsfor);//（线上实还金额，即还款表）
			
			BigDecimal deratemoneyoff=homepageTongjiMapper.deratemoneyunder(companyId,startTimestampsfor, endTimestampsfor);//线下实还金额，即线下减免表
			if(deratemoneyoff==null){
				deratemoneyoff=new BigDecimal("0.00");
			}
		
			BigDecimal deferredmoney=new BigDecimal("0.00");//（延期费）
			BigDecimal defmoney=homepageTongjiMapper.deferredmoney(companyId,startTimestampsfor, endTimestampsfor);//（线上延期费）
			if(defmoney==null){
				defmoney=new BigDecimal("0.00");
			}
			BigDecimal defmoneylay=homepageTongjiMapper.deferredmoneylay(companyId, startTimestampsfor, endTimestampsfor);//（人工延期费）
			if(defmoneylay==null){
				defmoneylay=new BigDecimal("0.00");
			}
			deferredmoney=defmoney.add(defmoneylay);
			
			BigDecimal overduemoney = new BigDecimal("0.00");//（逾期费）
			List<Orderdetails> listorderdetail=homepageTongjiMapper.overduemoney(companyId,startTimestampsfor, endTimestampsfor);
			for (int o = 0; o < listorderdetail.size(); o++) {
				if(listorderdetail.get(o).getInterestPenaltySum()==null){
					listorderdetail.get(o).setInterestPenaltySum(new BigDecimal("0.00") );
				}
				overduemoney=overduemoney.add(listorderdetail.get(o).getInterestPenaltySum());
			}
			
			BigDecimal deratemoney=new BigDecimal("0.00");//（线上减免金额）
			BigDecimal deratemoneyacc=homepageTongjiMapper.deratemoneyon(companyId,startTimestampsfor, endTimestampsfor);//线上，即是线上减免金额
			if(deratemoneyacc==null){
				deratemoneyacc=new BigDecimal("0.00");
			}
			deratemoney=deratemoneyacc;
			
			BigDecimal offderatemoney=homepageTongjiMapper.offmoney(companyId, startTimestampsfor, endTimestampsfor);//线下减免金额
			
			BigDecimal bankmoney = homepageTongjiMapper.bankMoney(companyId,startTimestampsfor, endTimestampsfor);//银行扣款金额
			
			if(shouldmoney==null){
				shouldmoney=new BigDecimal("0.00");
			}
			if(repaymentmoney==null){
				repaymentmoney=new BigDecimal("0.00");
			}
			if(deratemoneyoff==null){
				deratemoneyoff=new BigDecimal("0.00");
			}
		
			if(deratemoney==null){
				deratemoney=new BigDecimal("0.00");
			}
			if(offderatemoney==null){
				offderatemoney=new BigDecimal("0.00");
			}
			if(bankmoney==null){
				bankmoney=new BigDecimal("0.00");
			}
			
			BigDecimal originalshouldmoney=shouldmoney.add(deratemoney).add(bankmoney);//原始应还金额
			
			BigDecimal tobepaidmoney=originalshouldmoney.subtract(repaymentmoney).subtract(deratemoneyoff).subtract(deratemoney).subtract(offderatemoney).subtract(bankmoney);//待还金额
			String overduecvr="";
			if(overdueafternotrepay!=0||baddebt!=0||shouldorder!=0){
				overduecvr=(new DecimalFormat("0.00").format((overdueafternotrepay+baddebt)*1.0/shouldorder*100))+"%";//逾期率
			}else{
				overduecvr="0.00%";
			}
			//int derateaccon = homepageTongjiMapper.derateaccon(companyId,startTimestampsfor, endTimestampsfor);//线上减免已还清
			//int derateaccunder = homepageTongjiMapper.derateaccunder(companyId,startTimestampsfor, endTimestampsfor);//线下减免已还清
			//int deratebank = homepageTongjiMapper.deratebank(companyId,startTimestampsfor, endTimestampsfor);//银行扣款已还清
			String recovery=null;
			if(overduerepay!=0||overdueafterrepay!=0||shouldorder!=0){
				recovery=(new DecimalFormat("#0.00").format((overduerepay+overdueafterrepay)*1.0/(shouldorder)*100))+"%";//回收率
			}else{
				recovery="0.00%";
			}
			
			homepageTongji.setShouldtime(list.get(i));//应还日期
			homepageTongji.setShouldorder(shouldorder);//应还订单
			homepageTongji.setOverduenotrepay(overduenotrepay);//（逾前未还）
			homepageTongji.setOverduerepay(overduerepay);////逾前已还
			homepageTongji.setOverdueafterrepay(overdueafterrepay);//逾后已还
			homepageTongji.setOverdueafternotrepay(overdueafternotrepay);//逾后未还
			homepageTongji.setBaddebt(baddebt);//已坏账
			homepageTongji.setOriginalshouldmoney(originalshouldmoney);//原始应还
			homepageTongji.setShouldmoney(shouldmoney);//实际应还金额（期限内应还金额+逾期费）
			homepageTongji.setRealymoney(repaymentmoney);//线上实际还款金额
			homepageTongji.setOffrealymoney(deratemoneyoff);//线下实际还款金额
			homepageTongji.setDeferredmoney(deferredmoney);//延期费
			homepageTongji.setOverduemoney(overduemoney);//逾期费
			homepageTongji.setDeratemoney(deratemoney);//线上减免金额
			homepageTongji.setOffderatemoney(offderatemoney);//线下减免金额
			homepageTongji.setBankdeduction(bankmoney);//银行扣款金额
			homepageTongji.setTobepaid(tobepaidmoney);//待还金额
			homepageTongji.setOverduecvr(overduecvr);//逾期率
			homepageTongji.setRecovery(recovery);//回收率
			
			listtongji.add(homepageTongji);
		}
		DateListUtil.ListSort3(listtongji);//按照应还时间进行倒排序
		
	 	if(listtongji!=null && !listtongji.isEmpty()){
    		ListPageUtil listPageUtil=new ListPageUtil(listtongji,page,15);
    		listtongjito.addAll(listPageUtil.getData());
    		
    		pageUtil=new PageUtil2(listPageUtil.getCurrentPage(), listPageUtil.getPageSize(),listPageUtil.getTotalCount());
    	}else{
    		pageUtil=new PageUtil2(1,15,0);

    	}
		
	 	
		Map<String, Object> map=new HashMap<>();
		map.put("listtongjito", listtongjito);
		map.put("pageutil", pageUtil);
		return map;
	}
	
	/**
	 * 回收率报表
	 * 用于导出excel的查询结果
	 * 
	 * @param queryJson
	 * @return
	 * @throws IOException
	 */
	public void exportRecoveryStatement(Integer companyId,String shouldrepayStartTime,String shouldrepayEndTime, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		int lifeofloan = homepageTongjiMapper.querylifeOfLoan(companyId);//查询借款期限
		List<HomepageTongji> listtongji=new ArrayList<HomepageTongji>();
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=new Date();
		Calendar calendar = Calendar.getInstance(); //创建Calendar 的实例
		calendar.set(Calendar.DAY_OF_MONTH,-1); //当前时间减去一天，即一天前的时间
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = DateUtils.addDays(date, -lifeofloan);
		Date endDate = DateUtils.addDays(date, lifeofloan);
		//String date=sf.format(d);//date为当天时间(格式为年月日)
		
		String startTime = null;//开始时间（年月日格式）
		String endTime = null;//结束时间（年月日格式）
		if((shouldrepayStartTime!=null&&!"".equals(shouldrepayStartTime))&&(shouldrepayEndTime!=null&&!"".equals(shouldrepayEndTime))){
			startTime = shouldrepayStartTime;
			endTime = shouldrepayEndTime;
		}else{
			startTime = sf.format(startDate);
			endTime = sf.format(endDate);
		}
		
		List<String> list=DateListUtil.getDays(startTime, endTime);
		
		for (int i = 0; i < list.size(); i++) {
			String startTimefor = list.get(i);
			String endTimefor = list.get(i);
			
			String startTimestampsfor = null;//开始时间（时间戳格式）
			String endTimestampsfor = null;//结束时间（时间戳格式）
			try {
				startTimestampsfor = Timestamps.dateToStamp(startTimefor);
				endTimestampsfor = (Long.parseLong(Timestamps.dateToStamp(endTimefor))+86400000)+"";
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
			HomepageTongji homepageTongji=new HomepageTongji();//循环一次new出一个HomepageTongji实体类
			int shouldorder=homepageTongjiMapper.shouldorder(companyId, startTimestampsfor, endTimestampsfor);//（应还订单 ）
			int overduenotrepay=homepageTongjiMapper.overduenotrepay(companyId, startTimestampsfor, endTimestampsfor);//（逾前未还）
			int overduerepay=0;//（逾前已还）
			int overdueafterrepay=0;//（逾后已还）
			List<Orders> listorders=homepageTongjiMapper.overduerepay(companyId, startTimestampsfor, endTimestampsfor);//已还款订单  还款表
			List<Orders> listordersoff=homepageTongjiMapper.overduerepayoff(companyId, startTimestampsfor, endTimestampsfor);//已还款订单    线下还款表
			List<Orders> listordersbank=homepageTongjiMapper.overduerepaybank(companyId, startTimestampsfor, endTimestampsfor);//已还款订单    银行卡扣款表
			listorders.addAll(listordersoff);//合并两个集合
			listorders.addAll(listordersbank);//合并后的集合再次合并第三个集合
			
			for (int j = 0; j < listorders.size(); j++) {
				try {
					if(!listorders.isEmpty()&&listorders.size()!=0){
						String realtime=Timestamps.stampToDate(listorders.get(j).getRealtime());//实还时间
						String shouletime=Timestamps.stampToDate(listorders.get(j).getShouldReturnTime());//应还时间
					
						if(sdf.parse(realtime).getTime()>sdf.parse(shouletime).getTime()){//转成long类型比较
							System.out.println("实际还款时间大于应还时间");
							overdueafterrepay++;
						}else if(sdf.parse(realtime).getTime()<=sdf.parse(shouletime).getTime()){
							System.out.println("还款时间小于应还时间");
							overduerepay++;
						}
					}
					} catch (ParseException e) {
						e.printStackTrace();
				}
			}
			int overdueafternotrepay=homepageTongjiMapper.overdueafternotrepay(companyId,startTimestampsfor, endTimestampsfor);//（逾后未还）
			int baddebt=homepageTongjiMapper.baddebt(companyId,startTimestampsfor, endTimestampsfor);//（已坏账）
			
			
			BigDecimal shouldmoney = new BigDecimal("0.00");//（实际应还金额）
			List<Orderdetails> orderdetails=homepageTongjiMapper.shouldmoney(companyId,startTimestampsfor, endTimestampsfor);
			for (int k = 0; k < orderdetails.size(); k++) {
				if(orderdetails.get(k).getInterestPenaltySum()==null){
					orderdetails.get(k).setInterestPenaltySum(new BigDecimal("0.00") );
				}
				BigDecimal shouldmoneyfor=orderdetails.get(k).getShouldReapyMoney().add(orderdetails.get(k).getInterestPenaltySum());
				shouldmoney=shouldmoney.add(shouldmoneyfor);
			}
			BigDecimal repaymentmoney=homepageTongjiMapper.realymoney(companyId,startTimestampsfor, endTimestampsfor);//（线上实还金额，即还款表）
			
			BigDecimal deratemoneyoff=homepageTongjiMapper.deratemoneyunder(companyId,startTimestampsfor, endTimestampsfor);//线下实还金额，即线下减免表
			if(deratemoneyoff==null){
				deratemoneyoff=new BigDecimal("0.00");
			}
		
			BigDecimal deferredmoney=new BigDecimal("0.00");//（延期费）
			BigDecimal defmoney=homepageTongjiMapper.deferredmoney(companyId,startTimestampsfor, endTimestampsfor);//（线上延期费）
			if(defmoney==null){
				defmoney=new BigDecimal("0.00");
			}
			BigDecimal defmoneylay=homepageTongjiMapper.deferredmoneylay(companyId, startTimestampsfor, endTimestampsfor);//（人工延期费）
			if(defmoneylay==null){
				defmoneylay=new BigDecimal("0.00");
			}
			deferredmoney=defmoney.add(defmoneylay);
			
			BigDecimal overduemoney = new BigDecimal("0.00");//（逾期费）
			List<Orderdetails> listorderdetail=homepageTongjiMapper.overduemoney(companyId,startTimestampsfor, endTimestampsfor);
			for (int o = 0; o < listorderdetail.size(); o++) {
				if(listorderdetail.get(o).getInterestPenaltySum()==null){
					listorderdetail.get(o).setInterestPenaltySum(new BigDecimal("0.00") );
				}
				overduemoney=overduemoney.add(listorderdetail.get(o).getInterestPenaltySum());
			}
			
			BigDecimal deratemoney=new BigDecimal("0.00");//（线上减免金额）
			BigDecimal deratemoneyacc=homepageTongjiMapper.deratemoneyon(companyId,startTimestampsfor, endTimestampsfor);//线上，即是线上减免金额
			if(deratemoneyacc==null){
				deratemoneyacc=new BigDecimal("0.00");
			}
			deratemoney=deratemoneyacc;
			
			BigDecimal offderatemoney=homepageTongjiMapper.offmoney(companyId, startTimestampsfor, endTimestampsfor);//线下减免金额
			
			BigDecimal bankmoney = homepageTongjiMapper.bankMoney(companyId,startTimestampsfor, endTimestampsfor);//银行扣款金额
			
			if(shouldmoney==null){
				shouldmoney=new BigDecimal("0.00");
			}
			if(repaymentmoney==null){
				repaymentmoney=new BigDecimal("0.00");
			}
			if(deratemoneyoff==null){
				deratemoneyoff=new BigDecimal("0.00");
			}
		
			if(deratemoney==null){
				deratemoney=new BigDecimal("0.00");
			}
			if(offderatemoney==null){
				offderatemoney=new BigDecimal("0.00");
			}
			if(bankmoney==null){
				bankmoney=new BigDecimal("0.00");
			}
			
			BigDecimal originalshouldmoney=shouldmoney.add(deratemoney).add(bankmoney);//原始应还金额
			
			BigDecimal tobepaidmoney=originalshouldmoney.subtract(repaymentmoney).subtract(deratemoneyoff).subtract(deratemoney).subtract(offderatemoney).subtract(bankmoney);//待还金额
			String overduecvr="";
			if(overdueafternotrepay!=0||baddebt!=0||shouldorder!=0){
				overduecvr=(new DecimalFormat("0.00").format((overdueafternotrepay+baddebt)*1.0/shouldorder*100))+"%";//逾期率
			}else{
				overduecvr="0.00%";
			}
			//int derateaccon = homepageTongjiMapper.derateaccon(companyId,startTimestampsfor, endTimestampsfor);//线上减免已还清
			//int derateaccunder = homepageTongjiMapper.derateaccunder(companyId,startTimestampsfor, endTimestampsfor);//线下减免已还清
			//int deratebank = homepageTongjiMapper.deratebank(companyId,startTimestampsfor, endTimestampsfor);//银行扣款已还清
			String recovery=null;
			if(overduerepay!=0||overdueafterrepay!=0||shouldorder!=0){
				recovery=(new DecimalFormat("#0.00").format((overduerepay+overdueafterrepay)*1.0/(shouldorder)*100))+"%";//回收率
			}else{
				recovery="0.00%";
			}
			
			homepageTongji.setShouldtime(list.get(i));//应还日期
			homepageTongji.setShouldorder(shouldorder);//应还订单
			homepageTongji.setOverduenotrepay(overduenotrepay);//（逾前未还）
			homepageTongji.setOverduerepay(overduerepay);////逾前已还
			homepageTongji.setOverdueafterrepay(overdueafterrepay);//逾后已还
			homepageTongji.setOverdueafternotrepay(overdueafternotrepay);//逾后未还
			homepageTongji.setBaddebt(baddebt);//已坏账
			homepageTongji.setOriginalshouldmoney(originalshouldmoney);//原始应还
			homepageTongji.setShouldmoney(shouldmoney);//实际应还金额（期限内应还金额+逾期费）
			homepageTongji.setRealymoney(repaymentmoney);//线上实际还款金额
			homepageTongji.setOffrealymoney(deratemoneyoff);//线下实际还款金额
			homepageTongji.setDeferredmoney(deferredmoney);//延期费
			homepageTongji.setOverduemoney(overduemoney);//逾期费
			homepageTongji.setDeratemoney(deratemoney);//线上减免金额
			homepageTongji.setOffderatemoney(offderatemoney);//线下减免金额
			homepageTongji.setBankdeduction(bankmoney);//银行扣款金额
			homepageTongji.setTobepaid(tobepaidmoney);//待还金额
			homepageTongji.setOverduecvr(overduecvr);//逾期率
			homepageTongji.setRecovery(recovery);//回收率
			
			listtongji.add(homepageTongji);
		}
		DateListUtil.ListSort3(listtongji);//按照应还时间进行倒排序
		
		// 查询有多少行记录
		Integer count =listtongji.size();
		// 创建excel表的表头
		String[] headers = { "应还日期", "应还订单", "逾前未还", "逾前已还", "逾后未还", "逾后已还", "已坏账", "原始应还", "实际应还", "线上实还","线下实还","延期费","逾期费","线上减免","线下减免","银行扣款","待还金额","逾期率","回收率" };
		// 创建Excel工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 创建一个工作表sheet
		HSSFSheet sheet = workbook.createSheet();
		// 创建第一行
		HSSFRow row = sheet.createRow(0);
		// 定义一个单元格,相当于在第一行插入了三个单元格值分别是 "姓名", "性别", "年龄"
		HSSFCell cell = null;
		// 插入第一行数据
		for (int i = 0; i < headers.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(headers[i]);
		}
		// 追加数据
		for (int i = 1; i <= count; i++) {
			HSSFRow nextrow = sheet.createRow(i);
			HSSFCell cell2 = nextrow.createCell(0);
			cell2.setCellValue(listtongji.get(i - 1).getShouldtime());
			cell2 = nextrow.createCell(1);
			cell2.setCellValue(listtongji.get(i - 1).getShouldorder());
			cell2 = nextrow.createCell(2);
			cell2.setCellValue(listtongji.get(i - 1).getOverduenotrepay());
			cell2 = nextrow.createCell(3);
			cell2.setCellValue(listtongji.get(i - 1).getOverduerepay());
			cell2 = nextrow.createCell(4);
			cell2.setCellValue(listtongji.get(i - 1).getOverdueafternotrepay());
			cell2 = nextrow.createCell(5);
			cell2.setCellValue(listtongji.get(i - 1).getOverdueafterrepay());
			cell2 = nextrow.createCell(6);
			cell2.setCellValue(listtongji.get(i - 1).getBaddebt());
			cell2 = nextrow.createCell(7);
			cell2.setCellValue(listtongji.get(i - 1).getOriginalshouldmoney().toString());
			cell2 = nextrow.createCell(8);
			cell2.setCellValue(listtongji.get(i - 1).getShouldmoney().toString());
			cell2 = nextrow.createCell(9);
			cell2.setCellValue(listtongji.get(i - 1).getRealymoney().toString());
			cell2 = nextrow.createCell(10);
			cell2.setCellValue(listtongji.get(i - 1).getOffrealymoney().toString());
			cell2 = nextrow.createCell(11);
			cell2.setCellValue(listtongji.get(i - 1).getDeferredmoney().toString());
			cell2 = nextrow.createCell(12);
			cell2.setCellValue(listtongji.get(i - 1).getOverduemoney().toString());
			cell2 = nextrow.createCell(13);
			cell2.setCellValue(listtongji.get(i - 1).getDeratemoney().toString());
			cell2 = nextrow.createCell(14);
			cell2.setCellValue(listtongji.get(i - 1).getOffderatemoney().toString());
			cell2 = nextrow.createCell(15);
			cell2.setCellValue(listtongji.get(i - 1).getBankdeduction().toString());
			cell2 = nextrow.createCell(16);
			cell2.setCellValue(listtongji.get(i - 1).getTobepaid().toString());
			cell2 = nextrow.createCell(17);
			cell2.setCellValue(listtongji.get(i - 1).getOverduecvr());
			cell2 = nextrow.createCell(18);
			cell2.setCellValue(listtongji.get(i - 1).getRecovery());
		}
		// 将excel的数据写入文件
		ByteArrayOutputStream fos = null;
		byte[] retArr = null;
		try {
			fos = new ByteArrayOutputStream();
			workbook.write(fos);
			retArr = fos.toByteArray();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		OutputStream os = response.getOutputStream();
		try {
			response.reset();
			response.setHeader("Content-Disposition", "attachment; filename=agent_book.xls");// 要保存的文件名
			response.setContentType("application/octet-stream; charset=utf-8");
			os.write(retArr);
			os.flush();
		} finally {
			if (os != null) {
				os.close();
			}
		}
	}
	
	
	
	public List<Orderdetails> test(){
		List<Orderdetails> list=homepageTongjiMapper.test();
		BigDecimal shouldmoney = new BigDecimal("0.00");//实际应还金额
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getInterestPenaltySum()==null){
				list.get(i).setInterestPenaltySum(new BigDecimal("0.00"));
			}
			BigDecimal shouldmoneyfor=list.get(i).getInterestPenaltySum();
			shouldmoney=shouldmoney.add(shouldmoneyfor);
			System.out.println(list.get(i).getInterestPenaltySum()+"-----"+shouldmoney);
		}
		return list;
	}
}
