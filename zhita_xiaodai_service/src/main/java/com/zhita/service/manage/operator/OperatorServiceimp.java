package com.zhita.service.manage.operator;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhita.dao.manage.OperatorMapper;

@Service
public class OperatorServiceimp implements OperatorService{
	
	@Autowired 
	OperatorMapper operatorMapper;

	@Override
	public int updateSearch_id(String search_id, String userId) {
		 int number = operatorMapper.updateSearch_id(search_id,userId);
		return number;
	}

	@Override
	public int setredIdAndPhone(String reqId, int userId, String phone) {
		int number = operatorMapper.setredIdAndPhone(reqId,userId,phone);
		return number;
	}

	@Override
	public Map<String, Object> getOperator(int userId) {
		Map<String, Object> operator = operatorMapper.getOperator(userId);
		return operator;
	}

	@Override
	public int updateOperatorJson(String url,int userId) {
	int	number = operatorMapper.updateOperatorJson(url,userId);
		return number;
	}

	@Override
	public int getuserId(int userId) {
		int num = operatorMapper.getuserId(userId);
		return num;
	}

	@Override
	public void updatereqId(int userId, String reqId) {
		operatorMapper.updatereqId(userId,reqId);
		
	}

}
