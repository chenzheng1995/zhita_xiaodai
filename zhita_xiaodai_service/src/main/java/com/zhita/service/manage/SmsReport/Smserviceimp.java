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
import com.mysql.fabric.xmlrpc.base.Array;
import com.zhita.dao.manage.SmsMapper;
import com.zhita.model.manage.Shortmessage;
import com.zhita.model.manage.SmsSendRequest;
import com.zhita.model.manage.SmsSendResponse;
import com.zhita.model.manage.Thirdcalltongji;
import com.zhita.model.manage.Usershortmessage;
import com.zhita.util.ChuangLanSmsUtil;
import com.zhita.util.PageUtil;
import com.zhita.util.PhoneDeal;
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
			sm.setMsg("【米多宝】"+sm.getMsg());
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
		        System.out.println(sm.getCollection_time());
		        shor.setCollection_time(sm.getCollection_time());
		        
		        shor.setCompanyid(sm.getCompanyid());
		        
		        shor.setMsg(sm.getMsg());
		        
		        shor.setPhonenumber(sm.getPhone());
		        
		        shor.setPhonenum(sm.getPhonenum());
		        
		        if(smsSingleResponse.getErrorMsg().equals("")){
		        	SimpleDateFormat def = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		        	shor.setSend_time(def.format(new Date()));
		        	System.out.println(shor.getSend_time());
		        	Integer a = sdao.AddSms(shor);
		        	if(a != null){
		        		Thirdcalltongji th = new Thirdcalltongji();
		        		th.setCompanyid(shor.getCompanyid());
		        		th.setThirdtypeid(9);
		        		try {
		        			th.setDate(Timestamps.dateToStamp1(def.format(new Date())));
						} catch (Exception e) {
							// TODO: handle exception
						}
		        		th.setDeleted("0");
		        		sdao.AddThirdcallTongj(th);
		        	}
		        	map.put("code", "200");
		        	map.put("desc", "已发送,数据存储");
		        }else{
		        	map.put("code", "0");
		        	map.put("desc", "数据异常");
		        	
		        }
			}
			
			return map;
	}
	
	
	
	
	
	
	
	
	
	@Override
	public Map<String, Object> SendSmOne(SmsSendRequest sm) {
		sm.setPhonenum(1);
		
//		List<String> arraylist = new ArrayList<String>();
//		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
			
			Map<String, Object> map = new HashMap<String, Object>();
			sm.setMsg("【米多宝】"+sm.getMsg());
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
		        System.out.println(sm.getCollection_time());
		        shor.setCollection_time(sm.getCollection_time());
		        
		        shor.setCompanyid(sm.getCompanyid());
		        
		        shor.setMsg(sm.getMsg());
		        
		        shor.setPhonenumber(sm.getPhone());
		        
		        shor.setPhonenum(sm.getPhonenum());
		        
		        if(smsSingleResponse.getErrorMsg().equals("")){
		        	SimpleDateFormat def = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		        	shor.setSend_time(def.format(new Date()));
		        	System.out.println("时间:"+shor.getSend_time());
		        	Integer a = sdao.AddSms(shor);
		        	if(a != null){
		        		Thirdcalltongji th = new Thirdcalltongji();
		        		th.setCompanyid(shor.getCompanyid());
		        		th.setThirdtypeid(9);
		        		try {
		        			th.setDate(Timestamps.dateToStamp1(def.format(new Date())));
						} catch (Exception e) {
							// TODO: handle exception
						}
		        		th.setDeleted("0");
		        		sdao.AddThirdcallTongj(th);
		        	}
		        	map.put("code", "200");
		        	map.put("desc", "已发送,数据存储");
		        }else{
		        	map.put("code", "0");
		        	map.put("desc", "数据异常");
		        	
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
				for(int i=0;i<allsho.size();i++){
					allsho.get(i).setCompanyid(shor.getCompanyid());
					allsho.get(i).setShortmessagesize(sdao.SelectTimeSize(allsho.get(i)));
				}
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
		Date date = null;
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
		String a = sim.format(new Date());
		String c = a+" 00:00:01";
		String d = a+" 23:59:59";
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> phones = null;
		if(sm.getBiaoshi()==0){
			try {
				sm.setStatu_time(Timestamps.dateToStamp1(c));
				sm.setEnd_time(Timestamps.dateToStamp1(d));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}else if(sm.getBiaoshi() == 1){
			    calendar.add(calendar.DATE, 1);//把日期往后增加n天.正数往后推,负数往前移动 
			    date=calendar.getTime();  //这个时间就是日期往后推一天的结果 
			    a = sim.format(date);//延期后应还时间
			    System.out.println("a:"+a);
			    String statu_time = a+" 00:00:01";
			    String end_time = a+" 23:59:59";
			    System.out.println(statu_time+"1"+end_time);
			try {
				sm.setStatu_time(Timestamps.dateToStamp1(statu_time));
				sm.setEnd_time(Timestamps.dateToStamp1(end_time));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}else if(sm.getBiaoshi() == 2){
			calendar.add(Calendar.DATE, 2);
			 date=calendar.getTime();  //这个时间就是日期往后推一天的结果 
			 a = sim.format(date);//延期后应还时间
			 System.out.println("a:"+a);
			 String statu_time = a+" 00:00:01";
			 String end_time = a+" 23:59:59";
			 System.out.println(statu_time+"1"+end_time);
			try {
				sm.setStatu_time(Timestamps.dateToStamp1(statu_time));
				sm.setEnd_time(Timestamps.dateToStamp1(end_time));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}else if(sm.getBiaoshi() == 3){
			calendar.add(Calendar.DATE, 3);
			date=calendar.getTime();  //这个时间就是日期往后推一天的结果 
			 a = sim.format(date);//延期后应还时间
			 System.out.println("a:"+a);
			 String statu_time = a+" 00:00:01";
			 String end_time = a+" 23:59:59";
			 System.out.println(statu_time+"1"+end_time);
			try {
				sm.setStatu_time(Timestamps.dateToStamp1(statu_time));
				sm.setEnd_time(Timestamps.dateToStamp1(statu_time));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		List<String> phon = new ArrayList<String>();
		phones = sdao.AllPhone(sm);
		PhoneDeal p = new PhoneDeal();
		for(int i=0;i<phones.size();i++){
			phon.add(p.decryption(phones.get(i)));
		}
		Shortmessage shor = new Shortmessage();
		shor.setPhonesa(phon);
		shor.setCompanyid(sm.getCompanyid());
		shor.setSend_time(Timestamps.stampToDate1(sm.getStatu_time()));
		shor.setPhonenum(phones.size());
		List<Shortmessage> sho = new ArrayList<Shortmessage>();
		shor.setShortmessagesize(sdao.SelectTimeSize(shor));
		shor.setCollection_time(shor.getSend_time());
		sho.add(shor);
		map.put("Shortmessage", sho);
		return map;
	}

	@Override
	public Map<String, Object> AllCollection(String collection_time) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Shortmessage> shor = sdao.AllController(collection_time);
		map.put("Shortmessage", shor);
		return map;
	}

	@Override
	public Map<String, Object> UserTypes(Usershortmessage companyId) {
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println(companyId.getRegisteClient());
		List<String> phones = sdao.AllRegist(companyId);
		PhoneDeal p = new PhoneDeal();
		String a = null;
		List<String> phon = new ArrayList<String>();
		for (int i = 0; i < phones.size(); i++) {
			a=p.decryption(phones.get(i));
			phon.add(a);
		}
		Integer phonNum = phones.size();
		map.put("phones", phon);
		map.put("phonNum", phonNum);
		return map;
	}

	
	@Override
	public Map<String, Object> AllUserShortMessage(Usershortmessage usershortmessage) {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer totalCount = sdao.UserPage(usershortmessage.getCompanyId());
		PageUtil pages = new PageUtil(usershortmessage.getPage(), totalCount);
		usershortmessage.setPage(pages.getPage());
		List<Usershortmessage> usershort = sdao.AllUsershortmessage(usershortmessage);
		map.put("Usershortmessage", usershort);
		return map;
	}

	
	@Override
	public Map<String, Object> AddUserShortMessage(Usershortmessage shor) {
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println(shor.getPhone());
		if(shor.getPhone() == null){
			map.put("code", "0");
			map.put("desc", "手机号不能为空");
		}else if(shor.getShort_text() == null){
			map.put("code", "0");
			map.put("desc", "内容不能为空");
		}else{
			shor.setShort_text("【米多宝】"+shor.getShort_text());
			SmsSendRequest smsSingleRequest = new SmsSendRequest(account, password, shor.getShort_text(), shor.getPhone(),report);

	        String requestJson = JSON.toJSONString(smsSingleRequest);

	        System.out.println("before request string is: " + requestJson);

	        String response = ChuangLanSmsUtil.sendSmsByPost(smsSingleRequestServerUrl, requestJson);

	        System.out.println("response after request result is :" + response);

	        SmsSendResponse smsSingleResponse = JSON.parseObject(response, SmsSendResponse.class);

	        System.out.println("response  toString is :" + smsSingleResponse);
	        
	        
	        if(smsSingleResponse.getErrorMsg().equals("")){
	        	SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    		shor.setSend_time(sim.format(new Date()));
	    		if(null == shor.getUser_type()  || shor.getUser_type().equals("")){
					shor.setUser_type("全部");
				}
	    		Integer addId = sdao.AddUserShortmessage(shor);
	    		if(addId != null){
	    			Thirdcalltongji th = new Thirdcalltongji();
	        		th.setCompanyid(shor.getCompanyId());
	        		th.setThirdtypeid(9);
	        		try {
	        			th.setDate(Timestamps.dateToStamp(sim.format(new Date())));
					} catch (Exception e) {
						// TODO: handle exception
					}
	        		th.setDeleted("0");
	        		sdao.AddThirdcallTongj(th);
	    			map.put("code", 200);
	    			map.put("desc", "成功");
	    		}else{
	    			map.put("code", 200);
	    			map.put("desc", "成功");
	    		}
	        }else{
	        	map.put("code", "0");
	        	map.put("desc", "数据异常");
	        	
	        }
	}
	
		return map;
	}









	@Override
	public void sendDateSned(SmsSendRequest sms) {
		System.out.println("进接口");
		Calendar calendar = Calendar.getInstance();
		Date date = null;
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
		String a = sim.format(new Date());
		List<String> phones = null;
		SimpleDateFormat sims = new SimpleDateFormat("HH");
	    sms.setStatu_time(sims.format(new Date())+":00");
	    sms.setEnd_time(sims.format(new Date())+":59");
	    SmsSendRequest ss = sdao.getSelectMsg(sms);
	    if(ss!=null){
			calendar.add(Calendar.DATE, ss.getOverdueday());//把日期往后增加n天.正数往后推,负数往前移动 
		    date=calendar.getTime();  //这个时间就是日期往后推一天的结果 
		    a = sim.format(date);//延期后应还时间
		    System.out.println("a:"+a);
		    String statu_time = a+" 00:00:01";
		    String end_time = a+" 23:59:59";
		    System.out.println("短信内容:"+ss.getContent());
		    String msg = ss.getContent();
		    if(msg!=null){
		    	if(msg.length()!=0){
		    		System.out.println("进");
		    		sms.setMsg(msg);
				    System.out.println(statu_time+"1"+end_time);
				try {
					sms.setStatu_time(Timestamps.dateToStamp1(statu_time));
					sms.setEnd_time(Timestamps.dateToStamp1(end_time));
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				List<String> phon = new ArrayList<String>();
				phones = sdao.AllPhone(sms);
				PhoneDeal p = new PhoneDeal();
				for(int i=0;i<phones.size();i++){
					phon.add(p.decryption(phones.get(i)));
				}
				Shortmessage shor = new Shortmessage();
				shor.setPhonesa(phon);
				shor.setCompanyid(sms.getCompanyid());
				shor.setSend_time(Timestamps.stampToDate1(sms.getStatu_time()));
				shor.setPhonenum(phones.size());
				List<Shortmessage> sho = new ArrayList<Shortmessage>();
				shor.setShortmessagesize(sdao.SelectTimeSize(shor));
				shor.setCollection_time(shor.getSend_time());
				sho.add(shor);
		    	}
	    }
	    
	    
	}
	System.out.println("出接口");
}
	
}
	
	
	
	
	
