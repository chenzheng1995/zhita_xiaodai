package com.zhita.dao.manage;

import com.zhita.model.manage.Bankcard;

public interface StatisticsDao {
	
	Integer AddBankcard(Bankcard bank);
	
	
	Integer SelectTrxId(Bankcard bank);
	
	
	Integer UpdateStatu(Integer id);

}
