package com.zhita.service.manage.homepagetongji;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhita.model.manage.Orderdetails;

public interface IntHomepagetongjiService {
	//首页统计
	public Map<String, Object> queryAll(Integer companyId);
	
	//回收率报表
	public Map<String, Object> recoveryStatement(Integer companyId,Integer page,String shouldrepayStartTime,String shouldrepayEndTime);
	
	/**
	 * 回收率报表
	 * 用于导出excel的查询结果
	 * 
	 * @param queryJson
	 * @return
	 * @throws IOException
	 */
	public void exportRecoveryStatement(Integer companyId,String shouldrepayStartTime,String shouldrepayEndTime, HttpServletRequest request, HttpServletResponse response)
			throws IOException;
	
	List<Orderdetails> test();
}
