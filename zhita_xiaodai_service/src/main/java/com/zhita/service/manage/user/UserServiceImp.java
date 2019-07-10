package com.zhita.service.manage.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhita.dao.manage.UserMapper;
import com.zhita.model.manage.User;
import com.zhita.util.ListPageUtil;
import com.zhita.util.PageUtil;
import com.zhita.util.Timestamps;

@Service
public class UserServiceImp implements IntUserService{
	@Autowired
	private UserMapper userMapper;
	
	//后台管理----用户列表(公司id，page,姓名，注册开始时间，注册结束时间，用户认证状态，银行卡认证状态，运营商认证状态)
	public Map<String, Object> queryUserList(Integer companyId,Integer page,String name,String registeTimeStart,String registeTimeEnd,String userattestationstatus,String bankattestationstatus,String operaattestationstatus){
		List<User> list=new ArrayList<>();
		List<User> listto=new ArrayList<>();
		PageUtil pageUtil=null;
		list=userMapper.queryUserList(companyId, name, registeTimeStart, registeTimeEnd, userattestationstatus, bankattestationstatus, operaattestationstatus);
		
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setRegistetime(Timestamps.stampToDate(list.get(i).getRegistetime()));
		}
    	
    	if(list!=null && !list.isEmpty()){
    		ListPageUtil listPageUtil=new ListPageUtil(list,page,10);
    		listto.addAll(listPageUtil.getData());
    		
    		pageUtil=new PageUtil(listPageUtil.getCurrentPage(), listPageUtil.getPageSize(),listPageUtil.getTotalCount());
    	}
    	
		HashMap<String,Object> map=new HashMap<>();
		map.put("userlist", listto);
		map.put("pageutil", pageUtil);
    	return map;
	}
	
	//后台管理---添加黑名单
	public int insertBlacklist(Integer companyId,Integer userId,String operator){
		userMapper.upaBlacklistStatus(userId);
		String operationTime=System.currentTimeMillis()+"";//获取当前时间戳
		int num=userMapper.addBlacklist(companyId, userId, operator, operationTime);
		return num;
	}

	@Override
	public void updateScore(int score,int userId) {
		userMapper.updateScore(score,userId);
		
	}

	@Override
	public int getRiskControlPoints(int userId) {
		 int riskControlPoints = userMapper.getRiskControlPoints(userId);
		return riskControlPoints;
	}
}
