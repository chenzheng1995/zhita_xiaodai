package com.zhita.service.manage.projecttimer;

public interface IntProjecttimerService {
	
	//后台管理----查询订单表     所有期限内，已逾期，已延期的订单(定时任务     控制逾期)
	public void selAllover();
	
	
	//后台管理----控制逾期等级（定时任务）
	public void upaoverclass();
	
	
	//后台管理----控制逾期超过30天，打入黑名单（定时任务）
	public void addblack();
}
