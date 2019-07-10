package com.zhita.service.manage.user;

import java.util.Map;

public interface IntUserService {
	//后台管理----用户列表(公司id，page,姓名，注册开始时间，注册结束时间，用户认证状态，银行卡认证状态，运营商认证状态)
	public Map<String, Object> queryUserList(Integer companyId,Integer page,String name,String registeTimeStart,String registeTimeEnd,String userattestationstatus,String bankattestationstatus,String operaattestationstatus);
	
	//后台管理---添加黑名单
	public int insertBlacklist(Integer companyId,Integer userId,String operator);

	public void updateScore(int score, int userId);

	public int getRiskControlPoints(int userId);
}
