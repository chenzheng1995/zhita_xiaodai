package com.zhita.service.manage.order;

import java.util.Map;

import com.zhita.model.manage.OrderQueryParameter;

public interface IntOrderService {
	
	//后台管理----机审订单      (公司id，page，订单号，姓名，手机号，订单开始时间，订单结束时间，风控反馈)
	public Map<String, Object> queryatrOrders(OrderQueryParameter orderQueryParameter);
	
	//后台管理----机审拒绝未人审订单     (公司id，page，订单号，姓名，手机号，订单开始时间，订单结束时间)
	public Map<String, Object> queryroaOrders(OrderQueryParameter orderQueryParameter);
	
	//后台管理----机审拒绝未人审订单——审核通过操作
  	public int upaOrderIfpeopleWhose(Integer orderid,String assessor);
  	
   //后台管理----已机审已人审（公司id，page,订单号，姓名，手机号，订单开始时间，订单结束时间      审核员）
  	public Map<String, Object> queryroasOrders(OrderQueryParameter orderQueryParameter);
  	
  	//后台管理----订单 查询（公司id，page,订单号，姓名，手机号，注册开始时间，注册结束时间     渠道id）
  	public Map<String, Object> queryAllOrders(OrderQueryParameter orderQueryParameter);

	public int borrowNumber(int userId, int companyId);

	public int getOrdersId(int userId, int companyId);
}
