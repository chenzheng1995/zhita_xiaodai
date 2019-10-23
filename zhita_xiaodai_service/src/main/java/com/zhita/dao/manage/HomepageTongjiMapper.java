package com.zhita.dao.manage;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zhita.model.manage.Orderdetails;
import com.zhita.model.manage.Orders;

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
	
	//后台管理---今日放款新客
	int queryToDayLoannew(Integer companyId,String startTime,String endTime);
	
	//后台管理---今日放款复贷
	int queryToDayLoanold(Integer companyId,String startTime,String endTime);
	
	//后台管理---今日延期笔数--线上延期
	int queryToDayDeferred(Integer companyId,String startTime,String endTime);
	
	//后台管理---今日延期笔数--人工延期
	int queryToDayDeferredlay(Integer companyId,String startTime,String endTime);
	
	//后台管理---今日回款笔数(实还笔数)
	int queryToDayRepayment(Integer companyId,String startTime,String endTime);
	
	//后台管理---今日回款笔数(线上回款)
	int queryToDayRepaymentacc(Integer companyId,String startTime,String endTime);
	
	//后台管理---今日回款笔数(线下回款)
	int queryToDayRepaymentoff(Integer companyId,String startTime,String endTime);
	
	//后台管理---今日回款笔数(银行卡回款)
	int queryToDayRepaymentbank(Integer companyId,String startTime,String endTime);
	
	//后台管理---今日逾后已还笔数 ----还款表
	List<Orders> queryToDayOverdue(Integer companyId,String startTime,String endTime);
	
	//后台管理---今日逾后已还笔数 ----线下还款表
	List<Orders> queryToDayOverdueoff(Integer companyId,String startTime,String endTime);
	
	//后台管理---今日逾后已还笔数 ----银行卡扣款表
	List<Orders> queryToDayOverduebank(Integer companyId,String startTime,String endTime);
	
	//后台管理----今日放款总金额
	BigDecimal queryToDayLoanTotalmoney(Integer companyId,String startTime,String endTime);
	
	//后台管理---今日回款总金额（用户实还金额）
	BigDecimal queryToDayReturTotalmoney(Integer companyId,String startTime,String endTime);
	
	//后台管理---今日回款总金额（线上延期费）
	BigDecimal queryToDayDeffer(Integer companyId,String startTime,String endTime);
	
	//后台管理---今日回款总金额（人工延期费）
	BigDecimal queryToDayDefferlay(Integer companyId,String startTime,String endTime);
	
	//后台管理---今日回款总金额（减免后已还总金额）（线上）
	BigDecimal queryToDayDefferacc(Integer companyId,String startTime,String endTime);
	
	//后台管理---今日回款总金额（减免后已还总金额）（线下）
	BigDecimal queryToDayDefferoff(Integer companyId,String startTime,String endTime);
	
	//后台管理---今日回款总金额（银行扣款金额）
	BigDecimal queryToDayBank(Integer companyId,String startTime,String endTime);
	
	//后台管理---今日逾期已还金额（用户实还金额）
	BigDecimal queryToDayOverueTotalmoney(@Param("companyId") Integer companyId,@Param("startTime") String startTime,@Param("endTime") String endTime);
	
	//后台管理---今日逾期已还金额（线下减免已还）
	BigDecimal queryToDayOverueTotalmoneyoff(@Param("companyId") Integer companyId,@Param("startTime") String startTime,@Param("endTime") String endTime);
	
	//后台管理---今日逾期已还金额（银行卡扣款已还）
	BigDecimal queryToDayOverueTotalmoneybank(@Param("companyId") Integer companyId,@Param("startTime") String startTime,@Param("endTime") String endTime);
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
	
	//后台管理---回款总笔数(实还笔数)
	int querySumRepayment(Integer companyId);
	
	//后台管理---回款总笔数（线上减免已还清笔数）
	int querySumRepaymentacc(Integer companyId);
	
	//后台管理---回款总笔数（线下减免已还清笔数）
	int querySumRepaymentoff(Integer companyId);
	
	//后台管理---回款总笔数（银行卡扣款已结清笔数）
	int querySumRepaymentbank(Integer companyId);
	
	//后台管理---放款通过率---放款总笔数/注册用户总数*100%
	
	//后台管理---订单回款率---（回款总笔数）/放款总笔数*100%
	
	//后台管理---累计放款总金额
	BigDecimal querypayrecMoney(Integer companyId);
	
	//后台管理---累计回款总金额（实还金额）
	BigDecimal queryrepayMoney(Integer companyId);
	
	//后台管理---累计回款总金额（线上延期费）
	BigDecimal querydeffermoney(Integer companyId);
	
	//后台管理---累计回款总金额（人工延期费）
	BigDecimal querydeffermoneylay(Integer companyId);
	
	//后台管理---累计回款总金额（线上减免）
	BigDecimal querydeffermoneyacc(Integer companyId);
		
	//后台管理---累计回款总金额（线下减免）
	BigDecimal querydeffermoneyoff(Integer companyId);
		
	//后台管理---累计回款总金额（银行扣款）
	BigDecimal querydeffermoneybank(Integer companyId);
	
	//后台管理---累计减免总金额（线上减免的money）
	BigDecimal annulmoneyacc(Integer companyId);
	
	//后台管理---累计减免总金额（线下减免的money）
	BigDecimal annulmoneyoff(Integer companyId);
	
	//后台管理---累计原始应收总金额---订单明细表
	List<Orderdetails> queryshouldMoney(Integer companyId);
	
	//后台管理---累计应收总金额---延期表
	BigDecimal queryshouldMoneydef(Integer companyId);
	
	//后台管理---累计应收总金额---人工延期表
	BigDecimal queryshouldMoneylay(Integer companyId);
	
	//后台管理---实际收益=放款总金额-回款总金额
	
	
	//后台管理——截止今天为止应该还的订单
	int cutofftodayshouldrepay(Integer companyId,String todaycutofftime);
	
	/**
	 * 期限内数据
	 * @param companyId 公司id
	 * @return
	 */
	//后台管理----逾前未还笔数
	int overdue(Integer companyId);
	
	//后台管理----逾前实还金额(订单表所有还款订单)
	List<Orders> overduerealOrder(Integer companyId);
	
	//后台管理----逾前实还金额
	BigDecimal overduerealMoney(List<Integer> ids);
	
	//后台管理----逾前未还总金额
	BigDecimal overdueshouldMoney(Integer companyId);
	
	/**
	 * 逾期数据
	 * @param companyId 公司id
	 * @return
	 */
	//后台管理---逾后未还笔数
	int overdue1(Integer companyId);
	
	//后台管理---已坏账笔数
	int baddebt1(Integer companyId);
	
	//后台管理---应还订单
	int shouorder(Integer companyId);
	
	//后台管理---逾期率----(逾后未还+已坏账)/应还订单*100%
	
	//后台管理---逾期未还总金额
	List<Orderdetails> overshouldMoney(Integer companyId);
	
	/**
	 * 回收率报表
	 */
	//后台管理----查询订单表所有的应还时间
	List<String> queryAllShouldTime(Integer companyId);
	
	//后台管理----应还订单
	int shouldorder(Integer companyId,String shouldrepayStartTime,String shouldrepayEndTime);
	
	//后台管理----逾前未还
	int overduenotrepay(Integer companyId,String shouldrepayStartTime,String shouldrepayEndTime);
	
	//后台管理----已还款订单   还款表
	List<Orders> overduerepay(Integer companyId,String shouldrepayStartTime,String shouldrepayEndTime);
	
	//后台管理----已还款订单    线下还款表
	List<Orders> overduerepayoff(Integer companyId,String shouldrepayStartTime,String shouldrepayEndTime);
	
	//后台管理----已还款订单    银行卡扣款表
	List<Orders> overduerepaybank(Integer companyId,String shouldrepayStartTime,String shouldrepayEndTime);
	
	//后台管理----逾后未还
	int overdueafternotrepay(Integer companyId,String shouldrepayStartTime,String shouldrepayEndTime);
	
	//后台管理----已坏账
	int baddebt(Integer companyId,String shouldrepayStartTime,String shouldrepayEndTime);
	
	//后台管理----实际应还金额
	List<Orderdetails> shouldmoney(Integer companyId,String shouldrepayStartTime,String shouldrepayEndTime);
	
	//后台管理----线上实还金额
	BigDecimal realymoney(Integer companyId,String shouldrepayStartTime,String shouldrepayEndTime);
	
	//后台管理----线上延期费
	BigDecimal deferredmoney(Integer companyId,String shouldrepayStartTime,String shouldrepayEndTime);
	
	//后台管理----人工延期费
	BigDecimal deferredmoneylay(Integer companyId,String shouldrepayStartTime,String shouldrepayEndTime);
	
	//后台管理---逾期费
	List<Orderdetails> overduemoney(Integer companyId,String shouldrepayStartTime,String shouldrepayEndTime);
	
	//后台管理---减免金额（线上减免）
	BigDecimal deratemoneyon(Integer companyId,String shouldrepayStartTime,String shouldrepayEndTime);
	
	//后台管理---线下实际还的金额
	BigDecimal deratemoneyunder(Integer companyId,String shouldrepayStartTime,String shouldrepayEndTime);
	
	//后台管理---减免金额（线下减免金额）
	BigDecimal offmoney(Integer companyId,String shouldrepayStartTime,String shouldrepayEndTime);
	
	//后台管理----银行扣款金额
	BigDecimal bankMoney(Integer companyId,String shouldrepayStartTime,String shouldrepayEndTime);
	
	//后台管理---线上减免已还清订单
	int derateaccon(Integer companyId,String shouldrepayStartTime,String shouldrepayEndTime);
	
	//后台管理---线下减免已还清订单
	int derateaccunder(Integer companyId,String shouldrepayStartTime,String shouldrepayEndTime);
	
	//后台管理---银行扣款已还清订单
	int deratebank(Integer companyId,String shouldrepayStartTime,String shouldrepayEndTime);
	
	
	//后台管理---查询借款信息表的借款期限（比如借款期限是7）
	int querylifeOfLoan(Integer companyId);
	
	
	List<Orderdetails> test();
}
