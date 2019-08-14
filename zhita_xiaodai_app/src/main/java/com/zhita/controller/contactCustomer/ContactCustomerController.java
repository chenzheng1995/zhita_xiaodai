package com.zhita.controller.contactCustomer;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.service.manage.contactcustomer.IntContactcustomerService;

@Controller
@RequestMapping("/ContactCustomer")
public class ContactCustomerController {
	
	@Autowired
	IntContactcustomerService intContactcustomerService;

	    //联系客服
	    @RequestMapping("/getContactCustomer")
	    @ResponseBody
	    @Transactional
	    public Map<String, Object> getContactCustomer(int companyId){
	    	Map<String, Object> map = new HashMap<>();
	    	Map<String, Object> map1 = intContactcustomerService.getContactCustomer(companyId);
	    	String advertisingmap = (String) map1.get("advertisingmap");//宣传图
	    	String contactInformation = (String) map1.get("contactInformation");//联系方式
	    	String qrcode = (String) map1.get("qrcode");//二维码
	    	String remarks = (String) map1.get("remarks");//备注
	    	map.put("advertisingmap", advertisingmap);
	    	map.put("contactInformation", contactInformation);
	    	map.put("qrcode", qrcode);
	    	map.put("remarks", remarks);
			return map;

	    }

}