package com.zhita.controller.feedback;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.service.manage.fendback.IntFeedbackService;

//意见反馈
@Controller
@RequestMapping("/feedback")
public class FeedbackController {
	
	@Autowired
	private IntFeedbackService intFeedbackService;
	
	//后台管理----查询功能
	@ResponseBody
	@RequestMapping("/queryAll")
	public Map<String,Object> queryAll(Integer page){
		return intFeedbackService.queryAll(page);
	}
	
	//后台管理----修改解决状态，添加回复内容
	@ResponseBody
	@RequestMapping("/upastatus")
	public int upastatus(String replycontent,Integer id){
		return intFeedbackService.upastatus(replycontent, id);
	}
	
}
