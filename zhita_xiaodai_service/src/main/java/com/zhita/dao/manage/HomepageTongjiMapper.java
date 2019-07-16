package com.zhita.dao.manage;

import java.math.BigDecimal;

public interface HomepageTongjiMapper {
	/**
	 * 今日数据
	 * @param companyId 公司id
	 * @param startTime 今日开始时间
	 * @param endTime 今日结束时间
	 * @return
	 */
	//后台管理---今日注册人数
	int queryToDayRegiste(Integer companyId,String startTime,String endTime);
	
	//后台管理---今日申请人数
	int queryToDayApply(Integer companyId,String startTime,String endTime);
	
	//后台管理---今日放款人数
	int queryToDayLoan(Integer companyId,String startTime,String endTime);
	
	//后台管理---今日延期笔数
	int queryToDayDeferred(Integer companyId,String startTime,String endTime);
	
	//后台管理---今日还款笔数
	int queryToDayRepayment(Integer companyId,String startTime,String endTime);
	
	//后台管理---今日逾期已还笔数
	int queryToDayOverdue(Integer companyId,String startTime,String endTime);
	
	//后台管理----今日放款总金额
	BigDecimal queryToDayLoanTotalmoney(Integer companyId,String startTime,String endTime);
	
	//后台管理---今日回款总金额
	BigDecimal queryToDayReturTotalmoney(Integer companyId,String startTime,String endTime);
	
	//后台管理---今日逾期已还金额
	BigDecimal queryToDayOverueTotalmoney(Integer companyId,String startTime,String endTime);
	
	/**
	 * 累计数据
	 * @param companyId 公司id
	 * @return
	 */
	//后台管理---已注册用户总数
	int querySumRegiste(Integer companyId);
	
	//后台管理---用户申请总笔数
	int querySumApply(Integer companyId);
	
	//后台管理---放款总笔数
	int querySumLoan(Integer companyId);
	
	//后台管理---还款总笔数
	int querySumRepayment(Integer companyId);
	
	//后台管理---放款通过率---放款总笔数/注册用户总数*100%
	
	//后台管理---订单回款率---还款总笔数/放款总笔数*100%
	
	//后台管理---累计放款总金额
	
	/**
	 * 期限内数据
	 * @param companyId 公司id
	 * @return
	 */
	//后台管理----逾前未还笔数
	int overdue(Integer companyId);
	
	//后台管理----逾前实还金额
	int overdueMoney(Integer companyId);
	
	/**
	 * 逾期数据
	 * @param companyId 公司id
	 * @return
	 */
	//后台管理---已逾未还笔数
	int overdue1(Integer companyId);
	
	//后台管理---逾期率
	//int overdueCvr()
	
}
