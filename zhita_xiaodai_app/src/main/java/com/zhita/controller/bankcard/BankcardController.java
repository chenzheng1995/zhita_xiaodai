package com.zhita.controller.bankcard;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.model.manage.Bankcard;
import com.zhita.model.manage.Orders;
import com.zhita.service.manage.bankcard.BankcardService;




//绑卡
@Controller
@RequestMapping("bank")
public class BankcardController {
	
	
	@Autowired
	private BankcardService bser;
	
	
	
	/**
	 * 银行卡绑定添加
	 * @param bank
	 * @return
	 */
	@ResponseBody
	@RequestMapping("AddBankcard")
	public Map<String, Object> AddBankcard(Bankcard bank){
		return bser.AddBankcard(bank);
	}
	
	
	
}
