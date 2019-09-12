package com.zhita.controller.maillist;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.service.manage.maillist.IntMaillistService;

@Controller
@RequestMapping("/maillist")
public class MaillistController {

	@Autowired
	private IntMaillistService intMaillistService;
	
	@ResponseBody
	@RequestMapping("/AddMaillist")
	public Map<String, Object> AddMaillist(String jsonlist, String phone) {
		return intMaillistService.AddMaillist(jsonlist, phone);
	}
}
