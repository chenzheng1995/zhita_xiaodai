package com.zhita.dao.manage;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zhita.model.manage.DeferredAndOrder;
//import com.zhita.model.manage.DeferredAndOrder;
import com.zhita.model.manage.ManageControlSettings;
import com.zhita.model.manage.OrderQueryParameter;
import com.zhita.model.manage.Orders;
import com.zhita.model.manage.Source;
import com.zhita.model.manage.SysUser;

public interface OrdersMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Orders record);

    int insertSelective(Orders record);

    Orders selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Orders record);

    int updateByPrimaryKey(Orders record);
    
    //后台管理---查询订单表所有的风控名
    List<String> queryRiskcontrolname(Integer companyId);
    
    //后台管理---通过风控名查询风控信息
    ManageControlSettings querymancon(Integer companyId,String riskcontrolname);
    
    //后台管理----机审订单     (公司id，订单号，姓名，手机号，订单开始时间，订单结束时间，风控反馈，风控名字，风控分数(需要大于的参数)，风控分数（需要小于的参数）)
    List<Orders> queryatrOrders(OrderQueryParameter oqOrderQueryParameter);
  	
   //后台管理----机审拒绝未人审订单     (公司id，订单号，姓名，手机号，订单开始时间，订单结束时间   风控名字    风控分数开始   风控分数结束)
  	List<Orders> queryroaOrders(OrderQueryParameter oqOrderQueryParameter);
  	
  	//后台管理----机审拒绝未人审订单——审核通过操作
  	int upaOrderIfpeopleWhose(Integer orderid,Integer assessor);
  	
  	//后台管理----已机审已人审（公司id，订单号，姓名，手机号，订单开始时间，订单结束时间      审核员    风控名字       风控分数开始   风控分数结束）
  	List<Orders> queryroasOrders(OrderQueryParameter oqOrderQueryParameter);
  	
	//后台管理----订单 查询（公司id，订单号，姓名，手机号，注册开始时间，注册结束时间     渠道id   风控名字）
  	List<Orders> queryAllOrders(OrderQueryParameter orderQueryParameter);


	int borrowNumber(@Param("userId")int userId,@Param("companyId") int companyId);

	int getOrdersId(@Param("userId")int userId,@Param("companyId") int companyId);

  	
	//后台管理----订单 查询（公司id，订单号，姓名，手机号，订单开始时间，订单结束时间     渠道id  用户id）
  	List<Orders> queryAllOrdersByUserid(OrderQueryParameter orderQueryParameter);
  	
  	//后台管理----订单 查询（公司id，订单号，姓名，手机号，订单开始时间，订单结束时间     渠道id  用户id）——查询数量
   int queryAllOrdersByUseridcount(OrderQueryParameter orderQueryParameter);
  	
  	//后台管理----订单 查询（公司id，姓名，手机号，身份证号，注册开始时间，注册结束时间     渠道id）
  	List<Orders> queryAllOrdersByUserid1(OrderQueryParameter orderQueryParameter);
  	
	//后台管理----订单 查询（公司id，姓名，手机号，身份证号，注册开始时间，注册结束时间     渠道id）——查询数量
  	int queryAllOrdersByUserid1count(OrderQueryParameter orderQueryParameter);
  	
  	//后台管理---渠道统计模块——申请人数字段
  	int queryNum(Integer companyId,String sourcename);
  	
  	//后台管理---渠道统计模块——机审通过字段
  	int queryNum1(Integer companyId,String sourcename,String startscore,String endscore);
  	
  	//后台管理---查询产生该订单的用户是第几次借款
  	int queryHow(Integer userid);
  	
  	//后台管理----查询产生该订单的用户最后借款金额和最后借款时间
  	Orders qeuryafter(Integer userid);
  	
 	//后台管理---通过订单查询改订单在延期表信息
	List<DeferredAndOrder> queryDefer(Integer orderid);
	
	//后台管理---查询最后延期时间
	Orders qeuryFinalDefertime(Integer orderid);
 
  	//后台管理--查询出sysuser表所有的信息
  	List<SysUser> queryname(Integer companyId);
  	
  	//后台管理---查询出source表所有的信息
  	List<Source> querysource(Integer companyId);

	int setOrder(@Param("companyId")int companyId,@Param("userId") int userId,@Param("orderNumber") String orderNumber,@Param("orderCreateTime") String orderCreateTime,@Param("lifeOfLoan") int lifeOfLoan,
			@Param("howManyTimesBorMoney")int howManyTimesBorMoney,@Param("shouldReturned") String shouldReturned,@Param("riskmanagementFraction") int riskmanagementFraction,@Param("borrowMoneyWay") String borrowMoneyWay);

	int getOrderId(String orderNumber);

	Map<String, Object> getReimbursement(@Param("userId")int userId,@Param("companyId") int companyId);

	Map<String, Object> getRepayment(@Param("userId")int userId,@Param("companyId") int companyId);
	
	/**
	 * 订单查询（公司id，page,pagesize,订单号，姓名，手机号，订单开始时间，订单结束时间，渠道id）
	 */
	List<Orders> queryAllordersByLike(OrderQueryParameter orderQueryParameter);
	
	/**
	 * 订单查询（公司id，page,pagesize,订单号，姓名，手机号，订单开始时间，订单结束时间，渠道id）——查询数量
	 */
	Integer queryAllordersByLikeCount(OrderQueryParameter orderQueryParameter);
	
	int getmanageControlId(String sourceName);

	int getBorrowTimeLimit(@Param("userId")int userId,@Param("companyId") int companyId);

	int getorderStatus(@Param("userId")int userId,@Param("companyId") int companyId);

	String getorderStatus1(@Param("userId")int userId,@Param("companyId") int companyId);

	String getshouldReturnTime(@Param("userId")int userId,@Param("companyId") int companyId);


}