package com.zhita.service.manage.homepagetongji;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

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
		int todaydeferred = homepageTongjiMapper.queryToDayDeferred(companyId, startTimestamps, endTimestamps);//今日延期笔数
		int todayrepayment = 0;//今日回款笔数
		int todayrepaymentreal = homepageTongjiMapper.queryToDayRepayment(companyId,startTimestamps, endTimestamps);//今日回款笔数（实际回款）
		//int todayrepaymentacc = homepageTongjiMapper.queryToDayRepaymentacc(companyId, startTimestamps, endTimestamps);//今日回款笔数（线上回款）
		int todayrepaymentoff = homepageTongjiMapper.queryToDayRepaymentoff(companyId,startTimestamps, endTimestamps);//今日回款笔数（线下回款）
		int todayrepaymentbank = homepageTongjiMapper.queryToDayRepaymentbank(companyId, startTimestamps, endTimestamps);//今日回款笔数（银行卡回款）
		todayrepayment=todayrepaymentreal+todayrepaymentoff+todayrepaymentbank;
		
		int todayoverdue=0;//今日逾期已还笔数
		todayoverdue = homepageTongjiMapper.queryToDayOverdue(companyId, startTimestamps, endTimestamps);//今日逾期已还笔数
		
		BigDecimal todayloantotalmoney = homepageTongjiMapper.queryToDayLoanTotalmoney(companyId, startTimestamps, endTimestamps);//今日放款总金额
		
		BigDecimal todayreturtoalmoney = new BigDecimal("0.00");//今日回款总金额
		BigDecimal todayreturtoalmoneyreal = homepageTongjiMapper.queryToDayReturTotalmoney(companyId, startTimestamps, endTimestamps);//今日回款总金额（实还金额）
		if(todayreturtoalmoneyreal==null){
			todayreturtoalmoneyreal=new BigDecimal("0.00");
		}
		BigDecimal toDayDeffer = homepageTongjiMapper.queryToDayDeffer(companyId, startTimestamps, endTimestamps);//今日回款总金额（延期费）
		if(toDayDeffer==null){
			toDayDeffer=new BigDecimal("0.00");
		}
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
		BigDecimal todayoveruetotalmoneyacc = homepageTongjiMapper.queryToDayOverueTotalmoneyacc(companyId, startTimestamps, endTimestamps);//今日逾期已还金额（线上减免已还）
		if(todayoveruetotalmoneyacc==null){
			todayoveruetotalmoneyacc=new BigDecimal("0.00");
		}
		BigDecimal todayoveruetotalmoneyoff = homepageTongjiMapper.queryToDayOverueTotalmoneyoff(companyId, startTimestamps, endTimestamps);//今日逾期已还金额（线下减免已还）
		if(todayoveruetotalmoneyoff==null){
			todayoveruetotalmoneyoff=new BigDecimal("0.00");
		}
		todayoveruetotalmoney=todayoveruetotalmoneyreal.add(todayoveruetotalmoneyacc).add(todayoveruetotalmoneyoff);
		
		String orderrepaytodaycvr=null;//当日订单回款率
		if(todayloan==0){
			orderrepaytodaycvr="0.00%";
		}else{
			orderrepaytodaycvr = (new DecimalFormat("#0.00").format(todayrepayment*1.0/todayloan*100))+"%";//当日订单回款率
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
		
		String orderrepaycvr=null;
		if(sumloan==0){
			orderrepaycvr="0.00%";
		}else{
			orderrepaycvr = (new DecimalFormat("#0.00").format(sumrepayment*1.0/sumloan*100))+"%";//订单回款率
		}
		
		BigDecimal payrecmoney = homepageTongjiMapper.querypayrecMoney(companyId);//累计放款总金额
		if(payrecmoney==null){
			payrecmoney=new BigDecimal("0.00");
		}
		
		BigDecimal repaymoney = new BigDecimal("00");//累计回款总金额
		BigDecimal repaymoneyreal = homepageTongjiMapper.queryrepayMoney(companyId);//累计回款总金额(实还金额)
		if(repaymoneyreal==null){
			repaymoneyreal=new BigDecimal("0.00");
		}
		BigDecimal deffermoney = homepageTongjiMapper.querydeffermoney(companyId);//累计回款总金额（延期费）
		if(deffermoney==null){
			deffermoney=new BigDecimal("0.00");
		}
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
		
		BigDecimal shouldMoney = new BigDecimal("0.00");//累计应收总金额
		List<Orderdetails> listdetail = homepageTongjiMapper.queryshouldMoney(companyId);
		for (int i = 0; i < listdetail.size(); i++) {
			if(listdetail.get(i).getInterestPenaltySum()==null){
				listdetail.get(i).setInterestPenaltySum(new BigDecimal("0.00"));
			}
			BigDecimal queryshouldMoneyfor=listdetail.get(i).getShouldReapyMoney().add(listdetail.get(i).getInterestPenaltySum());
			shouldMoney=shouldMoney.add(queryshouldMoneyfor);
		}
		BigDecimal realymoney = repaymoney.subtract(payrecmoney);//实际收益
		
		/**
		 * 期限内数据
		 */
		int overdue = homepageTongjiMapper.overdue(companyId);//逾前未还笔数
		
		BigDecimal overduemoney = homepageTongjiMapper.overdueshouldMoney(companyId);//逾前应收总金额
		if(overduemoney==null){
			overduemoney=new BigDecimal("0.00");
		}
		
		/**
		 * 已逾期数据
		 */
		int overdue1 = homepageTongjiMapper.overdue1(companyId);//逾后未还笔数
		int baddebt = homepageTongjiMapper.baddebt1(companyId);//已坏账笔数
		int shouorder = homepageTongjiMapper.shouorder(companyId);//应还订单
		String overduecvr=null;
		if(shouorder==0){
			overduecvr="0.00%";
		}else{
			overduecvr = (new DecimalFormat("#0.00").format((overdue1+baddebt)*1.0/shouorder*100))+"%";//逾期率
		}
		
		BigDecimal overshouldMoney = new BigDecimal("0.00");//逾期应收总金额
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
		map.put("todaydeferred", todaydeferred);//今日延期笔数
		map.put("todayrepayment", todayrepayment);//今日回款笔数
		map.put("todayoverdue", todayoverdue);//今日逾期已还笔数
		map.put("todayloantotalmoney",todayloantotalmoney);//今日放款总金额
		map.put("todayreturtoalmoney",todayreturtoalmoney );//今日回款总金额
		map.put("todayoveruetotalmoney",todayoveruetotalmoney);//今日逾期已还金额
		map.put("orderrepaytodaycvr", orderrepaytodaycvr);//当日订单回款率
		
		map.put("sumregiste",sumregiste);//累计注册用户
		map.put("sumapply",sumapply);//累计申请用户总数
		map.put("sumloan",sumloan);//累计放款总笔数
		map.put("sumrepayment",sumrepayment);//累计还款总笔数
		map.put("paymentpasscvr",paymentpasscvr);//放款通过率
		map.put("orderrepaycvr",orderrepaycvr);//订单回款率
		map.put("payrecmoney",payrecmoney);//累计放款总金额
		map.put("repaymoney",repaymoney);//累计回款总金额
		map.put("shouldMoney",shouldMoney);//累计应收总金额
		map.put("realymoney", realymoney);//实际收益
		
		map.put("overdue",overdue);//逾前未还笔数
		//map.put("overduerealMoney", overduerealMoney);//逾期实还金额
		map.put("overduemoney", overduemoney);//逾前应收总金额
		
		map.put("overdue1", overdue1);//逾后未还笔数
		map.put("overduecvr", overduecvr);//逾期率
		map.put("overshouldMoney", overshouldMoney);//逾期应收总金额
		return map;
	}
	
	//回收率报表
	public Map<String, Object> recoveryStatement(Integer companyId,Integer page,String shouldrepayStartTime,String shouldrepayEndTime){
		List<HomepageTongji> listtongji=new ArrayList<HomepageTongji>();
		List<HomepageTongji> listtongjito=new ArrayList<HomepageTongji>();
		PageUtil2 pageUtil=null;
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d=new Date();
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		String date=sf.format(d);//date为当天时间(格式为年月日)
		
		String startTime = null;//开始时间（年月日格式）
		String endTime = null;//结束时间（年月日格式）
		if((shouldrepayStartTime!=null&&!"".equals(shouldrepayStartTime))&&(shouldrepayEndTime!=null&&!"".equals(shouldrepayEndTime))){
			startTime = shouldrepayStartTime;
			endTime = shouldrepayEndTime;
		}else{
			startTime = date;
			endTime = date;
		}
		
		List<String> list=DateListUtil.getDays(startTime, endTime);
		List<String> liststr=homepageTongjiMapper.queryAllShouldTime(companyId);
		List<String> liststrStamp = new ArrayList<>();// 用来存时间戳转换后的时间（年月日格式的时间）（订单表所有的应还时间）
		for (int k = 0; k < liststr.size(); k++) {
			liststrStamp.add(Timestamps.stampToDate1(liststr.get(k)));
		}
		HashSet h1 = new HashSet(liststrStamp);
		liststrStamp.clear();
		liststrStamp.addAll(h1);
		
		list.retainAll(liststrStamp);//两个集合的交集
		
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
			listorders.addAll(listordersoff);//合并两个集合
			for (int j = 0; j < listorders.size(); j++) {
				try {
						String realtime=Timestamps.stampToDate(listorders.get(j).getRealtime());//实还时间
						String shouletime=Timestamps.stampToDate(listorders.get(j).getShouldReturnTime());//应还时间
					
						if(sdf.parse(realtime).getTime()>sdf.parse(shouletime).getTime()){//转成long类型比较
							System.out.println("实际还款时间大于应还时间");
							overdueafterrepay++;
						}else if(sdf.parse(realtime).getTime()<=sdf.parse(shouletime).getTime()){
							System.out.println("还款时间小于应还时间");
							overduerepay++;
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
		
			BigDecimal deferredmoney=homepageTongjiMapper.deferredmoney(companyId,startTimestampsfor, endTimestampsfor);//（延期费）
			
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
			if(deferredmoney==null){
				deferredmoney=new BigDecimal("0.00");
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
		
	 	if(listtongji!=null && !listtongji.isEmpty()){
    		ListPageUtil listPageUtil=new ListPageUtil(listtongji,page,10);
    		listtongjito.addAll(listPageUtil.getData());
    		
    		pageUtil=new PageUtil2(listPageUtil.getCurrentPage(), listPageUtil.getPageSize(),listPageUtil.getTotalCount());
    	}else{
    		pageUtil=new PageUtil2(1,10,0);

    	}
		
	 	DateListUtil.ListSort3(listtongjito);//按照应还时间进行倒排序
	 	
		Map<String, Object> map=new HashMap<>();
		map.put("listtongjito", listtongjito);
		map.put("pageutil", pageUtil);
		return map;
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
