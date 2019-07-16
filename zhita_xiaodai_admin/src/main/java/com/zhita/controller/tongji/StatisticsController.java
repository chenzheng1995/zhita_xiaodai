package com.zhita.controller.tongji;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.zhita.service.manage.Statistic.Statisticsservice;




//合计
@Controller
@RequestMapping("Statistic")
public class StatisticsController {
	
	@Autowired
	private Statisticsservice stvier;
	
	
	
	
}
