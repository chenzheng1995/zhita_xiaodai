package com.zhita.service.manage.usthresholdvalue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhita.dao.manage.UsersourceThresholdvalueMapper;
import com.zhita.util.DateListUtil;
import com.zhita.util.Timestamps;

@Service
public class UsthresholdvalueServiceImp implements IntUsthresholdvalueService{
	@Autowired
	private UsersourceThresholdvalueMapper usersourceThresholdvalueMapper;
	
	//流量渠道最大阀值
	public Map<String,Object> usersourcemax(Integer companyId){
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
		//当天注册量
		int todayregiste = usersourceThresholdvalueMapper.queryAllSourceRegistetoday(companyId, startTimestamps, endTimestamps);
		
		//user表所有的注册数量
		int allregiste = usersourceThresholdvalueMapper.queryAllSourceRegiste(companyId);
		
		//user表最早的注册时间
		String minregistestamps = usersourceThresholdvalueMapper.queryregistetimemin(companyId);
		String minregiste = Timestamps.stampToDate1(minregistestamps);
		Date minregisteDate = null;
		try {
			 minregisteDate = sf.parse(minregiste);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int days=DateListUtil.differentDaysByMillisecond(minregisteDate, d)+1;
		
		int dailyregiste=allregiste/days;//日均注册数
		
		int maxthresholdvalue = usersourceThresholdvalueMapper.maxthresholdvalue(companyId);//注册量最大阀值
		
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("todayregiste", todayregiste);//当天注册量
		map.put("dailyregiste", dailyregiste);//日均注册数
		map.put("maxthresholdvalue", maxthresholdvalue);//注册量最大阀值
		return map;
	}
	
	//修改最大阀值
	@Transactional
	public int upamaxthresholdvalue(Integer maxthresholdvalue){
		int num=usersourceThresholdvalueMapper.upamaxthresholdvalue(maxthresholdvalue);
		return num;
	}

	@Override
	public int getmaxthresholdvalue(int companyId) {
		int maxthresholdvalue = usersourceThresholdvalueMapper.getmaxthresholdvalue(companyId);
		return maxthresholdvalue;
	}
}
