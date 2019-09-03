package com.zhita.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.model.manage.Orders;
import com.zhita.model.manage.User;
import com.zhita.service.manage.login.IntLoginService;
import com.zhita.service.test.TestService;
import com.zhita.util.DateListUtil;
import com.zhita.util.PhoneDeal;
import com.zhita.util.RedisClientUtil;
import com.zhita.util.TuoMinUtil;




@Controller
@RequestMapping("/test")
public class TestController {
	@Autowired
	TestService testService;
	@Autowired
	private IntLoginService intLoginService;

	
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
		/*DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = new Date();
		String date = sdf.format(d);
		System.out.println("当天时间："+date);
		
		String shoulddatestr = "2019-07-21 00:00:00";
		
		if(sdf.parse(date).getTime()>sdf.parse(shoulddatestr).getTime()){//转成long类型比较
			System.out.println("当前时间大于应还时间");
			Date shoulddate = sdf.parse(shoulddatestr);
			System.out.println(DateListUtil.differentDaysByMillisecond(shoulddate,d)+"*****");
		}*/
		/*Integer in=9;
		
		List<OverdueSettings> list=new ArrayList<>();
		List<BigDecimal> list1=new ArrayList<>();
		OverdueSettings ov=new OverdueSettings();
		ov.setOverduehowmanydaysage(2);
		ov.setPenaltyinterestrates(new BigDecimal(0.1));
		
		OverdueSettings ov1=new OverdueSettings();
		ov1.setOverduehowmanydaysage(4);
		ov1.setPenaltyinterestrates(new BigDecimal(0.2));
		
		OverdueSettings ov2=new OverdueSettings();
		ov2.setOverduehowmanydaysage(6);
		ov2.setPenaltyinterestrates(new BigDecimal(0.3));
		
		OverdueSettings ov3=new OverdueSettings();
		ov3.setOverduehowmanydaysage(8);
		ov3.setPenaltyinterestrates(new BigDecimal(0.4));
		
		OverdueSettings ov4=new OverdueSettings();
		ov4.setOverduehowmanydaysage(10);
		ov4.setPenaltyinterestrates(new BigDecimal(0.5));
		
		
		list.add(ov);
		list.add(ov1);
		list.add(ov2);
		list.add(ov3);
		list.add(ov4);
		for (int i = 0; i < list.size(); i++) {
			if(in<=list.get(i).getOverduehowmanydaysage()){
				System.out.println(list.get(i).getPenaltyinterestrates()+"-----------");
				list1.add(list.get(i).getPenaltyinterestrates());
			}
		}
		
		System.out.println(list1.get(0)+"*********");*/
		
		/*BigDecimal a = new BigDecimal(3000);
		BigDecimal b = new BigDecimal(0.1);
		System.out.println(a.multiply(b).divide(new BigDecimal("100"),2,BigDecimal.ROUND_HALF_UP)+"**********");*/
		/*String over="5";
		String s="1-3";
		String[] str=s.split("-");
		String start = str[0];
		String end = str[0];
		for (int i = 0; i < str.length; i++) {
			if(Integer.parseInt(over)>=Integer.parseInt(start)&& Integer.parseInt(over)<=Integer.parseInt(end)){
				
			}
		}*/
		PhoneDeal p = new PhoneDeal();
		TuoMinUtil tm = new TuoMinUtil();
		//System.out.println(tm.mobileEncrypt(p.decryption("6376462xx82")));
		//System.out.println(p.decryption("90667926x29"));
		System.out.println(p.encryption("15652456230"));
		/*String province="山西省稷山县蔡村乡杨村第九居民组";
		System.out.println(province.substring(0,3));*/
		//System.out.println(Timestamps.stampToDate(null));
		//DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		RedisClientUtil redisClientUtil=new RedisClientUtil();
		redisClientUtil.set("贷回家qudao12019/08/26xiaodaiKey", "1");
		System.out.println(redisClientUtil.get("贷回家qudao12019/08/26xiaodaiKey"));
		
		redisClientUtil.set("贷回家qudao12019/08/27xiaodaiKey", "12");
		System.out.println(redisClientUtil.get("贷回家qudao12019/08/27xiaodaiKey"));
		
		redisClientUtil.set("贷回家qudao12019/08/28xiaodaiKey", "2");
		System.out.println(redisClientUtil.get("贷回家qudao12019/08/28xiaodaiKey"));
		
		String dateStart="2019-08-26";
		String dateEnd="2019-09-02";
		List<String> listdate=DateListUtil.getDays(dateStart, dateEnd);
		for (int i = 0; i < listdate.size(); i++) {
			System.out.println(listdate.get(i));
		}
		
		int v=7;
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		Date startDate = DateUtils.addDays(now, -v);
		Date endDate = DateUtils.addDays(now, v);
		System.out.println(sf.format(startDate)+"--------"+sf.format(endDate));
		
		String realmoney="0";
		String offmoney="0";
		String bankmoney="0";
		BigDecimal money=new BigDecimal(realmoney).add(new BigDecimal(offmoney)).add(new BigDecimal(bankmoney));
		System.out.println(String.valueOf(money));
		
		List<Orders> list1=new ArrayList<Orders>();
		/*Orders  o1=new Orders();
		o1.setId(1);
		list1.add(o1);*/
		
		List<Orders> list2=new ArrayList<Orders>();
		Orders  o2=new Orders();
		o2.setId(2);
		list1.add(o2);
		
		List<Orders> list3=new ArrayList<Orders>();
		/*Orders  o3=new Orders();
		o3.setId(3);
		list1.add(o3);*/
		
		list1.addAll(list2);
		list1.addAll(list3);
		for (int i = 0; i < list1.size(); i++) {
			System.out.println(list1.get(i).getId()+"size");
		}
		
		int a=0;
		List<User> listuser=new ArrayList<>();
		User user=new User();
		user.setRegistetime("123333");
		user.setLogintime("233333");
		
		User user1=new User();
		user1.setRegistetime("231");
		user1.setLogintime("332");
		
		
		User user2=new User();
		user2.setRegistetime("1");
		user2.setLogintime("1");
		
		
		User user3=new User();
		user3.setRegistetime("134");
		user3.setLogintime("467");
		
		
		listuser.add(user);
		listuser.add(user1);
		listuser.add(user2);
		listuser.add(user3);
		for (int i = 0; i < listuser.size(); i++) {
			if(listuser.get(i).getRegistetime()!=listuser.get(i).getLogintime()&&listuser.get(i).getRegistetime().equals(listuser.get(i).getLogintime())==false){
				a++;
			}
		}
		System.out.println(a+"---------------");
	}	
}
