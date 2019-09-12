package com.zhita.service.manage.maillist;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.zhita.dao.manage.MaillistMapper;
import com.zhita.model.manage.Jsonlist;
import com.zhita.model.manage.Maillist;
import com.zhita.util.PhoneDeal;

@Service
public class MaillistServiceImp implements IntMaillistService{
	
	@Autowired
	private MaillistMapper maillistMapper;
	
	
	
	@Override
	public Map<String, Object> AddMaillist(String jsonlist, String phone) {
		PhoneDeal phoneDeal=new PhoneDeal();
		phone=phoneDeal.encryption(phone);
		List<Jsonlist> jsonlists = JSONObject.parseArray(jsonlist, Jsonlist.class);
		Integer userId = maillistMapper.queryuserid(phone);
		Map<String, Object> map = new HashMap<String, Object>();
		int count = maillistMapper.querycount(userId);
		if(count == 0){
			for(int i=0;i<jsonlists.size();i++){
				if(!StringUtils.isEmpty(jsonlists.get(i).getName())){
			       String name = jsonlists.get(i).getName().replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "");
			       jsonlists.get(i).setName(name);
				}else{
			        jsonlists.get(i).getName();
			    }
				System.out.println("数据:"+jsonlists.get(i).getName()+":CC:"+jsonlists.get(i).getPhone());
				Maillist ma = new Maillist();
				ma.setPhone(jsonlists.get(i).getPhone());
				ma.setName(jsonlists.get(i).getName());
				ma.setUserid(userId);
				maillistMapper.insert(ma);
			}
		
			map.put("code", 200);
			map.put("msg", "导入成功");
		}else{
			map.put("code", 0);
			map.put("msg", "已导入");
		}
		return map;
	}
	
}
