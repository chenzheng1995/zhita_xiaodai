package com.zhita.controller.homepage_viewpager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.model.manage.HomepageViewpager;
import com.zhita.service.manage.homepageviewpager.IntHomepageViewpageService;
import com.zhita.service.manage.userattestation.UserAttestationService;

@Controller
@RequestMapping("/homepageViewpager")
public class homepageViewpagerController {

    @Autowired
    IntHomepageViewpageService intHomepageViewpageService;

		@RequestMapping("/gethomepageViewpager")
		@ResponseBody
		public Map<String, Object> getqrcode(int companyId) {
			HashMap<String,Object> map=new HashMap<>();
			map.put("Ncode","2000");
		    	List<HomepageViewpager> list=intHomepageViewpageService.gethomepageViewpager(companyId); //获取轮播图的所有数据   	
		    	map.put("listshuff",list);

	    	return map;
		}
	}

