package com.zhita.controller.face;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/face")
public class FaceController {
    @RequestMapping("/getface")
    @ResponseBody
    @Transactional
	public Map<String, Object> getface(int userId){
		 Map<String, Object> map = new HashMap<String, Object>();
	        if (StringUtils.isEmpty(userId)) {
	            map.put("msg", "userId不能为空");
	            return map;
	        } else {
	        	
	        }
			return map;

	}
}
