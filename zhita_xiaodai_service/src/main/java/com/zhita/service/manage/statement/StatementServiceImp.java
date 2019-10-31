package com.zhita.service.manage.statement;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhita.dao.manage.HomepageTongjiMapper;
import com.zhita.dao.manage.OrdersMapper;
import com.zhita.dao.manage.StatmentMapper;
import com.zhita.model.manage.HomepageTongji;
import com.zhita.model.manage.RepayStatment;
import com.zhita.model.manage.Source;
import com.zhita.util.DateListUtil;
import com.zhita.util.PageUtil2;
import com.zhita.util.Timestamps;

@Service
public class StatementServiceImp implements IntstatementService{
	
	@Autowired
	private OrdersMapper ordersMapper;
	@Autowired
	private HomepageTongjiMapper homepageTongjiMapper;
	@Autowired
	private StatmentMapper statmentMapper;
	
	//后台管理——查询所有正常的渠道
	public List<Source> querysource(Integer companyId){
		List<Source> listsource=ordersMapper.querysource(companyId);
		return listsource;
	}
	
	//后台管理——还款表
	public Map<String,Object> paymentrecord(Integer companyId,Integer page,String repaystartTime,String rapayendTime){
		List<RepayStatment> listtongji=new ArrayList<RepayStatment>();
		List<RepayStatment> listtongjito=new ArrayList<RepayStatment>();
		PageUtil2 pageUtil=null;
		
		int lifeofloan = homepageTongjiMapper.querylifeOfLoan(companyId);//查询借款期限
		Date date=new Date();
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = DateUtils.addDays(date, -lifeofloan);
		
		String startTime = null;//开始时间（年月日格式）
		String endTime = null;//结束时间（年月日格式）
		if((repaystartTime!=null&&!"".equals(repaystartTime))&&(rapayendTime!=null&&!"".equals(rapayendTime))){
			startTime = repaystartTime;
			endTime = rapayendTime;
		}else{
			startTime = sf.format(startDate);
			endTime = sf.format(date);
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
			
			BigDecimal paysummoney = homepageTongjiMapper.queryToDayLoanTotalmoney(companyId, startTimestampsfor, endTimestampsfor);//当前日期总计放款金额-
			int paysumcount = homepageTongjiMapper.queryToDayLoan(companyId, startTimestampsfor, endTimestampsfor);//当前日期总计放款人数-
			int newpaycount = 0;//当前日期新客放款人数-
			BigDecimal newpaymoney = new BigDecimal("0.00");//当前日期新客放款金额-
			List<Integer> queryLoannewlist=statmentMapper.queryLoannew(companyId, startTimestampsfor, endTimestampsfor);//当前日期借款次数为1的订单id
			BigDecimal money=new BigDecimal("0.00");
			for (int j = 0; j < queryLoannewlist.size(); j++) {
				int orderid=queryLoannewlist.get(i);
				int count=statmentMapper.querydefercount(orderid);//该订单延期次数
				if(count<=1){//代表该订单是新客
					newpaycount++;
					money=statmentMapper.queryrepaymoney(orderid);
				}
				newpaymoney=newpaymoney.add(money);
			}
			
			int oldpaycount = homepageTongjiMapper.queryToDayLoanold(companyId, startTimestampsfor, endTimestampsfor);//当前日期老客放款人数-
			BigDecimal oldpaymoney = statmentMapper.queryoldmoney(companyId, startTimestampsfor, endTimestampsfor);//当前日期老客放款金额-
			int loanyetrepay = statmentMapper.queryloanyetrepay(companyId, startTimestampsfor, endTimestampsfor);//当前日期放款的订单已还人数-
			String oldborrowcvr = (new DecimalFormat("#0.00").format((oldpaycount)*1.0/loanyetrepay*100))+"%";//复借率-
			int querypass = statmentMapper.querypass(companyId, startTimestampsfor, endTimestampsfor);//当天通过人数-
			String borrowcvr = (new DecimalFormat("#0.00").format((newpaycount+oldpaycount)*1.0/querypass*100))+"%";//当天借款率
			String oldguestborrowcvr = (new DecimalFormat("#0.00").format((oldpaycount)*1.0/paysumcount*100))+"%";//老客复借占比
			
		}
		Map<String,Object> map=new HashMap<>();
		return map;
	}
}
