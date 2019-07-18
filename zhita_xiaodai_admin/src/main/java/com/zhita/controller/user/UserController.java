package com.zhita.controller.user;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.model.manage.OrderQueryParameter;
import com.zhita.model.manage.UserLikeParameter;
import com.zhita.service.manage.user.IntUserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private IntUserService intUserService;

	//后台管理----用户列表(公司id，page,姓名，手机号，注册开始时间，注册结束时间，用户认证状态，银行卡认证状态，运营商认证状态)
	@ResponseBody
	@RequestMapping("/queryUserList")
	public Map<String, Object> queryUserList(UserLikeParameter userLikeParameter){
		Map<String, Object> map=intUserService.queryUserList(userLikeParameter);
		return map;
	}
	
	//后台管理---添加黑名单
	@ResponseBody
	@RequestMapping("/insertBlacklist")
	public int insertBlacklist(Integer companyId,Integer userId,Integer operator){
		int num=intUserService.insertBlacklist(companyId, userId, operator);
		return num;
	}
	
	//后台管理---解除黑名单
	@ResponseBody
	@RequestMapping("/removeBlacklist")
	public int removeBlacklist(Integer companyId,Integer userId){
		int num=intUserService.removeBlacklist(companyId, userId);
		return num;
	}

	//后台管理----用户认证信息——借款信息（公司id，page,订单号，姓名，手机号，注册开始时间，注册结束时间     渠道id  用户id）
	@ResponseBody
	@RequestMapping("/queryAllOrdersByUserid")
  	public Map<String,Object> queryAllOrdersByUserid(OrderQueryParameter orderQueryParameter){
  		Map<String,Object> map=intUserService.queryAllOrdersByUserid(orderQueryParameter);
  		return map;
  	}
  	
	//后台管理----黑名单用户订单 查询（公司id  page，姓名，手机号，身份证号，注册开始时间，注册结束时间     渠道id）——黑名单用户  机审判定黑名单
	@ResponseBody
	@RequestMapping("/queryAllOrdersByUserid1")
  	public Map<String,Object> queryAllOrdersByUserid1(OrderQueryParameter orderQueryParameter){
  		Map<String,Object> map=intUserService.queryAllOrdersByUserid1(orderQueryParameter);
  		return map;
  	}
	
	//后台管理---用户认证信息
	@ResponseBody
	@RequestMapping("/queryUserAttesta")
	public Map<String,Object> queryUserAttesta(Integer userid){
		Map<String,Object> map=intUserService.queryUserAttesta(userid);
		return map;
	}
}
