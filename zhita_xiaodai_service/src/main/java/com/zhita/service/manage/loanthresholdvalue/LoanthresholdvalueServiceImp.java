package com.zhita.service.manage.loanthresholdvalue;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhita.dao.manage.LoanThresholdvalueMapper;
import com.zhita.util.DateListUtil;
import com.zhita.util.Timestamps;

@Service
public class LoanthresholdvalueServiceImp implements IntLoanthresholdvalueService{
	@Autowired
	private LoanThresholdvalueMapper loanThresholdvalueMapper;
	

	//放款渠道最大阀值
	public Map<String,Object> loanmax(Integer companyId){
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
		//当天放款金额
		BigDecimal loantoday = loanThresholdvalueMapper.paymentToday(companyId, startTimestamps, endTimestamps);
		
		//当天所有的放款金额
		BigDecimal loanall = loanThresholdvalueMapper.allpayment(companyId);
		
		//放款表最早的放款时间
		String minregistestamps = loanThresholdvalueMapper.minpaymenttime(companyId);
		String minregiste = Timestamps.stampToDate1(minregistestamps);
		Date minregisteDate = null;
		try {
			 minregisteDate = sf.parse(minregiste);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int days=DateListUtil.differentDaysByMillisecond(minregisteDate, d)+1;
		BigDecimal daysbig=new BigDecimal(days);
		
		BigDecimal dailyloanmoney=new BigDecimal("0.00");
		if(daysbig.compareTo(BigDecimal.ZERO)!=0){
			dailyloanmoney=loanall.divide(daysbig,2,BigDecimal.ROUND_HALF_UP);//日均放款金额
		}
		
		int maxthresholdvalue = loanThresholdvalueMapper.maxthresholdvalue(companyId);//放款渠道最大阀值
		
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("loantoday", loantoday);//当天放款金额
		map.put("dailyloanmoney", dailyloanmoney);//日均放款金额
		map.put("maxthresholdvalue", maxthresholdvalue);//放款渠道最大阀值
		return map;
	}
	
	//修改最大阀值
	@Transactional
	public int upamaxthresholdvalue(Integer maxthresholdvalue){
		int num=loanThresholdvalueMapper.upamaxthresholdvalue(maxthresholdvalue);
		return num;
	}
	
	//后台管理---查看当天放款金额
	public Map<String,Object> queryloantoday(Integer companyId){
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
		//当天放款金额
		BigDecimal loantoday = loanThresholdvalueMapper.paymentToday(companyId, startTimestamps, endTimestamps);
		List<String> list=loanThresholdvalueMapper.queryStatus(companyId);
		
		Map<String,Object> map=new HashMap<>();
		map.put("loantoday", loantoday);
		map.put("status", list.get(0));
		return map;
	}
	
	//后台管理----修改放款渠道状态
	@Transactional
    public int upaloanstatus(String status,Integer companyId){
    	int num= loanThresholdvalueMapper.upaloanstatus(status,companyId);
    	return num;
    }

	@Override
	public int getmaxthresholdvalue(int companyId) {
		int maxthresholdvalue = loanThresholdvalueMapper.getmaxthresholdvalue(companyId);
		return maxthresholdvalue;
	}
}
