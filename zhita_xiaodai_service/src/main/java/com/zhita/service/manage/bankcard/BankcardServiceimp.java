package com.zhita.service.manage.bankcard;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhita.dao.manage.BankcardMapper;
import com.zhita.dao.manage.StatisticsDao;
import com.zhita.model.manage.Bankcard;
import com.zhita.model.manage.BankcardType;
import com.zhita.model.manage.Orders;
import com.zhita.util.Timestamps;


@Service
public class BankcardServiceimp implements BankcardService{
	
	
	@Autowired
	private StatisticsDao sdao;
	
	@Autowired
	private BankcardMapper bankcardMapper;
	
	
	

	@Override
	public Map<String, Object> AddBankcard(Bankcard bank) {
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			bank.setAuthentime(Timestamps.dateToStamp1(sim.format(new Date())));
		} catch (Exception e) {
			// TODO: handle exception
		}
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


	@Override
	public Map<String, Object> SelectBankCard(Integer companyId) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<BankcardType> banks = bankcardMapper.getBankcardtype(companyId);
		map.put("BankcardType", banks);
		map.put("code", 200);
		map.put("Ncode", 2000);
		return map;
	}


	@Override
	public Integer GetBanktype(String bankcardTypeName) {
		// TODO Auto-generated method stub
		return bankcardMapper.SelectBankName(bankcardTypeName);
	}







	
	
	
}
