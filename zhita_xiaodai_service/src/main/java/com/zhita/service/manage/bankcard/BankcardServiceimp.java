package com.zhita.service.manage.bankcard;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhita.dao.manage.StatisticsDao;
import com.zhita.model.manage.Bankcard;
import com.zhita.model.manage.Orders;


@Service
public class BankcardServiceimp implements BankcardService{
	
	
	@Autowired
	private StatisticsDao sdao;
	

	@Override
	public Map<String, Object> AddBankcard(Bankcard bank) {
		Integer addId = sdao.AddBankcard(bank);
		Map<String, Object> map = new HashMap<String, Object>();
		if(addId != null){
			map.put("code", 200);
			map.put("desc", "添加成功");
		}else{
			map.put("code", 0);
			map.put("desc", "添加失败");
		}
		return map;
	}


	@Override
	public Map<String, Object> AddOrders(Orders orde) {
		// TODO Auto-generated method stub
		return null;
	}


	
	
	
}
