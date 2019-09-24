package com.zhita.dao.manage;

import java.math.BigDecimal;
import java.util.List;

import com.zhita.model.manage.Orders;
import com.zhita.model.manage.OverdueClass;
import com.zhita.model.manage.OverdueSettings;

public interface ProjecttimerMapper{
	/**
	 * 控制订单逾期
	 */
	
	//后台管理----查询订单表     所有期限内，已逾期，已延期的订单
	List<Orders> queryAllover(Integer companyId);
	
	//后台管理----修改订单状态为逾期
	int updateover(Integer orderid);
	
	//后台管理---查询逾期设置表所有信息
	List<OverdueSettings> queryAllOversett(Integer companyId);
	
	//后台管理----修改订单明细表信息（在明细表更新逾期天数，日均罚息，逾期总罚息，更新总利息）
	int upaorderdetail(Integer overdueNumberOfDays,BigDecimal interestPenaltyDay,BigDecimal interestPenaltySum,BigDecimal interestInAll,Integer orderid);
	
	/**
	 * 控制逾期等级
	 */
	//后台管理----查询出所有已逾期的订单
	List<Orders> queryAlloverdue(Integer companyId);
	
	//后台管理----查询所有逾期等级表的信息
	List<OverdueClass> queryAlloverclass(Integer companyId);
	
	//后台管理---更新订单表逾期等级字段
	int upaoverclass(String overdueGrade,Integer orderid);
	
	
	/**
	 * 控制逾期超过30天   该用户进入黑名单
	 * @param userid
	 * @return
	 */
	//后台管理---添加黑名单(修改当前用户的黑名单状态)
	int upaBlacklistStatus(Integer userid);
	
	//后台管理---查询黑名单分界线的值
	int queryblackline();
	
	//后台管理---添加黑名单（将当前用户存进黑名单里）
	int addBlacklist(Integer companyId,Integer userId,String blackType);
	
}
