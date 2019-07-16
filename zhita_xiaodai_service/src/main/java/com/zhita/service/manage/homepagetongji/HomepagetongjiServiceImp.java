package com.zhita.service.manage.homepagetongji;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhita.dao.manage.HomepageTongjiMapper;
import com.zhita.util.Timestamps;

@Service
public class HomepagetongjiServiceImp implements IntHomepagetongjiService{
	
	@Autowired
	private HomepageTongjiMapper homepageTongjiMapper;
	
	//首页统计
	public Map<String, Object> queryAll() throws ParseException{
		Date d=new Date();
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		String date=sf.format(d);//date为当天时间(格式为年月日)
		
		String startTime = date;
		String startTimestamps = Timestamps.dateToStamp(startTime);
		String endTime = date;
		String endTimestamps = (Long.parseLong(Timestamps.dateToStamp(endTime))+86400000)+"";
		
		
		
		Map<String, Object> map=new HashMap<>();
		return map;
	}
}
