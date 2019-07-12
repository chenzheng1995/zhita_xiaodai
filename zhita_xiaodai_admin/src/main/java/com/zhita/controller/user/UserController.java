package com.zhita.controller.user;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.model.manage.OrderQueryParameter;
import com.zhita.service.manage.user.IntUserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private IntUserService intUserService;

	//后台管理----用户列表(公司id，page,姓名，注册开始时间，注册结束时间，用户认证状态，银行卡认证状态，运营商认证状态)
	@ResponseBody
	@RequestMapping("/queryUserList")
	public Map<String, Object> queryUserList(Integer companyId,Integer page,String name,String registeTimeStart,String registeTimeEnd,String userattestationstatus,String bankattestationstatus,String operaattestationstatus){
		Map<String, Object> map=intUserService.queryUserList(companyId, page, name, registeTimeStart, registeTimeEnd, userattestationstatus, bankattestationstatus, operaattestationstatus);
		return map;
	}
	
	//后台管理---添加黑名单
	@ResponseBody
	@RequestMapping("/insertBlacklist")
	public int insertBlacklist(Integer companyId,Integer userId,String operator){
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

	//后台管理----用户订单 查询（公司id，订单号，订单开始时间，订单结束时间     渠道id  用户id）
	@ResponseBody
	@RequestMapping("/queryAllOrdersByUserid")
  	public Map<String,Object> queryAllOrdersByUserid(OrderQueryParameter orderQueryParameter){
  		Map<String,Object> map=intUserService.queryAllOrdersByUserid(orderQueryParameter);
  		return map;
  	}
  	
	//后台管理----黑名单用户订单 查询（公司id    page）
	@ResponseBody
	@RequestMapping("/queryAllOrdersByUserid1")
  	public Map<String,Object> queryAllOrdersByUserid1(Integer companyId,Integer page){
  		Map<String,Object> map=intUserService.queryAllOrdersByUserid1(companyId, page);
  		return map;
  	}
}
