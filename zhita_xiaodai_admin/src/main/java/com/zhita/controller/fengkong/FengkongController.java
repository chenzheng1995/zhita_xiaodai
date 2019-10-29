package com.zhita.controller.fengkong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.service.manage.fengkong.FengkongService;


//风控规则
@Controller
@RequestMapping("fk")
public class FengkongController {

	
	@Autowired
	private FengkongService fservi;
	
	
	@RequestMapping("passuser")
	@ResponseBody
	public Integer Passuser(Integer userId){
		return fservi.Passuser(userId);
	}
	
	
}
