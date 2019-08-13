package com.zhita.controller.order;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.model.manage.OrderQueryParameter;
import com.zhita.model.manage.UserLikeParameter;
import com.zhita.service.manage.order.IntOrderService;
import com.zhita.service.manage.user.IntUserService;

@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private IntOrderService intOrderService;
	@Autowired
	private IntUserService intUserService;
	
/*	//后台管理----机审订单     (公司id，page，订单号，姓名，手机号，订单开始时间，订单结束时间，风控反馈)
	@ResponseBody
	@RequestMapping("/queryatrOrders")
	public Map<String, Object> queryatrOrders(OrderQueryParameter orderQueryParameter){
		Map<String, Object> map=intOrderService.queryatrOrders(orderQueryParameter);
		return map;
	}
	
	//后台管理----机审拒绝未人审订单     (公司id，page，订单号，姓名，手机号，订单开始时间，订单结束时间)
	@ResponseBody
	@RequestMapping("/queryroaOrders")
	public Map<String, Object> queryroaOrders(OrderQueryParameter orderQueryParameter){
		Map<String, Object> map=intOrderService.queryroaOrders(orderQueryParameter);
		return map;
	}
	
	//后台管理----机审拒绝未人审订单——审核通过操作(assessor是当前登录用户的id)
	@ResponseBody
	@RequestMapping("/upaOrderIfpeopleWhose")
  	public int upaOrderIfpeopleWhose(Integer orderid,Integer assessor){
  		int num=intOrderService.upaOrderIfpeopleWhose(orderid, assessor);
  		return num;
  	}
	
	//后台管理----已机审已人审（公司id，page,订单号，姓名，手机号，订单开始时间，订单结束时间      审核员id）
	@ResponseBody
	@RequestMapping("/queryroasOrders")
  	public Map<String, Object> queryroasOrders(OrderQueryParameter orderQueryParameter){
  		Map<String, Object> map=intOrderService.queryroasOrders(orderQueryParameter);
  		return map;
  	}
	
	//后台管理----订单 查询（公司id，page,订单号，姓名，手机号，注册开始时间，注册结束时间     渠道id）
	@ResponseBody
	@RequestMapping("/queryAllOrders")
  	public Map<String, Object> queryAllOrders(OrderQueryParameter orderQueryParameter){
		Map<String, Object> map=intOrderService.queryAllOrders(orderQueryParameter);
		return map;
	}*/
	
	
	/**
	 * 订单查询【机审通过和人审通过的用户    在放款后在订单表产生的订单数据】（公司id，page,订单号，姓名，手机号，注册开始时间，注册结束时间，渠道id）
	 */
	@ResponseBody
	@RequestMapping("/queryAllordersByLike")
	public Map<String,Object> queryAllordersByLike(OrderQueryParameter orderQueryParameter){
		Map<String, Object> map=intOrderService.queryAllordersByLike(orderQueryParameter);
		return map;
	}
	
	/**
	 * 机审状态用户【包含机审拒绝和机审通过用户】（公司id，page,申请编号，姓名，手机号，申请时间开始，申请时间结束）
	 */
	@ResponseBody
	@RequestMapping("/queryAllUser")
	public Map<String, Object> queryAllUser(UserLikeParameter userLikeParameter){
		Map<String, Object> map=intOrderService.queryAllUser(userLikeParameter);
		return map;
	}
	
	/**
	 * 人审状态用户（公司id，page,申请编号，姓名，手机号，申请时间开始，申请时间结束）
	 */
	@ResponseBody
	@RequestMapping("/queryAllUserPeople")
	public Map<String, Object> queryAllUserPeople(UserLikeParameter userLikeParameter){
		Map<String, Object> map=intOrderService.queryAllUserPeople(userLikeParameter);
		return map;
	}
	
	/**
	 * 人审通过按钮
	 */
	@ResponseBody
	@RequestMapping("/updateShareOfState")
	public int updateShareOfState(Integer sysuserid,Integer userid){
		int num=intOrderService.updateShareOfState(sysuserid,userid);
		return num;
	}
	
	
	/**
	 * 人审不通过按钮
	 */
	@ResponseBody
	@RequestMapping("/updateShareOfStateNo")
	public int updateShareOfStateNo(Integer sysuserid,Integer userid){
		int num=intOrderService.updateShareOfStateNo(sysuserid,userid);
		return num;
	}
	
	/**
	 * 人审过后状态用户【包括人审不通过和人审通过】（公司id，page,申请编号，姓名，手机号，申请时间开始，申请时间结束，审核员id）
	 */
	@ResponseBody
	@RequestMapping("queryAllUserPeopleYet")
	public Map<String, Object> queryAllUserPeopleYet(UserLikeParameter userLikeParameter){
		Map<String, Object> map=intOrderService.queryAllUserPeopleYet(userLikeParameter);
		return map;
	}
	
	/**
	 * 用户认证信息
	 * @param userid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryUserAttesta")
	public Map<String,Object> queryUserAttesta(Integer userid){
		Map<String,Object> map=intUserService.queryUserAttesta(userid);
		return map;
	}
}
