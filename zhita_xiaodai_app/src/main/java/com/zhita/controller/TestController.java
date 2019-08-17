package com.zhita.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.zhita.controller.face.postDemo;
import com.zhita.controller.operator.demo.ScoreDemo;
import com.zhita.service.test.TestService;




@Controller
@RequestMapping("/test")
public class TestController {
	@Autowired
	TestService testService;

	
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
		System.out.println("test");
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
	public static void main(String[] args) {
    	ScoreDemo scoreDemo = new ScoreDemo();
    	String result = scoreDemo.getScore("15659544720058296796","15835996762","刘晓云","142727199807191015","158359967621565954394246");
    	  JSONObject jsonObject =null;
    	  jsonObject = JSONObject.parseObject(result);
          String tianji_api_tianjiscore_pdscorev5_response =jsonObject.get("tianji_api_tianjiscore_pdscorev5_response").toString();
          jsonObject = JSONObject.parseObject(tianji_api_tianjiscore_pdscorev5_response);
          int score = Integer.parseInt(jsonObject.get("score").toString());
          System.out.println(score);
	}

	
}
