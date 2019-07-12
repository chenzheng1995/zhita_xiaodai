package com.zhita.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.util.SystemOutLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.service.manage.login.IntLoginService;
import com.zhita.service.test.TestService;
import com.zhita.util.Timestamps;




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
	
	
	public static void main(String[] args) throws ParseException {
		 /*String str="600-800"; 
		 String[] strarray=str.split("-"); 
		 System.out.println(strarray[0]+"----"+strarray[1]);*/
		 
		/*//获取前一天的日期
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		String dateyes = df.format(calendar.getTime());
		System.out.println("今天日期的前一天："+dateyes);*/
		
		// System.out.println(1000*20/100);
		/*int a=0;
		int b=0;
		int c=0;
		if((a==1||(b==1)||(c==1))){
			System.out.println("0000");
		}*/
		String date="2019-07-12";
		String startTime = date;
		String startTimestamps = Timestamps.dateToStamp(startTime);
		String endTime = date;
		String endTimestamps = (Long.parseLong(Timestamps.dateToStamp(endTime))+86400000)+"";
		System.out.println(startTimestamps+"---"+endTimestamps);
	}
}
