package com.zhita.controller.user;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhita.service.manage.user.IntUserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private IntUserService intUserService;

	//后台管理----用户列表(公司id，page,姓名，注册开始时间，注册结束时间，用户认证状态，银行卡认证状态，运营商认证状态)
	public Map<String, Object> queryUserList(Integer companyId,Integer page,String name,String registeTimeStart,String registeTimeEnd,String userattestationstatus,String bankattestationstatus,String operaattestationstatus){
		Map<String, Object> map=intUserService.queryUserList(companyId, page, name, registeTimeStart, registeTimeEnd, userattestationstatus, bankattestationstatus, operaattestationstatus);
		return map;
	}
	
	//后台管理---添加黑名单
	public int insertBlacklist(Integer companyId,Integer userId,String operator){
		int num=intUserService.insertBlacklist(companyId, userId, operator);
		return num;
	}
}
