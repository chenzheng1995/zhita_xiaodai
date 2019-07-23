package com.zhita.service.manage.homepagetongji;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.zhita.util.PageUtil;
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
		
		//今日数据
		int todayregiste = homepageTongjiMapper.queryToDayRegiste(companyId, startTimestamps, endTimestamps);//今日注册人数
		int todayapply = homepageTongjiMapper.queryToDayApply(companyId, startTimestamps, endTimestamps);//今日申请人数
		int todayloan = homepageTongjiMapper.queryToDayLoan(companyId, startTimestamps, endTimestamps);//今日放款人数
		int todaydeferred = homepageTongjiMapper.queryToDayDeferred(companyId, startTimestamps, endTimestamps);//今日延期笔数
		int todayrepayment = homepageTongjiMapper.queryToDayRepayment(companyId, startTimestamps, endTimestamps);//今日回款笔数
		//int todayoverdue = homepageTongjiMapper.queryToDayOverdue(companyId, startTimestamps, endTimestamps);//今日逾期已还笔数
		int todayoverdue=0;//今日逾期已还笔数
		List<Orders> listorders=homepageTongjiMapper.overduerepay(companyId, startTimestamps, endTimestamps);//已还款订单
		for (int j = 0; j < listorders.size(); j++) {
			try {
					String realtime=Timestamps.stampToDate(listorders.get(j).getRealtime());//实还时间
					String shouletime=Timestamps.stampToDate(listorders.get(j).getShouldReturnTime());//应还时间
				
					if(sdf.parse(realtime).getTime()>sdf.parse(shouletime).getTime()){//转成long类型比较
						System.out.println("实际还款时间大于应还时间");
						todayoverdue++;
					}
				} catch (ParseException e) {
					e.printStackTrace();
			}
		}
		BigDecimal todayloantotalmoney = homepageTongjiMapper.queryToDayLoanTotalmoney(companyId, startTimestamps, endTimestamps);//今日放款总金额
		BigDecimal todayreturtoalmoney = homepageTongjiMapper.queryToDayReturTotalmoney(companyId, startTimestamps, endTimestamps);//今日回款总金额（实还金额）
		BigDecimal queryToDayDeffer = homepageTongjiMapper.queryToDayDeffer(companyId, startTimestamps, endTimestamps);//今日回款总金额（延期费）
		BigDecimal todayoveruetotalmoney = homepageTongjiMapper.queryToDayOverueTotalmoney(companyId, startTimestamps, endTimestamps);//今日逾期已还金额
		
		//累计数据
		int sumregiste = homepageTongjiMapper.querySumRegiste(companyId);//累计注册用户
		int sumapply = homepageTongjiMapper.querySumApply(companyId);//累计申请用户总数
		int sumloan = homepageTongjiMapper.querySumLoan(companyId);//累计放款总笔数
		int sumrepayment = homepageTongjiMapper.querySumRepayment(companyId);//累计还款总笔数
		String paymentpasscvr = (sumloan/sumregiste)*100+"%";//放款通过率
		String orderrepaycvr = (sumrepayment/sumloan)*100+"%";//订单回款率
		BigDecimal payrecmoney = homepageTongjiMapper.querypayrecMoney(companyId);//累计放款总金额
		BigDecimal repaymoney = homepageTongjiMapper.queryrepayMoney(companyId);//累计回款总金额(实还金额)
		BigDecimal querydeffermoney = homepageTongjiMapper.querydeffermoney(companyId);//累计回款总金额（延期费）
		BigDecimal shouldmoney = homepageTongjiMapper.queryshouldMoney(companyId);//累计应收总金额
		//BigDecimal realymoney = payrecmoney.subtract(repaymoney);//实际收益
		
		//期限内数据
		int overdue = homepageTongjiMapper.overdue(companyId);//逾前未还笔数
		BigDecimal overduemoney = homepageTongjiMapper.overdueMoney(companyId);//逾前应收总金额
		
		//逾期数据
		int overdue1 = homepageTongjiMapper.overdue1(companyId);//逾后未还笔数
		
		Map<String, Object> map=new HashMap<>();
		map.put("todayregiste", todayregiste);//今日注册人数
		map.put("todayapply", todayapply);//今日申请人数
		map.put("todayloan", todayloan);//今日放款人数
		map.put("todaydeferred", todaydeferred);//今日延期笔数
		map.put("todayrepayment", todayrepayment);//今日回款笔数
		map.put("todayoverdue", todayoverdue);//今日逾期已还笔数
		map.put("todayloantotalmoney",todayloantotalmoney);//今日放款总金额
		//map.put("", );//今日回款总金额
		//map.put("", );//今日逾期已还金额
		
		map.put("sumregiste",sumregiste);//累计注册用户
		map.put("sumapply",sumapply);//累计申请用户总数
		map.put("sumloan",sumloan);//累计放款总笔数
		map.put("sumrepayment",sumrepayment);//累计还款总笔数
		map.put("paymentpasscvr",paymentpasscvr);//放款通过率
		map.put("orderrepaycvr",orderrepaycvr);//订单回款率
		map.put("payrecmoney",payrecmoney);//累计放款总金额
		//map.put("",);//累计回款总金额
		//map.put("",);//累计应收总金额
		//map.put("realymoney", realymoney);//实际收益
		
		map.put("overdue",overdue);//逾前未还笔数
		map.put("overduemoney", overduemoney);//逾前应收总金额
		
		map.put("overdue1", overdue1);//逾后未还笔数
		return map;
	}
	
	//回收率报表
	public Map<String, Object> recoveryStatement(Integer companyId,Integer page,String shouldrepayStartTime,String shouldrepayEndTime){
		List<HomepageTongji> listtongji=new ArrayList<HomepageTongji>();
		List<HomepageTongji> listtongjito=new ArrayList<HomepageTongji>();
		PageUtil pageUtil=null;
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
			List<Orders> listorders=homepageTongjiMapper.overduerepay(companyId, startTimestampsfor, endTimestampsfor);//已还款订单
			for (int j = 0; j < listorders.size(); j++) {
				try {
						String realtime=Timestamps.stampToDate(listorders.get(j).getRealtime());//实还时间
						String shouletime=Timestamps.stampToDate(listorders.get(j).getShouldReturnTime());//应还时间
					
						if(sdf.parse(realtime).getTime()>sdf.parse(shouletime).getTime()){//转成long类型比较
							System.out.println("实际还款时间大于应还时间");
							overdueafterrepay++;
						}else if(sdf.parse(realtime).getTime()<=sdf.parse(shouletime).getTime()){
							System.out.println("时间还款时间小于应还时间");
							overduerepay++;
						}
					} catch (ParseException e) {
						e.printStackTrace();
				}
			}
			int overdueafternotrepay=homepageTongjiMapper.overdueafternotrepay(companyId,startTimestampsfor, endTimestampsfor);//（逾后未还）
			int baddebt=homepageTongjiMapper.baddebt(companyId,startTimestampsfor, endTimestampsfor);//（已坏账）
			
			BigDecimal shouldmoney = new BigDecimal("0.00");//（应还金额）
			List<Orderdetails> orderdetails=homepageTongjiMapper.shouldmoney(companyId,startTimestampsfor, endTimestampsfor);
			for (int k = 0; k < orderdetails.size(); k++) {
				if(orderdetails.get(k).getInterestPenaltySum()==null){
					orderdetails.get(k).setInterestPenaltySum(new BigDecimal("0.00") );
				}
				BigDecimal shouldmoneyfor=orderdetails.get(k).getShouldReapyMoney().add(orderdetails.get(k).getInterestPenaltySum());
				shouldmoney=shouldmoney.add(shouldmoneyfor);
			}
			BigDecimal repaymentmoney=homepageTongjiMapper.realymoney(companyId,startTimestampsfor, endTimestampsfor);//（实际还金额）
		
			BigDecimal deferredmoney=homepageTongjiMapper.deferredmoney(companyId,startTimestampsfor, endTimestampsfor);//（延期费）
			
			BigDecimal overduemoney = new BigDecimal("0.00");//（逾期费）
			List<Orderdetails> listorderdetail=homepageTongjiMapper.overduemoney(companyId,startTimestampsfor, endTimestampsfor);
			for (int o = 0; o < listorderdetail.size(); o++) {
				if(orderdetails.get(o).getInterestPenaltySum()==null){
					orderdetails.get(o).setInterestPenaltySum(new BigDecimal("0.00") );
				}
				overduemoney=overduemoney.add(listorderdetail.get(i).getInterestPenaltySum());
			}
			
			BigDecimal deratemoney=new BigDecimal("0.00");//（减免金额）
			BigDecimal deratemoneyacc=homepageTongjiMapper.deratemoneyon(companyId,startTimestampsfor, endTimestampsfor);//线上
			BigDecimal deratemoneyoff=homepageTongjiMapper.deratemoneyunder(companyId,startTimestampsfor, endTimestampsfor);//线下
			deratemoney=deratemoneyacc.add(deratemoneyoff);
			
			BigDecimal tobepaidmoney=shouldmoney.subtract(repaymentmoney).subtract(deferredmoney).subtract(deratemoney);//待还金额
			String overduecvr=(overdueafternotrepay+baddebt)/shouldorder*100+"%";//逾期率
			//String recovery=
			
			
			homepageTongji.setShouldtime(list.get(i));//应还日期
			homepageTongji.setShouldorder(shouldorder);//应还订单
			homepageTongji.setOverduenotrepay(overduenotrepay);//（逾前未还）
			homepageTongji.setOverduerepay(overduerepay);////逾前已还
			homepageTongji.setOverdueafterrepay(overdueafterrepay);//逾后已还
			homepageTongji.setOverdueafternotrepay(overdueafternotrepay);//逾后未还
			homepageTongji.setBaddebt(baddebt);//已坏账
			homepageTongji.setShouldmoney(shouldmoney);//应还金额（期限内应还金额+逾期费）
			homepageTongji.setRealymoney(repaymentmoney);//实际还款金额
			homepageTongji.setDeferredmoney(deferredmoney);//延期费
			homepageTongji.setOverduemoney(overduemoney);//逾期费
			homepageTongji.setDeratemoney(deratemoney);//减免金额
			homepageTongji.setTobepaid(tobepaidmoney);//待还金额
			homepageTongji.setOverduecvr(overduecvr);//逾期率
			
			listtongji.add(homepageTongji);
		}
		
	 	if(listtongji!=null && !listtongji.isEmpty()){
    		ListPageUtil listPageUtil=new ListPageUtil(listtongji,page,10);
    		listtongjito.addAll(listPageUtil.getData());
    		
    		pageUtil=new PageUtil(listPageUtil.getCurrentPage(), listPageUtil.getPageSize(),listPageUtil.getTotalCount());
    	}else{
    		pageUtil=new PageUtil(1,10,0);

    	}
		
		Map<String, Object> map=new HashMap<>();
		map.put("listtongjito", listtongjito);
		map.put("pageutil", pageUtil);
		return map;
	}
	
	public List<Orderdetails> test(){
		List<Orderdetails> list=homepageTongjiMapper.test();
		BigDecimal shouldmoney = new BigDecimal("0.00");//应还金额
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
