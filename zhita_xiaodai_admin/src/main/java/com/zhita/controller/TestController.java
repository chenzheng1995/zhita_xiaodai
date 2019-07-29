package com.zhita.controller;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.model.manage.OrderQueryParameter;
import com.zhita.service.manage.login.IntLoginService;
import com.zhita.service.test.TestService;
import com.zhita.util.DateListUtil;
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
	/*	Integer in=5;
		
		List<Integer> list=new ArrayList<>();
		list.add(2);
		list.add(4);
		list.add(6);
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i)>in){
				
			}else{
				
			}
			if(){
				
			}
		}*/
	}
}
