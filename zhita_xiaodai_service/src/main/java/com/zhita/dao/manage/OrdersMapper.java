package com.zhita.dao.manage;

import java.util.List;

import com.zhita.model.manage.ManageControlSettings;
import com.zhita.model.manage.OrderQueryParameter;
import com.zhita.model.manage.Orders;

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
    
    //后台管理----机审订单     (公司id，订单号，订单开始时间，订单结束时间，风控反馈，风控名字，风控分数(需要大于的参数)，风控分数（需要小于的参数）)
    List<Orders> queryatrOrders(OrderQueryParameter oqOrderQueryParameter);
  	
   //后台管理----机审拒绝未人审订单     (公司id，订单号，订单开始时间，订单结束时间   风控名字    风控分数开始   风控分数结束)
  	List<Orders> queryroaOrders(OrderQueryParameter oqOrderQueryParameter);
  	
  	//后台管理----机审拒绝未人审订单——审核通过操作
  	int upaOrderIfpeopleWhose(Integer orderid,String assessor);
  	
  	//后台管理----已机审已人审（公司id，订单号，订单开始时间，订单结束时间      审核员    风控名字       风控分数开始   风控分数结束）
  	List<Orders> queryroasOrders(OrderQueryParameter oqOrderQueryParameter);
  	
	//后台管理----订单 查询（公司id，订单号，订单开始时间，订单结束时间     渠道id   风控名字）
  	List<Orders> queryAllOrders(OrderQueryParameter orderQueryParameter);
  	
	//后台管理----订单 查询（公司id，订单号，订单开始时间，订单结束时间     渠道id  用户id）
  	List<Orders> queryAllOrdersByUserid(OrderQueryParameter orderQueryParameter);
  	
  	//后台管理----订单 查询（公司id）
  	List<Orders> queryAllOrdersByUserid1(Integer companyId);
  	
  	//后台管理---渠道统计模块——申请人数字段
  	int queryNum(Integer companyId,String sourcename);
  	
  	//后台管理---渠道统计模块——机审通过字段
  	int queryNum1(Integer companyId,String sourcename,String startscore,String endscore);
}