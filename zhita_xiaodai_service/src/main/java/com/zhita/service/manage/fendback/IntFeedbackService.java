package com.zhita.service.manage.fendback;

import java.util.Map;

public interface IntFeedbackService {
	
	//后台管理----查询功能
	public Map<String,Object> queryAll(Integer page);
	
	//后台管理----修改解决状态，添加回复内容
	public int upastatus(String replycontent,Integer id);

}
