package com.zhita.service.manage.SmsReport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.zhita.dao.manage.SmsMapper;
import com.zhita.model.manage.Shortmessage;
import com.zhita.model.manage.SmsSendRequest;
import com.zhita.model.manage.SmsSendResponse;
import com.zhita.util.ChuangLanSmsUtil;
import com.zhita.util.PageUtil;
import com.zhita.util.Timestamps;


@Service
public class Smserviceimp implements Smservice{
	

	 public static final String charset = "utf-8";
	    // 用户平台API账号(非登录账号,示例:N1234567)
	 public static String account = "N7893961";
	    // 用户平台API密码(非登录密码)
	 public static String password = "xap6Mikey";
	 
	 //请求地址请登录253云通讯自助通平台查看或者询问您的商务负责人获取
     String smsSingleRequestServerUrl = "http://smssh1.253.com/msg/send/json";

   //状态报告
     String report= "true";
	
	@Autowired
	private SmsMapper sdao;

	@Override
	public Map<String, Object> SendSm(SmsSendRequest sm) {
		
//		List<String> arraylist = new ArrayList<String>();
//		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
			
			Map<String, Object> map = new HashMap<String, Object>();
			System.out.println(sm.getPhone());
			if(sm.getPhone() == null){
				map.put("code", "0");
				map.put("desc", "手机号不能为空");
			}else if(sm.getMsg() == null){
				map.put("code", "0");
				map.put("desc", "内容不能为空");
			}else{
				SmsSendRequest smsSingleRequest = new SmsSendRequest(account, password, sm.getMsg(), sm.getPhone(),report);

		        String requestJson = JSON.toJSONString(smsSingleRequest);

		        System.out.println("before request string is: " + requestJson);

		        String response = ChuangLanSmsUtil.sendSmsByPost(smsSingleRequestServerUrl, requestJson);

		        System.out.println("response after request result is :" + response);

		        SmsSendResponse smsSingleResponse = JSON.parseObject(response, SmsSendResponse.class);

		        System.out.println("response  toString is :" + smsSingleResponse);
		        
		        Shortmessage shor = new Shortmessage();
		        
		        shor.setCompanyid(sm.getCompanyid());
		        
		        shor.setSmg(sm.getMsg());
		        
		        shor.setPhonenumber(sm.getPhone());
		        
		        shor.setPhonenum(sm.getPhonenum());
		        
		        if(smsSingleResponse.getErrorMsg() != null && smsSingleResponse.getErrorMsg() != ""){
		        	map.put("code", "0");
		        	map.put("desc", "数据异常");
		        }else{
		        	
		        	SimpleDateFormat def = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		        	shor.setSend_time(def.format(new Date()));
		        	sdao.AddSms(shor);
		        	map.put("code", "200");
		        	map.put("desc", "已发送,数据存储");
		        }
			}
			
			return map;
	}

	@Override
	public Map<String, Object> DayShortMessage(Shortmessage shor) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(shor.getCompanyid() != null){
			List<Shortmessage> Total = sdao.DayTotalCount(shor.getCompanyid());
			PageUtil pages = new PageUtil(shor.getPage(), Total.size());
			shor.setPage(pages.getPage());
			if(shor.getCompanyid() != null){
				List<Shortmessage> allsho = sdao.DayShortMessage(shor);
				map.put("Shortmessage", allsho);
				map.put("pages", pages);
			}else{
				map.put("Shortmessage", "无数据");
				map.put("pages", 0);
			}
		}else{
			map.put("Shortmessage", "公司ID不未null");
			map.put("pages", 0);
		}
		
		return map;
	}
	
	
	

	@Override
	public Map<String, Object> OneDayShortMessage(Shortmessage shor) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Shortmessage> Total = sdao.AllDayShortMessage(shor);
		map.put("Shortmessage", Total);
		return map;
	}
	
	
	
	
	

	@Override
	public Map<String, Object> AllPhone(SmsSendRequest sm) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, +1); //得到前一天
		Date date = calendar.getTime();
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
		String a = sim.format(date);
		String b = sim.format(new Date());
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> phones = null;
		if(sm.getBiaoshi()==0){
			try {
				sm.setStatu_time(Timestamps.dateToStamp(b));
				sm.setEnd_time(Timestamps.dateToStamp(a));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}else if(sm.getBiaoshi() == 1){
			calendar.add(Calendar.DATE, +1); //得到前一天
			date = calendar.getTime();
			a = sim.format(date);
			calendar.add(Calendar.DATE, +2); //得到前两天
			date = calendar.getTime();
			b = sim.format(date);
			try {
				sm.setStatu_time(Timestamps.dateToStamp(b));
				sm.setEnd_time(Timestamps.dateToStamp(a));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}else if(sm.getBiaoshi() == 2){
			calendar.add(Calendar.DATE, +2);
			date = calendar.getTime();
			a = sim.format(date);
			calendar.add(Calendar.DATE, +3); 
			date = calendar.getTime();
			b = sim.format(date);
			try {
				sm.setStatu_time(Timestamps.dateToStamp(b));
				sm.setEnd_time(Timestamps.dateToStamp(a));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}else if(sm.getBiaoshi() == 3){
			calendar.add(Calendar.DATE, +3);
			date = calendar.getTime();
			a = sim.format(date);
			calendar.add(Calendar.DATE, +4);
			date = calendar.getTime();
			b = sim.format(date);
			try {
				sm.setStatu_time(Timestamps.dateToStamp(b));
				sm.setEnd_time(Timestamps.dateToStamp(a));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		phones = sdao.AllPhone(sm);
		Shortmessage shor = new Shortmessage();
		shor.setPhonesa(phones);
		shor.setCollection_time(Timestamps.stampToDate1(sm.getStatu_time()));
		shor.setPhonenum(phones.size());
		List<Shortmessage> sho = new ArrayList<Shortmessage>();
		sho.add(shor);
		map.put("Shortmessage", sho);
		return map;
	}

	@Override
	public Map<String, Object> AllCollection(String collection_time) {
//		try {
//			collection_time = Timestamps.dateToStamp(collection_time);
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
		Map<String, Object> map = new HashMap<String, Object>();
		List<Shortmessage> shor = sdao.AllController(collection_time);
		map.put("Shortmessage", shor);
		return map;
	}
	
}
