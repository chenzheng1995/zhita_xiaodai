package com.zhita.service.manage.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.zhita.model.manage.DeferredAndOrder;
import com.zhita.model.manage.Offlinedelay;
import com.zhita.model.manage.OrderQueryParameter;
import com.zhita.model.manage.Orders;
import com.zhita.model.manage.User;
import com.zhita.model.manage.UserLikeParameter;

public interface IntOrderService {
	
	//后台管理----机审订单      (公司id，page，订单号，姓名，手机号，订单开始时间，订单结束时间，风控反馈)
	public Map<String, Object> queryatrOrders(OrderQueryParameter orderQueryParameter);
	
	//后台管理----机审拒绝未人审订单     (公司id，page，订单号，姓名，手机号，订单开始时间，订单结束时间)
	public Map<String, Object> queryroaOrders(OrderQueryParameter orderQueryParameter);
	
	//后台管理----机审拒绝未人审订单——审核通过操作
  	public int upaOrderIfpeopleWhose(Integer orderid,Integer assessor);
  	
   //后台管理----已机审已人审（公司id，page,订单号，姓名，手机号，订单开始时间，订单结束时间      审核员）
  	public Map<String, Object> queryroasOrders(OrderQueryParameter orderQueryParameter);
  	
  	//后台管理----订单 查询（公司id，page,订单号，姓名，手机号，注册开始时间，注册结束时间     渠道id）
  	public Map<String, Object> queryAllOrders(OrderQueryParameter orderQueryParameter);

	public int borrowNumber(int userId, int companyId);

	public int getOrdersId(int userId, int companyId);

	public int setOrder(int companyId, int userId, String orderNumber, String orderCreateTime, int lifeOfLoan,
			int howManyTimesBorMoney, String shouldReturned, int riskmanagementFraction, String borrowMoneyWay,String borrowRepayBankcard,String bank);

	public int getOrderId(String orderNumber);

	public Map<String, Object> getReimbursement(int userId, int companyId);

	public Map<String, Object> getRepayment(int userId, int companyId);


	public int getBorrowTimeLimit(int userId, int companyId);

	public int getorderStatus(int userId, int companyId);

	public String getorderStatus1(int userId, int companyId);


	
	/**
	 * 订单查询【机审通过和人审通过的用户    在放款后在订单表产生的订单数据】（公司id，page,订单号，姓名，手机号，注册开始时间，注册结束时间，渠道id）
	 */
	public Map<String,Object> queryAllordersByLike(OrderQueryParameter orderQueryParameter);
	/**
	 * 订单查询（公司id，page,pagesize,订单号，姓名，手机号，订单开始时间，订单结束时间，渠道id）——查询数量
	 */
	public Integer queryAllordersByLikeCount(OrderQueryParameter orderQueryParameter);
	/**
	 * 订单查询（导出Excel）
	 */
	public List<Orders> queryAllordersByLikeExcel(OrderQueryParameter orderQueryParameter);
	
	/**
	 * 报废订单查询（公司id，page,pagesize,订单号，姓名，手机号，订单开始时间，订单结束时间，渠道id）
	 */
	public List<Orders> queryAllordersByLikeacrap(OrderQueryParameter orderQueryParameter);
	
	/**
	 * 报废订单查询（公司id，page,pagesize,订单号，姓名，手机号，订单开始时间，订单结束时间，渠道id）——查询数量
	 */
	public Integer queryAllordersByLikeCountacrap(OrderQueryParameter orderQueryParameter);
	
	/**
	 * 报废订单查询（导出Excel）
	 */
	public List<Orders> queryAllordersByLikeacrapExcel(OrderQueryParameter orderQueryParameter);
	
	
	/**
	 * 机审状态用户【包含机审拒绝和机审通过用户】（公司id，page,申请编号，姓名，手机号，申请时间开始，申请时间结束）
	 */
	public Map<String, Object> queryAllUser(UserLikeParameter userLikeParameter);
	
	/**
	 * 机审状态用户【包含机审拒绝和机审通过用户】（公司id，page，pagesize,申请编号，姓名，手机号，申请时间开始，申请时间结束）——查询数量
	 */
	public int queryAllUsercount(UserLikeParameter userLikeParameter);
	
	/**
	 * 机审状态用户【包含机审拒绝和机审通过用户】（导出Excel）
	 */
	public List<User> queryAllUserExcel(UserLikeParameter userLikeParameter);
	
	
	/**
	 * 人审状态用户【包含机审拒绝和机审通过用户】（公司id，page,申请编号，姓名，手机号，申请时间开始，申请时间结束）
	 */
	public Map<String, Object> queryAllUserPeople(UserLikeParameter userLikeParameter);
	
	/**
	 * 人审状态用户（公司id，page，pagesize,申请编号，姓名，手机号，申请时间开始，申请时间结束）——查询数量
	 */
	public int queryAllUserPeoplecount(UserLikeParameter userLikeParameter);
	
	/**
	 * 人审状态用户（导出Excel）
	 */
	public List<User> queryAllUserPeopleExcel(UserLikeParameter userLikeParameter);
	
	/**
	 * 人审通过按钮
	 */
	public int updateShareOfState(Integer sysuserid,Integer userid);
	
	/**
	 * 人审不通过按钮
	 */
	public int updateShareOfStateNo(Integer sysuserid,Integer userid);
	
	/**
	 * 人审过后状态用户【包括人审不通过和人审通过】（公司id，page,申请编号，姓名，手机号，申请时间开始，申请时间结束，审核员id）
	 */
	public Map<String, Object> queryAllUserPeopleYet(UserLikeParameter userLikeParameter);
	
	/**
	 * 人审过后状态用户【包括人审不通过和人审通过】（公司id，page,pagesize,申请编号，姓名，手机号，申请时间开始，申请时间结束，审核员id）——查询数量
	 */
	public int queryAllUserPeopleYetcount(UserLikeParameter userLikeParameter);
	/**
	 * 人审过后状态用户（导出Excel）
	 */
	public List<User> queryAllUserPeopleYetExcel(UserLikeParameter userLikeParameter);
	
	//后台管理---查询该订单还款成功的还款金额---还款表
	public BigDecimal queryrepaymoney(Integer orderid);
	
	//后台管理---查询该订单还款成功的还款金额---线下减免表
	public BigDecimal queryrepaymoneyoff(Integer orderid);
		
	//后台管理---查询该订单还款成功的还款金额---银行扣款表
	public BigDecimal queryrepaymoneybank(Integer orderid);
	
 	//后台管理---通过订单查询改订单在延期表信息
	public List<DeferredAndOrder> queryDefer(Integer orderid);
	
	//后台管理---通过订单查询改订单在人工延期表信息
	public List<Offlinedelay> queryDeferlay(Integer orderid);
	
	//后台管理---查询最后延期时间---线上延期
	public Orders qeuryFinalDefertime(Integer orderid);
	
	//后台管理---查询最后延期时间---人工延期
	public Orders qeuryFinalDefertimelay(Integer orderid);


	public String getshouldReturnTime(int userId, int companyId);

	public String getorderNumber(int userId);

	public String getrealtime(int userId);


	
	//还款成功修改订单状态  添加还款记录
	Map<String, Object> UpdateOrdersUpdate();

	public Map<String, Object> getOrder(int userId);

	public int getId(int userId);

	public Map<String, Object> lifeOfLoan(int userId);
	

}
