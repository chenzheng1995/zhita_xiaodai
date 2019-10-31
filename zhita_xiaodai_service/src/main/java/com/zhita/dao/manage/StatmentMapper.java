package com.zhita.dao.manage;

import java.math.BigDecimal;
import java.util.List;

public interface StatmentMapper {
	
	//后台管理---当前日期借款次数为1的订单id
	List<Integer> queryLoannew(Integer companyId,String startTime,String endTime);
	
	//后台管理---该订单在延期表的延期次数
	int querydefercount(Integer orderid);
	
	//后台管理---该订单的放款金额
	BigDecimal queryrepaymoney(Integer orderid);
	
	//后台管理---当前日期老客放款金额
	BigDecimal queryoldmoney(Integer companyId,String startTime,String endTime);
	
	//后台管理---当前日期放款的订单已还人数
	int queryloanyetrepay(Integer companyId,String startTime,String endTime);
	
	//后台管理---当天通过人数
	int querypass(Integer companyId,String startTime,String endTime);

}
