package com.zhita.controller;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.dao.manage.SourceDiscountHistoryMapper;
import com.zhita.dao.manage.SourceMapper;
import com.zhita.model.manage.User;
import com.zhita.service.manage.login.IntLoginService;
import com.zhita.service.test.TestService;
import com.zhita.util.PhoneDeal;
import com.zhita.util.RedisClientUtil;
import com.zhita.util.Timestamps;
import com.zhita.util.TuoMinUtil;




@Controller
@RequestMapping("/test")
public class TestController {
	@Autowired
	TestService testService;
	@Autowired
	private IntLoginService intLoginService;
	@Autowired
	private SourceMapper sourceMapper;
	@Autowired
	private SourceDiscountHistoryMapper sourceDiscountHistoryMapper;

	
	@RequestMapping("/settest")
	@ResponseBody
	@Transactional
	public Map<String, Object> settest(String name) {
		Map<String, Object> map = new HashMap<String, Object>();
		int number = testService.settest(name);
		if (number == 1) {		
			map.put("msg", "添加成功");
			map.put("SCode", "200");
		} else {
			map.put("msg", "添加失败");
			map.put("SCode", "405");
		}
		return map;
	
	}
	
	@RequestMapping("/gettest")
	@ResponseBody
	@Transactional
	public Map<String, Object> gettest(int id) {
		Map<String, Object> map = new HashMap<String, Object>();
		String name = testService.gettest(id);
		map.put("name",name);
		return map;
	}
	
	@ResponseBody
	@RequestMapping("/test")
	public void test(HttpServletRequest request) {
		String account=(String) request.getSession().getAttribute("account");
		String pwd=(String) request.getSession().getAttribute("pwd");
		// 获取账号的角色和权限信息
		List<String> list=intLoginService.queryRoleByAccountAndPwd(account, pwd);//查询当前用户所拥有的角色
		boolean ishas=false;
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).equals("经理")){
				ishas=true;
			}else{
				ishas=false;
			}
		}
		if(ishas){
			System.out.println("看到的数据不脱敏");
		}else{
			System.out.println("看到的数据脱敏");
		}
	}
	
	
	public static String getShouldReturned(int day) {
	    Date date = new Date();//取时间 
	    Calendar calendar  =   Calendar.getInstance();		 
	    calendar.setTime(date); //需要将date数据转移到Calender对象中操作
	    calendar.add(Calendar.DATE, day);//把日期往后增加n天.正数往后推,负数往前移动 
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
	
	
	public static void main(String[] args) throws ParseException {
		//获取前一天的日期
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		String dateyes = df.format(calendar.getTime());
		System.out.println("今天日期的前一天："+dateyes);
		
		String startTime1 = dateyes;
		String startTimestamps1 = Timestamps.dateToStamp(startTime1);//该时间为时间戳格式
		String endTime1 = dateyes;
		String endTimestamps1 = (Long.parseLong(Timestamps.dateToStamp(endTime1))+86400000)+"";
		System.out.println(startTimestamps1+"----------"+endTimestamps1);
	}	
	
	@ResponseBody
	@RequestMapping("/lhqtest")
	public void test(){
		Integer companyId=3;
		String sourceName="CS888";
		Integer sourceid=65;
			List<String> liststr=sourceMapper.queryTime(companyId, sourceName);// 查询出当前渠道所有的注册时间(liststr里面的时间为时间戳格式)
			if(liststr!=null&&!liststr.isEmpty()) {//代表当前渠道在用户表有注册的用户
				List<String> list1 = new ArrayList<>();// 用来存时间戳转换后的时间（年月日格式的时间）(user的注册时间)
				for (int j = 0; j< liststr.size(); j++) {
					list1.add(Timestamps.stampToDate1(liststr.get(j)));
				}
				HashSet h = new HashSet(list1);
				list1.clear();
				list1.addAll(h);
				
				Date d=new Date();
				SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
				String date=sf.format(d);//date为当天时间(格式为年月日)
				
				list1.remove(date);//将当天时间从list1中移除
				List<String> listdate=sourceDiscountHistoryMapper.queryDate(sourceid);//当前渠道在历史表的时间（listdate里面的时间为时间戳格式）
				
				List<String> list2 = new ArrayList<>();// 用来存时间戳转换后的时间（年月日格式的时间）（历史表的时间）
				for (int k = 0; k < listdate.size(); k++) {
					list2.add(Timestamps.stampToDate1(listdate.get(k)));
				}
				HashSet h1 = new HashSet(list2);
				list2.clear();
				list2.addAll(h1);
				
				List<String> intersectionlist=list1.stream().filter(t-> !list2.contains(t)).collect(Collectors.toList());//差集
				intersectionlist.stream().forEach(System.out::println);
				
				for (int i = 0; i < list1.size(); i++) {
					System.out.println("list1:"+list1.get(i));
				}
				System.out.println("-------------------");
				for (int i = 0; i < list2.size(); i++) {
					System.out.println("list2:"+list2.get(i));
				}
				System.out.println("-------------------");
				for (int i = 0; i < intersectionlist.size(); i++) {
					System.out.println("intersectionlist:"+intersectionlist.get(i));
				}
		}
	}
}
