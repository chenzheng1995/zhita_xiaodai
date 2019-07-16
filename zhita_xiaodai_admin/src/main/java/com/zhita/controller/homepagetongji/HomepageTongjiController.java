package com.zhita.controller.homepagetongji;

import java.text.ParseException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.service.manage.homepagetongji.IntHomepagetongjiService;
/**
 * 首页统计controller
 * @author lhq
 * @{date} 2019年7月13日
 */
@Controller
@RequestMapping("/homepagetongji")
public class HomepageTongjiController {
	@Autowired
	private IntHomepagetongjiService intHomepagetongjiService;
	
	//首页统计
	@ResponseBody
	@RequestMapping("/queryAll")
	public Map<String, Object> queryAll()throws ParseException{
		Map<String, Object> map=intHomepagetongjiService.queryAll();
		return map;
	}
}
