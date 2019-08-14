package com.zhita.controller.about_us;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.service.manage.aboutus.IntAboutusService;

@Controller
@RequestMapping("/aboutus")
public class AboutUsController {

	    
	    @Autowired
	    IntAboutusService intAboutusService;
		
	    
	    //获取协议
	    @RequestMapping("/getaboutus")
	    @ResponseBody
	    @Transactional
	    public Map<String, Object> getaboutus(int companyId){
	    	Map<String, Object> map =intAboutusService.getaboutus(companyId);
			return map;

	    }
}
